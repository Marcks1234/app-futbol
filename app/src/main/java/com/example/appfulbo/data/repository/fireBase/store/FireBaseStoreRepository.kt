package com.example.appfulbo.data.repository.fireBase.store

import com.example.appfulbo.data.repository.fireBase.store.firestoreData.FireStoreConstants
import com.example.appfulbo.data.repository.fireBase.store.firestoreData.FireStoreConstants.Collections
import com.example.appfulbo.model.Place
import com.example.appfulbo.model.ReservationDetails
import com.example.appfulbo.model.Reserve
import com.example.appfulbo.model.SoccerField
import com.example.appfulbo.model.User
import com.example.appfulbo.ui.states.GeneralState
import com.example.appfulbo.ui.states.StateSoccerField
import com.example.appfulbo.ui.states.UserState
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Singleton
class FireBaseStoreRepository @Inject constructor(
    private val db: FirebaseFirestore
) {

    /*#### USUARIOS ####*/
    /**
     * [userId] es el id de usuario actual
     * - Obtener datos del usuario mediante el id del usuario
     */
    suspend fun getDataUserId(userId: String): User {
        val query = db.collection(Collections.USERS)
            .document(userId)
            .get().await()
        return query.toObject(User::class.java) ?: User()
    }

    /**
     * [uid] es el id de usuario actual
     * - true: es administrador
     * - false: es usuario normal
     */
    suspend fun isAdmin(uid: String): Boolean {
        val query = db.collection(Collections.USERS)
            .document(uid)
            .get().await()
        return query.getBoolean("admin") == true
    }

    suspend fun checkEmail(email: String): UserState {
        return try {
            val result = db.collection(Collections.USERS)
                .whereEqualTo(FireStoreConstants.Fields.EMAIL, email).get().await()
            if (!result.isEmpty) {
                UserState.Success
            } else {
                UserState.Error("Email no registrado en la app")
            }
        } catch (e: Exception) {
            UserState.Error(e.toString())
        }
    }

    suspend fun getUsers(): List<User> {
        return try {
            val snapshot = db.collection(Collections.USERS)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(User::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun isUserNameExist(userName: String): Boolean {
        return try {
            val snapshot = db.collection(Collections.USERS)
                .whereEqualTo("userName", userName)
                .get().await()
            !snapshot.isEmpty
        } catch (e: Exception) {
            false
        }
    }


    /*#### LOCALES ####*/
    /**
     * [adminId] es el identeificar de usuario admin
     * - Obtener datos del local
     * - @return [com.example.appfulbo.model.Place]
     */
    suspend fun getDataPlace(adminId: String): Place {
        val query =
            db.collection(Collections.PLACES)
                .whereEqualTo(FireStoreConstants.Fields.ADMIN_ID, adminId)
                .limit(1)
                .get().await()
        val doc = query.documents.firstOrNull()
        val place = doc?.toObject(Place::class.java)?.copy(id = doc.id)

        return place ?: Place()
    }

    /**
     *  [adminId] es el identificar de usuario admin
     *  - Obtener el identificador del local asignado a un admin
     */
    suspend fun getPlaceAdminId(adminId: String): String? {
        val queryPlace = db.collection(Collections.PLACES)
            .whereEqualTo(FireStoreConstants.Fields.ADMIN_ID, adminId)
            .limit(1)
            .get().await()
        return queryPlace.documents.firstOrNull()?.id
    }

    suspend fun savePlace(place: Place, adminId: String): GeneralState<Unit> = withContext(Dispatchers.IO) {
        try {
            val existingPlaceId = getPlaceAdminId(adminId)
            val placeId = existingPlaceId ?: db.collection(Collections.PLACES).document().id
            val placeToSave = place.copy(id = placeId, adminId = adminId)

            db.collection(Collections.PLACES)
                .document(placeId)
                .set(placeToSave)
                .await()
            GeneralState.Success(Unit)
        } catch (e: Exception) {
            GeneralState.Error(e.message ?: "Error al guardar el local")
        }
    }

    suspend fun getPlaceWithId(placeId: String): Place {
        val queryPlace = db.collection(Collections.PLACES)
            .document(placeId)
            .get().await();
        return queryPlace.toObject<Place>(Place::class.java)!!
    }

    // ESCRITURA PARA DATA CLASES
    suspend fun addDataUser(uid: String, newUser: User): UserState {
        return try {
            db.collection(Collections.USERS)
                .document(uid)
                .set(newUser).await()
            UserState.Success
        } catch (e: Exception) {
            UserState.Error(e.message ?: "error desconocido")
        }
    }

    suspend fun addDataSoccerField(adminId: String, newSoccerField: SoccerField): StateSoccerField {
        return withContext(Dispatchers.IO) {
            try {
                val placeId = getPlaceAdminId(adminId)
                if (placeId == null) return@withContext StateSoccerField.Error("Administrador sin local")

                newSoccerField.apply { this.placeId = placeId }
                var collectionReferenceSoccorFields =
                    db.collection(Collections.PLACES)
                        .document(placeId)
                        .collection(Collections.SOCCER_FIELD)

                collectionReferenceSoccorFields.add(newSoccerField).await()

                val snapshot = collectionReferenceSoccorFields.get().await()

                val numberOfFields = snapshot.size()

                val typesSoccerField = snapshot.documents.mapNotNull {
                    it.get(FireStoreConstants.Fields.TYPE_SOCCER_FIELD)
                }.toSet().toList()

                db.collection(Collections.PLACES)
                    .document(placeId)
                    .update(
                        mapOf(
                            FireStoreConstants.Fields.NUMBER_OF_FIELDS to numberOfFields,
                            FireStoreConstants.Fields.FIELD_TYPE to typesSoccerField
                        )
                    )
                StateSoccerField.Success()
            } catch (e: Exception) {
                StateSoccerField.Error(e.message ?: "error desconocido")
            }
        }
    }

    // LECTURA DE DATOS
    /**
     * Obtener las canchas de un administrador en tiempo real
     */
    fun getSoccerFieldsWithAdminId(adminId: String): Flow<StateSoccerField> = callbackFlow {
        try {
            val placeId = getPlaceAdminId(adminId)
            if (placeId == null) {
                trySend(StateSoccerField.Error("Administrador sin local"))
                close()
                return@callbackFlow
            }
            val registration: ListenerRegistration =
                db.collection(Collections.PLACES)
                    .document(placeId)
                    .collection(Collections.SOCCER_FIELD)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            trySend(StateSoccerField.Error(error.message ?: "Error desconocido"))
                            close()
                            return@addSnapshotListener
                        }
                        if (snapshot != null && !snapshot.isEmpty) {
                            val soccerFields = snapshot.documents.mapNotNull {
                                it.toObject(SoccerField::class.java)?.copy(id = it.id)
                            }
                            trySend(StateSoccerField.Success(soccerFields))
                        } else {
                            trySend(StateSoccerField.Success(emptyList()))
                        }
                    }
            awaitClose { registration.remove() }
        } catch (e: Exception) {
            trySend(StateSoccerField.Error(e.message ?: "Error desconocido"))
            close()
        }
    }

    /**
     * Obtener una cancha deacuedo al [placeid] y [soccerFieldId]
     */
    suspend fun getSoccerFieldWithId(placeId: String, soccerFieldId: String): SoccerField {
        val quearySoccerField = db.collection(Collections.PLACES)
            .document(placeId).collection(Collections.SOCCER_FIELD)
            .document(soccerFieldId).get().await()
        return quearySoccerField.toObject<SoccerField>(SoccerField::class.java)!!;
    }

    suspend fun getAllSocerField(adminId: String): List<SoccerField> {
        return try {
            val placeId = getPlaceAdminId(adminId)
            if (placeId == null) {
                return emptyList()
            }
            val dataSoccerFields = db.collection(Collections.PLACES)
                .document(placeId).collection(Collections.SOCCER_FIELD).get().await()

            dataSoccerFields.documents.mapNotNull { doc ->
                doc.toObject(SoccerField::class.java)?.copy(id = doc.id)
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Obtener un dato tipo [Place]
     */
    suspend fun getPlaces(): List<Place> = withContext(Dispatchers.IO) {
        val query = db.collection(Collections.PLACES).get().await()
        return@withContext query.documents.mapNotNull { doc ->
            doc?.toObject(Place::class.java)?.copy(id = doc.id)
        }
    }

    fun getSoccerFields(idPlace: String): Flow<StateSoccerField> = callbackFlow {
        try {
            val registration: ListenerRegistration =
                db.collection(Collections.PLACES).document(idPlace)
                    .collection(Collections.SOCCER_FIELD)
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            trySend(
                                StateSoccerField.Error(error.message ?: "Error en obtener datos")
                            )
                            close()
                            return@addSnapshotListener
                        }
                        if (snapshot != null && !snapshot.isEmpty) {
                            val soccerFiels = snapshot.documents.mapNotNull {
                                it.toObject(SoccerField::class.java)?.copy(id = it.id)
                            }
                            trySend(StateSoccerField.Success(soccerFiels))
                        } else {
                            trySend(StateSoccerField.Empty)
                        }
                    }
            awaitClose { registration.remove() }
        } catch (e: Exception) {
            trySend(StateSoccerField.Error(e.message ?: "Error en la conexión a fire store"))
            close()
        }
    }

    /**
     * Actualizar una cancha
     */
    suspend fun updateSoccerField(soccerField: SoccerField, adminId: String) {
        db.collection(Collections.PLACES)
            .document(getPlaceAdminId(adminId)!!)
            .collection(Collections.SOCCER_FIELD)
            .document(soccerField.id).set(soccerField).await()
    }


    /**
     * Obtener una escucha que retorna siempre una Lista de las reservas
     */
    fun getReserveWithSoccerIdAndDate(
        soccerFieldId: String, date: String, onChange: (List<Reserve>) -> Unit
    ): ListenerRegistration {
        return db.collection(Collections.RESERVES)
            .whereEqualTo(FireStoreConstants.Fields.SOCCER_FIELD_ID, soccerFieldId)
            .whereEqualTo(FireStoreConstants.Fields.DATE, date)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    onChange(emptyList())
                    return@addSnapshotListener
                }
                val reserves = snapshot.documents.mapNotNull { it.toObject(Reserve::class.java) }
                onChange(reserves)
            }
    }

    fun getReservesWithUserId(userId: String): Flow<List<ReservationDetails>> = callbackFlow {
        val reservesReference = db.collection(Collections.RESERVES)
        val campoId =
            if (isAdmin(userId)) FireStoreConstants.Fields.ADMIN_ID else FireStoreConstants.Fields.USER_ID

        val listener = reservesReference
            .whereEqualTo(campoId, userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null && !snapshot.isEmpty) {
                    val tasks = snapshot.documents.map { doc ->
                        val reserve = doc.toObject(Reserve::class.java) ?: return@map null

                        val userTask = if (reserve.userId.isNotBlank()) {
                            db.collection(Collections.USERS)
                                .document(reserve.userId)
                                .get()
                        } else {
                            Tasks.forResult(null)
                        }

                        val soccerTask = db.collection(Collections.PLACES)
                            .document(reserve.placeId)
                            .collection(Collections.SOCCER_FIELD)
                            .document(reserve.soccerFieldId).get()

                        Tasks.whenAllSuccess<DocumentSnapshot>(userTask, soccerTask)
                            .continueWith { task ->
                                val userSnap = task.result[0] as? DocumentSnapshot
                                val soccerSnap = task.result[1] as DocumentSnapshot

                                val user = userSnap?.toObject(User::class.java) ?: User()
                                val soccer = soccerSnap.toObject(SoccerField::class.java) ?: SoccerField()
                                ReservationDetails(
                                    reserva = reserve,
                                    user = user,
                                    soccerField = soccer
                                )
                            }
                    }.filterNotNull()

                    Tasks.whenAllSuccess<ReservationDetails>(tasks)
                        .addOnSuccessListener { results ->
                            trySend(results)
                        }.addOnFailureListener {
                            trySend(emptyList())
                        }
                } else {
                    trySend(emptyList())
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun addReserve(reserve: Reserve): GeneralState<Boolean> {
        return try {
            db.collection(Collections.RESERVES).add(reserve).await()
            GeneralState.Success(true)
        } catch (e: Exception) {
            GeneralState.Error(e.toString())
        }
    }
}




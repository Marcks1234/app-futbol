package com.example.appfulbo.data.repository.fireBase

import android.util.Log
import com.example.appfulbo.data.repository.fireBase.authentication.FireBaseAuthRepository
import com.example.appfulbo.data.repository.fireBase.store.FireBaseStoreRepository
import com.example.appfulbo.model.Place
import com.example.appfulbo.model.SoccerField
import com.example.appfulbo.model.User
import com.example.appfulbo.ui.states.GeneralState
import com.example.appfulbo.ui.states.StateSoccerField
import com.example.appfulbo.ui.states.UserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.withContext

@Singleton
class FireBaseStoreAuthRepository @Inject constructor(
    private val auth: FireBaseAuthRepository,
    private val db: FireBaseStoreRepository
) {

    suspend fun registerUser(email: String, password: String, newUser: User): UserState =
        withContext(Dispatchers.IO) {
            try {
                val nameExist = db.isUserNameExist(newUser.userName)
                if (nameExist) {
                    return@withContext UserState.Error("El nombre de usuario ya está en uso")
                }
                val user = auth.createUserAuth(email, password)
                if (user != null) {
                    return@withContext db.addDataUser(user.uid, newUser.copy(id = user.uid))
                } else {
                    return@withContext UserState.Error("No se pudo obtener UID del usuario")
                }
            } catch (e: Exception) {
                UserState.Error(e.message ?: "Error desconocido al registrar")
            }
        }


    suspend fun getUserType(): TypeUser = withContext(Dispatchers.IO) {
        val uid = auth.currentUid()
        Log.i("getUserType", "UID actual: $uid")
        if (uid == null) return@withContext TypeUser.NEW_USER
        val isAdmin = db.isAdmin(uid)
        Log.i("get isAdmin", "UID actual: $isAdmin")
        return@withContext if (isAdmin) TypeUser.ADMIN else TypeUser.USER
    }

    suspend fun getUser(): User = withContext(Dispatchers.IO) {
        val uid = auth.currentUid()
        if (uid == null) User() else db.getDataUserId(uid)
    }

    suspend fun registerSoccerField(newSoccerField: SoccerField): StateSoccerField {
        val uid = auth.currentUid()
        return if (uid == null) StateSoccerField.Empty else db.addDataSoccerField(
            uid,
            newSoccerField
        )
    }

    fun getRealTimeSoccerFields(): Flow<StateSoccerField> {
        val uid = auth.currentUid()
        return if (uid == null) {
            flowOf(StateSoccerField.Empty)
        } else {
            db.getSoccerFieldsWithAdminId(uid)
        }
    }

    suspend fun getPlace(): Place {
        val uid = auth.currentUid()
        return if (uid == null) {
            Place()
        } else {
            db.getDataPlace(uid)
        }
    }

    suspend fun savePlace(place: Place): GeneralState<Unit> {
        val uid = auth.currentUid()
        return if (uid == null) {
            GeneralState.Error("Usuario no autenticado")
        } else {
            db.savePlace(place, uid)
        }
    }

    suspend fun updateSoccerField(soccerField: SoccerField) = withContext(Dispatchers.IO) {
        val uid = auth.currentUid()
        if (uid != null) {
            db.updateSoccerField(soccerField, uid)
        }
    }

    suspend fun sendEmail(email: String): UserState = withContext(Dispatchers.IO) {
        val userState = db.checkEmail(email)
        if (userState is UserState.Success) {
            try {
                auth.sendEmail(email)
                UserState.Success
            } catch (e: Exception) {
                UserState.Error("Error al enviar el correo: ${e.message}")
            }
        } else {
            userState
        }
    }

    suspend fun getAllSoccerField() : List<SoccerField>{
        val uid = auth.currentUid()
        return if(uid == null) return  emptyList() else db.getAllSocerField(uid)
    }

    suspend fun updateUserProfileImage(newImageUrl: String) {
        val uid = auth.currentUid()
        if (uid != null) {
            val currentUser = db.getDataUserId(uid)
            val updatedUser = currentUser.copy(profileImageURL = newImageUrl)
            db.addDataUser(uid, updatedUser)
        }
    }

}

enum class TypeUser {
    NEW_USER,
    USER,
    ADMIN
}

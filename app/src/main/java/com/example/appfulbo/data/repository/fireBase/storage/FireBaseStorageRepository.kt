package com.example.appfulbo.data.repository.fireBase.storage

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await
import java.util.UUID

@Singleton
class FireBaseStorageRepository @Inject constructor(
    private val storage: FirebaseStorage
) {

    suspend fun uploadProfileImage(uri: Uri, userId: String): String {
        return try {
            val ref = storage.reference
                .child("users_profile_images/$userId/${UUID.randomUUID()}.jpg")

            ref.putFile(uri).await()
            ref.downloadUrl.await().toString()
        } catch (e: Exception) {
            throw Exception("Error al subir imagen: ${e.message}")
        }
    }
}

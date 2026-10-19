package com.example.appfulbo.data.repository.fireBase.authentication

import com.example.appfulbo.ui.states.UserState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Singleton
class FireBaseAuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    suspend fun loginUserAuth(email: String, password: String): UserState {
        return withContext(Dispatchers.IO) {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                UserState.Success
            } catch (e: Exception) {
                UserState.Error(e.message ?: "error desconocido")
            }
        }
    }

    suspend fun createUserAuth(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }

    fun currentUid(): String? {
        return auth.currentUser?.uid
    }

    fun logout() {
        auth.signOut()
    }

    suspend fun sendEmail(email: String) {
        auth.sendPasswordResetEmail(email).await()
    }
}

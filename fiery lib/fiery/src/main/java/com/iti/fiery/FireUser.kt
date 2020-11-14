package com.iti.fiery

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

class FireUser(
    context: Context,
    private val userCallbacks: UserCallbacks
) {
    private val auth = FirebaseAuth.getInstance()
    private val sp = SharedPrefsRepo(context)

    fun createNewUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signAdminBackIn()
                } else {
                    task.exception?.let {
                        userCallbacks.onError(it)
                    }
                }
            }
    }


    private fun signAdminBackIn() {
        //sign the new user out
        auth.signOut()
        //get admin data from secure shared prefs
        val secretEmail = sp.readString(EMAIL)!!
        val secretPassword = sp.readString(PASSWORD)!!
        //sign admin back in
        auth.signInWithEmailAndPassword(secretEmail, secretPassword)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    userCallbacks.onNewUserCreated()
                } else {
                    task.exception?.let { userCallbacks.onError(it) }
                }
            }
    }
}

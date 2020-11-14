package com.iti.fiery

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

class FirebaseAdmin(
    private val context: Context,
    private val email: String,
    private val password: String
) {
    private val auth = FirebaseAuth.getInstance()
    private val sp = SharedPrefsRepo(context)

    fun singAdminIn() {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {

                }
            }
    }

    fun signAdminOut() {
        sp.deleteSharedPreferences()
        auth.signOut()
    }

    private fun saveDataInSecureSP() {
        //save secured data in sp
        email.let { sp.saveString(EMAIL, it) }
        password.let { sp.saveString(PASSWORD, it) }
    }
}

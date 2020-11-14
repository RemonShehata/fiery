package com.example.fire_users

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.iti.fiery.EMAIL
import com.iti.fiery.PASSWORD
import com.iti.fiery.SharedPrefsRepo

class FireUser(context: Context) {
    private val auth = FirebaseAuth.getInstance()
    private val sp = SharedPrefsRepo(context)

    fun createNewUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signAdminBackIn()
                } else {

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

                } else {

                }
            }
    }
}

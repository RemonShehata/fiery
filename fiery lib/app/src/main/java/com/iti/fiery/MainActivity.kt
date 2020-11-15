package com.iti.fiery

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setclickListeners()
    }

    private fun setclickListeners() {
        logAdminIn.setOnClickListener { logAdminInClicked() }
        createNewUser.setOnClickListener { createNewUserClicked() }
        getCurrentUser.setOnClickListener { getCurrentUserClicked() }
    }

    private fun getCurrentUserClicked() {
        val email = FirebaseAuth.getInstance().currentUser?.email
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show()

    }

    private fun createNewUserClicked() {
        val email = userEmail.text.toString().trim()
        val password = userPassword.text.toString().trim()

        FireUser(this, object : UserCallbacks{
            override fun onNewUserCreated() {
                Toast.makeText(this@MainActivity, "New user Created", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exception: java.lang.Exception) {
                Log.e(TAG, exception.toString())
            }

        }).createNewUser(email, password)
    }

    private fun logAdminInClicked() {
        val email = adminEmail.text.toString()
        val password = adminPassword.text.toString()

        FirebaseAdmin(this, email, password, object : AdminCallbacks {
            override fun onLoggedin() {
                Toast.makeText(this@MainActivity, "Admin Logged in", Toast.LENGTH_LONG).show()
            }

            override fun onLoggedout() {
                TODO("Not yet implemented")
            }

            override fun onError(exception: Exception) {
                Log.e(TAG, exception.toString())
            }

        }).singAdminIn()

    }
}
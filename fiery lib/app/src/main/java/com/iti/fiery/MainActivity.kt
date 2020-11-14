package com.iti.fiery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setclickListeners()
    }

    private fun setclickListeners() {
        logAdminIn.setOnClickListener { logAdminInClicked() }
        createNewUser.setOnClickListener { createNewUserClicked() }
    }

    private fun createNewUserClicked() {
        val email = userEmail.text.toString()
        val password = userPassword.text.toString()
    }

    private fun logAdminInClicked() {
        val email = adminEmail.text.toString()
        val password = adminPassword.text.toString()
    }
}
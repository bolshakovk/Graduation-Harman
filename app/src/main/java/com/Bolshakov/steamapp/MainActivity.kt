package com.Bolshakov.steamapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.properties.Delegates

class   MainActivity : AppCompatActivity() {
    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var login: Button
    private lateinit var loginLocked: TextView
    private lateinit var attempts: TextView
    private lateinit var numberOfAttempts: TextView
    private var numberOfRemainingLoginAttempts = 3


    fun Login(view: View) {
        if (username.text.toString() == "admin"
            && password.text.toString() == "admin"
        ) {
            Toast.makeText(applicationContext, "Вход выполнен", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoggedActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(applicationContext, "Неправильные данные!", Toast.LENGTH_SHORT)
                .show()
            numberOfRemainingLoginAttempts--
        }
        attempts.visibility = View.VISIBLE
        numberOfAttempts.visibility = View.VISIBLE
        numberOfAttempts.text = numberOfRemainingLoginAttempts.toString()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        username = findViewById(R.id.edit_user)
        password = findViewById(R.id.edit_password)
        login = findViewById(R.id.button_login)
        loginLocked = findViewById(R.id.login_locked)
        attempts = findViewById(R.id.attempts)
        numberOfAttempts = findViewById(R.id.number_of_attempts)
    }
}
package com.Bolshakov.steamapp.Activities

import android.app.Activity
import android.app.Person
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.Bolshakov.steamapp.DataBase.DBManagerUsers
import com.Bolshakov.steamapp.Models.Person.login
import com.Bolshakov.steamapp.R

class RegistrationActivity : AppCompatActivity()  {
    private lateinit var personLogin : EditText
    private lateinit var  personName : EditText
    private lateinit var  personSurname : EditText
    private lateinit var  personEmail : EditText
    private lateinit var  personPassword : EditText
    private lateinit var  personPasswordRepeat : EditText
    private lateinit var  wrongText : TextView
    val imageRequestCode = 10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration_layout)
        personLogin  = findViewById(R.id.personLogin)
        personName  = findViewById(R.id.personName)
        personSurname  = findViewById(R.id.personSurname)
        personEmail  = findViewById(R.id.personEmailAddress)
        personPassword  = findViewById(R.id.personPassword)
        personPasswordRepeat  = findViewById(R.id.personPassword_repeat)
        wrongText  = findViewById(R.id.wrongPasswordText)
    }
    override fun onDestroy() {
        super.onDestroy()
        //dbManager.closeDb()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == imageRequestCode){

        }
    }


    fun onClickSave(view: android.view.View) {
        val text = "Пароль неверный!"
        val duration = Toast.LENGTH_SHORT
        var toast = Toast.makeText(applicationContext, text, duration)
        toast.setGravity(Gravity.CENTER, 0, 0)
        wrongText.text = ""
        val saveButton : Button = findViewById(R.id.saveButton_id)
        saveButton.setOnClickListener(View.OnClickListener {

            if (personPassword.text.toString().equals(personPasswordRepeat.text.toString())){
                DBManagerUsers.insertToDb(login = personLogin.text.toString(), name = personName.text.toString(), surname = personSurname.text.toString(),
                    password = personPassword.text.toString(), email = personEmail.text.toString())
                val intent = Intent(this, LoggedActivity::class.java)
                startActivity(intent)
            Log.d("tag", "password: $personPassword and repeat password $personPasswordRepeat")
            }else {
                toast = Toast.makeText(applicationContext, "Пароли не корректны!", duration)
                toast.show()
            }
        })
    }

    fun onClickAddImg(view: android.view.View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }
}
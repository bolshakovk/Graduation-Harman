package com.Bolshakov.steamapp.Activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.Bolshakov.steamapp.DataBase.DBManagerUsers
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
        wrongText.text = ""
        val saveButton : Button = findViewById(R.id.saveButton_id)
        saveButton.setOnClickListener(View.OnClickListener {
            //if (personPassword.text.equals(personPasswordRepeat.text)){
                DBManagerUsers.insertToDb(login = personLogin.text.toString(), name = personName.text.toString(), surname = personSurname.text.toString(),
                    password = personPassword.text.toString(), email = personEmail.text.toString())
                val intent = Intent(this, LoggedActivity::class.java)
                startActivity(intent)
            Log.d("tag", "password: $personPassword and repeat password $personPasswordRepeat")
            //}else {
                //wrongText.text = "Пароли не корректны"
            //}
        })
    }

    fun onClickAddImg(view: android.view.View) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, imageRequestCode)
    }
}
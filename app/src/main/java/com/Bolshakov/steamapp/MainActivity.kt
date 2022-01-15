package com.Bolshakov.steamapp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import org.w3c.dom.Text

class   MainActivity : AppCompatActivity() {
    //функция проверки на валидность, проверяется вводный текст, и сравнивается с ожидаемым
    private fun isValid(editText: EditText, expected: String): Boolean {
        val actual = editText.text.toString()
        return expected.equals(actual)
    }
    //функция визуализации ошибки, красное если все плохо
    //TODO починить что оно красится в зеленое после возврата, не красится при ничего
    private fun visualizeValidity(editText: EditText, isValid: Boolean) {
        val backgroundColor: Int = if (isValid) Color.GREEN else Color.RED
        editText.setBackgroundColor(backgroundColor)
    }

    // кнопка подключение... обратывается поле логина, если соотвествует хардкоженному "asd", то войдет
    fun connectSteam(view: View){
        val buttonConnectSteam : Button = findViewById(R.id.button_connectSteam)
        val textUserName : EditText = findViewById(R.id.text_userName)
        val textUserPassword : TextView = findViewById(R.id.text_userPassword)
        buttonConnectSteam.setOnClickListener(View.OnClickListener {
            fun checkEditText(source : EditText, expected: String) : Boolean{
                val valid = isValid(source, expected)
                visualizeValidity(source, valid)
                return valid
            }
            //TODO убрать хардкод, добавить проверку пароля
            val allTextValid: Boolean = checkEditText(textUserName, "asd")
            if (allTextValid){
        val intent = Intent(this, ConnectSteamActivity::class.java)
        startActivity(intent)
            }

        })
    }

    fun registrationSteam(view: View){
        val intent = Intent(this, RegistrationSteamActivity::class.java)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
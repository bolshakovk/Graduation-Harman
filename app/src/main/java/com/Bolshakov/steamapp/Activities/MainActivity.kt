package com.Bolshakov.steamapp.Activities

import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.Bolshakov.steamapp.DataBase.DBConfig
import com.Bolshakov.steamapp.DataBase.DBManager
import com.Bolshakov.steamapp.DataBase.Person
import com.Bolshakov.steamapp.R

class   MainActivity : AppCompatActivity() {


    //функция проверки на валидность, проверяется вводный текст, и сравнивается с ожидаемым
    private fun isValid(editText: EditText, expected: String): Boolean {
        val actual = editText.text.toString()
        return expected?.equals(actual)!!
    }
    //функция визуализации ошибки, красное если все плохо
    //TODO починить что оно красится в зеленое после возврата, не красится при ничего
    private fun visualizeValidity(editText: EditText, isValid: Boolean) {
        val backgroundColor: Int = if (isValid) Color.GREEN else Color.RED
        editText.setBackgroundColor(backgroundColor)
    }

    // кнопка подключение... обратывается поле логина, если соотвествует хардкоженному "12", то войдет
    fun connectSteam(view: View){
        val buttonConnectSteam : Button = findViewById(R.id.button_connectSteam)
        val textUserName : EditText = findViewById(R.id.text_userName)
        val textUserPassword : TextView = findViewById(R.id.text_userPassword)
        buttonConnectSteam.setOnClickListener(View.OnClickListener {
            fun checkEditText(source: EditText, expected: Cursor?) : Boolean{
                val valid = isValid(source, expected.toString())
                visualizeValidity(source, valid)
                return valid
            }
            //TODO убрать хардкод, добавить проверку пароля
            val cursor = DBManager.db?.query(DBConfig.TABLE_NAME,
                arrayOf(DBConfig.COLUMN_NAME_LOGIN),
                "login = ?",
                arrayOf(textUserName.text.toString()),
                null, null, null)

            val allTextValid: Boolean = false
            val testInt = cursor?.count
            Log.d("tag", "cursor count $testInt")
            if (testInt!! >= 1){
                val intent = Intent(this, LoggedActivity::class.java)
                startActivity(intent)
            }
                /*
            if (allTextValid){
                val intent = Intent(this, LoggedActivity::class.java)
                startActivity(intent)
            }*/

        })
    }

    fun registrationSteam(view: View){
        val buttonConnectSteam : Button = findViewById(R.id.button_registrationSteam)
        buttonConnectSteam.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        })
    }

    override fun onDestroy(){
        Log.d("tag", "sometext")
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //var testView: TextView = findViewById(R.id.TESTLOGINVIEW)
        DBManager.init(this)
        DBManager.openDb()
        DBManager.readDbData()
        //testView.text = Person.name.toString()
        Log.d("tag", "person name is ${Person.name}, person email is ${Person.email}, person login is ${Person.login} FROM MAIN ACTIVITY!!!!!")
    }

    fun onClickEdit(view: android.view.View) {
        val intent = Intent(this, EditActivity::class.java)
        startActivity(intent)
    }

}
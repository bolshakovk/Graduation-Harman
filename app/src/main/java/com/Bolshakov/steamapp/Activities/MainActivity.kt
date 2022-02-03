package com.Bolshakov.steamapp.Activities

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.Bolshakov.steamapp.API.TwitchAPI
import com.Bolshakov.steamapp.DataBase.*
import com.Bolshakov.steamapp.Models.Game
import com.Bolshakov.steamapp.Models.Person
import com.Bolshakov.steamapp.R
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.IGDBWrapper.apiProtoRequest
import com.api.igdb.request.TwitchAuthenticator
import com.api.igdb.utils.TwitchToken
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class   MainActivity : AppCompatActivity() {
    //функция проверки на валидность, проверяется вводный текст, и сравнивается с ожидаемым
    private fun isValid(editText: EditText, expected: String): Boolean {
        val actual = editText.text.toString()
        return expected.equals(actual)
    }
    //функция визуализации ошибки, красное если все плохо
    private fun visualizeValidity(editText: EditText, isValid: Boolean) {
        val backgroundColor: Int = if (isValid) Color.GREEN else Color.RED
        editText.setBackgroundColor(backgroundColor)
    }
    //хеш пароля
    private fun hashMe(password: String): String? {
        var password = password
        try {
            val md: MessageDigest = MessageDigest.getInstance("SHA-1") //could also be MD5, SHA-256 etc.
            md.reset()
            md.update(password.toByteArray(charset("UTF-8")))
            val resultByte: ByteArray = md.digest()
            password = String.format("%01x", BigInteger(1, resultByte))
        } catch (e: NoSuchAlgorithmException) {
            Log.d("EXEPTION", e.toString())
        } catch (ex: UnsupportedEncodingException) {
            Log.d("EXEPTION", ex.toString())
        }
        return password
    }

    // кнопка подключение...
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
            val request = textUserName.text.toString()
            val isOK = DBManagerUsers.readDbData(request)
            DBManagerUsers.readDbData(request)
            Log.d("tag", "person name is ${Person.name}, person email is ${Person.email}, person login is ${Person.login}, person id is ${Person.id}")

            val testInt = DBManagerUsers.readDbData(request).size

            val text = "Пароль неверный!"
            val duration = Toast.LENGTH_SHORT
            var toast = Toast.makeText(applicationContext, text, duration)
            toast.setGravity(Gravity.CENTER, 0, 0)



            if (testInt >= 1){
                Log.d("hash: ", "without hash: ${textUserPassword.text.toString()} " +
                        "with hash ${hashMe(textUserPassword.text.toString())}" +
                        "person without hash: ${Person.password} " +
                        "person with hash: ${hashMe(Person.password)} ")
                if (hashMe(textUserPassword.text.toString()).equals((Person.password))){
                    val intent = Intent(this, LoggedActivity::class.java)
                    intent.putExtra("login", request)
                    startActivity(intent)
                }else if (hashMe(textUserPassword.text.toString()).equals(""))
                else{
                    toast = Toast.makeText(applicationContext, "Пароль не корректен!", duration)
                    toast.show()
                }
            }else {
                toast = Toast.makeText(applicationContext, "Логин не корректен!", duration)
                toast.show()
            }
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
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DBManagerUsers.init(this)
        DBManagerUsers.openDb()

        //считать всю бд дабы не крашилось от лейт инита
        DBManagerUsers.readDbData()

        //parse(DBManagerUsers.testRawQuery("misha").toString().drop(10))
        //parse(DBManagerUsers.testRawQuery("alex").toString().drop(10))

    }

    fun parse(array : String): String {

        var i = 0
        var m : Array<String> = arrayOf("")
        var subString = ""
        var strangeArray : ArrayList<String> = ArrayList()
        while (i<array.length - 1){
            i++
            if(array[i] == ','||array[i] ==']' || array[i] == '(' || array[i] == ')' || array[i] == '['){

                m + subString
                Log.d("ttt", subString)
                subString = ""
                continue
            }
            for (item in strangeArray){
                Log.d("aaa", item)
            }
            subString += array[i]
        }
        Log.d("ttt", subString)
        return subString
    }
    fun onClickEdit(view: android.view.View) {
        val intent = Intent(this, EditActivity::class.java)
        startActivity(intent)
    }
}

package com.Bolshakov.steamapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.Bolshakov.steamapp.R
import android.content.ContentValues
import android.widget.Toast
import com.Bolshakov.steamapp.DataBase.DBConfigUsers
import com.Bolshakov.steamapp.DataBase.DBManagerUsers
import java.io.UnsupportedEncodingException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class EditActivity : AppCompatActivity() {
    private lateinit var saveButton : Button
    private lateinit var personLoginEdit: EditText
    private lateinit var personMailEdit: EditText
    private lateinit var personPasswordtEdit: EditText
    private lateinit var personPasswordRepeatEdit: EditText

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        personLoginEdit= findViewById(R.id.personLoginEdit)
        saveButton = findViewById(R.id.saveButton)
        personMailEdit = findViewById(R.id.personMailEdit)
        personPasswordtEdit = findViewById(R.id.personPasswordtEdit)
        personPasswordRepeatEdit = findViewById(R.id.personPasswordRepeatEdit)
        val text = "Пароли не совпадают!"
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(applicationContext, text, duration)



        saveButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoggedActivity::class.java)
            Log.d("tag", "${personLoginEdit.text}")
            val values = ContentValues()
            if (personPasswordtEdit.text.toString().equals(personPasswordRepeatEdit.text.toString())){
                values.put(DBConfigUsers.COLUMN_NAME_EMAIL, personMailEdit.text.toString())
                values.put(DBConfigUsers.COLUMN_NAME_PASSWORD, hashMe(personPasswordtEdit.text.toString()))
                values.put(DBConfigUsers.COLUMN_NAME_LOGIN, personLoginEdit.text.toString())

                DBManagerUsers.updateDbDataByLoginWithPass(values, personPasswordtEdit.text.toString())
                startActivity(intent)
            }else{
                toast.show()
            }

        })
    }
}
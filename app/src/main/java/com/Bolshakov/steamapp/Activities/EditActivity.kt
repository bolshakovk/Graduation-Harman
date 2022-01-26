package com.Bolshakov.steamapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.Bolshakov.steamapp.Models.Person
import com.Bolshakov.steamapp.R

class EditActivity : AppCompatActivity() {
    private lateinit var saveButton : Button
    private lateinit var personLoginEdit: EditText
    private lateinit var personMailEdit: EditText
    private lateinit var personPasswordtEdit: EditText
    private lateinit var personPasswordRepeatEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        personLoginEdit= findViewById(R.id.personLoginEdit)
        saveButton = findViewById(R.id.saveButton)
        personMailEdit = findViewById(R.id.personMailEdit)
        personPasswordtEdit = findViewById(R.id.personPasswordtEdit)
        personPasswordRepeatEdit = findViewById(R.id.personPasswordRepeatEdit)
        Person.login = personLoginEdit.text.toString()
        Person.email = personMailEdit.text.toString()
        Person.password = personPasswordtEdit.text.toString()
        Log.d("tag", "")
        saveButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoggedActivity::class.java)
            startActivity(intent)
        })
    }
}
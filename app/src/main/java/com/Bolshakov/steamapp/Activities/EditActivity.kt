package com.Bolshakov.steamapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.Bolshakov.steamapp.R

class EditActivity : AppCompatActivity() {
    private lateinit var saveButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        saveButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoggedActivity::class.java)
            startActivity(intent)
        })
    }
}
package com.Bolshakov.steamapp

import android.app.Activity
import android.os.Bundle

class LoggedActivity : Activity() {
    @Override
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.logged_activity)
    }
}
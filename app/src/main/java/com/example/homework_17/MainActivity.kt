package com.example.homework_17

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.homework_17.session.SessionManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.initialize(applicationContext)
        setContentView(R.layout.activity_main)
    }
}
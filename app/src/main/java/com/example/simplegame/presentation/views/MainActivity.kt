package com.example.simplegame.presentation.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.simplegame.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_layout, GameWindow())
            .commit()
    }


}

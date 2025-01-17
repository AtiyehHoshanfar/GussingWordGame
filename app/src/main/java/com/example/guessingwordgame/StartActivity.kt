package com.example.guessingwordgame

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {


    class StartActivity : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_start)

            val startButton = findViewById<FrameLayout>(R.id.container_start_button)
            startButton.setOnClickListener {

                val intent = Intent(this, PartsActivity::class.java)
                startActivity(intent)
            }
        }
    }

}
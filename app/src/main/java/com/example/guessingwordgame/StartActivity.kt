package com.example.guessingwordgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.guessingwordgame.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_start)
        val startButton: Button = findViewById(R.id.container_start_button)
        startButton.setOnClickListener {
            val intent = Intent(this, PartsActivity::class.java)
            startActivity(intent)
        }
    }
}
package com.example.guessingwordgame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.guessingwordgame.databinding.ActivityPartsBinding


class PartsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPartsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_parts)


        val easyButton: Button = findViewById(R.id.easy_button)
        val mediumButton: Button = findViewById(R.id.medium_button)
        val difficultButton: Button = findViewById(R.id.difficult_button)

        easyButton.setOnClickListener {
            val intent = Intent(this, EasyLevelsActivity::class.java)
            startActivity(intent)
        }

        mediumButton.setOnClickListener {
            val intent = Intent(this, MediumLevelsActivity::class.java)
            startActivity(intent)
        }

        difficultButton.setOnClickListener {
            val intent = Intent(this, DifficultLevelsActivity::class.java)
            startActivity(intent)
        }
    }
}
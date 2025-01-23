package com.example.guessingwordgame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


class MediumLevelsActivity : AppCompatActivity() {

    // Define level states
    private val levelCount = 26 // Number of levels in Medium mode
    private val levelProgressKey = "MEDIUM_LEVEL_PROGRESS"
    private lateinit var levelFrames: List<FrameLayout>
    private lateinit var levelBackgrounds: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medium_levels)

        // Initialize frame layouts for levels
        levelFrames = listOf(
            findViewById(R.id.container_frame14),
            findViewById(R.id.container_frame15),
            findViewById(R.id.container_frame16),
            findViewById(R.id.container_frame17),
            findViewById(R.id.container_frame18),
            findViewById(R.id.container_frame19),
            findViewById(R.id.container_frame20),
            findViewById(R.id.container_frame21),
            findViewById(R.id.container_frame22),
            findViewById(R.id.container_frame23),
            findViewById(R.id.container_frame24),
            findViewById(R.id.container_frame25),
            findViewById(R.id.container_frame26)
        )

        levelBackgrounds = levelFrames.map { it.findViewById<ImageView>(R.id.image_vector14) }

        // Load level progress
        val levelProgress = getLevelProgress()

        // Set up frames
        for (i in 0 until levelCount) {
            if (i == 0 || i <= levelProgress) {
                // Level is unlocked
                levelFrames[i].setOnClickListener {
                    startGameScreen(i + 14) // Pass the level number (14 is the starting level)
                }

                if (i > 0 && i <= levelProgress) {
                    // Mark as passed for levels other than the first
                    levelBackgrounds[i].setImageResource(R.drawable.vectorpassed)
                } else {
                    // Use a default unlocked background for the first level
                    levelBackgrounds[i].setImageResource(R.drawable.vector)
                }
            } else {
                // Level is locked
                levelFrames[i].alpha = 0.5f
                levelFrames[i].isClickable = false
            }
        }
    }

    private fun startGameScreen(level: Int) {
        val intent = Intent(this, GameScreenActivity::class.java).apply {
            putExtra("LEVEL", level)
        }
        startActivity(intent)
    }

    private fun getLevelProgress(): Int {
        val sharedPreferences = getSharedPreferences("GameProgress", Context.MODE_PRIVATE)
        return sharedPreferences.getInt(levelProgressKey, 0)
    }

    fun updateLevelProgress(level: Int) {
        val sharedPreferences = getSharedPreferences("GameProgress", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(levelProgressKey, level)
        editor.apply()
    }
}


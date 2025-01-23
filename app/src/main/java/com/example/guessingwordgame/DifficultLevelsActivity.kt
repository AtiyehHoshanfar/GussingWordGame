package com.example.guessingwordgame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.guessingwordgame.databinding.ActivityDifficultLevelsBinding

class DifficultLevelsActivity : AppCompatActivity() {

    private val levelCount = 13
    private val levelProgressKey = "LEVEL_PROGRESS"
    private lateinit var levelFrames: List<FrameLayout>
    private lateinit var levelBackgrounds: List<ImageView>
    private lateinit var levelTextViews: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficult_levels)

        // Initialize frame layouts for levels
        levelFrames = listOf(
            findViewById(R.id.container_frame27),
            findViewById(R.id.container_frame28),
            findViewById(R.id.container_frame29),
            findViewById(R.id.container_frame30),
            findViewById(R.id.container_frame31),
            findViewById(R.id.container_frame32),
            findViewById(R.id.container_frame33),
            findViewById(R.id.container_frame34),
            findViewById(R.id.container_frame35),
            findViewById(R.id.container_frame36),
            findViewById(R.id.container_frame37),
            findViewById(R.id.container_frame38),
            findViewById(R.id.container_frame39)
        )

        // Initialize TextViews for levels
        levelTextViews = listOf(
            findViewById(R.id.text_level27),
            findViewById(R.id.text_level28),
            findViewById(R.id.text_level29),
            findViewById(R.id.text_level30),
            findViewById(R.id.text_level31),
            findViewById(R.id.text_level32),
            findViewById(R.id.text_level33),
            findViewById(R.id.text_level34),
            findViewById(R.id.text_level35),
            findViewById(R.id.text_level36),
            findViewById(R.id.text_level37),
            findViewById(R.id.text_level38),
            findViewById(R.id.text_level39)
        )

        // Initialize background images for levels
        levelBackgrounds = levelFrames.map { it.findViewById<ImageView>(R.id.image_vector27) }

        // Load level progress
        val levelProgress = getLevelProgress()

        // Set up frames
        for (i in levelFrames.indices) {
            if (i == 0 || i <= levelProgress) {
                // Level is unlocked
                levelFrames[i].setOnClickListener {
                    val levelText = levelTextViews[i].text.toString() // Get the level name text
                    startGameScreen(levelText) // Pass the level name
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

    private fun startGameScreen(levelText: String) {
        val intent = Intent(this, GameScreenActivity::class.java).apply {
            putExtra("LEVEL_NAME", levelText) // Pass level name (text)
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

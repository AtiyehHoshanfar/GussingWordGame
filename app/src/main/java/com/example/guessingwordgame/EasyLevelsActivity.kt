package com.example.guessingwordgame

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.collections.listOf as listOf1


class EasyLevelsActivity : AppCompatActivity() {

    // Define level states
    private val levelCount = 13
    private val levelProgressKey = "LEVEL_PROGRESS"
    private lateinit var levelFrames: List<FrameLayout>
    private lateinit var levelBackgrounds: List<ImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easy_levels)

        levelFrames = listOf1(
            findViewById(R.id.container_frame1),
            findViewById(R.id.container_frame2),
            findViewById(R.id.container_frame3),
            findViewById(R.id.container_frame4),
            findViewById(R.id.container_frame5),
            findViewById(R.id.container_frame6),
            findViewById(R.id.container_frame7),
            findViewById(R.id.container_frame8),
            findViewById(R.id.container_frame9),
            findViewById(R.id.container_frame10),
            findViewById(R.id.container_frame11),
            findViewById(R.id.container_frame12),
            findViewById(R.id.container_frame13)
        )

        // Initialize TextViews for levels
        val levelTextViews = listOf1(
            findViewById<TextView>(R.id.text_level1),
            findViewById(R.id.text_level2),
            findViewById(R.id.text_level3),
            findViewById(R.id.text_level4),
            findViewById(R.id.text_level5),
            findViewById(R.id.text_level6),
            findViewById(R.id.text_level7),
            findViewById(R.id.text_level8),
            findViewById(R.id.text_level9),
            findViewById(R.id.text_level10),
            findViewById(R.id.text_level11),
            findViewById(R.id.text_level12),
            findViewById(R.id.text_level13)
        )

        levelBackgrounds = levelFrames.map { it.findViewById<ImageView>(R.id.image_vector1) }

        val levelProgress = getLevelProgress()
        for (i in 0 until levelCount) {
            if (i == 0 || i <= levelProgress) {
                levelFrames[i].setOnClickListener {
                    val levelText = levelTextViews[i].text.toString()
                    startGameScreen(levelText)
                }

                if (i > 0 && i <= levelProgress) {
                    levelBackgrounds[i].setImageResource(R.drawable.vectorpassed)
                } else {
                    levelBackgrounds[i].setImageResource(R.drawable.vector)
                }
            } else {
                levelFrames[i].alpha = 0.5f
                levelFrames[i].isClickable = false
            }
        }
    }

    private fun startGameScreen(levelText: String) {
        val intent = Intent(this, GameScreenActivity::class.java).apply {
            putExtra("LEVEL_NAME", levelText) 
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

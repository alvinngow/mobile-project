package com.example.mobileproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import java.io.PrintStream
import java.util.Scanner

class LeaderboardActivity : AppCompatActivity() {
    private var leaderboardScore = arrayListOf<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        // Read Leaderboard Text File
        readLeaderboardFile()

        // Update Leaderboard TextView
        updateLeaderboardTV()
    }

    // --- Sliding animation for exiting intent
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    // --- Read Leaderboard Text File
    private fun readLeaderboardFile() {
        // Reset existing LeaderboardScore value
        leaderboardScore.clear()

        // Read output
        try {
            // Load local file into LeaderboardScore if it exists
            val scanner2 = Scanner(openFileInput("leaderboard_scores_local.txt"))
            while (scanner2.hasNextLine()) {
                val line = scanner2.nextLine()
                leaderboardScore.add(line.toInt())
            }
        } catch (exception: Exception) {
            // Most likely the local file hasn't been created
            val defaultLeaderboardScore = intArrayOf(3, 2, 1)

            // Add default values into LeaderboardScore
            leaderboardScore.add(defaultLeaderboardScore[0])
            leaderboardScore.add(defaultLeaderboardScore[1])
            leaderboardScore.add(defaultLeaderboardScore[2])

            // Print default values into local file
            val outStream = PrintStream(openFileOutput("leaderboard_scores_local.txt", MODE_APPEND))
            outStream.println(defaultLeaderboardScore[0])
            outStream.println(defaultLeaderboardScore[1])
            outStream.println(defaultLeaderboardScore[2])
            outStream.close()
        }
        Log.d("!LeaderboardScores", "$leaderboardScore")
    }

    // --- Update Leaderboard TextView
    private fun updateLeaderboardTV () {
        var leaderboardString = "1: " + leaderboardScore[0] + "\n2: " + leaderboardScore[1] + "\n3: " + leaderboardScore[2]
        var leaderboardTV = findViewById<TextView>(R.id.leaderboardText)
        leaderboardTV.text = leaderboardString
    }

    // --- End intent & return to MainActivity
    fun returnClick(view: View) {
        finish()
    }
}
package com.example.mobileproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import java.io.PrintStream
import java.util.Scanner

class LeaderboardActivity : AppCompatActivity() {
    private var leaderboardScore = arrayListOf<Array<String>>()
    private var scoreManager = ScoreManager
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        // Get and set current user
        username = intent.getStringExtra("username").toString()
        Log.d("!LeaderboardActivity: Username", "$username")

        // Read Leaderboard Text File
        readLeaderboardFile()

        // Check Score Manager
        checkScoreManager()

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
            val scanner = Scanner(openFileInput("scores.txt"))
            while (scanner.hasNextLine()) {
                val line = scanner.nextLine()
                val splitLine = line.split("\t")
                leaderboardScore.add(arrayOf(splitLine[0], splitLine[1]))
            }
        } catch (exception: Exception) {
            // Cause of Exception: Most likely the scores.txt hasn't been created

            // Add default values into leaderboardScore
            leaderboardScore.add(arrayOf("maars", "20"))
            leaderboardScore.add(arrayOf("Ron", "6"))
            leaderboardScore.add(arrayOf("Jane", "1"))

            // Print default values into scores.txt
            val outStream = PrintStream(openFileOutput("scores.txt", MODE_APPEND))
            outStream.println("maars\t20")
            outStream.println("Ron\t6")
            outStream.println("Jane\t1")
            outStream.close()
        }
    }

    // -- Check ScoreManager
    fun checkScoreManager() {
        val bestScore = scoreManager.bestScore
        Log.d("!SCORE", "$bestScore")
        var newLeaderboardScore = arrayListOf<Array<String>>()
        var notAdded = true
        leaderboardScore.forEach{ score  -> if (bestScore > score[1].toInt() && notAdded) {
            newLeaderboardScore.add(arrayOf("$username", "$bestScore"))
            newLeaderboardScore.add(score)
            notAdded = false
            } else {
                newLeaderboardScore.add(score)
            }
        }
        if (notAdded) {
            newLeaderboardScore.add(arrayOf("$username", "$bestScore"))
        }
        newLeaderboardScore.removeLast()

        // Update leaderboardScore
        leaderboardScore = newLeaderboardScore

        // Update values into scores.txt
        val outStream = PrintStream(openFileOutput("scores.txt", MODE_PRIVATE))
        leaderboardScore.forEach{ score  -> outStream.println(score[0] + "\t" + score[1])}
        outStream.close()

        scoreManager.score = 0
        scoreManager.bestScore = 0
    }

    // --- Update Leaderboard TextView
    private fun updateLeaderboardTV () {
        // Generate string based on leaderboardScore
        var leaderboardString = ""
        leaderboardScore.forEach{ score  -> leaderboardString+= score[1] + " - " + score[0] + "\n"}

        // Update leaderboard textview with generated string
        var leaderboardTV = findViewById<TextView>(R.id.leaderboardText)
        leaderboardTV.text = leaderboardString
    }

    // --- End intent & return to MainActivity
    fun returnClick(view: View) {
        finish()
    }
}
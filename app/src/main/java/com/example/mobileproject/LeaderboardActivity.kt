package com.example.mobileproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import java.io.PrintStream
import java.util.Scanner

class LeaderboardActivity : AppCompatActivity() {
    private var leaderboardScore = arrayListOf<Array<String>>()

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
            val scanner = Scanner(openFileInput("scores.txt"))
            while (scanner.hasNextLine()) {
                val line = scanner.nextLine()
                val splitLine = line.split("\t")
                leaderboardScore.add(arrayOf(splitLine[0], splitLine[1]))
            }
        } catch (exception: Exception) {
            // Cause of Exception: Most likely the scores.txt hasn't been created

            // Add default values into leaderboardScore
            leaderboardScore.add(arrayOf("maars", "17"))
            leaderboardScore.add(arrayOf("Ron", "10"))
            leaderboardScore.add(arrayOf("Jane", "4"))

            // Print default values into scores.txt
            val outStream = PrintStream(openFileOutput("scores.txt", MODE_APPEND))
            outStream.println("maars\t17")
            outStream.println("Ron\t10")
            outStream.println("Jane\t4")
            outStream.close()
        }
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
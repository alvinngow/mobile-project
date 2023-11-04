package com.example.mobileproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import java.io.PrintStream
import java.util.HashMap
import java.util.Scanner
import android.content.Context
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context
//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class LeaderboardActivity : AppCompatActivity() {
    private var leaderboardScore = arrayListOf<Int>()
    private var userScores = HashMap<String,Int>()
    private lateinit var ma: MainActivity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        // Read Leaderboard Text File
        readLeaderboardFile()

        // Update Leaderboard TextView
        updateLeaderboardTV()
    }

    fun cleantext() {
        val outStream = PrintStream(openFileOutput("score.txt", MODE_PRIVATE))
        outStream.println()
        outStream.close()

    }

    // --- Sliding animation for exiting intent
    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    private fun writeScore(line: String){
        val outStream = PrintStream(openFileOutput("score.txt", MODE_APPEND))
        outStream.println(line)
        outStream.close()
    }

    fun editScore(username: String, score: Int){
        println("button: editScore - "+score)
        val scanner = Scanner(openFileInput("score.txt"))
        var oldContent = ""
        while(scanner.hasNextLine()){
            val line = scanner.nextLine()
            val pieces = line.split("\t")

            if (pieces.size >= 3){
//                updates only if score is higher:
                if (pieces[0].equals(username) && pieces[2].toInt() < score) {
                    oldContent += "${pieces[0]}\t${pieces[1]}\t${score}\n"
                }else{ oldContent += line + "\n" }
            }
        }
        val outStream = PrintStream(openFileOutput("score.txt", MODE_PRIVATE))
        outStream.println(oldContent)
        outStream.close()
    }

    // --- Read Leaderboard Text File
    private fun readLeaderboardFile() {
        // Reset existing LeaderboardScore value
        userScores.clear()

        // Read output
        try {
            // Load local file into LeaderboardScore if it exists
            val scanner2 = Scanner(openFileInput("score.txt"))

//            for clean demo:
            cleantext()
            writeScore("maars\tmaars1\t0")
            writeScore("Ron\tmaars1\t3")
            writeScore("Jane\tmaars1\t5")


            editScore("maars", ScoreManager.bestScore)
            while (scanner2.hasNextLine()) {
                val line = scanner2.nextLine()
//                leaderboardScore.add(line.toInt())
                val pieces = line.split("\t")
                if (pieces.size >= 3){
//                    println("readFile3: ${pieces[0]}-${pieces[1]}-${pieces[2].toInt()}-")
                    userScores[pieces[0]] = pieces[2].toInt()

                }
            }
        } catch (exception: Exception) {
//            // Most likely the local file hasn't been created
//            val defaultLeaderboardScore = intArrayOf(3, 2, 1)
//
//            // Add default values into LeaderboardScore
//            leaderboardScore.add(defaultLeaderboardScore[0])
//            leaderboardScore.add(defaultLeaderboardScore[1])
//            leaderboardScore.add(defaultLeaderboardScore[2])
            writeScore("maars\tmaars1\t4")
            writeScore("Ron\tmaars1\t17")
            writeScore("Jane\tmaars1\t10")


            // Print default values into local file
//            val outStream = PrintStream(openFileOutput("leaderboard_scores_local.txt", MODE_APPEND))
//            outStream.println(defaultLeaderboardScore[0])
//            outStream.println(defaultLeaderboardScore[1])
//            outStream.println(defaultLeaderboardScore[2])
//            outStream.close()
        }
        Log.d("!LeaderboardScores", "$userScores")
    }

    // --- Update Leaderboard TextView
    private fun updateLeaderboardTV () {
        val result = userScores.toList().sortedBy { (_, value) -> -value}.toMap()
        val playerNames2: ArrayList<String> = ArrayList(result.keys)
        val playerScores2: ArrayList<Int> = ArrayList(result.values)
        var leaderboardString = "";
        for (i in 0..playerNames2.size-1) {
            println("LeaderboardScores: " + playerNames2[i] + "-- " + playerScores2[i])
            leaderboardString += playerNames2[i] + ": " + playerScores2[i] + "\n";
        }
//        var leaderboardString = "1: " + leaderboardScore[0] + "\n2: " + leaderboardScore[1] + "\n3: " + leaderboardScore[2]
        var leaderboardTV = findViewById<TextView>(R.id.leaderboardText)
        leaderboardTV.text = leaderboardString
    }

    // --- End intent & return to MainActivity
    fun returnClick(view: View) {
        finish()
    }
}
package com.example.mobileproject

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import java.io.PrintStream
import java.util.HashMap
import java.util.Scanner
import kotlin.math.max

class MainActivity : Activity() {

    lateinit var txt_score: TextView
    lateinit var txt_best_score: TextView
    lateinit var txt_score_over: TextView
    lateinit var r1_game_over: RelativeLayout
    lateinit var btn_start: Button
    var score: Int = 0
    var bestScore: Int = 0
    private lateinit var gv: GameView
    lateinit var gridView: GridView

//    lateinit var gridView: GridView
    private var playerNames3: ArrayList<String> = ArrayList<String>()
    private var playerScores3: ArrayList<Int> = ArrayList<Int>()
    private var userScores = HashMap<String,Int>()

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

    fun cleantext() {
        val outStream = PrintStream(openFileOutput("score.txt", MODE_PRIVATE))
        outStream.println()
        outStream.close()

    }


    private fun readFile3(){
        val scanner = Scanner(openFileInput("score.txt"))
        while(scanner.hasNextLine()){
            val line = scanner.nextLine()
            val pieces = line.split("\t")

            if (pieces.size >= 3){
                println("readFile3: ${pieces[0]}-${pieces[1]}-${pieces[2].toInt()}-")
                playerNames3.add(pieces[0])
                playerScores3.add(pieces[2].toInt())
                userScores[pieces[0]] = pieces[2].toInt()
            }
        }
        println("readFile3 done")
    }
    fun showLeaderboard(){
        println("readfile showLeaderboard")
        readFile3()
        val result = userScores.toList().sortedBy { (_, value) -> -value}.toMap()

        val playerNames2: ArrayList<String> = ArrayList(result.keys)
        val playerScores2: ArrayList<Int> = ArrayList(result.values)
        gridView = findViewById(R.id.gridView)
        val mainAdapter = MainAdapter(this, playerNames2, playerScores2)
        println("showLeaderboard4")
        gridView.adapter = mainAdapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//            Toast.makeText(
//                applicationContext, "You CLicked " + playerNames[+position],
//                Toast.LENGTH_SHORT
//            ).show()
        }
        println("readFile3 after ")
    }

    fun updateBestScore(username: String){
        val scanner = Scanner(openFileInput("score.txt"))
        while(scanner.hasNextLine()){
            val line = scanner.nextLine()
            val pieces = line.split("\t")
            if (pieces.size >= 3){
                if (pieces[0] == username){
                    bestScore = max(pieces[2].toInt(), bestScore)
                    println("button: update bestScore done-" + pieces[2])
                } } }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        println("button: mainactivity onstart start")
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        Constants.SCREEN_WIDTH = displayMetrics.widthPixels
        Constants.SCREEN_HEIGHT = displayMetrics.heightPixels
        setContentView(R.layout.activity_main)
        txt_score = findViewById(R.id.txt_score)
        txt_best_score = findViewById(R.id.txt_best_score)
        txt_score_over = findViewById(R.id.txt_score_over)
        r1_game_over = findViewById(R.id.rl_game_over)
        gv = findViewById(R.id.gv)
        btn_start = findViewById(R.id.btn_start)
        gridView = findViewById(R.id.gridView)


        gridView.visibility = TextView.VISIBLE
        val scanner = Scanner(openFileInput("score.txt"))
//        editScore("maars", bestScore)
        showLeaderboard()


        btn_start.setOnClickListener() {
            println("button: btn_start start")
            gv.start = true
            r1_game_over.visibility = RelativeLayout.INVISIBLE
            txt_score.visibility = TextView.VISIBLE
            txt_score.text = "0"
            btn_start.visibility = Button.INVISIBLE

//            editScore(scanner, "maars", gv.score)
            gridView.visibility = TextView.VISIBLE
//            update with best score from score.txt:
//            editScore(scanner, "maars", bestScore)
            println("button: before updateBestScore -" + bestScore)
            updateBestScore("maars")
            showLeaderboard()
            println("button: btn_start end -" + bestScore)
        }
        r1_game_over.setOnClickListener() {
            println("button: r1_game_over listener start")
            gridView.visibility = TextView.INVISIBLE
            editScore("maars", bestScore)
            showLeaderboard()
//            cleantext()

            btn_start.visibility = Button.VISIBLE
            r1_game_over.visibility = RelativeLayout.INVISIBLE
            txt_score.visibility = TextView.VISIBLE
            gv.start = false
            gv.reset()
            println("button: r1_game_over listener end - "+bestScore)
        }
        println("button: mainactivity onstart end")
    }
}
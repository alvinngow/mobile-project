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
import java.util.HashMap
import java.util.Scanner

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
    private var playerNames3: ArrayList<String> = ArrayList<String>()
    private var playerScores3: ArrayList<Int> = ArrayList<Int>()
    private var userScores = HashMap<String,Int>()

    private fun readFile3(scanner: Scanner){
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
        val scanner4 = Scanner(openFileInput("score.txt"))
        readFile3(scanner4)
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
    override fun onCreate(savedInstanceState: Bundle?) {
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
//        gridView = findViewById(R.id.gridView)
//        gridView.visibility = TextView.INVISIBLE
//        showLeaderboard()
            btn_start = findViewById(R.id.btn_start)
        btn_start.setOnClickListener() {
            gv.start = true
            r1_game_over.visibility = RelativeLayout.INVISIBLE
            txt_score.visibility = TextView.VISIBLE
            txt_score.text = "0"
//            score += 1;
//            txt_score.text = score.toString()
            txt_score.text = "Score: " + gv.score.toString()
//            Toast.makeText(this, "score: " + score.toString(), Toast.LENGTH_SHORT).show()

            btn_start.visibility = Button.INVISIBLE
        }
        r1_game_over.setOnClickListener() {
            btn_start.visibility = Button.VISIBLE
            r1_game_over.visibility = RelativeLayout.INVISIBLE
            txt_score.visibility = TextView.VISIBLE
//            gridView.visibility = TextView.VISIBLE
//            println("showLeaderboard2")
////            showLeaderboard()
//            println("showLeaderboard3")
//            score += 1;
            txt_score.text = gv.score.toString()
            Toast.makeText(this, "score2: " + gv.score.toString(), Toast.LENGTH_SHORT).show()
            gv.start = false
            gv.reset()
        }
    }
}
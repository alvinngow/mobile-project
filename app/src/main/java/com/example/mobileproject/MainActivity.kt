package com.example.mobileproject

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView

class MainActivity : Activity() {

    lateinit var txt_score: TextView
    lateinit var txt_best_score: TextView
    lateinit var txt_score_over: TextView
    lateinit var r1_game_over: RelativeLayout
    lateinit var btn_start: Button
    private lateinit var gv: GameView
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
        btn_start = findViewById(R.id.btn_start)
        btn_start.setOnClickListener() {
            gv.start = true
            r1_game_over.visibility = RelativeLayout.INVISIBLE
            txt_score.visibility = TextView.VISIBLE
            txt_score.text = "0"
            btn_start.visibility = Button.INVISIBLE
        }
        r1_game_over.setOnClickListener() {
            btn_start.visibility = Button.VISIBLE
            r1_game_over.visibility = RelativeLayout.INVISIBLE
            txt_score.visibility = TextView.VISIBLE
            gv.start = false
            gv.reset()
        }
    }
}
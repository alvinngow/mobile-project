package com.example.mobileproject

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView

class MainActivity : Activity() {

    var textScore: TextView? = null
    var textHighScore: TextView? = null
    var textScoreOver: TextView? = null
    var r1GameOver: RelativeLayout? = null
    var buttonStart: Button ?= null
    private var gameView: GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        Constants.SCREEN_WIDTH = displayMetrics.widthPixels
        Constants.SCREEN_HEIGHT = displayMetrics.heightPixels
        setContentView(R.layout.activity_main)
        textScore = findViewById(R.id.textScore)
        textHighScore = findViewById(R.id.textHighScore)
        textScoreOver = findViewById(R.id.textScoreOver)
        r1GameOver = findViewById(R.id.r1GameOver)
        buttonStart = findViewById(R.id.buttonStart)
        gameView = findViewById(R.id.gameView)
        buttonStart!!.setOnClickListener {
            gameView!!.start = true
            textScore!!.visibility = TextView.VISIBLE
            buttonStart!!.visibility = Button.INVISIBLE
        }
        r1GameOver!!.setOnClickListener {
            gameView!!.start = true
            buttonStart!!.visibility = Button.VISIBLE
            r1GameOver!!.visibility = RelativeLayout.INVISIBLE
            gameView!!.reset()
        }
    }

}
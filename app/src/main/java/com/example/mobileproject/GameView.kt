package com.example.mobileproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import java.io.PrintStream
import java.util.Scanner


class GameView : View, SensorEventListener {
    lateinit var sensorMan:SensorManager
//    lateinit var gyroscope: Sensor
    lateinit var accelerometer: Sensor

    private var bird: Bird? = null
    private var handler: Handler? = Handler()
    private var runnable: Runnable? = null
    private var score = 0
    private var bestScore = 0
    var start = false

    private enum class GameState {
        Neutral, Reaching2, Reaching0, JumpReady
    }

    private var state = GameState.Neutral
    private val yValue2 = 1
    private val yValue0 = 0

    private var leaderboardScore = arrayListOf<Array<String>>()


    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initBird()
        runnable = Runnable {
            invalidate()
        }
    }

    private fun initBird() {
        bird = Bird()
        bird!!.width = 100 * Constants.SCREEN_WIDTH / 1080
        bird!!.height = 100 * Constants.SCREEN_HEIGHT / 1920
        bird!!.x = (100 * Constants.SCREEN_WIDTH / 1080 / 2).toFloat()
        bird!!.y = (Constants.SCREEN_HEIGHT / 2 - bird!!.height / 2).toFloat()
        val arrBms = ArrayList<Bitmap>()
        arrBms.add(BitmapFactory.decodeResource(resources, R.drawable.bird1))
        arrBms.add(BitmapFactory.decodeResource(resources, R.drawable.bird2))
        bird!!.setArrBms(arrBms)


    }




    override fun draw(canvas: Canvas) {
        super.draw(canvas)
//        Log.d("Record Best Score:", "Best score - ${recordBestScore()}")
        if (start) {
            bird!!.draw(canvas)
            if (bird!!.y - bird!!.height < 0 || bird!!.y + bird!!.height > Constants.SCREEN_HEIGHT) {
                val mainActivity = context as MainActivity
                mainActivity.txt_score_over.text = mainActivity.txt_score.text
                mainActivity.txt_score.visibility = View.INVISIBLE
                mainActivity.r1_game_over.visibility = View.VISIBLE
                mainActivity.txt_best_score.text = "Best Score: ${recordBestScore().toString()}"
                ScoreManager.bestScore = recordBestScore()
                stopScoringSystem()
                resetScoringSystem()


            }
        } else {
            if (bird!!.y > Constants.SCREEN_HEIGHT/2) {
                bird!!.drop = (-15 * Constants.SCREEN_HEIGHT / 1920).toFloat()
            }
            bird!!.draw(canvas)
        }

        handler!!.postDelayed(runnable!!, 1000/60)
    }

//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        if (event!!.action == MotionEvent.ACTION_DOWN) {
//            bird!!.drop = -15f
//        }
//        return true
//    }

    fun reset() {
        val mainActivity = context as MainActivity
        mainActivity.txt_score.text = "0"
        score = 0
        initBird()
    }

    fun increaseBirdDropRate() {
        val dropRateRunnable = object : Runnable {
            override fun run() {
                ScoreManager.birdDropRate *= 1.1f
                handler?.postDelayed(this, 3000)
//                Log.d("Bird Drop Rate,", "Drop rate is ${ScoreManager.birdDropRate}")
            }

        }
        handler?.post(dropRateRunnable)
    }

    fun startScoringSystem() {
        val scoreUpdateRunnable = object : Runnable {
            override fun run() {
                val mainActivity = context as MainActivity
                ScoreManager.score++
                mainActivity.txt_score.text = ScoreManager.score.toString()
                handler?.postDelayed(this, 1000)
//                Log.d("Current score,", "Score is $ScoreManager.score")
            }
        }
        handler?.post(scoreUpdateRunnable)
    }

    fun getScore(): Int {
        return score
    }

    fun stopScoringSystem() {
        handler?.removeCallbacksAndMessages(null)
    }

    fun resetScoringSystem() {
        ScoreManager.score = 0
    }

    fun recordBestScore(): Int {
        if (ScoreManager.score > bestScore) {
            bestScore = ScoreManager.score
        }

        return bestScore
    }


    override fun onSensorChanged(p0: SensorEvent) {
        val x = p0.values[0]
        val y = p0.values[1]
        val z = p0.values[2]


//        if (z > 0) {
//            bird!!.drop = -15f
//        }

        when (state) {
            GameState.Neutral -> if (z >= yValue2) {
                state = GameState.Reaching2
            }
            GameState.Reaching2 -> if (z <= yValue0) {
                state = GameState.Reaching0
            }
            GameState.Reaching0 -> if (z >= yValue2) {
                state = GameState.JumpReady
            }
            GameState.JumpReady -> {
                bird!!.drop = -15f
                state = GameState.Neutral
            }
        }


        Log.d("SensorDebug", "Accelerometer Data - X: $x, Y: $y, Z: $z")
//        startScoringSystem()

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }




}
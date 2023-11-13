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


class GameView : View, SensorEventListener {
    lateinit var sensorMan:SensorManager
    lateinit var accelerometer: Sensor

    private var bird: Bird? = null
    private var handler: Handler? = Handler()
    private var runnable: Runnable? = null
    private var score = 0
    private var bestScore = 0
    var start = false

    private enum class GameState {
        Neutral, FirstState, SecondState, JumpReady
    }

    private var state = GameState.Neutral
    private val firstStateThreshold = 12.0f
    private val secondStateThreshold = 9.9f

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
        if (start) {
            bird!!.draw(canvas)
            if (bird!!.y - bird!!.height < 0 || bird!!.y + bird!!.height > Constants.SCREEN_HEIGHT) {
                val mainActivity = context as MainActivity
                mainActivity.txt_score_over.text = mainActivity.txt_score.text
                mainActivity.txt_score.visibility = View.INVISIBLE
                mainActivity.r1_game_over.visibility = View.VISIBLE
                mainActivity.txt_best_score.text = "Best Score: ${recordBestScore().toString()}"
                ScoreManager.bestScore = recordBestScore()
                ScoreManager.birdDropRate = 0.6f
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

    fun reset() {
        val mainActivity = context as MainActivity
        mainActivity.txt_score.text = "0"
        score = 0
        ScoreManager.birdDropRate = 0.6f
        ScoreManager.gameState = true
        initBird()
    }

    fun increaseBirdDropRate() {
        val dropRateRunnable = object : Runnable {
            override fun run() {
                if (ScoreManager.birdDropRate >= 1.3f) {
                    ScoreManager.birdDropRate = 1.3f
                    Log.d("BirdDropRate", "Drop Rate is: ${ScoreManager.birdDropRate}")
                } else {
                    ScoreManager.birdDropRate *= 1.1f
                    Log.d("BirdDropRate", "Drop Rate is: ${ScoreManager.birdDropRate}")
                    handler?.postDelayed(this, 3000)
                }

            }

        }
        handler?.post(dropRateRunnable)
    }

    fun startScoringSystem() {
        val scoreUpdateRunnable = object : Runnable {
            override fun run() {
                val mainActivity = context as MainActivity
                if (ScoreManager.gameState) {
                    ScoreManager.score++
                    mainActivity.txt_score.text = ScoreManager.score.toString()
                    handler?.postDelayed(this, 1000)
                }
            }
        }
        handler?.post(scoreUpdateRunnable)
    }
    fun stopScoringSystem() {
//        handler?.removeCallbacksAndMessages(null)
        ScoreManager.gameState = false
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

        when (state) {
            GameState.Neutral -> if (y >= 9.81) {
                state = GameState.FirstState
            }
            GameState.FirstState -> if (y <= secondStateThreshold) {
                state = GameState.SecondState
            }
            GameState.SecondState -> if (y >= firstStateThreshold) {
                state = GameState.JumpReady
            }
            GameState.JumpReady -> {
                bird!!.drop = -20f
                state = GameState.Neutral
            }
        }

        Log.d("SensorDebug", "Accelerometer Data - X: $x, Y: $y, Z: $z")

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }




}
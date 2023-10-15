package com.example.mobileproject

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class GameView : View {
    private var bird: Bird? = null
    private var handler: Handler? = Handler()
    private var runnable: Runnable? = null
    private var arrPipes = ArrayList<Pipe>()
    private var sumPipe = 0
    private var distance = 0
    private var score = 0
    private var bestScore = 0
    private var isGameOver = false
    var start = false
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initBird()
        initPipe()
        runnable = Runnable {
            invalidate()
        }
    }

    private fun initPipe() {
        sumPipe = 6
        distance = 300 * Constants.SCREEN_WIDTH / 1920
        for (i in 0 until sumPipe) {
            if (i < sumPipe/2) {
                arrPipes.add(Pipe(Constants.SCREEN_WIDTH + i * (Constants.SCREEN_WIDTH + 200 * Constants.SCREEN_WIDTH/1080)/ (sumPipe/2), 0f, 200*Constants.SCREEN_WIDTH/1080, Constants.SCREEN_HEIGHT/2))
                arrPipes[arrPipes.size - 1].setBm(BitmapFactory.decodeResource(resources, R.drawable.pipe2))
                arrPipes[arrPipes.size - 1].randomY()
            } else {
                // adjust y of pipe2 to end at bottom of page
                arrPipes.add(Pipe(Constants.SCREEN_WIDTH + (i - sumPipe/2) * (Constants.SCREEN_WIDTH + 200 * Constants.SCREEN_WIDTH/1080)/ (sumPipe/2), arrPipes[i - sumPipe/2].y + arrPipes[i - sumPipe/2].height + distance + 500, 200*Constants.SCREEN_WIDTH/1080, Constants.SCREEN_HEIGHT/2))
                arrPipes[arrPipes.size - 1].setBm(BitmapFactory.decodeResource(resources, R.drawable.pipe1))
            }
        }
    }

    private fun initBird() {
        val width = 100 * Constants.SCREEN_WIDTH / 1080
        val height = 100 * Constants.SCREEN_HEIGHT / 1920
        val x = (100 * Constants.SCREEN_WIDTH / 1080 / 2).toFloat()
        val y = (Constants.SCREEN_HEIGHT/2 - height/2).toFloat()
        bird = Bird(x.toInt(), y.toInt(), width, height)
        val arrBms = ArrayList<Bitmap>()
        arrBms.add(BitmapFactory.decodeResource(resources, R.drawable.bird1))
        arrBms.add(BitmapFactory.decodeResource(resources, R.drawable.bird2))
        bird!!.setArrBms(arrBms)
    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (start) {
            bird!!.draw(canvas)
            for (i in 0 until sumPipe) {
                if (bird!!.rect!!.intersect(arrPipes[i].rect!!) || bird!!.y - bird!!.height < 0  || bird!!.y > Constants.SCREEN_HEIGHT) {
                    Pipe.speed = 0
                    val mainActivity = context as MainActivity
                    mainActivity.textScoreOver!!.text = score.toString()
                    val tempTextHighScore = "Best: $bestScore"
                    mainActivity.textHighScore!!.text = tempTextHighScore
                    mainActivity.r1GameOver!!.visibility = VISIBLE
                }
            }
            for (i in 0 until sumPipe) {
                if (bird!!.x + bird!!.width > arrPipes[i].x + arrPipes[i].width/2 && bird!!.x + bird!!.width <= arrPipes[i].x + arrPipes[i].width/2 + Pipe.speed && i < sumPipe/2) {
                    score++
                    val mainActivity = context as MainActivity
                    mainActivity.textScore!!.text = score.toString()
                }
                if (arrPipes[i].x < -arrPipes[i].width) {
                    arrPipes[i].x = (Constants.SCREEN_WIDTH).toFloat()
                    if (i < sumPipe/2) {
                        arrPipes[i].randomY()
                    } else {
                        arrPipes[i].y = arrPipes[i - sumPipe/2].y + arrPipes[i].height + distance + 300
                    }
                }
                arrPipes[i].draw(canvas)
            }
        } else {
            if (bird!!.y > Constants.SCREEN_HEIGHT/2) {
                bird!!.drop = (-15 * Constants.SCREEN_HEIGHT / 1920).toFloat()
            }
            bird!!.draw(canvas)
        }
        handler!!.postDelayed(runnable!!, 1000/60)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            bird!!.drop = -15f
        }
        return true
    }

    fun reset() {
        val mainActivity = context as MainActivity
        mainActivity.textScore!!.text = "0"
        score = 0
        Pipe.speed = 10 * Constants.SCREEN_WIDTH / 1080
        initBird()
        initPipe()
    }
}
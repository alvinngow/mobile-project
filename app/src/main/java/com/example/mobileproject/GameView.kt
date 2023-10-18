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
    private var score = 0
    private var bestScore = 0
    var start = false
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
        bird!!.y = (Constants.SCREEN_HEIGHT/2 - bird!!.height/2).toFloat()
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
        mainActivity.txt_score.text = "0"
        score = 0
        initBird()
    }
}
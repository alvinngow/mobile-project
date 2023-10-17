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
        bird!!.draw(canvas)
        handler!!.postDelayed(runnable!!, 1000/60)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event!!.action == MotionEvent.ACTION_DOWN) {
            bird!!.drop = -15f
        }
        return true
    }
}
package com.example.mobileproject

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GamePanel : SurfaceView, SurfaceHolder.Callback{
    private var mainThread: MainThread? = null

    constructor(context: Context) : super(context) {
        getHolder().addCallback(this)
        mainThread = MainThread(getHolder(), this)
        setFocusable(true)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        mainThread?.setRunning(false)
        mainThread?.join()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mainThread = MainThread(getHolder(), this)
        mainThread?.setRunning(true)
        mainThread?.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                mainThread?.setRunning(false)
                mainThread?.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            retry = false
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return super.onTouchEvent(event)
    }

    fun update() {

    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
    }
}
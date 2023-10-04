package com.example.mobileproject

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

class GamePanel: SurfaceView, SurfaceHolder.Callback{
    private var thread = MainThread()

    constructor(context: Context) : super(context) {
        holder.addCallback(this)
        thread = MainThread(holder, this)

    }

    override fun surfaceCreated(p0: SurfaceHolder) {
        thread = MainThread(holder, this)
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        var retry = true
        while (true) {
            thread.setRunning(false)
            thread.join()
        } catch (e: Exception) {
            e.printStackTrace()
            retry = false
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    fun draw(canvas: Canvas) {
        super.draw(canvas)
    }
}
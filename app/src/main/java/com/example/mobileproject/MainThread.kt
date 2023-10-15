package com.example.mobileproject

import android.graphics.Canvas
import android.view.SurfaceHolder

class MainThread(surfaceHolder: SurfaceHolder, gamePanel: GamePanel) : Thread() {
    companion object {
        const val  MAX_FPS: Long = 30
        var canvas: Canvas? = null
    }
    private var averageFPS: Double = 0.0
    private var running: Boolean = false
    private var surfaceHolder: SurfaceHolder? = surfaceHolder
    private var gamePanel: GamePanel? = gamePanel

    fun setRunning(running: Boolean) {
        this.running = running
    }

    override fun run() {
        var startTime: Long
        var timeMillis: Long
        var waitTime: Long
        var frameCount = 0
        var totalTime: Long = 0
        val targetTime: Long = 1000 / MAX_FPS

        while (running) {
            startTime = System.nanoTime()
            canvas = null

            try {
                canvas = this.surfaceHolder?.lockCanvas()
                surfaceHolder?.let {
                    synchronized(it) {
                        this.gamePanel?.update()
                        canvas?.let { it1 -> this.gamePanel?.draw(it1) }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder?.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }

            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis
            try {
                if (waitTime > 0) {
                    sleep(waitTime)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            totalTime += System.nanoTime() - startTime
            frameCount++

            if (frameCount.toLong() == MAX_FPS) {
                averageFPS = (1000 / ((totalTime / frameCount) / 1000000)).toDouble()
                frameCount = 0
                totalTime = 0
                println(averageFPS)
            }
        }
    }
}
package com.example.mobileproject

import android.graphics.Bitmap
import android.graphics.Canvas
import kotlin.random.Random

class Pipe: BaseObject {

    companion object {
        var speed = 10* Constants.SCREEN_WIDTH / 1080
    }
    constructor(x: Int, y: Float, width: Int, height: Int): super(x, y, width, height) {
        this.x = x.toFloat()
        this.y = y
        this.width = width
        this.height = height
    }

    fun draw(canvas: Canvas) {
        this.x -= speed
        canvas.drawBitmap(bitmap!!, x, y, null)
    }

    fun randomY() {
        val r = Random
        this.y = (r.nextInt((0 + this.height/4) + 1) - this.height/4).toFloat()
    }

    fun setBm(bm: Bitmap) {
        this.bitmap = Bitmap.createScaledBitmap(bm, width, height, true)
    }
}
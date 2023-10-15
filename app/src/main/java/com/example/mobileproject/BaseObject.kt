package com.example.mobileproject

import android.graphics.Bitmap
import android.graphics.Rect

open class BaseObject {
    var x: Float = 0.toFloat()
    var y: Float = 0.toFloat()
    var width: Int = 0
    var height: Int = 0
    var rect: Rect? = null
    var bitmap: Bitmap? = null

    constructor() {}

    constructor(x: Float, y: Float, width: Int, height: Int) {
        this.x = x
        this.y = y
        this.width = width
        this.height = height
        rect = Rect(x.toInt(), y.toInt(), (x + width).toInt(), y.toInt() + height)
    }

}
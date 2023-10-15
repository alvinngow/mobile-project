package com.example.mobileproject

import android.graphics.Bitmap
import android.graphics.Rect

open class BaseObject {
    var x: Float = 0.toFloat()
    var y: Float = 0.toFloat()
    var width: Int = 0
    var height: Int = 0
    private var rect: Rect? = null
    var bitmap: Bitmap? = null

    constructor() {}

    constructor(x: Int, y: Float, width: Int, height: Int) {
        this.x = x.toFloat()
        this.y = y
        this.width = width
        this.height = height
        rect = Rect(x, y.toInt(), x + width, y.toInt() + height)
    }

}
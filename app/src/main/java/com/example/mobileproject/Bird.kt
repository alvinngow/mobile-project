package com.example.mobileproject

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix

class Bird: BaseObject {

    private var arrBms = ArrayList<Bitmap>()
    private var count = 0
    private var vFlap = 5
    private var idCurrentBm = 0
    var drop: Float = 0.toFloat()
    constructor()

    fun draw(canvas: Canvas) {
        drop()
        canvas.drawBitmap(getBm(), x, y, null)
    }

    private fun drop() {
        this.drop += 0.6f
        this.y += this.drop
    }

    fun setArrBms(arrBms: ArrayList<Bitmap>) {
        this.arrBms = arrBms
        // loop through index of arrBms
        for (i in 0 until arrBms.size) {
            arrBms[i] = Bitmap.createScaledBitmap(arrBms[i], width, height, true)
        }
    }

    private fun getBm(): Bitmap {
        count++
        if (count == vFlap) {
            for (i in 0 until arrBms.size) {
                if (i == idCurrentBm) {
                    idCurrentBm = (idCurrentBm + 1) % arrBms.size
                    break
                } else if (idCurrentBm == i) {
                    idCurrentBm = (idCurrentBm + 1) % arrBms.size
                    break
                }
            }
            count = 0
        }
        return if (drop < 0) {
            val matrix = Matrix()
            matrix.postRotate(-25f)
            Bitmap.createBitmap(arrBms[idCurrentBm], 0, 0, arrBms[idCurrentBm].width, arrBms[idCurrentBm].height, matrix, true)
        } else {
            val matrix = Matrix()
            if (this.drop < 70) {
                matrix.postRotate(-25f + this.drop)
            } else {
                matrix.postRotate(45f)
            }
            Bitmap.createBitmap(arrBms[idCurrentBm], 0, 0, arrBms[idCurrentBm].width, arrBms[idCurrentBm].height, matrix, true)
        }
    }

}
package com.example.mobileproject

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import java.util.HashMap
import java.util.Scanner

internal class MainAdapter(
    private val context: Context,

    private val numbersInWords: ArrayList<String>,
//    private val numberImage: IntArray
    private val playerScores: ArrayList<Int>
//    private val numbersInWords: Array<String>,
////    private val numberImage: IntArray
//    private val playerScores: Array<Int>
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var textView2: TextView
    override fun getCount(): Int {
        return numbersInWords.size
    }
    override fun getItem(position: Int): Any? {
        return null
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View? {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.row_item, null)
        }
//        imageView = convertView!!.findViewById(R.id.imageView)
        textView = convertView!!.findViewById(R.id.textView)
        textView2 = convertView.findViewById(R.id.textView2)
//        imageView.setImageResource(numberImage[position])
        textView.text = numbersInWords[position]

        textView2.text = playerScores[position].toString()
        return convertView
    }
}
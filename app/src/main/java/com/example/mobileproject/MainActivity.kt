package com.example.mobileproject

import android.app.Activity
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.google.android.material.color.utilities.Score

class MainActivity : Activity(), SensorEventListener {

    lateinit var txt_score: TextView
    lateinit var txt_best_score: TextView
    lateinit var txt_score_over: TextView
    lateinit var r1_game_over: RelativeLayout
    lateinit var btn_start: Button
    private lateinit var gv: GameView


    private lateinit var sensorMan:SensorManager
//    private lateinit var gyroscope: Sensor
    private lateinit var accelerometer: Sensor

    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get and set current user
        username = intent.getStringExtra("username").toString()
        Log.d("!MainActivity: Username", "$username")

        sensorMan = getSystemService(SENSOR_SERVICE) as SensorManager
//        gyroscope =sensorMan.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        accelerometer = sensorMan.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        Constants.SCREEN_WIDTH = displayMetrics.widthPixels
        Constants.SCREEN_HEIGHT = displayMetrics.heightPixels
        setContentView(R.layout.activity_main)
        txt_score = findViewById(R.id.txt_score)
        txt_best_score = findViewById(R.id.txt_best_score)
        txt_score_over = findViewById(R.id.txt_score_over)
        r1_game_over = findViewById(R.id.rl_game_over)
        gv = findViewById(R.id.gv)
        btn_start = findViewById(R.id.btn_start)
        btn_start.setOnClickListener() {
            gv.start = true
            gv.startScoringSystem()
            gv.increaseBirdDropRate()
            r1_game_over.visibility = RelativeLayout.INVISIBLE
            txt_score.visibility = TextView.VISIBLE
            txt_score.text = "0"
            btn_start.visibility = Button.INVISIBLE
        }
        r1_game_over.setOnClickListener() {
            btn_start.visibility = Button.VISIBLE
            r1_game_over.visibility = RelativeLayout.INVISIBLE
            txt_score.visibility = TextView.VISIBLE
            gv.start = false
            txt_best_score.text = gv.recordBestScore().toString()
            gv.reset()


        }

        gv.sensorMan = sensorMan
//        gv.gyroscope = gyroscope
        gv.accelerometer = accelerometer


        val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val sensors = sensorManager.getSensorList(Sensor.TYPE_ALL)
        for(s:Sensor in sensors) {
            Log.i("SensorManager","Found Sensor: ${s.name}" )
        }
    }

    // --- Start new intent and move to LeaderboardActivity
    fun leaderboardClick(view: View) {
        val intent = Intent(this, LeaderboardActivity::class.java)
        intent.putExtra("username", "$username")
        startActivity(intent)
        // Sliding animation to next intent
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

    }

    override fun onResume() {
        super.onResume()
        sensorMan.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
//        gv.startScoringSystem()
    }

    override fun onPause() {
        super.onPause()
        sensorMan.unregisterListener(this)
        gv.stopScoringSystem()

    }
//
    override fun onSensorChanged(p0: SensorEvent) {
        return gv.onSensorChanged(p0)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return gv.onAccuracyChanged(p0, p1)
    }

}
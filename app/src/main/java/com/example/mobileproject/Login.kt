package com.example.mobileproject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.datadog.android.Datadog
//import com.datadog.android.core.NoOpInternalSdkCore.trackingConsent
//import com.datadog.android.core.NoOpInternalSdkCore.trackingConsent
import com.datadog.android.core.configuration.Configuration
import com.datadog.android.privacy.TrackingConsent
import com.datadog.android.trace.AndroidTracer
import com.datadog.android.trace.Trace
import com.datadog.android.trace.TraceConfiguration
import io.opentracing.util.GlobalTracer
import java.io.PrintStream
import java.util.HashMap
import java.util.Scanner
class Login : AppCompatActivity() {
    private var words = ArrayList<String>()
    private var wordToDefn = HashMap<String,String>()
    private var usernamePassword = HashMap<String,String>()

    private fun writeScore(line: String){
        val outStream = PrintStream(openFileOutput("score.txt", MODE_APPEND))
        outStream.println(line)
        outStream.close()
    }

    fun cleantext() {
        val outStream = PrintStream(openFileOutput("score.txt", MODE_PRIVATE))
        outStream.println()
        outStream.close()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login)
        Log.d("LifeCycle", "onCreate() is called")
        println("writeScore done")
        try {
            // Load local file into LeaderboardScore if it exists
////            for clean demo:
            val scanner4 = Scanner(openFileInput("score.txt"))
            readFile2(scanner4)
        } catch (exception: Exception) {
//            // Most likely the local file hasn't been created
//            cleantext()
            writeScore("maars\tmaars1\t0")
            writeScore("Ron\tmaars1\t3")
            writeScore("Jane\tmaars1\t5")

        }
        val scanner4 = Scanner(openFileInput("score.txt"))
        readFile2(scanner4)
    }

    private fun readFile2(scanner: Scanner){
        while(scanner.hasNextLine()){
            val line = scanner.nextLine()
            val pieces = line.split("\t")
            println("pieces: -${line}-${pieces.size}")
            if (pieces.size >= 3){
                usernamePassword[pieces[0]] = pieces[1]
            }
        }
    }

    fun loginSubmit(view: View) {
        val scanner4 = Scanner(openFileInput("score.txt"))
        readFile2(scanner4)
        val username = findViewById<EditText>(R.id.loginUsername)
        val password = findViewById<EditText>(R.id.loginPassword)
        var usernametext = username.text.toString()
        var passwordText = password.text.toString()
        if (usernamePassword.containsKey(usernametext) &&
            usernamePassword[usernametext] == passwordText &&
            !usernametext.isNullOrEmpty() && !passwordText.isNullOrEmpty()
        ){
            println("entered!   $usernametext -- $passwordText ")
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            val myIntent2 = Intent(this, MainActivity::class.java)
            println("intent: " + username.text)
            myIntent2.putExtra("username", username.text.toString())
            username.text.clear()
            password.text.clear()

//            go to game:
            startActivity(myIntent2)
        }else{
            val one = usernamePassword[usernametext];
            println("why   $usernametext -- $passwordText --$one")
            Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show()
            username.text.clear()
            password.text.clear()
        }

    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    }

}
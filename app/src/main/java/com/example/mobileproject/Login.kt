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
import java.io.PrintStream
import java.util.HashMap
import java.util.Scanner


class Login : AppCompatActivity() {
    private var words = ArrayList<String>()
    private var wordToDefn = HashMap<String,String>()
    private var usernamePassword = HashMap<String,String>()

//    val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//        if(it.resultCode == Activity.RESULT_OK){
//            val newWord = it.data?.getStringExtra("newWord") ?:""
//            val newDefn = it.data?.getStringExtra("newDefn") ?:""
//            wordToDefn.put(newWord, newDefn)
//            words.add(newWord)
//        }
//    }

    private fun writeScore(line: String){
        val outStream = PrintStream(openFileOutput("score.txt", MODE_APPEND))
        outStream.println(line)
        outStream.close()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login)
        Log.d("LifeCycle", "onCreate() is called")
//        create method to insert these entries if not there:
//        writeScore("maars\tmaars1\t4")
//        writeScore("Ron\tmaars1\t17")
//        writeScore("Jane\tmaars1\t10")
        println("writeScore done")
//        val scanner3  = Scanner(resources.openRawResource(R.raw.username_password))
        val scanner4 = Scanner(openFileInput("score.txt"))

//        readFile2(scanner3)
        readFile2(scanner4)
//        for ((key, value) in usernamePassword) {
//            println("mmmmm $key = $value")
//        }
//
//        val usernameEtc = findViewById<EditText>(R.id.loginUsername)
//        val passwordEtc = findViewById<EditText>(R.id.loginPassword)
//        usernameEtc.hint = "Enter username"
//        passwordEtc.hint = "Enter password"

        val registerButton:Button = findViewById(R.id.register)

        registerButton.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }

    private fun readFile2(scanner: Scanner){
        while(scanner.hasNextLine()){
            val line = scanner.nextLine()
            val pieces = line.split("\t")
            println("pieces: -${line}-${pieces.size}")
            if (pieces.size >= 3){
                usernamePassword[pieces[0]] = pieces[1]
            }
//            println("pieces: ${pieces[0]}-${pieces[1]}-${pieces[2]}")
//            passwords.add(pieces[0])

        }
    }

    fun loginSubmit(view: View) {

//        val scanner3  = Scanner(resources.openRawResource(R.raw.username_password))
//        leaderboard_scores_local2
        val scanner4 = Scanner(openFileInput("score.txt"))
//        readFile2(scanner3)
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
            username.text.clear()
            password.text.clear()

//            go to game:
            val myIntent2 = Intent(this, MainActivity::class.java)
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
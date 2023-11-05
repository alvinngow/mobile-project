package com.example.mobileproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.PrintStream
import java.util.HashMap
import java.util.Scanner


class Login : AppCompatActivity() {
    private var usernamePassword = HashMap<String,String>()

    private fun writeScore(line: String){
        val outStream = PrintStream(openFileOutput("users.txt", MODE_APPEND))
        outStream.println(line)
        outStream.close()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        try {
            // Get user accounts from users.txt
            val scanner4 = Scanner(openFileInput("users.txt"))
            readFile2(scanner4)
        } catch (exception: Exception) {
            // Create default user accounts if the users.txt hasn't been created
            writeScore("maars\tmaars1")
            writeScore("Ron\tron1")
            writeScore("Jane\tjane1")
        }

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
            if (pieces.size >= 2){
                usernamePassword[pieces[0]] = pieces[1]
            }
        }
    }

    fun loginSubmit(view: View) {
        val scanner4 = Scanner(openFileInput("users.txt"))
        readFile2(scanner4)
        val username = findViewById<EditText>(R.id.loginUsername)
        val password = findViewById<EditText>(R.id.loginPassword)
        val usernametext = username.text.toString()
        val passwordText = password.text.toString()


        if (usernamePassword.containsKey(usernametext) &&
            usernamePassword[usernametext] == passwordText &&
            !usernametext.isNullOrEmpty() && !passwordText.isNullOrEmpty()
        ) {
            println("entered!   $usernametext -- $passwordText ")
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show()
            username.text.clear()
            password.text.clear()

//            go to game:
            val myIntent2 = Intent(this, MainActivity::class.java)
            myIntent2.putExtra("username", "$usernametext")
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
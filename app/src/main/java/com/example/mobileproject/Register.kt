package com.example.mobileproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import java.io.PrintStream
import java.util.Scanner

class Register : AppCompatActivity() {

    private fun checkIfUserExist(): ArrayList<String> {
        val scoreScanner = Scanner(openFileInput("score.txt"))

        val usernames = ArrayList<String>()

        while (scoreScanner.hasNextLine()) {
            val line = scoreScanner.nextLine()
            val pieces = line.split("\t")
            println("pieces: -${line}-${pieces.size}")
            usernames.add(pieces[0])
        }

        return usernames
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username: TextInputEditText = findViewById(R.id.username)
        val password: TextInputEditText = findViewById(R.id.password)

        val submitBtn: Button = findViewById(R.id.submitBtn)



        submitBtn.setOnClickListener {


            val users = checkIfUserExist()
            val usernameText = username.text.toString()
            val passwordText = password.text.toString()

            if (users.contains(usernameText)) {
                Toast.makeText(this, "Username exists", Toast.LENGTH_SHORT).show()
            } else {
                val registerStream = PrintStream(openFileOutput("score.txt", MODE_APPEND))


                registerStream.println(usernameText + '\t' + passwordText + '\t' + '0')
                finish()

            }


        }
    }
}
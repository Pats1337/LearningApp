package com.github.pats1337.learningapp

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val string = "This is title from code!"
        titleTextView.text = string
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            titleTextView.setTextColor(resources.getColor(R.color.black, null))
        }else{
            titleTextView.setTextColor(resources.getColor(R.color.black))
        }
        titleTextView.visibility = View.INVISIBLE
    }
}
package com.github.pats1337.learningapp


import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    private companion object {
        const val INITIAL = 0
        const val PROGRESS = 1
        const val SUCCESS = 2
        const val FAILED = 3
    }

    private var state = INITIAL

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText

    private val textWatcher = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            Log.d(TAG, "changed ${s.toString()}")
            textInputLayout.isErrorEnabled = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "OnCreate ${savedInstanceState == null}")
        savedInstanceState?.let {
            state = it.getInt("screenState")
        }
        Log.d(TAG, "state is $state")

        val contentLayout = findViewById<ViewGroup>(R.id.contentLayout)
        val progressBar = findViewById<View>(R.id.progressBar)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)

        textInputLayout = findViewById(R.id.textInputLayout)
        textInputEditText = textInputLayout.editText as TextInputEditText

        loginButton.isEnabled = false
        checkBox.setOnCheckedChangeListener { _, isChecked -> loginButton.isEnabled = isChecked }

        loginButton.setOnClickListener {
            if (EMAIL_ADDRESS.matcher(textInputEditText.text.toString()).matches()) {
                contentLayout.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                state = PROGRESS
                Handler(Looper.myLooper()!!).postDelayed({
                    state = FAILED
                    contentLayout.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    showDialog(contentLayout)
                }, 3000)
            } else {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error = getString(R.string.invalid_email_message)
            }
        }
        when (state) {
            FAILED -> showDialog(contentLayout)
            SUCCESS -> {
                Snackbar.make(contentLayout, "Success", Snackbar.LENGTH_LONG).show()
                state = INITIAL
            }
        }
    }

    private fun showDialog(viewGroup: ViewGroup) {
        val dialog = Dialog(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog, viewGroup, false)
        dialog.setCancelable(false)
        view.findViewById<View>(R.id.closeButton).setOnClickListener {
            state = INITIAL
            dialog.dismiss()
        }
        dialog.setContentView(view)
        dialog.show()
    }


    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
        textInputEditText.removeTextChangedListener(textWatcher)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        textInputEditText.addTextChangedListener(textWatcher)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("screenState", state)
    }
}


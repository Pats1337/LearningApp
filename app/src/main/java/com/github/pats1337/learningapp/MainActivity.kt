package com.github.pats1337.learningapp


import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        val textInputEditText = findViewById<TextInputEditText>(R.id.textInputEditText)
        val contentLayout = findViewById<View>(R.id.contentLayout)
        val progressBar = findViewById<View>(R.id.progressBar)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val checkBox = findViewById<CheckBox>(R.id.checkBox)

        loginButton.isEnabled = false
        checkBox.setOnCheckedChangeListener { _, isChecked -> loginButton.isEnabled = isChecked }

        loginButton.setOnClickListener {
            if (EMAIL_ADDRESS.matcher(textInputEditText.text.toString()).matches()) {
                loginButton.isEnabled = false
                contentLayout.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                Snackbar.make(loginButton, "Go to postLogin", Snackbar.LENGTH_LONG).show()
                Handler(Looper.myLooper()!!).postDelayed({
                    contentLayout.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle(R.string.attention).setMessage(R.string.service_is_unavailable)
                        .setNeutralButton("Cancel") { dialogInterface, which -> }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()
                }, 3000)
            } else {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error = getString(R.string.invalid_email_message)
            }
        }

        textInputEditText.listenChanges { textInputLayout.isErrorEnabled = false }

    }


    fun TextInputEditText.listenChanges(block: (text: String) -> Unit) {
        addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                block.invoke(s.toString())
            }
        })
    }

}


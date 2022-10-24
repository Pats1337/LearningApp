package com.github.pats1337.learningapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns.EMAIL_ADDRESS
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        val textInputEditText = findViewById<TextInputEditText>(R.id.textInputEditText)

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            if (EMAIL_ADDRESS.matcher(textInputEditText.text.toString()).matches()) {
                Snackbar.make(loginButton, "Go to post login", Snackbar.LENGTH_LONG).show()
            } else {
                textInputLayout.isErrorEnabled = true
                textInputLayout.error = getString(R.string.invalid_email_message)
            }
        }

        textInputEditText.listenChanges { textInputLayout.isErrorEnabled = false }

        val checkBox = findViewById<CheckBox>(R.id.checkBox)
        val spannableString = SpannableString(getString(R.string.agreement_full_text))
        checkBox.text = spannableString
        loginButton.isEnabled = false
        checkBox.setOnCheckedChangeListener { _, isChecked -> loginButton.isEnabled = isChecked }

    }

    fun TextInputEditText.listenChanges(block: (text: String) -> Unit) {
        addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                block.invoke(s.toString())
            }
        })
    }
}


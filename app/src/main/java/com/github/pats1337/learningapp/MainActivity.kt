package com.github.pats1337.learningapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout)
        val textInputEditText = textInputLayout.editText as TextInputEditText

        textInputEditText.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(p0: Editable?) {
                val valid = android.util.Patterns.EMAIL_ADDRESS.matcher(p0.toString()).matches()
                textInputLayout.isErrorEnabled = !valid
                val error = if (valid) "" else getString(R.string.invalid_email_message)
                textInputLayout.error = error
                if (valid) Toast.makeText(
                    this@MainActivity,
                    R.string.valid_email_message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }
}
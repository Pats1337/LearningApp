package com.github.pats1337.learningapp

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private companion object {
        const val URL =
            "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiWj4C2qWdTfHtZS11SKHgE2rae404ImVR7gaXjZarO9aUYkvJR0LNbzR3r2OWajQIlu2W7_b7xz09lw_bqLVLwLBG_BcuZzg0s6nkJ8UIxzAz0ETcoAJVseLURltDI_rhhBSmcQ5Xu4FyeCR5mfZO1WQ-WZUNPCzBkxH96vAezjp3Szyb_8cqVIGip/w400-h400/image1.png"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val image = findViewById<ImageView>(R.id.ImageView)
        val netImage = NetImage(URL, object : ImageCallback {
            override fun success(bitmap: Bitmap) {
                image.setImageBitmap(bitmap)
            }

            override fun failed() {
                Snackbar.make(image, "failed", Snackbar.LENGTH_SHORT).show()
            }
        })
        netImage.start()
    }
}
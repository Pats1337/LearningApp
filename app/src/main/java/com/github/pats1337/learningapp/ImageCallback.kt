package com.github.pats1337.learningapp

import android.graphics.Bitmap

interface ImageCallback {

    fun success(bitmap: Bitmap)

    fun failed()
}
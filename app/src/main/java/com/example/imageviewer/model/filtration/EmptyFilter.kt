package com.example.imageviewer.model.filtration


import android.graphics.Bitmap
import com.example.imageviewer.core.BitmapFilter

class EmptyFilter : BitmapFilter {

    override fun apply(bitmap: Bitmap): Bitmap {
        return bitmap
    }
}
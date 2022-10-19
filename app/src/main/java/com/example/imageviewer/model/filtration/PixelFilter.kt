package com.example.imageviewer.model.filtration

import android.graphics.Bitmap
import com.example.imageviewer.core.BitmapFilter
import com.example.imageviewer.utils.applyPixelFilter

class PixelFilter : BitmapFilter {

    override fun apply(bitmap: Bitmap): Bitmap {
        return applyPixelFilter(bitmap)
    }
}
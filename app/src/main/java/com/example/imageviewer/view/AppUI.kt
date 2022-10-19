package com.example.imageviewer.view

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import com.example.imageviewer.model.AppState
import com.example.imageviewer.model.ScreenType
import com.example.imageviewer.model.ContentState
import com.example.imageviewer.style.Gray

@Composable
fun AppUI(content: ContentState) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Gray
    ) {
        when (AppState.screenState()) {
            ScreenType.MainScreen -> {
                MainScreen(content)
            }
            ScreenType.FullscreenImage -> {
                FullscreenImage(content)
            }
        }
    }
}

fun showPopUpMessage(text: String, context: Context) {
    Toast.makeText(
        context,
        text,
        Toast.LENGTH_SHORT
    ).show()
}
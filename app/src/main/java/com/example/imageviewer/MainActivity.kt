package com.example.imageviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.imageviewer.model.ContentState
import com.example.imageviewer.view.AppUI

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val content = ContentState.applyContent(
            this@MainActivity,
            "https://raw.githubusercontent.com/JetBrains/compose-jb/master/artwork/imageviewerrepo/fetching.list"
        )

        setContent {
            AppUI(content)
        }
    }
}
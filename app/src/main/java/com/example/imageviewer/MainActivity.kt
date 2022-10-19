package com.example.imageviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.imageviewer.model.ContentState
import com.example.imageviewer.ui.theme.ImageViewerTheme
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
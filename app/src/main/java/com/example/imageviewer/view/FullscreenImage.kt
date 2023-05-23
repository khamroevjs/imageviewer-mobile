package com.example.imageviewer.view

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.example.imageviewer.core.FilterType
import com.example.imageviewer.model.AppState
import com.example.imageviewer.model.ContentState
import com.example.imageviewer.model.ScreenType
import com.example.imageviewer.style.DarkGray
import com.example.imageviewer.style.Foreground
import com.example.imageviewer.style.MiniatureColor
import com.example.imageviewer.style.Transparent
import com.example.imageviewer.style.icBack
import com.example.imageviewer.style.icFilterBlurOff
import com.example.imageviewer.style.icFilterBlurOn
import com.example.imageviewer.style.icFilterGrayscaleOff
import com.example.imageviewer.style.icFilterGrayscaleOn
import com.example.imageviewer.style.icFilterPixelOff
import com.example.imageviewer.style.icFilterPixelOn
import com.example.imageviewer.utils.adjustImageScale
import com.example.imageviewer.utils.cropBitmapByScale
import com.example.imageviewer.utils.displayWidth
import kotlin.math.abs

@Composable
fun FullscreenImage(
    content: ContentState
) {
    Column {
        ToolBar(content.getSelectedImageName(), content)
        Image(content)
    }
    if (!content.isContentReady()) {
        LoadingScreen()
    }
}

@Composable
fun ToolBar(
    text: String,
    content: ContentState
) {
    val scrollState = rememberScrollState()
    Surface(color = MiniatureColor, modifier = Modifier.height(44.dp)) {
        Row(modifier = Modifier.padding(end = 30.dp)) {
            Surface(
                color = Transparent,
                modifier = Modifier.padding(start = 20.dp).align(Alignment.CenterVertically),
                shape = CircleShape
            ) {
                Clickable(
                    onClick = {
                        if (content.isContentReady()) {
                            content.restoreMainImage()
                            AppState.screenState(ScreenType.MainScreen)
                        }
                    }) {
                    Image(
                        icBack(),
                        contentDescription = null,
                        modifier = Modifier.size(38.dp)
                    )
                }
            }
            Text(
                text,
                color = Foreground,
                maxLines = 1,
                modifier = Modifier.padding(start = 30.dp).weight(1f)
                    .align(Alignment.CenterVertically),
                style = MaterialTheme.typography.bodyLarge
            )

            Surface(
                color = Color(255, 255, 255, 40),
                modifier = Modifier.size(154.dp, 38.dp)
                    .align(Alignment.CenterVertically),
                shape = CircleShape
            ) {
                Row(Modifier.horizontalScroll(scrollState)) {
                    for (type in FilterType.values()) {
                        FilterButton(content, type)
                    }
                }
            }
        }
    }
}

@Composable
fun FilterButton(
    content: ContentState,
    type: FilterType,
    modifier: Modifier = Modifier.size(38.dp)
) {
    Box(
        modifier = Modifier.background(color = Transparent).clip(CircleShape)
    ) {
        Clickable(
            onClick = { content.toggleFilter(type) }
        ) {
            Image(
                getFilterImage(type = type, content = content),
                contentDescription = null,
                modifier
            )
        }
    }

    Spacer(Modifier.width(20.dp))
}

@Composable
fun getFilterImage(type: FilterType, content: ContentState): Painter {
    return when (type) {
        FilterType.GrayScale -> if (content.isFilterEnabled(type)) icFilterGrayscaleOn() else icFilterGrayscaleOff()
        FilterType.Pixel -> if (content.isFilterEnabled(type)) icFilterPixelOn() else icFilterPixelOff()
        FilterType.Blur -> if (content.isFilterEnabled(type)) icFilterBlurOn() else icFilterBlurOff()
    }
}

@Composable
fun Image(content: ContentState) {
    val drag = remember { DragHandler() }
    val scale = remember { ScaleHandler() }

    Surface(
        color = DarkGray,
        modifier = Modifier.fillMaxSize()
    ) {
        Draggable(dragHandler = drag, modifier = Modifier.fillMaxSize()) {
            Scalable(onScale = scale, modifier = Modifier.fillMaxSize()) {
                val bitmap = imageByGesture(content, scale, drag)
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = adjustImageScale(bitmap)
                )
            }
        }
    }
}

@Composable
fun imageByGesture(
    content: ContentState,
    scale: ScaleHandler,
    drag: DragHandler
): Bitmap {
    val bitmap = cropBitmapByScale(content.getSelectedImage(), scale.factor.value, drag)

    if (scale.factor.value > 1f)
        return bitmap

    if (abs(drag.getDistance().x) > displayWidth() / 10) {
        if (drag.getDistance().x < 0) {
            content.swipeNext()
        } else {
            content.swipePrevious()
        }
        drag.cancel()
    }

    return bitmap
}

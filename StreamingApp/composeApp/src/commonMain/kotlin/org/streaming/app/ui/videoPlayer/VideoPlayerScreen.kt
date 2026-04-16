package org.streaming.app.ui.videoPlayer

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VideoPlayerScreen(onBackClick: () -> Unit) {
    // na mobilnom landscape
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Text("Video Player Placeholder", color = Color.White, modifier = Modifier.align(Alignment.Center))

        IconButton(onClick = onBackClick, modifier = Modifier.padding(16.dp).align(Alignment.TopStart)) {
            Icon(Icons.Default.Close, contentDescription = "Close Player", tint = Color.White)
        }
    }
}
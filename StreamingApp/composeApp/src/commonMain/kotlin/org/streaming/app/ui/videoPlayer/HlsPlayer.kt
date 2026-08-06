package org.streaming.app.ui.videoPlayer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun HlsPlayer(
    url: String,
    modifier: Modifier = Modifier
)

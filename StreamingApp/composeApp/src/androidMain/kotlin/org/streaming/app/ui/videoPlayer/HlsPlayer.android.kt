package org.streaming.app.ui.videoPlayer

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@Composable
actual fun HlsPlayer(url: String, modifier: Modifier) {
    val context = LocalContext.current

    val player = remember {
        ExoPlayer.Builder(context)
            .build()
            .apply {

                val mediaItem =
                    MediaItem.fromUri(
                        Uri.parse(url)
                    )

                setMediaItem(mediaItem)

                prepare()

                playWhenReady = true
            }
    }


    DisposableEffect(Unit) {

        onDispose {
            player.release()
        }
    }


    AndroidView(
        factory = {
            PlayerView(it).apply {

                this.player = player

                useController = true

            }
        },
        modifier = modifier
    )
}
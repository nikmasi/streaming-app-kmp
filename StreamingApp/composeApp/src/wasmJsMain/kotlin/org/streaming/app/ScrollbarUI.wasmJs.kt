package org.streaming.app

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
actual fun CommonVerticalScrollbar(
    state: LazyListState,
    modifier: Modifier,
) {
    VerticalScrollbar(
        modifier = modifier.fillMaxHeight().padding(end = 2.dp),
        adapter = rememberScrollbarAdapter(state),
        style = defaultScrollbarStyle().copy(
            unhoverColor = Color.White.copy(alpha = 0.2f),
            hoverColor = Color.Red.copy(alpha = 0.7f)
        )
    )
}

@Composable
actual fun CommonVerticallGridScrollbar(
    state: LazyGridState,
    modifier: Modifier,
) {
    VerticalScrollbar(
        modifier = modifier.fillMaxHeight().padding(end = 2.dp),
        adapter = rememberScrollbarAdapter(state),
        style = defaultScrollbarStyle().copy(
            unhoverColor = Color.White.copy(alpha = 0.2f),
            hoverColor = Color.Red.copy(alpha = 0.7f)
        )
    )
}

@Composable
actual fun CommonVerticalScrollbar(
    state: ScrollState,
    modifier: Modifier,
) {
    VerticalScrollbar(
        modifier = modifier,
        adapter = rememberScrollbarAdapter(state)
    )
}
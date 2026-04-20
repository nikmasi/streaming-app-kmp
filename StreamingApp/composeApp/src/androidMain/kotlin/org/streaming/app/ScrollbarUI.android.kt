package org.streaming.app

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun CommonVerticalScrollbar(
    state: LazyListState,
    modifier: Modifier,
) {
}

@Composable
actual fun CommonVerticallGridScrollbar(
    state: LazyGridState,
    modifier: Modifier,
) {
}

@Composable
actual fun CommonVerticalScrollbar(
    state: ScrollState,
    modifier: Modifier,
) {
}
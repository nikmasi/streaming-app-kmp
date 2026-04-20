package org.streaming.app

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun CommonVerticalScrollbar(
    state: LazyListState,
    modifier: Modifier = Modifier
)

@Composable
expect fun CommonVerticallGridScrollbar(
    state: LazyGridState,
    modifier: Modifier = Modifier
)

@Composable
expect fun CommonVerticalScrollbar(
    state: ScrollState,
    modifier: Modifier = Modifier
)
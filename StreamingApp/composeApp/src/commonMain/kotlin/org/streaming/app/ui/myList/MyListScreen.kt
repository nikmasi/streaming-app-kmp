package org.streaming.app.ui.myList

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyListScreen(onMovieClick: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Text(
            text = "My List",
            style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(6) { index ->
                Box(
                    modifier = Modifier
                        .aspectRatio(2/3f)
                        .background(Color.DarkGray)
                        .clickable { onMovieClick("my_list_$index") }
                )
            }
        }
    }
}
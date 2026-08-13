package org.streaming.app.ui.myList

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.streaming.app.CommonVerticalGridScrollbar
import org.streaming.app.networking.model.ListType
import org.streaming.app.ui.search.MovieItem

@Composable
fun MyListScreen(
    onMovieClick: (String, String, String, List<String>, Int, Int, String, String) -> Unit,
    myListViewModel: MyListViewModel,
    email: String
) {
    val myList = myListViewModel.myList

    LaunchedEffect(Unit){
        myListViewModel.myList(email = email, type = ListType.MY_LIST)
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Text(
            text = "My List",
            style = TextStyle(color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

        val lazyListState = rememberLazyGridState()

        Box(modifier = Modifier.fillMaxSize()){
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                state = lazyListState
            ) {
                items(myList.size) { index ->
                    val movie = myList[index]
                    MovieItem(movie = movie, onClick = {
                        onMovieClick(
                            movie.id,
                            movie.title,
                            movie.description,
                            movie.genres,
                            movie.duration,
                            movie.releaseYear,
                            movie.thumbnailUrl,
                            movie.videoUrl
                        )
                    })
                }
            }
            CommonVerticalGridScrollbar(
                state = lazyListState,
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
            )
        }
    }
}
package org.streaming.app.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.streaming.app.networking.model.Movie
import streamingapp.composeapp.generated.resources.Res
import streamingapp.composeapp.generated.resources.get_started

@Composable
fun HomeScreen(
    onMovieClick: (String, String, String, Int, Int, String, String) -> Unit,
    homeViewModel: HomeViewModel
) {
    LaunchedEffect(Unit) {
        homeViewModel.dramaTop5()
        homeViewModel.releaseYearTop5()
    }

    val top5 = homeViewModel.top5
    val genreTop5 = homeViewModel.top5Drama
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item { FeaturedSection() }

            item { MovieCategory("Newest Releases", onMovieClick, false,top5) }
            item { MovieCategory("Newest Releases Drama", onMovieClick, false, genreTop5) }

            item { Spacer(modifier = Modifier.height(180.dp)) }

        }
        TopNavigation()
    }
}

@Composable
fun FeaturedSection() {
    Box(modifier = Modifier.fillMaxWidth().height(500.dp)) {
        Image(
            painter = painterResource(Res.drawable.get_started),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier.fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f), Color.Black),
                        startY = 200f
                    )
                )
        )

        Column(
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Sci-Fi", color = Color.White, fontSize = 12.sp)
                Text(" • ", color = Color.Red)
                Text("Action", color = Color.White, fontSize = 12.sp)
                Text(" • ", color = Color.Red)
                Text("Drama", color = Color.White, fontSize = 12.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("Play", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.6f)),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("My List", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun MovieCategory(title: String, onMovieClick: (String, String, String, Int, Int, String, String) -> Unit
                  , isLarge: Boolean = false, movies: List<Movie>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movies) { movie ->
                Box(
                    modifier = Modifier
                        .width(if (isLarge) 150.dp else 110.dp)
                        .height(if (isLarge) 220.dp else 160.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.DarkGray)
                        .clickable { onMovieClick(
                            movie.title, movie.description, movie.genre,
                            movie.duration, movie.releaseYear, movie.thumbnailUrl, movie.videoUrl
                        ) }
                ) {
                    Text(
                        "Movie ${movie.title}",
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 10.sp
                    )

                    AsyncImage(
                        model =  movie.thumbnailUrl,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun TopNavigation() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 40.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("N", color = Color.Red, fontSize = 35.sp, fontWeight = FontWeight.ExtraBold)

        Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
            Text("TV Shows", color = Color.White, fontSize = 14.sp)
            Text("Movies", color = Color.White, fontSize = 14.sp)
            Text("Categories", color = Color.White, fontSize = 14.sp)
        }
    }
}
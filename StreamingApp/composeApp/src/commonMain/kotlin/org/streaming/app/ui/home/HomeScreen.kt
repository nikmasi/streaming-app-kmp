package org.streaming.app.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.streaming.app.CommonVerticalScrollbar
import org.streaming.app.networking.Constants
import org.streaming.app.networking.model.Movie
import org.streaming.app.networking.model.WatchProgress
import org.streaming.app.ui.auth.LoginScreen

@Composable
fun HomeScreen(
    onMovieClick: (String, String, String, List<String>, Int, Int, String, String) -> Unit,
    homeViewModel: HomeViewModel
) {
    LaunchedEffect(Unit) {
        homeViewModel.getHomeContent()
        homeViewModel.continueWatchingContent()
    }

    val continueWatchingContent = homeViewModel.continueWatchingContent

    val homeContent = homeViewModel.homeContent

    val lazyListState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = lazyListState
        ) {
            item { FeaturedSection(homeViewModel.top5) }

            item {
                WatchingMovieContent(
                    title = "Continue watching",
                    movies = continueWatchingContent,
                    onMovieClick = onMovieClick,
                    isLarge = false
                )
            }

            homeContent.forEach { (genre, movies) ->
                item {
                    MovieCategory(
                        title = "Top $genre",
                        movies = movies,
                        onMovieClick = onMovieClick,
                        isLarge = false
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(180.dp)) }
        }

        CommonVerticalScrollbar(
            state = lazyListState,
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight()
        )

        TopNavigation()
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FeaturedSection(movies: List<Movie>) {

    if (movies.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
                .background(Color.Black)
        )
        return
    }

    val pagerState = rememberPagerState(pageCount = { movies.size })

    // auto-scroll (stabilan, bez memory leak-a)
    LaunchedEffect(movies.size) {
        if (movies.size > 1) {
            while (true) {
                kotlinx.coroutines.delay(4000)

                val nextPage =
                    if (pagerState.currentPage + 1 < movies.size)
                        pagerState.currentPage + 1
                    else 0

                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(modifier = Modifier.fillMaxWidth().height(380.dp)) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->

            val movie = movies[page]

            AsyncImage(
                model = Constants.imageUrl(movie.thumbnailUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f),
                                Color.Black
                            )
                        )
                    )
            )

            // title overlay
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
            )
        }
    }
}
@Composable
fun MovieCategory(
    title: String,
    onMovieClick: (String, String, String, List<String>, Int, Int, String, String) -> Unit,
    isLarge: Boolean = false, movies: List<Movie>
) {
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
                        .clickable {
                            onMovieClick(movie.id, movie.title, movie.description,
                                movie.genres, movie.duration, movie.releaseYear,
                                movie.thumbnailUrl, movie.videoUrl)
                        }
                ) {
                    AsyncImage(
                        model = Constants.imageUrl(movie.thumbnailUrl),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                    startY = 100f
                                )
                            )
                    )

                    Text(
                        text = movie.title,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.BottomStart).padding(8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Composable
fun WatchingMovieContent(
    title: String,
    onMovieClick: (String, String, String, List<String>, Int, Int, String, String) -> Unit,
    isLarge: Boolean = false, movies: List<WatchProgress>
) {
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
                        .clickable {
                            onMovieClick(movie.movie.id, movie.movie.title,
                                movie.movie.description,
                                movie.movie.genres,
                                movie.movie.duration, movie.movie.releaseYear,
                                movie.movie.thumbnailUrl, movie.movie.videoUrl)
                        }
                ) {
                    AsyncImage(
                        model = Constants.imageUrl(movie.movie.thumbnailUrl),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                                    startY = 100f
                                )
                            )
                    )

                    Text(
                        text = movie.movie.title,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.BottomStart).padding(8.dp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
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
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    //HomeScreen()
}
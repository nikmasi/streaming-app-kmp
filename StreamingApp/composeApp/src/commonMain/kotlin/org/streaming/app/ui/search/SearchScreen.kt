package org.streaming.app.ui.search

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.streaming.app.CommonVerticalGridScrollbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: MovieViewModel,
    onMovieClick: (String, String, String, List<String>, Int, Int, String, String) -> Unit,
    userEmail: String
) {
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit){
        viewModel.searchHistory(email = userEmail)
    }

    val searchHistory = viewModel.searchHistory

    Column(modifier = Modifier.fillMaxSize().background(Color.Black).statusBarsPadding()) {
        Surface(
            color = Color(0xFF121212),
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.search(title = it, email = userEmail)
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
                placeholder = {
                    Text("Pretraži filmove ili žanrove", color = Color.Gray, fontSize = 15.sp)
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = {
                            searchQuery = ""
                            viewModel.search(title = "", email = userEmail)
                        }) {
                            Icon(Icons.Default.Close, contentDescription = null, tint = Color.LightGray)
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF2B2B2B),
                    unfocusedContainerColor = Color(0xFF2B2B2B),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFFE50914)
                )
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (searchQuery.isEmpty()) "Top pretrage" else "Rezultati za \"$searchQuery\"",
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 16.dp, bottom = 12.dp)
        )

        if (searchQuery.isEmpty()) {

            Text(
                text = "Istorija pretrage",
                color = Color.LightGray,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                searchHistory.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                searchQuery = item.query
                                viewModel.search(title = item.query, email = userEmail)
                            }
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = item.query,
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        if (viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFFE50914), modifier = Modifier.size(30.dp))
            }
        }

        if (viewModel.movies.isEmpty() && searchQuery.isNotEmpty() && !viewModel.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(bottom = 100.dp), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Search, contentDescription = null, tint = Color.DarkGray, modifier = Modifier.size(64.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Nismo pronašli ništa pod tim imenom.", color = Color.Gray, textAlign = TextAlign.Center)
                }
            }
        } else {
            val lazyListState = rememberLazyGridState()

            Box(modifier = Modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = lazyListState
                ) {
                    items(viewModel.movies.size) { index ->
                        val movie = viewModel.movies[index]
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
}
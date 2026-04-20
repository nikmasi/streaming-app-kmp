package org.streaming.app.ui.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.streaming.app.networking.model.Movie
import org.streaming.app.networking.KtorClient
import org.streaming.app.networking.model.Search

class MovieViewModel(
    private val ktorClient: KtorClient,
) : ViewModel() {
    var movies by mutableStateOf<List<Movie>>(emptyList())
        private set

    var searchHistory by mutableStateOf<List<Search>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    fun search(title: String, email: String) {
        if (title.isBlank()) {
            movies = emptyList()
            return
        }

        viewModelScope.launch {
            isLoading = true
            try {
                movies = ktorClient.searchMovies(title, email)
            } catch (e: Exception) {
                println("Greška pri pretrazi: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun searchHistory(email: String){
        viewModelScope.launch {
            try {
                searchHistory =ktorClient.searchHistoryTop10(email)
                println("search history "+ searchHistory)
            }
            catch (e: Exception){
                println("Greska")
            }
        }
    }
}
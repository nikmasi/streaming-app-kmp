package org.streaming.app.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.streaming.app.networking.KtorClient
import org.streaming.app.networking.model.Movie
import org.streaming.app.networking.model.WatchProgress

class HomeViewModel(
    private val ktorClient: KtorClient
) : ViewModel(){

    var top5 by mutableStateOf<List<Movie>>(emptyList())
        private set

    var top5Drama by mutableStateOf<List<Movie>>(emptyList())
        private set

    var homeContent by mutableStateOf<Map<String, List<Movie>>>(emptyMap())
        private set

    var continueWatchingContent by mutableStateOf<List<WatchProgress>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isLoading2 by mutableStateOf(false)
        private set

    fun getHomeContent() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = ktorClient.getHomeData()
                homeContent = response
            } catch (e: Exception) {
                println("Greška pri dohvatanju: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    fun continueWatchingContent(){
        viewModelScope.launch {
            isLoading2 = true
            try {
                val response = ktorClient.getHistoryData()
                continueWatchingContent = response
            } catch (e: Exception) {
                println("Greška pri dohvatanju: ${e.message}")
            } finally {
                isLoading2 = false
            }
        }
    }

    fun releaseYearTop5() {
        viewModelScope.launch {
            try {
                top5 = ktorClient.releaseYearTop5Movies()
            } catch (e: Exception) {
                top5 =emptyList()
            }
        }
    }

    fun dramaTop5() {
        viewModelScope.launch {
            try {
                top5Drama = ktorClient.genreTop5Movies("Drama")
            } catch (e: Exception) {
                top5Drama =emptyList()
            }
        }
    }
}
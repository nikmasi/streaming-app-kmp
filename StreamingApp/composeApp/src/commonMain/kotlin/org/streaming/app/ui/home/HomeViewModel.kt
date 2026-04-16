package org.streaming.app.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.streaming.app.networking.KtorClient
import org.streaming.app.networking.model.Movie

class HomeViewModel(
    private val ktorClient: KtorClient
) : ViewModel(){

    var top5 by mutableStateOf<List<Movie>>(emptyList())
        private set

    var top5Drama by mutableStateOf<List<Movie>>(emptyList())
        private set

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
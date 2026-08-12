package org.streaming.app.ui.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.streaming.app.networking.KtorClient
import org.streaming.app.networking.model.Movie
import org.streaming.app.networking.model.ProfileResponse


class ProfileViewModel(
    private val ktorClient: KtorClient
) : ViewModel(){

    var homeContent by mutableStateOf<Map<String, List<Movie>>>(emptyMap())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var userProfile by mutableStateOf(ProfileResponse("","","","",""))
        private set

    fun getProfileInfo() {
        viewModelScope.launch {
            isLoading = true
            try {
                val response = ktorClient.getProfileInfo("gica@test.com")
                userProfile = response
            } catch (e: Exception) {
                println("Greška pri dohvatanju: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }
}
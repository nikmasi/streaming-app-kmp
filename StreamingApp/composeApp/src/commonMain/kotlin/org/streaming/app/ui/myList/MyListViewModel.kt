package org.streaming.app.ui.myList


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.streaming.app.networking.model.Movie
import org.streaming.app.networking.KtorClient
import org.streaming.app.networking.model.ListType

class MyListViewModel(
    private val ktorClient: KtorClient,
) : ViewModel() {
    var myList by mutableStateOf<List<Movie>>(emptyList())
        private set


    fun myList(email: String, type: ListType){
        viewModelScope.launch {
            try {
                myList =ktorClient.myListMovie(email,type)
                println("myList "+ myList)
            }
            catch (e: Exception){
                println("Greska")
            }
        }
    }

    fun addMyList(email: String, movieId: String, type: ListType){
        viewModelScope.launch {
            try {
                ktorClient.addMyList(email,movieId,type)
                println("addMyList "+ myList)
            }
            catch (e: Exception){
                println("Greska")
            }
        }
    }
}
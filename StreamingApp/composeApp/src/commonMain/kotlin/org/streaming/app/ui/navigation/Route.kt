package org.streaming.app.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable data object GetStarted : Route
    @Serializable data object Login : Route
    @Serializable data object SignUp : Route
    @Serializable data object ForgotPassword : Route

    @Serializable data object Home : Route
    @Serializable data object Search : Route
    @Serializable data object MyList : Route
    @Serializable data object Downloads : Route
    @Serializable data object Profile : Route
    @Serializable data class Details(
        val id: Long,
        val title: String, val desc: String, val genre: String,
        val duration:Int, val year: Int, val url: String, val videoUrl: String
    ) : Route
    @Serializable data object VideoPlayer : Route


}
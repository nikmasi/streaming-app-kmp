package org.streaming.app

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.streaming.app.encrypted.createKSafe
import org.streaming.app.networking.KtorClient
import org.streaming.app.ui.auth.AuthViewModel
import org.streaming.app.ui.auth.ForgotPasswordScreen
import org.streaming.app.ui.auth.LoginResult
import org.streaming.app.ui.auth.LoginScreen
import org.streaming.app.ui.auth.SignUpScreen
import org.streaming.app.ui.components.BottomBar
import org.streaming.app.ui.details.DetailsScreen
import org.streaming.app.ui.downloads.DownloadsScreen
import org.streaming.app.ui.getstarted.GetStartedScreen
import org.streaming.app.ui.home.HomeScreen
import org.streaming.app.ui.home.HomeViewModel
import org.streaming.app.ui.myList.MyListScreen
import org.streaming.app.ui.navigation.Route
import org.streaming.app.ui.profile.ProfileScreen
import org.streaming.app.ui.search.MovieViewModel
import org.streaming.app.ui.search.SearchScreen
import org.streaming.app.ui.videoPlayer.VideoPlayerScreen

fun shouldShowBottomBar(route: Any?): Boolean {
    return when (route) {
        Route.Home,
        Route.Search,
        Route.MyList,
        Route.Downloads,
        Route.Profile-> true

        else -> false
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun App() {
    val ktorClient = remember { KtorClient() }
    val authViewModel = AuthViewModel(ktorClient, createKSafe())
    val movieViewModel = MovieViewModel(ktorClient)
    val homeViewModel = HomeViewModel(ktorClient)

    MaterialTheme {
        val backStack = remember { mutableStateListOf<Any>(Route.GetStarted) }
        val currentRoute = backStack.lastOrNull()
        Scaffold(
            bottomBar = {
                if (shouldShowBottomBar(currentRoute)) {
                    BottomBar(
                        currentRoute = currentRoute,
                        onNavigate = { route ->
                            backStack.add(route)
                        }
                    )
                }
            }
        ) { padding ->
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<Route.GetStarted> {
                        val token = authViewModel.token

                        LaunchedEffect(token.accessToken) {
                            if (!token.accessToken.isNullOrEmpty()) {
                                backStack.clear()
                                backStack.add(Route.Home)
                            }
                        }

                        GetStartedScreen(onGetStartedClick = {
                            backStack.add(Route.SignUp)
                        }, onLocalChange = {
                            backStack.add(Route.Login)
                        })
                    }
                    entry<Route.Login> {
                        val loginState = authViewModel.loginState

                        LaunchedEffect(loginState) {
                            if (loginState is LoginResult.Success) {
                                backStack.add(Route.Home)
                                authViewModel.resetState()
                            }
                        }

                        LoginScreen(
                            onLoginClick = { email, password ->
                                authViewModel.login(email, password)
                            },
                            onBackClick = { backStack.add(Route.ForgotPassword) }
                        )
                    }
                    entry<Route.SignUp> {
                        val registerState = authViewModel.registerState

                        LaunchedEffect(registerState) {
                            if (registerState!=null) {
                                backStack.add(Route.Home)
                                authViewModel.resetRegisterState()
                            }
                        }

                        SignUpScreen(
                            onSignUpClick = {email, password,fullName, phone ->
                                authViewModel.register(email, password,fullName, phone)
                            },
                            onBackToLoginClick = {backStack.add(Route.Login)}
                        )
                    }
                    entry<Route.ForgotPassword> {
                        ForgotPasswordScreen(
                            onSendEmailClick = {backStack.add(Route.Home)},
                            onBackClick = {backStack.add(Route.Login)}
                        )
                    }
                    entry<Route.Home> {
                        HomeScreen(
                            onMovieClick = { title, description, genre, duration, releaseYear, thumbnailUrl, videoUrl ->
                                backStack.add( Route.Details(
                                    title, description, genre, duration, releaseYear, thumbnailUrl, videoUrl
                                ))
                            },
                            homeViewModel = homeViewModel
                        )
                    }
                    entry<Route.Search> {
                        SearchScreen(
                            viewModel = movieViewModel,
                            onMovieClick = { title, description, genre, duration, releaseYear, thumbnailUrl, videoUrl ->
                                backStack.add( Route.Details(
                                    title, description, genre, duration, releaseYear, thumbnailUrl, videoUrl
                                ))
                            }
                        )
                    }
                    entry<Route.MyList> {
                        MyListScreen(
                            onMovieClick = {h ->},
                        )
                    }
                    entry<Route.Downloads> {
                        DownloadsScreen()
                    }
                    entry<Route.Details> { route: Route.Details ->
                        DetailsScreen(
                            title = route.title,
                            onPlayClick = { backStack.add(Route.VideoPlayer) },
                            onBackClick = { backStack.removeLastOrNull() },
                            desc = route.desc,
                            genre = route.genre,
                            duration = route.duration,
                            year = route.year,
                            url = route.url,
                            videoUrl = route.videoUrl
                        )
                    }
                    entry<Route.Profile> {
                        ProfileScreen(
                            authViewModel =authViewModel,
                            onLogout = {
                                authViewModel.logout()
                                backStack.clear()
                                backStack.add(Route.GetStarted)
                            }
                        )
                    }
                    entry<Route.VideoPlayer> {
                        VideoPlayerScreen(
                            onBackClick = { backStack.add(Route.Details) }
                        )
                    }
                }
            )
        }

    }
}
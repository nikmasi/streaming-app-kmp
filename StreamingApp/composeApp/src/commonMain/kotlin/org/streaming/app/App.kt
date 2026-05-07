package org.streaming.app

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import org.streaming.app.encrypted.createKSafe
import org.streaming.app.networking.KtorClient
import org.streaming.app.networking.model.ListType
import org.streaming.app.ui.auth.AuthViewModel
import org.streaming.app.ui.auth.ForgotPasswordScreen
import org.streaming.app.ui.auth.LoginResult
import org.streaming.app.ui.auth.LoginScreen
import org.streaming.app.ui.auth.SignUpScreen
import org.streaming.app.ui.details.DetailsScreen
import org.streaming.app.ui.downloads.DownloadsScreen
import org.streaming.app.ui.getstarted.GetStartedScreen
import org.streaming.app.ui.home.HomeScreen
import org.streaming.app.ui.home.HomeViewModel
import org.streaming.app.ui.myList.MyListScreen
import org.streaming.app.ui.myList.MyListViewModel
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

@Composable
@Preview
fun App(backStack: SnapshotStateList<Any>) {
    val ktorClient = remember { KtorClient() }
    val authViewModel = remember { AuthViewModel(ktorClient, createKSafe()) }
    val movieViewModel = remember { MovieViewModel(ktorClient) }
    val homeViewModel = remember { HomeViewModel(ktorClient) }
    val myListViewModel = remember { MyListViewModel(ktorClient) }

    //val backStack = remember { mutableStateListOf<Any>(Route.GetStarted) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Route.GetStarted> {
                val token = authViewModel.token

                LaunchedEffect(token.accessToken) {
                    if (token.accessToken?.isNotEmpty() == true) {
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
                    onMovieClick = { id, title, description, genres, duration, releaseYear, thumbnailUrl, videoUrl ->
                        backStack.add( Route.Details(
                            id, title, description, genres, duration, releaseYear, thumbnailUrl, videoUrl
                        ))
                    },
                    homeViewModel = homeViewModel
                )
            }
            entry<Route.Search> {
                SearchScreen(
                    viewModel = movieViewModel,
                    onMovieClick = { id, title, description, genres, duration, releaseYear, thumbnailUrl, videoUrl ->
                        backStack.add( Route.Details(
                            id, title, description, genres, duration, releaseYear, thumbnailUrl, videoUrl
                        ))
                    },
                    userEmail = authViewModel.userProfile.email
                )
            }
            entry<Route.MyList> {
                MyListScreen(
                    myListViewModel = myListViewModel,
                    onMovieClick = { id, title, description, genres, duration, releaseYear, thumbnailUrl, videoUrl ->
                        backStack.add( Route.Details(
                            id, title, description, genres, duration, releaseYear, thumbnailUrl, videoUrl
                        ))
                    },
                    email = authViewModel.userProfile.email
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
                    genres = route.genres,
                    duration = route.duration,
                    year = route.year,
                    url = route.url,
                    videoUrl = route.videoUrl,
                    onClickMyList = {
                        myListViewModel.addMyList(
                            email = authViewModel.userProfile.email,
                            movieId = route.id,
                            type = ListType.MY_LIST
                        )
                    },
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
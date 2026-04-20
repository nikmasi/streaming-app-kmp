package org.streaming.app

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import org.streaming.app.ui.navigation.Route

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3WindowSizeClassApi::class)
fun main() {
    ComposeViewport {
        MaterialTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF141414)
            ) {
                val backStack = remember { mutableStateListOf<Any>(Route.GetStarted) }
                val currentRoute = backStack.lastOrNull()

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        NavItem("Home", currentRoute is Route.Home) {
                            backStack.add(Route.Home)
                        }
                        NavItem("Search", currentRoute is Route.Search) {
                            backStack.add(Route.Search)
                        }
                        NavItem("My List", currentRoute is Route.MyList) {
                            backStack.add(Route.MyList)
                        }
                        NavItem("Profile", currentRoute is Route.Profile) {
                            backStack.add(Route.Profile)
                        }
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        App(backStack)
                    }
                }
            }
        }
    }
}

@Composable
fun NavItem(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = title,
        modifier = Modifier.padding(horizontal = 12.dp).clickable { onClick() },
        color = if (isSelected) Color.Red else Color.LightGray,
        style = MaterialTheme.typography.titleMedium
    )
}
package org.streaming.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.streaming.app.ui.navigation.Route

@Composable
fun BottomBar(
    currentRoute: Any?,
    onNavigate: (Any) -> Unit
) {
    NavigationBar(containerColor = Color.Black, tonalElevation = 8.dp) {
        //home
        NavigationBarItem(
            selected = currentRoute == Route.Home,
            onClick = { onNavigate(Route.Home) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            },
            label = { Text("Home", fontSize = 10.sp) },
            colors = netflixNavBarColors()
        )

        //search
        NavigationBarItem(
            selected = currentRoute == Route.Search,
            onClick = { onNavigate(Route.Search) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            label = { Text("Search", fontSize = 10.sp) },
            colors = netflixNavBarColors()
        )

        // my list
        NavigationBarItem(
            selected = currentRoute == Route.MyList,
            onClick = { onNavigate(Route.MyList) },
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "My List"
                )
            },
            label = { Text("My List", fontSize = 10.sp) },
            colors = netflixNavBarColors()
        )

        // Downloads
        NavigationBarItem(
            selected = currentRoute == Route.Downloads,
            onClick = { onNavigate(Route.Downloads) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Downloads"
                )
            },
            label = { Text("Downloads", fontSize = 10.sp) },
            colors = netflixNavBarColors()
        )

        NavigationBarItem(
            selected = currentRoute == Route.Profile,
            onClick = { onNavigate(Route.Profile) },
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Profile"
                )
            },
            label = { Text("Profile", fontSize = 10.sp) },
            colors = netflixNavBarColors()
        )
    }
}


@Composable
fun netflixNavBarColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = Color.White,
    selectedTextColor = Color.White,
    unselectedIconColor = Color.Gray,
    unselectedTextColor = Color.Gray,
    indicatorColor = Color.Transparent
)
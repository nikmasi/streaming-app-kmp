package org.streaming.app

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import org.streaming.app.ui.components.BottomBar
import org.streaming.app.ui.navigation.Route

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val backStack = remember { mutableStateListOf<Any>(Route.GetStarted) }
                val currentRoute = backStack.lastOrNull()

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar(currentRoute)) {
                            BottomBar(
                                currentRoute = currentRoute,
                                onNavigate = { backStack.add(it) }
                            )
                        }
                    }
                ) {
                    App(backStack)
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    //App()
}
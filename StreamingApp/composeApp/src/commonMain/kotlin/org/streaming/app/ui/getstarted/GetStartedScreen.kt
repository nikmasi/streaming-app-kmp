package org.streaming.app.ui.getstarted

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import streamingapp.composeapp.generated.resources.Res
import streamingapp.composeapp.generated.resources.get_started
import streamingapp.composeapp.generated.resources.get_started_btn
import streamingapp.composeapp.generated.resources.get_started_desc
import streamingapp.composeapp.generated.resources.get_started_sign_in_btn

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GetStartedScreen(
    onGetStartedClick: () -> Unit,
    onLocalChange: () -> Unit
) {
    Scaffold { padding ->
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black) //.padding(padding)
        ) {
            Image(
                painter = painterResource(Res.drawable.get_started),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f), Color.Black),
                        startY = 0f
                    )
                )
            )

            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(22.dp))
                Text(
                    text = "NETFLIXCLONE",
                    style = TextStyle(
                        color = Color(0xFFE50914),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = (-1).sp,
                        shadow = androidx.compose.ui.graphics.Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = androidx.compose.ui.geometry.Offset(2f, 4f),
                            blurRadius = 4f
                        )
                    ),
                    modifier = Modifier.padding(top = 40.dp)
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(Res.string.get_started),
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 34.sp
                    )
                )
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(Res.string.get_started_desc),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.LightGray,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier.widthIn(max = 400.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(color = Color(0xFFE50914), shape = RoundedCornerShape(4.dp))
                            .clickable { onGetStartedClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.get_started_btn),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(color = Color.White.copy(alpha = 0.1f), shape = RoundedCornerShape(4.dp))
                            .clickable { onLocalChange() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.get_started_sign_in_btn),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(44.dp))
            }
        }
    }
}

@Preview
@Composable
fun GetStartedPreview(){
    GetStartedScreen(onGetStartedClick = {}, onLocalChange = {})
}
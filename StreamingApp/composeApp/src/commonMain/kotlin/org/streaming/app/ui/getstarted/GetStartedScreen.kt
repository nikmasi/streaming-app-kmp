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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
    onGetStartedClick: () ->Unit,
    onLocalChange: () -> Unit
) {
    Scaffold(
        containerColor = Color.Black
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.Black)
        ) {
            Image(
                painter = painterResource(Res.drawable.get_started),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.25f),
                                Color.Black.copy(alpha = 0.85f),
                                Color.Black
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(52.dp))

                Text(
                    text = "NETFLIX",
                    color = Color(0xFFE50914),
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = (-1).sp
                )

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.widthIn(max = 360.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(Res.string.get_started),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        lineHeight = 42.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(Res.string.get_started_desc),
                        color = Color(0xFFE5E5E5),
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .background(
                                Color(0xFFE50914),
                                RoundedCornerShape(4.dp)
                            )
                            .clickable { onGetStartedClick() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.get_started_btn),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .background(
                                Color(0xFF333333),
                                RoundedCornerShape(4.dp)
                            )
                            .clickable { onLocalChange() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(Res.string.get_started_sign_in_btn),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Preview
@Composable
fun GetStartedPreview(){
    GetStartedScreen(onGetStartedClick = {}, onLocalChange = {})
}
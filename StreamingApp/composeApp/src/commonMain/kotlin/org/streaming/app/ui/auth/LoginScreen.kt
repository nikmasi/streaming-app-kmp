package org.streaming.app.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import streamingapp.composeapp.generated.resources.Res
import streamingapp.composeapp.generated.resources.email_phone_input
import streamingapp.composeapp.generated.resources.forgot_password
import streamingapp.composeapp.generated.resources.get_started
import streamingapp.composeapp.generated.resources.password
import streamingapp.composeapp.generated.resources.sign_in
import streamingapp.composeapp.generated.resources.visibility_off

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onBackClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        Image(
            painter = painterResource(Res.drawable.get_started),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(0.4f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.widthIn(max = 400.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.sign_in),
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(28.dp))

                // email input
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(Res.string.email_phone_input)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // password input
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(Res.string.password),
                    isPassword = !isPasswordVisible,
                    trailingIcon = {
                        val icon = if (isPasswordVisible) Res.drawable.visibility_off else Res.drawable.visibility_off
                        Image(
                            painter = painterResource(icon),
                            contentDescription = null,
                            modifier = Modifier.clickable { isPasswordVisible = !isPasswordVisible }
                        )
                    }
                )
                Spacer(modifier = Modifier.height(24.dp))

                // login button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(Color(0xFFE50914), RoundedCornerShape(4.dp))
                        .clickable { onLoginClick(email, password) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.sign_in),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(Res.string.forgot_password),
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { onBackClick() }
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(onLoginClick = {i,l->},{})
}
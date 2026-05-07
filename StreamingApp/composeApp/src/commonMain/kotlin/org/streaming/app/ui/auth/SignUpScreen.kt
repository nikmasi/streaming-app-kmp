package org.streaming.app.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import streamingapp.composeapp.generated.resources.Res
import streamingapp.composeapp.generated.resources.confirm_password
import streamingapp.composeapp.generated.resources.create_account
import streamingapp.composeapp.generated.resources.email
import streamingapp.composeapp.generated.resources.full_name
import streamingapp.composeapp.generated.resources.get_started
import streamingapp.composeapp.generated.resources.have_account
import streamingapp.composeapp.generated.resources.join_us
import streamingapp.composeapp.generated.resources.password
import streamingapp.composeapp.generated.resources.phone
import streamingapp.composeapp.generated.resources.sign_in
import streamingapp.composeapp.generated.resources.sign_up

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpScreen(
    onSignUpClick: (String, String, String, String) -> Unit,
    onBackToLoginClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        Image(
            painter = painterResource(Res.drawable.get_started),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(0.3f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.widthIn(max = 400.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(Res.string.create_account),
                    style = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White),
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(Res.string.join_us),
                    style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(32.dp))

                // name input
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    label = stringResource(Res.string.full_name)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // email input
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(Res.string.email)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // phone input
                TextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = stringResource(Res.string.phone)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // password input
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(Res.string.password),
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                // confirm password input
                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = stringResource(Res.string.confirm_password),
                    isPassword = true
                )
                Spacer(modifier = Modifier.height(32.dp))

                // register Button
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(
                            color = if (password == confirmPassword && password.isNotEmpty())
                                Color(0xFFE50914) else Color.Gray.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable(enabled = password == confirmPassword) {
                            onSignUpClick(email, password, name, phone)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(Res.string.sign_up),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                Row {
                    Text(
                        text = stringResource(Res.string.have_account),
                        color = Color.Gray
                    )
                    Text(
                        text = stringResource(Res.string.sign_in),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onBackToLoginClick() }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen(onSignUpClick = {i,l,k,o->}, onBackToLoginClick = {})
}
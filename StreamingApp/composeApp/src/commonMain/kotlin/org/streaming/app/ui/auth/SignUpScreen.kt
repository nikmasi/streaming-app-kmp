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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import streamingapp.composeapp.generated.resources.first_name
import streamingapp.composeapp.generated.resources.full_name
import streamingapp.composeapp.generated.resources.get_started
import streamingapp.composeapp.generated.resources.have_account
import streamingapp.composeapp.generated.resources.join_us
import streamingapp.composeapp.generated.resources.last_name
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
    var firstname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val isFormValid = firstname.isNotBlank() &&
        email.isNotBlank() && lastname.isNotBlank() &&
        password.isNotBlank() && password == confirmPassword

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(Res.drawable.get_started),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .alpha(.35f),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black.copy(alpha = .45f),
                            Color.Black.copy(alpha = .75f),
                            Color.Black
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            Text(
                text = "NETFLIX",
                color = Color(0xFFE50914),
                fontSize = 34.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp
            )

            Spacer(modifier = Modifier.height(48.dp))

            Column(
                modifier = Modifier.widthIn(max = 420.dp)
            ) {

                Text(
                    text = stringResource(Res.string.create_account),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(Res.string.join_us),
                    color = Color(0xFFB3B3B3),
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(28.dp))

                TextField(
                    value = firstname,
                    onValueChange = { firstname = it },
                    label = stringResource(Res.string.first_name)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = lastname,
                    onValueChange = { lastname = it },
                    label = stringResource(Res.string.last_name)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = stringResource(Res.string.email)
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = stringResource(Res.string.password),
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = stringResource(Res.string.confirm_password),
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(32.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .background(
                            if (isFormValid)
                                Color(0xFFE50914)
                            else
                                Color(0xFF555555),
                            RoundedCornerShape(4.dp)
                        )
                        .clickable(enabled = isFormValid) {
                            onSignUpClick(
                                email,
                                password,
                                firstname,
                                lastname
                            )
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

                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {

                    Text(
                        text = stringResource(Res.string.have_account),
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = stringResource(Res.string.sign_in),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            onBackToLoginClick()
                        }
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen(onSignUpClick = {i,l,k,o->}, onBackToLoginClick = {})
}
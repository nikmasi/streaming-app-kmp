package org.streaming.app.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import streamingapp.composeapp.generated.resources.Res
import streamingapp.composeapp.generated.resources.back_to_sign_in
import streamingapp.composeapp.generated.resources.email
import streamingapp.composeapp.generated.resources.email_reset_password
import streamingapp.composeapp.generated.resources.forgot_password
import streamingapp.composeapp.generated.resources.send_email

@Composable
fun ForgotPasswordScreen(
    onSendEmailClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(Color.Black).padding(horizontal = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().widthIn(max = 400.dp).align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(Res.string.forgot_password),
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(Res.string.email_reset_password),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.LightGray,
                    lineHeight = 22.sp
                ),
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(32.dp))

            // email input
            TextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(Res.string.email)
            )
            Spacer(modifier = Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        color = if (email.isNotEmpty()) Color(0xFFE50914) else Color.Gray.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .clickable(enabled = email.isNotEmpty()) {
                        onSendEmailClick(email)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.send_email),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(Res.string.back_to_sign_in),
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                modifier = Modifier.clickable { onBackClick() }.padding(8.dp)
            )
        }
    }
}
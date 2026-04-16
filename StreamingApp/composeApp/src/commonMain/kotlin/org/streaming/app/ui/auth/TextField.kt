package org.streaming.app.ui.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun TextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isPassword: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    androidx.compose.material3.TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth().height(60.dp),
        visualTransformation = if (isPassword)
            PasswordVisualTransformation()
        else VisualTransformation.None,
        colors = androidx.compose.material3.TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF333333),
            unfocusedContainerColor = Color(0xFF333333),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(4.dp),
        trailingIcon = trailingIcon
    )
}

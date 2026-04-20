package org.streaming.app.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.streaming.app.CommonVerticalScrollbar
import org.streaming.app.ui.auth.AuthViewModel
import streamingapp.composeapp.generated.resources.Res
import streamingapp.composeapp.generated.resources.blue_face
import streamingapp.composeapp.generated.resources.chicken_face
import streamingapp.composeapp.generated.resources.purple_face
import streamingapp.composeapp.generated.resources.red_face
import streamingapp.composeapp.generated.resources.yellow_face

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(authViewModel: AuthViewModel,onLogout: () -> Unit) {
    var isEditMode by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf(false) }
    var showSubscriptionInfo by remember { mutableStateOf(false) }

    val userProfile = authViewModel.userProfile

    var showIconPicker by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    if (showPasswordDialog) {
        ChangePasswordDialog(
            onDismiss = { showPasswordDialog = false },
            onConfirm = { _, _ -> showPasswordDialog = false }
        )
    }

    if (showSubscriptionInfo) {
        SubscriptionDialog(onDismiss = { showSubscriptionInfo = false })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profil", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                actions = {
                    TextButton(onClick = { isEditMode = !isEditMode }) {
                        Text(
                            text = if (isEditMode) "SAČUVAJ" else "IZMENI",
                            color = if (isEditMode) Color.Green else Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color(0xFFE50914))
                        .clickable(enabled = isEditMode) {
                            showIconPicker = true
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = getProfilePainter(authViewModel.userProfile.profileImage),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                if (isEditMode) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(28.dp)
                            .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(4.dp))
                            .padding(4.dp)
                    )
                }

                if (showIconPicker) {
                    ProfileIconPickerDialog(
                        onDismiss = { showIconPicker = false },
                        onIconSelected = { selectedName ->
                            authViewModel.updateProfileImage(selectedName)
                            //authViewModel.userProfile.copy(profileImage = selectedName)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            ProfileSectionTitle("LIČNI PODACI")
            ProfileInputField("Ime i Prezime", userProfile.fullName, isEditMode) { userProfile.fullName = it }
            ProfileInputField("Email", userProfile.email, isEditMode) { userProfile.email = it }
            ProfileInputField("Telefon", userProfile.phone, isEditMode) { userProfile.phone = it }

            Spacer(modifier = Modifier.height(24.dp))

            ProfileSectionTitle("UPRAVLJANJE NALOGOM")

            ProfileOption(
                title = "Promeni profil (Ko gleda?)",
                icon = Icons.Default.AccountBox,
                onClick = { /* Ovde bi išla navigacija na ProfileSelectionScreen */ }
            )
            ProfileOption(
                title = "Pretplata: Premium Plan",
                icon = Icons.Default.Star,
                onClick = { showSubscriptionInfo = true }
            )
            ProfileOption(
                title = "Promeni lozinku",
                icon = Icons.Default.Lock,
                onClick = { showPasswordDialog = true }
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileSectionTitle("APLIKACIJA")
            ProfileOption("Moja lista", Icons.Default.Check, {})
            ProfileOption("Podešavanja aplikacije", Icons.Default.Settings, {})

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "Odjavi se",
                color = Color.LightGray,
                modifier = Modifier.fillMaxWidth().clickable { onLogout() }.padding(16.dp),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(74.dp))
        }
            CommonVerticalScrollbar(
                state = scrollState,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .fillMaxHeight()
                    .padding(vertical = 4.dp, horizontal = 2.dp)
            )
        }
    }
}

@Composable
fun ProfileOption(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, color = Color.White, fontSize = 15.sp)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = Color.DarkGray)
    }
}

@Composable
fun SubscriptionDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1A1A1A),
        title = { Text("Status pretplate", color = Color.White) },
        text = {
            Column {
                Text("Vaš paket: Premium (4K + HDR)", color = Color.White, fontWeight = FontWeight.Bold)
                Text("Sledeća naplata: 14. Maj 2026.", color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text("PROMENI PAKET", color = Color.Black)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("ZATVORI", color = Color(0xFFE50914)) }
        }
    )
}

@Composable
fun ProfileSectionTitle(title: String) {
    Text(
        text = title,
        color = Color.Gray,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
    )
}

@Composable
fun ProfileInputField(
    label: String, value: String, isEditable: Boolean, onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(if (isEditable) Color(0xFF1A1A1A) else Color.Transparent, RoundedCornerShape(4.dp))
            .padding(if (isEditable) 12.dp else 0.dp)
    ) {
        if (!isEditable) {
            Text(label, color = Color.Gray, fontSize = 11.sp)
        }

        if (isEditable) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                cursorBrush = SolidColor(Color(0xFFE50914)),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Column {
                        Text(label, color = Color(0xFFE50914), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        innerTextField()
                    }
                }
            )
        } else {
            Text(
                value,
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
            )
            Divider(color = Color(0xFF222222), thickness = 1.dp)
        }
    }
}

@Composable
fun getProfilePainter(imageName: String?): Painter {
    return when (imageName) {
        "blue_face" -> painterResource(Res.drawable.blue_face)
        "chicken_face" -> painterResource(Res.drawable.chicken_face)
        "purple_face" -> painterResource(Res.drawable.purple_face)
        "red_face" -> painterResource(Res.drawable.red_face)
        "yellow_face" -> painterResource(Res.drawable.yellow_face)
        else -> painterResource(Res.drawable.red_face)
    }
}

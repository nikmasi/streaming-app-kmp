package org.streaming.app.ui.profile

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource
import org.streaming.app.CommonVerticalScrollbar
import streamingapp.composeapp.generated.resources.Res
import streamingapp.composeapp.generated.resources.blue_face
import streamingapp.composeapp.generated.resources.chicken_face
import streamingapp.composeapp.generated.resources.purple_face
import streamingapp.composeapp.generated.resources.red_face
import streamingapp.composeapp.generated.resources.yellow_face


data class UserProfile(
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String,
    var role: String
)

@Composable
fun ProfileInfoGrid(
    firstName: String,
    lastName: String,
    email: String,
    role: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ProfileInfoCard(
            label = "Firstname",
            value = firstName,
            modifier = Modifier.fillMaxWidth()
        )

        ProfileInfoCard(
            label = "Lastname",
            value = lastName,
            modifier = Modifier.fillMaxWidth()
        )

        ProfileInfoCard(
            label = "Email",
            value = email,
            modifier = Modifier.fillMaxWidth()
        )

        ProfileInfoCard(
            label = "Role",
            value = role.uppercase(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ProfileInfoCard(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF2B2B2B))
            .padding(
                horizontal = 15.dp,
                vertical = 20.dp
            )
    ) {
        Text(
            text = label,
            color = Color(0xFFAAAAAA),
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = value,
            color = Color.White,
            fontSize = 15.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel,
    onLogout: () -> Unit,
    onWatchHistory: () -> Unit = {},
    onHome: () -> Unit = {},
    onMyList: () -> Unit = {}
) {
    var isEditMode by remember { mutableStateOf(false) }
    var showPasswordDialog by remember { mutableStateOf(false) }
    var showIconPicker by remember { mutableStateOf(false) }

    val userProfile = profileViewModel.userProfile
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        profileViewModel.getProfileInfo()
    }

    if (showPasswordDialog) {
        ChangePasswordDialog(
            onDismiss = {
                showPasswordDialog = false
            },
            onConfirm = { _, _ ->
                showPasswordDialog = false
            }
        )
    }

    if (showIconPicker) {
        ProfileIconPickerDialog(
            onDismiss = {
                showIconPicker = false
            },
            onIconSelected = { selectedName ->
                //profileViewModel.updateProfileImage(selectedName)
                showIconPicker = false
            }
        )
    }

    Scaffold(
        containerColor = Color(0xFF141414)
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF141414))
                .padding(padding)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 32.dp,
                            vertical = 55.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .widthIn(max = 1080.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF1F1F1F))
                            .padding(50.dp)
                    ) {

                        // header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(144.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFFE50914))
                                    .clickable(enabled = isEditMode) {
                                        showIconPicker = true
                                    }
                            ) {

                                Image(
                                    painter = getProfilePainter(
                                        userProfile.profileImage
                                    ),
                                    contentDescription = "Profile image",
                                    contentScale = ContentScale.Crop
                                )

                                if (isEditMode) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit profile image",
                                        tint = Color.White,
                                        modifier = Modifier
                                            .align(Alignment.BottomEnd)
                                            .padding(8.dp)
                                            .size(34.dp)
                                            .background(
                                                Color.Black.copy(alpha = 0.75f),
                                                RoundedCornerShape(6.dp)
                                            )
                                            .padding(7.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        HorizontalDivider(
                            color = Color(0xFF383838),
                            thickness = 1.dp
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        ProfileInfoGrid(
                            firstName = userProfile.firstname,
                            lastName = userProfile.lastname,
                            email = userProfile.email,
                            role = userProfile.role
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            ProfileActionButton(
                                text = if (isEditMode) {
                                    "Save Profile"
                                } else {
                                    "Edit Profile"
                                },
                                backgroundColor = Color(0xFFE50914),
                                textColor = Color.White,
                                onClick = {
                                    isEditMode = !isEditMode
                                }
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            ProfileActionButton(
                                text = "Change Password",
                                backgroundColor = Color(0xFF4A4A4A),
                                textColor = Color.White,
                                onClick = {
                                    showPasswordDialog = true
                                }
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            ProfileActionButton(
                                text = "Logout",
                                backgroundColor = Color(0xFF1B1B1B),
                                textColor = Color(0xFFE50914),
                                onClick = onLogout
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            ProfileActionButton(
                                text = "Watch History",
                                backgroundColor = Color(0xFFE50914),
                                textColor = Color.White,
                                onClick = onWatchHistory
                            )
                        }
                    }
                }

                CommonVerticalScrollbar(
                    state = scrollState,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .fillMaxHeight()
                        .padding(
                            vertical = 4.dp,
                            horizontal = 2.dp
                        )
                )
            }
        }
    }
}

@Composable
fun ProfileActionButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues(
            horizontal = 26.dp,
            vertical = 12.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
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

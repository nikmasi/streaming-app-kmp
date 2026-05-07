package org.streaming.app.ui.details

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun DetailsScreen(
    title: String,
    desc: String,
    genres: List<String>,
    duration: Int,
    year: Int,
    url: String,
    videoUrl: String,
    onPlayClick: () -> Unit,
    onBackClick: () -> Unit,
    onClickMyList: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Color.Black).verticalScroll(rememberScrollState())
    ) {

        Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {
            AsyncImage(
                model = url,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f), Color.Black)
                        )
                    )
            )
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Text(
                text = title,
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)
            )
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoChip(text = "$year")
                InfoChipList(text = genres)
                InfoChip(text = "$duration min")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onPlayClick,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text("Play", color = Color.Black)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = desc,
                color = Color.White.copy(alpha = 0.85f),
                lineHeight = 20.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                DetailsActionButton(
                    icon = Icons.Default.Add,
                    label = "My List",
                    onClick = { onClickMyList() }
                )

                DetailsActionButton(
                    icon = Icons.Default.Star,
                    label = "Rate",
                    onClick = { /* tvoja logika */ }
                )

                DetailsActionButton(
                    icon = Icons.Default.Share,
                    label = "Share",
                    onClick = { /* tvoja logika */ }
                )
            }
        }
    }
}

@Composable
fun InfoChip(text: String) {
    Box(
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.12f), shape = RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}


@Composable
fun InfoChipList(text: List<String>) {
    Box(
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.12f), shape = RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        for (t in text){
            Text(
                text = t,
                color = Color.White,
                fontSize = 12.sp
            )
        }

    }
}

@Composable
fun DetailsActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick).padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = Color.LightGray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )
    }
}
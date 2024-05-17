package com.app.fitnessapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.fitnessapp.ui.theme.RobotoMono

@Composable
fun NotificationScreen() {
    Column(
        modifier = Modifier
            .background(background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        NotificationTopAppBar()
        Column(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Step 1/7",
                color = color,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                fontFamily = RobotoMono
            )
            Box(modifier = Modifier.width(300.dp)) {
                Text(
                    text = "Do you want to turn on notification?",
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    fontFamily = RobotoMono,
                    textAlign = TextAlign.Center
                )
            }
        }
        Box(modifier = Modifier
            .size(220.dp),
            contentAlignment = Alignment.Center){
            Image(painter = painterResource(id = R.drawable.notification),
                contentDescription = "notification",
                modifier = Modifier
                    .fillMaxSize()
                    )

        }

        Column(modifier = Modifier.height(150.dp)) {
            Row (modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()){

                Text(text = "New weekly health reminder",
                    color = Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    fontFamily = RobotoMono,)


            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTopAppBar() {
    TopAppBar(

        title = {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .height(50.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.size(30.dp)
                )
                LinearProgressIndicator(
                    progress = 3.0f,
                    color = color,
                    modifier = Modifier
                        .background(Black, RoundedCornerShape(10.dp))
                        .height(5.dp)
                        .width(200.dp)
                )
                Text(
                    text = "Skip",
                    color = color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = RobotoMono
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(background)
    )
}

@Preview
@Composable
fun NotificationPreview() {
    NotificationScreen()
}

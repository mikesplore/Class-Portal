package com.app.fitnessapp

import android.annotation.SuppressLint
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.fitnessapp.ui.theme.RobotoMono

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NotificationScreen() {
    Scaffold(
        topBar = { NotificationTopAppBar() },
        containerColor = background,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(background)
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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
                    Box(
                        modifier = Modifier
                            .size(220.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.notification),
                            contentDescription = "notification",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                        verticalArrangement = Arrangement.SpaceAround) {
                        Row(modifier = Modifier.absolutePadding(15.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.hint),
                                contentDescription = "water_drop",
                                modifier = Modifier.size(30.dp),
                                tint = color
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "New weekly health reminder",
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                fontFamily = RobotoMono
                            )
                        }
                        Row(modifier = Modifier.absolutePadding(15.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.water_drop),
                                contentDescription = "water_drop",
                                modifier = Modifier.size(30.dp),
                                tint = color
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Motivational reminder",
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                fontFamily = RobotoMono
                            )
                        }
                        Row(modifier = Modifier.absolutePadding(15.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.heart),
                                contentDescription = "water_drop",
                                modifier = Modifier.size(30.dp),
                                tint = color
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Personalized Program",
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                fontFamily = RobotoMono
                            )
                        }
                    }
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                            .height(50.dp)
                            .width(300.dp),
                        colors = ButtonDefaults.buttonColors(color),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "Allow",
                            color = White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            fontFamily = RobotoMono
                        )
                    }
                }

            }
        }
    )
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
                    trackColor = Color(0xff89CFF3),
                    progress = 1 / 3.0f,
                    color = color,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(Black, RoundedCornerShape(10.dp))
                        .height(10.dp)
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
        colors = TopAppBarDefaults.smallTopAppBarColors(background),
    )
}


@Preview
@Composable
fun NotificationPreview() {
    NotificationScreen()
}

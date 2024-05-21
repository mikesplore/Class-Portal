package com.app.fitnessapp

import android.annotation.SuppressLint
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Dashboard(navController: NavController) {
    val username = if(global.selectedcategory.value == "student") "Student" else "Teacher"
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val drawerBackgroundColor = Color(0xFF1A1A1A)
    val primaryColor = Color(0xFF27374D)
    val textColor = Color.White
    val horizontalScrollState = rememberScrollState()

    // Define the list of boxes
    val boxes = listOf(
        R.drawable.announcement to "Announcements" to "soon",
        R.drawable.attendance to "Attendance" to "attendance",
        R.drawable.assignment to "Assignment" to "soon"
    )

    // Calculate the duration for each box
    val totalDuration = 10000 // Total duration for the entire scroll
    val delayDuration = 5000L // Duration to delay at each box
    val boxCount = boxes.size
    val boxScrollDuration = (totalDuration / boxCount)

    LaunchedEffect(Unit) {
        while (true) {
            for (i in 0 until boxCount) {
                // Calculate the target scroll position for each box
                val targetScrollPosition = i * (horizontalScrollState.maxValue / (boxCount - 1))

                // Animate to the target position
                horizontalScrollState.animateScrollTo(
                    targetScrollPosition,
                    animationSpec = tween(durationMillis = boxScrollDuration, easing = EaseInOut)
                )
                // Delay at each box
                delay(delayDuration)
            }

            // Instantaneous return to the start for a seamless loop
            horizontalScrollState.scrollTo(0)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.background(drawerBackgroundColor)) {
                // Drawer content
                Text(text = "Menu Item 1", color = Color.White, modifier = Modifier.padding(16.dp))
                Text(text = "Menu Item 2", color = Color.White, modifier = Modifier.padding(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Hello, $username", color = textColor, fontFamily = RobotoMono) },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = textColor)
                        }
                    },
                    actions = {
                        IconButton(onClick = { /* Handle profile click */ }) {
                            Icon(
                                painter = if(global.selectedcategory.value == "student") painterResource(id = R.drawable.student)
                                else painterResource(id = R.drawable.teacher),
                                contentDescription = "dp",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    },
                    colors = topAppBarColors(
                        containerColor = primaryColor,
                        navigationIconContentColor = textColor,
                        titleContentColor = textColor,
                        actionIconContentColor = textColor
                    )
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .background(color1)
                        .padding(top = 66.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Horizontally scrollable row below the top app bar
                    Row(
                        modifier = Modifier
                            .requiredHeight(200.dp)
                            .fillMaxWidth()
                            .horizontalScroll(horizontalScrollState)
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Spacer(modifier = Modifier.width(10.dp))
                        boxes.forEach { item ->
                            TopBoxes(
                                image = painterResource(id = item.first.first),  // Access the Int from the nested Pair
                                description = item.first.second,                // Access the String from the nested Pair
                                route = item.second,
                                navController = navController
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Attendance",
                        modifier = Modifier.padding(10.dp),
                        fontFamily = RobotoMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = textColor)
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                    ){
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRows(route = "AttendanceReport",
                            content = "View Attendance report",
                            navController = navController,
                            image = painterResource(id = R.drawable.view))
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRows(route = "RecordAttendance",
                            content = "Record Attendance",
                            navController = navController,
                            image = painterResource(id = R.drawable.sign))
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRows(route = "DeleteStudent",
                            content = "Delete Student",
                            navController = navController,
                            image = painterResource(id = R.drawable.delete))
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRows(route = "AddStudent",
                            content = "Add Student",
                            navController = navController,
                            image = painterResource(id = R.drawable.add))
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRows(route = "EditStudent",
                            content = "Edit Student",
                            navController = navController,
                            image = painterResource(id = R.drawable.edit))
                        Spacer(modifier = Modifier.width(10.dp))




                    }
                }
            }
        )
    }
}




@Composable
fun TopBoxes(image: Painter, description: String,route: String,navController: NavController) {
    Row(
        modifier = Modifier
            .clickable {
                navController.navigate(route)
            }
            .background(Color.Transparent, shape = RoundedCornerShape(30.dp))
            .fillMaxHeight()
            .width(300.dp)
    ) {
        Box(modifier = Modifier) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .fillMaxSize()
            ) {
                Image(
                    painter = image,
                    contentDescription = "sample",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter) // Position at the bottom
                        .background(
                            Color.Black.copy(alpha = 0.5f), // Semi-transparent black background
                            shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                        )
                        .padding(16.dp),
                ) {
                    Text(
                        text = description,
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.9f),
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun MiddleRows(image: Painter, content: String,route: String,navController: NavController) {
    Row(
        modifier = Modifier
            .clickable {
                navController.navigate(route)
            }
            .background(Color.Transparent, shape = RoundedCornerShape(30.dp))
            .fillMaxHeight()
            .width(150.dp)
    ) {
        Box(modifier = Modifier) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .fillMaxSize()
            ) {
                Image(
                    painter = image,
                    contentDescription = "sample",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter) // Position at the bottom
                        .background(
                            Color.Transparent

                        )
                        .padding(16.dp),
                ) {
                    Text(
                        text = content,
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.9f),
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController()
    Dashboard(navController)
}

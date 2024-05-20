package com.app.fitnessapp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TeacherDashboard(navController: NavController) {
    // Define drawer state and coroutine scope
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Modal navigation drawer for the side menu
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Content for the side menu
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(270.dp)
                        .background(teachercolor3),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Content of the drawer
                    Column(
                        modifier = Modifier
                            .background(teachercolor1, shape = RoundedCornerShape(10.dp))
                            .height(250.dp)
                            .fillMaxWidth()
                    ) {
                        // Back button in the top right corner
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier
                                    .clickable {
                                        // Handle back button click to close the drawer
                                        scope.launch {
                                            drawerState.apply {
                                                if (isClosed) open() else close()
                                            }
                                        }
                                    }
                                    .padding(8.dp)
                            )
                        }
                        // Image and name in the middle of the drawer
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier.height(200.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.teacher),
                                    contentDescription = "dp",
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(70.dp)
                                )
                                Text(
                                    "Michael Odhiambo",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                                Text(
                                    "BSCS/108J/2021",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black,
                                )
                            }
                        }
                    }
                    Row (modifier = Modifier
                        .absolutePadding(0.dp, 0.dp, 0.dp, 10.dp)
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                        ){
                        Icon(imageVector = Icons.Outlined.ExitToApp, contentDescription = "Logout")
                        Text(text = "Logout",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black,
                            modifier = Modifier.clickable{
                                navController.navigate("teacherlogin")
                            })
                    }
                }
            }
        }
    ) {
        // Scaffold for the main content
        Scaffold(
            topBar = {
                // Top app bar
                TopAppBar(

                    title = {
                    Row(Modifier.width(290.dp),
                        horizontalArrangement = Arrangement.Center){
                        Text(text = "Dashboard",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            fontFamily = RobotoMono,
                            color = Color.White,)} },
                    navigationIcon = {
                        // Menu icon to open/close the drawer
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = "Menu",
                                modifier = Modifier.clickable {
                                    // Handle click to open/close the drawer
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                },
                                tint = Color.White
                            )
                        }
                    },
                    // Set top app bar colors
                    colors = topAppBarColors(
                    teachercolor1
                    )
                )
            },
            content = {
                // Main content of the screen
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(teachercolor4), // Assuming brush is defined elsewhere
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(70.dp)) // Height of the top app bar
                    TeacherHeaderContent() // Content for the header
                    TeacherBoxContent(navController) // Content for the main section
                }
            }
        )
    }
}


@Composable
fun TeacherHeaderContent() {
    Box(modifier = Modifier.shadow(
        elevation = 15.dp,
        shape = RoundedCornerShape(20.dp)
    )){
       Column(
        modifier = Modifier
            .padding(top = 25.dp)
            .width(350.dp)
            .height(200.dp)
            .background(teachercolor2, shape = RoundedCornerShape(20.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            painter = painterResource(id = R.drawable.teacher),
            contentDescription = "dp",
            modifier = Modifier
                .clip(CircleShape)
                .size(100.dp)
        )
        Text(
            text = "TUM/0052/2020",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = RobotoMono
        )
    }
    }
}

@Composable
fun TeacherBoxContent(navController: NavController) {
        Column (modifier = Modifier

            .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally){


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TSquareBox(
                imageName = painterResource(id = R.drawable.timetable),
                content = "Timetable",
                route = "timetable",
                navController = navController,
            )
            TSquareBox(
                imageName = painterResource(id = R.drawable.attendance),
                content = "Attendance",
                route = "attendance",
                navController = navController,
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TSquareBox(
                imageName = painterResource(id = R.drawable.announcement),
                content = "Announcements",
                route = "announcements",
                navController = navController,
            )
            TSquareBox(
                imageName = painterResource(id = R.drawable.resources),
                content = "Resources",
                route = "resources",
                navController = navController,

            )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        TSquareBox(
            imageName = painterResource(id = R.drawable.assignment),
            content = "Assignments",
            route = "assignments",
            navController = navController,

        )

        }
    }
}

@Composable
fun TSquareBox(
    imageName: Painter,
    content: String,
    route: String,
    navController: NavController,
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(10.dp)
            )
            .background(teachercolor2)
            .size(150.dp)
            .clickable { navController.navigate(route) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main content: Image and Text

            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(teachercolor3, shape = CircleShape)
            ) {
                Image(
                    painter = imageName,
                    contentDescription = "box content",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(90.dp)
                )
            }
            Text(
                text = content,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = RobotoMono
            )

        }
    }
}









@Preview
@Composable
fun TeacherProfilePreview() {
    TeacherDashboard(rememberNavController())
}
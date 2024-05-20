package com.app.fitnessapp
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboard(navController: NavController) {
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
                        .background(color1),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Content of the drawer
                    Column(
                        modifier = Modifier
                            .height(250.dp)
                            .fillMaxWidth()
                            .background(color2)
                    ) {
                        // Example drawer content
                        Text(
                            "Drawer Item 1",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 20.sp
                        )
                        Text(
                            "Drawer Item 2",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 20.sp
                        )
                    }
                    Text(
                        "Logout",
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { navController.navigate("studentlogin") },
                        fontSize = 20.sp
                    )
                }
            }
        }
    ) {
        // Scaffold for the main content
        Scaffold(
            topBar = {
                // Top app bar
                TopAppBar(
                    title = { Text(text = "Dashboard", color = color4, fontFamily = RobotoMono) },
                    navigationIcon = {
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
                                tint = color4
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = color1
                    )
                )
            },
            content = {
                // Main content of the screen
                Column(modifier = Modifier
                    .background(color1)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly) {
                    Spacer(modifier = Modifier.height(10.dp))
                    StudentHeader()
                    StudentBox(navController = navController)
                }

            }
        )
    }
}

@Composable
fun StudentBox(navController: NavController){

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StudentSquareBox(
            imageName = painterResource(id = R.drawable.announcement),
            content = "Announcements",
            route = "timetable",
            navController = navController,
        )
        StudentSquareBox(
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
        StudentSquareBox(
            imageName = painterResource(id = R.drawable.timetable),
            content = "Timetable",
            route = "timetable",
            navController = navController,
        )
        StudentSquareBox(
            imageName = painterResource(id = R.drawable.discussion),
            content = "Discussion",
            route = "discussion",
            navController = navController,
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StudentSquareBox(
            imageName = painterResource(id = R.drawable.assignment),
            content = "Assignments",
            route = "assignments",
            navController = navController,
        )
        StudentSquareBox(
            imageName = painterResource(id = R.drawable.resources),
            content = "Resources",
            route = "resources",
            navController = navController,
        )
    }

}


@Composable
fun StudentHeader() {
    Box(modifier = Modifier.shadow(
        elevation = 15.dp,
        shape = RoundedCornerShape(20.dp)
    )){
        Column(
            modifier = Modifier
                .width(350.dp)
                .height(200.dp)
                .background(color = color2, shape = RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(id = R.drawable.student),
                contentDescription = "dp",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(100.dp)
            )
            Text(
                text = "STUDENT NAME",
                color = color4,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = RobotoMono
            )
        }
    }
}
@Composable
fun StudentSquareBox(
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
            .background(color = color2)
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
                    .background(color = color3, shape = CircleShape)
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
                color = color4,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = RobotoMono
            )

        }
    }
}
@Preview
@Composable
fun StudentScreenPreview() {
    StudentDashboard(rememberNavController())
}

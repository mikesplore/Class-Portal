package com.app.fitnessapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onNavigate: (String) -> Unit, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance Dashboard") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = color,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {

            Column(
                modifier = Modifier
                    .size(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.attendance),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                val buttonModifier = Modifier
                    .size(100.dp)
                    .padding(8.dp)

                Row(horizontalArrangement = Arrangement.Center) {
                    NavigationButton(onNavigate, "AddStudent", "Add Student", )
                    NavigationButton(onNavigate, "DeleteStudent", "Delete Student", )
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    NavigationButton(onNavigate, "RecordAttendance", "Record Attendance", )
                    NavigationButton(onNavigate, "EditStudent", "Edit Student", )
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    NavigationButton(onNavigate, "AttendanceReport", "View Attendance Report", )
                }
            }
        }
    }
}

@Composable
fun NavigationButton(onNavigate: (String) -> Unit, destination: String, text: String) {
    Button(
        onClick = { onNavigate(destination) },
        modifier = Modifier
            .padding(8.dp)
            .shadow(
                elevation = 5.dp,
                shape = RectangleShape,
                clip = true
            )
            .size(130.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(color)
    ) {
        Text(text,
            fontFamily = RobotoMono,
            color = Color.White,
            fontSize = 13.sp,
            textAlign = TextAlign.Center)
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController(), onNavigate = {})
}

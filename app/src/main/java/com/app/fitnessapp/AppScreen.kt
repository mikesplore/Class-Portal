package com.app.fitnessapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back",
                            modifier = Modifier.clickable {
                                navController.popBackStack()

                            })
                    }
                },
                actions = {
                    // Add any additional actions (e.g., settings icon) here
                }
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
                    .size(300.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.attendance),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

            }
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
            Button(
                onClick = { onNavigate("AddStudent") },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(color)
            ) {
                Text("Add New Student")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onNavigate("RecordAttendance") },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(color)
            )
            { Text("Record Attendance") }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onNavigate("AttendanceReport") },
                modifier = Modifier
                    .height(50.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(color)
            )
            { Text("View Report") }
        }
    }
}

}

@Preview
@Composable
fun MainScreenPreview() {
    MainScreen(navController = rememberNavController(), onNavigate = {})
}


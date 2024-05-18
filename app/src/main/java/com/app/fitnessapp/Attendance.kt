package com.app.fitnessapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Attendance(onNavigate: (String) -> Unit) {

    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Fitness App", // Replace with your app's name
                        style = typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(colors.background)
                .fillMaxSize()
                .padding(innerPadding), // Add inner padding for content adjustment
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { onNavigate("AddStudent") }) {
                Text("Add Student", style = typography.labelLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onNavigate("RecordAttendance") }) {
                Text("Record Attendance", style = typography.labelLarge)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { onNavigate("AttendanceReport") }) {
                Text("View Report", style = typography.labelLarge)
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    Attendance(onNavigate = {})
}

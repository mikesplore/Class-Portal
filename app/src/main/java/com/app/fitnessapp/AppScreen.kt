package com.app.fitnessapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { onNavigate("AddStudent") }) { Text("Add Student") }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavigate("RecordAttendance") }) { Text("Record Attendance") }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onNavigate("AttendanceReport") }) { Text("View Report") }
    }
}



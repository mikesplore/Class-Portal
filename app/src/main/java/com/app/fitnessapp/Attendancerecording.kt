package com.app.fitnessapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.app.fitnessapp.ui.theme.RobotoMono


data class AttendanceRecord(val date: String, val isPresent: Boolean)

// Your data classes (Student and AttendanceRecord) would go here

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordAttendanceScreen(students: List<Student>, onAttendanceRecorded: (List<Student>) -> Unit) {
    val attendanceState = remember { mutableStateMapOf<String, Boolean>() }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "RECORD ATTENDANCE",
                        fontFamily = RobotoMono,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = color,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign ",

                fontWeight = FontWeight.Bold
            )
            students.forEach { student ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(color, RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = student.name,
                        fontFamily = RobotoMono,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    Checkbox(
                        checked = attendanceState[student.id] ?: false,
                        onCheckedChange = { isChecked ->
                            attendanceState[student.id] = isChecked
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = color,
                            uncheckedColor = background
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val updatedStudents = students.map { student ->
                        val isPresent = attendanceState[student.id] ?: false
                        student.copy(
                            attendanceRecords = student.attendanceRecords +
                                    AttendanceRecord("2024-05-17", isPresent)
                        )
                    }
                    onAttendanceRecorded(updatedStudents)
                },
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth(0.8f),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(color)
            ) {
                Text(
                    text = "Submit Attendance",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontFamily = RobotoMono

                )
            }
        }
    }
}


@Preview
@Composable
fun RecordAttendanceScreenPreview() {
    val students = listOf(
        Student("1", "Michael Odhiambo", emptyList()),
        Student("2", "Christopher Matere", emptyList())
    )
    RecordAttendanceScreen(students) {}
}
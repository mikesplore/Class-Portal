package com.app.fitnessapp

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController

@Composable
fun AppScreen() {
    val navController = rememberNavController()
    var students by remember { mutableStateOf(listOf<Student>()) }
    var currentScreen by remember { mutableStateOf("Authentication") }

    when (currentScreen) {
        "Authentication" -> AuthenticationScreen(onLoginSuccess = { currentScreen = "Main" }, navController = navController)
        "Main" -> Attendance(onNavigate = { screen -> currentScreen = screen })
        "AddStudent" -> AddStudentScreen(onStudentAdded = { newStudent ->
            students = students + newStudent
            currentScreen = "Main"
        })
        "RecordAttendance" -> RecordAttendanceScreen(students = students, onAttendanceRecorded = { updatedStudents ->
            students = updatedStudents
            currentScreen = "Main"
        })
        "AttendanceReport" -> AttendanceReportScreen(students = students)

        "TeacherDashboard" -> TeacherDashboard(navController = navController)
    }
}

@Preview
@Composable
fun AppScreenPreview() {
    AppScreen()
}

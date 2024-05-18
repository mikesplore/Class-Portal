package com.app.fitnessapp

import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppScreen() {
    val navController = rememberNavController()
    var students by remember { mutableStateOf(listOf<Student>()) }

    NavHost(navController = navController, startDestination = "welcomeScreen") {
        composable("welcomeScreen") { WelcomeScreen(navController = navController)}
        composable("loginCategory"){ LoginCategory(navController = navController)}
        composable("teacherLogin"){ TeacherLoginScreen(navController = navController)}
        composable("teacherDashboard"){ TeacherDashboard(navController = navController)}
        composable("studentManagement"){ StudentManagementScreen(navController = navController)}
        composable("addStudent"){ AddStudentScreen(navController = navController)}
        composable("attendanceReport"){ AttendanceReportScreen(navController = navController)}")





    }
}


@Preview
@Composable
fun AppScreenPreview() {
    AppScreen()
}

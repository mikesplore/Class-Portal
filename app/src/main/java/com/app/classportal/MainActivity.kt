package com.app.classportal

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class Global(
    var selectedcategory: MutableState<String> = mutableStateOf("Student"),
    var firstname: MutableState<String> = mutableStateOf(""),
    var lastname: MutableState<String> = mutableStateOf(""),
    var regID: MutableState<String> = mutableStateOf(""),
    var loggedinuser: MutableState<String> = mutableStateOf("Anonymous"),
    var loggedinlastname: MutableState<String> = mutableStateOf(""),
    var loggedinregID: MutableState<String> = mutableStateOf(""),
    var enableEdgeToEdge: MutableState<Boolean> = mutableStateOf(true),
    var usernames: MutableState<String> = mutableStateOf(""),



)


var global = Global()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (global.enableEdgeToEdge.value){
            enableEdgeToEdge()}
            LaunchedEffect(Unit) {
                globalcolors.currentScheme = globalcolors.loadColorScheme(this@MainActivity)
            }
            val navController = rememberNavController()
            NavigationComponent(navController, this)
        }
    }

    @Composable
    fun NavigationComponent(navController: NavHostController, context: Context) {
        
        NavHost(navController, startDestination = "welcome") {
            composable("dashboard") {
                Dashboard(navController, context)
            }
            composable("AddStudent") {
                AddStudentScreen(
                    onStudentAdded = { navController.navigate("dashboard") },
                    context = context,
                    navController
                )
            }
            composable("RecordAttendance") {
                RecordAttendanceScreen(
                    context = context,
                    onAttendanceRecorded = { navController.navigate("dashboard") },
                    navController = navController
                )
            }
            composable("AttendanceReport") {
                AttendanceReportScreen(context = context, navController)
            }
            composable("DeleteStudent") {
                DeleteStudentScreen(
                    context = context,
                    navController = navController
                )
            }
            composable("EditStudent") {
                EditStudentScreen(
                    onBack = { navController.navigate("dashboard") },
                    context = context,
                    navController = navController
                )
            }
            composable("welcome") {
                WelcomeScreen(navController)
            }

            composable("announcements") {
                AnnouncementsScreen(navController = navController, context)
            }
            composable("login") {
                LoginScreen(navController = navController, context)
            }

            composable("timetable") {
                Timetable(navController)
            }
            composable("assignments") {
                AssignmentScreen(navController)
            }
            composable("students"){
                ShowStudentsScreen(context, navController)
            }
            composable("settings"){
                SettingsScreen(navController, context)
            }
            
        }
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        NavigationComponent(rememberNavController(), this)
    }
}

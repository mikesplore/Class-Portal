package com.app.classportal

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
    var username: MutableState<String> = mutableStateOf(""),
    var showdialog: MutableState<Boolean> = mutableStateOf(false),
    var password: MutableState<String> = mutableStateOf(""),

)


var global = Global()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (global.enableEdgeToEdge.value){
                enableEdgeToEdge()}
            LaunchedEffect(Unit) {
                GlobalColors.currentScheme = GlobalColors.loadColorScheme(this@MainActivity)
            }
            val navController = rememberNavController()
            createNotificationChannel(this)
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
                WelcomeScreen(navController, context)
            }

            composable("announcements") {
                AnnouncementsScreen(navController = navController, context)
            }
            composable("login") {
                LoginScreen(navController = navController, context)
            }
            composable("timetable") {
                Timetable(navController, context)
            }
            composable("assignments") {
                AssignmentScreen(navController, context)
            }
            composable("students"){
                ShowStudentsScreen(context, navController)
            }
            composable("settings"){
                SettingsScreen(navController, context)
            }
            composable("password"){
                PasswordResetScreen(navController, context)
            }
        }
    }




    @Preview
    @Composable
    fun DefaultPreview() {
        NavigationComponent(rememberNavController(), this)
    }
}

package com.app.classportal

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
val primaryColor = Color(0xff003C43)
val secondaryColor = Color(0xff135D66)
val tertiaryColor = Color(0xff77B0AA)
val textColor = Color(0xffE3FEF7)
class Global(
    var selectedcategory: MutableState<String> = mutableStateOf("student"),
    var firstname: MutableState<String> = mutableStateOf(""),
    var lastname: MutableState<String> = mutableStateOf(""),
    var regID: MutableState<String> = mutableStateOf(""),
    var loggedinuser: MutableState<String> = mutableStateOf("Anonymous"),



)


var global = Global()

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            enableEdgeToEdge()
            val navController = rememberNavController()
            NavigationComponent(navController, this)
        }
    }

    @Composable
    fun NavigationComponent(navController: NavHostController, context: Context) {
        NavHost(navController, startDestination = "login") {
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
            composable("logincategory") {
                LoginCategory(navController = navController)
            }

            composable("announcements") {
                AnnouncementsScreen(navController = navController, context)
            }
            composable("login") {
                LoginScreen(navController = navController, context)
            }
            composable("gallery") {
                Gallery(navController)
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
            
        }
    }

    @Preview
    @Composable
    fun DefaultPreview() {
        NavigationComponent(rememberNavController(), this)
    }
}

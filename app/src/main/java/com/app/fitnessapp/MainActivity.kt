package com.app.fitnessapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.FitnessAPPTheme
import androidx.compose.ui.platform.LocalContext


data class Student(val id: Int, val name: String)
data class AttendanceRecord(val studentId: Int, val date: String, val present: Boolean)

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val navController = rememberNavController()
                NavigationComponent(navController, this)

        }
    }



    @Composable
    fun NavigationComponent(navController: NavHostController, context: Context) {
        NavHost(navController, startDestination = "main") {
            composable("main") {
                MainScreen(onNavigate = { navController.navigate(it) }, navController)
            }
            composable("AddStudent") {
                AddStudentScreen(
                    onStudentAdded = { navController.navigate("main") },
                    context = context,
                    navController
                )
            }
            composable("RecordAttendance") {
                RecordAttendanceScreen(
                    context = context,
                    onAttendanceRecorded = { navController.navigate("main") }, navController = navController)

            }
            composable("AttendanceReport") {
                AttendanceReportScreen(context = context, navController)
            }
        }
    }


}








package com.app.fitnessapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController



data class Student(val studentid: String, val name: String)
data class AttendanceRecord(val studentId: String, val date: String, val present: Boolean)

class attendanceActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                val navController = rememberNavController()
                NavigationComponent(navController, this)

        }
    }



    @Composable
    fun NavigationComponent(navController: NavHostController, context: Context) {
        NavHost(navController, startDestination = "welcome") {
            composable("attendance") {
                AttendanceScreen(onNavigate = { navController.navigate(it) }, navController)
            }
            composable("AddStudent") {
                AddStudentScreen(
                    onStudentAdded = { navController.navigate("attendance") },
                    context = context,
                    navController
                )
            }
            composable("RecordAttendance") {
                RecordAttendanceScreen(
                    context = context,
                    onAttendanceRecorded = { navController.navigate("attendance") }, navController = navController)

            }
            composable("AttendanceReport") {
                AttendanceReportScreen(context = context, navController)
            }
            composable("DeleteStudent"){ DeleteStudentScreen(

                context = context,
                navController = navController)
            }
            composable("EditStudent"){ EditStudentScreen(
                onBack = { navController.navigate("attendance") },
                context = context,
                navController = navController)
            }
            composable("welcome"){ WelcomeScreen(
               navController = navController)
            }
            composable("logincategory"){ LoginCategory(
                navController = navController)
            }
            composable("teacherlogin"){ TeacherLogin(
                navController = navController)
            }
            composable("studentlogin"){ StudentLogin(
                navController = navController)
            }
            composable("teacherdashboard"){ TeacherDashboard(
                navController = navController)
            }
            composable("studentdashboard"){ StudentDashboard(
                navController = navController)
            }
            composable("announcements"){ Announcements(
                navController = navController)
            }
            composable("discussion"){ Discussion(
                navController = navController)
            }
            composable("gender"){ GenderScreen(
                navController = navController)
            }
            composable("password"){ PasswordScreen(
                navController = navController)
            }
            composable("notification"){ NotificationScreen(
                navController = navController)
            }
            composable("assignments"){ Assignments(
                navController = navController)
            }
            composable("teacherregister"){ TeacherRegister(
                navController = navController)
            }
            composable("studentregister"){ StudentRegister(
                navController = navController)
            }
            composable("resources"){ Resources(
                navController = navController)
            }
            composable("timetable"){ Timetable(
                navController = navController)
            }



        }
    }


}









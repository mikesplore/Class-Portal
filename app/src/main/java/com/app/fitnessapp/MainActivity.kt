package com.app.fitnessapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class Global(
    var selectedcategory: MutableState<String> = mutableStateOf("student"),
    var firstname: MutableState<String> = mutableStateOf("Mike"),
    var regID: MutableState<String> = mutableStateOf("BSCS/108J/2021"),

)
data class Student(val registrationID: String, val studentname: String)
data class AttendanceRecord(val studentId: String, val date: String, val present: Boolean)



var global = Global()

val color1 = Color(0xff27374D)
val color2 = Color(0xff526D82)
val color3 = Color(0xff9DB2BF)
val color4 = Color(0xffDDE6ED)
val textcolor = Color(0xff00A9FF)

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
            composable("attendance") {
                MainScreen(onNavigate = { navController.navigate(it) }, navController)
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
            composable("EditStudent") {
                EditStudentScreen(
                    onBack = { navController.navigate("attendance") },
                    context = context,
                    navController = navController
                )
            }
            composable("welcome"){
                WelcomeScreen(navController)
            }
            composable("logincategory"){ LoginCategory(
                navController = navController)
            }
            composable("dashboard"){ Dashboard(
                navController = navController, context)
            }

            composable("gender"){ GenderScreen(
                navController = navController,context)
            }

            composable("notification"){ NotificationScreen(
                navController = navController)
            }
            composable("soon"){ ComingSoon(
                navController = navController)
            }
            composable("login"){ LoginScreen(
                navController = navController,context)
            }

            composable("resources"){ Resources(
                navController = navController)
            }


        }
    }
@Preview
@Composable
fun DefaultPreview() {
    NavigationComponent(rememberNavController(), this)
}

}









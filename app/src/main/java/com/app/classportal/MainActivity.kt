package com.app.classportal

import AssignmentScreen
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    var usernames: MutableState<String> = mutableStateOf(""),
    var showdialog: MutableState<Boolean> = mutableStateOf(false)



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
            createNotificationChannel(this)
            NavigationComponent(navController, this)
            LoginDialog( navController = navController)
        }
    }

    @Composable
    fun NavigationComponent(navController: NavHostController, context: Context) {
        
        NavHost(navController, startDestination = "dashboard") {
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
        }
    }




    @Preview
    @Composable
    fun DefaultPreview() {
        NavigationComponent(rememberNavController(), this)
    }
}


@Composable
fun LoginDialog(navController: NavController){
    if(global.showdialog.value){
        AlertDialog(
            onDismissRequest = { global.showdialog.value = false},
            confirmButton = {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                Button(onClick = {
                    global.showdialog.value = false
                    navController.navigate("login")},
                    colors = ButtonDefaults.buttonColors(globalcolors.primaryColor),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(130.dp)) {
                    Text("Login now",
                        style = myTextStyle,)
                }
                Button(onClick = {
                    global.showdialog.value = false},
                    colors = ButtonDefaults.buttonColors(globalcolors.primaryColor),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.width(130.dp)) {
                    Text("Cancel",
                        style = myTextStyle,)
                }}

            },
            title = { Text("Login First",
                style = myTextStyle,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold) },
            text = { Text("You have to login to perform this action.",
                style = myTextStyle,) },
            containerColor = globalcolors.secondaryColor,
        )
    }
}

@Preview
@Composable
fun AutomaticLogoutPreview(){
    LoginDialog(navController = rememberNavController())
}
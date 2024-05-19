package com.app.fitnessapp

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStudentScreen(onBack: () -> Unit, context: Context, navController: NavController) {
    var studentId by remember { mutableStateOf("") }
    var newStudentName by remember { mutableStateOf("") }
    var studentFound by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back navigation */ }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back",
                            modifier = Modifier.clickable {
                                navController.popBackStack()

                            })
                    }
                },
                actions = {
                    // Add any additional actions (e.g., settings icon) here
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = color,
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            // Your main content goes here
            TextField(
                value = studentId,
                onValueChange = { studentId = it },
                label = { Text("Enter Student ID") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val students = FileUtil.loadStudents(context)
                val student = students.find { it.studentid == studentId }
                if (student != null) {
                    newStudentName = student.name
                    studentFound = true
                }
            }) {
                Text("Find Student")
            }

            if (studentFound) {
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = newStudentName,
                    onValueChange = { newStudentName = it },
                    label = { Text("New Student Name") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    FileUtil.editStudent(context, Student(studentId, newStudentName))
                    studentFound = false
                    onBack()
                }) {
                    Text("Update Student")
                }
            }


        }

    }
}




@Preview
@Composable
fun EditStudentScreenPreview() {
    EditStudentScreen(onBack = {},context = LocalContext.current,navController = rememberNavController())
}
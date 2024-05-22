package com.app.classportal

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStudentScreen(onBack: () -> Unit, context: Context, navController: NavController) {
    var studentId by remember { mutableStateOf("") }
    var newStudentName by remember { mutableStateOf("") }
    var studentFound by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Student",
                    fontFamily = RobotoMono) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back",
                            tint = color4,
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            })
                    }
                },
                actions = {
                    // Add any additional actions (e.g., settings icon) here
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = color1,
                    titleContentColor = textcolor
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(color1)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Your main content goes here
            TextField(
                value = studentId, textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontFamily = RobotoMono
                ),
                onValueChange = { studentId = it },
                label = { Text("Enter Student ID", fontFamily = RobotoMono) },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xffA0E9FF),
                    unfocusedIndicatorColor = Color(0xffA0E9FF),
                    focusedContainerColor = Color(0xffA0E9FF),
                    unfocusedContainerColor = Color(0xffA0E9FF),
                ),
                singleLine = true,
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp),
                        clip = true
                    )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val students = FileUtil.loadStudents(context)
                    val student = students.find { it.registrationID == studentId }
                    if (student != null) {
                        newStudentName = student.studentname
                        studentFound = true
                    } else {
                        showDialog = true
                    }
                },
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp),
                        clip = true
                    )
                    .width(200.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(color),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Find Student",
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontFamily = RobotoMono
                )
            }

            if (studentFound) {
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = newStudentName,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = RobotoMono
                    ),
                    onValueChange = { newStudentName = it },
                    label = { Text("New Student Name", fontFamily = RobotoMono) },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color(0xffA0E9FF),
                        unfocusedIndicatorColor = Color(0xffA0E9FF),
                        focusedContainerColor = Color(0xffA0E9FF),
                        unfocusedContainerColor = Color(0xffA0E9FF)),
                    singleLine = true,
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(10.dp),
                            clip = true
                        )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        FileUtil.editStudent(context, Student(studentId, newStudentName))
                        studentFound = false
                        onBack()
                    },
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(10.dp),
                            clip = true
                        )
                        .width(200.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(color),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Update Student",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontFamily = RobotoMono
                    )
                }
            }

            // Dialog for student not found feedback
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(textcolor),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()) {
                            Text("OK",
                                fontFamily = RobotoMono,
                                fontSize = 16.sp,
                                color = color1)
                        }
                    },
                    title = { Text("Student Not Found",
                        fontFamily = RobotoMono,
                        fontSize = 16.sp,
                        color = color1,
                        fontWeight = FontWeight.Bold) },
                    text = { Text("The student with ID $studentId was not found.",
                        fontFamily = RobotoMono,
                        fontSize = 16.sp,
                        color = color1) }
                )
            }
        }
    }
}





@Preview
@Composable
fun EditStudentScreenPreview() {
    EditStudentScreen(onBack = {},context = LocalContext.current,navController = rememberNavController())
}
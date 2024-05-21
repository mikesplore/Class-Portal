package com.app.fitnessapp

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono


val textfieldColor = Color(0xff89CFF3)
@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddStudentScreen(onStudentAdded: () -> Unit, context: Context, navController: NavController) {
        var studentname by remember { mutableStateOf("") }
        var studentId by remember { mutableStateOf("") }
        val pattern = Regex("^[A-Za-z]{4}/\\d{3}[A-Za-z]/\\d{4}$")
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = { /* Handle back navigation */ }) {
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
                        titleContentColor = textcolor,
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // Use innerPadding for proper content positioning
            ) {
                // Your main content goes here
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color1)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {





                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            // Instructions
                            Text(
                                text = "Enter Student Name",
                                fontFamily = RobotoMono,
                                fontSize = 16.sp,
                                color = textcolor
                            )

                            // CustomTextField for student name
                            CustomTextField(
                                value = studentname,

                                onValueChange = { studentname = it },
                                placeholder = "Student name"
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Instructions
                            Text(
                                text = "Enter Student ID (Format: BSBS/108J/2021)",
                                fontFamily = RobotoMono,
                                fontSize = 16.sp,
                                color = textcolor
                            )

                            // CustomTextField for student ID
                            CustomTextField(
                                value = studentId,
                                onValueChange = { studentId = it },
                                placeholder = "Student ID"
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Button to add student
                            Button(
                                onClick = {
                                    if (studentname.isNotEmpty() && pattern.matches(studentId)) {
                                        val students = FileUtil.loadStudents(context).toMutableList()
                                        students.add(Student(registrationID = studentId, studentname = studentname))
                                        FileUtil.saveStudents(context, students)
                                        studentname = ""
                                        studentId = ""
                                        Toast.makeText(context, "Student added successfully", Toast.LENGTH_SHORT).show()
                                        onStudentAdded()
                                    } else {
                                        Toast.makeText(context, "Please enter a valid student ID", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier
                                    .width(200.dp)
                                    .height(50.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(color)
                            ) {
                                Text(
                                    "Add Student",
                                    color = Color.White,
                                    fontFamily = RobotoMono,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }


                }

            }

    }

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null
) {
    TextField(
        value = value,
        textStyle = TextStyle(
            color = color1,
            fontFamily = RobotoMono,
            fontSize = 16.sp
        ),
        onValueChange = onValueChange,
        modifier = modifier
            .width(300.dp)
            .height(50.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = textfieldColor,
            unfocusedContainerColor = textfieldColor,
            focusedIndicatorColor = textfieldColor,
            unfocusedIndicatorColor = textfieldColor,
            focusedTextColor = color1,
            unfocusedTextColor = color1,
            focusedLabelColor = color1,
            unfocusedLabelColor = color1
        ),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        placeholder = { placeholder?.let { Text(it) } }
    )
}



@Preview
@Composable
fun AddStudentScreenPreview() {
    AddStudentScreen(onStudentAdded = {},navController = rememberNavController(), context = LocalContext.current)
}
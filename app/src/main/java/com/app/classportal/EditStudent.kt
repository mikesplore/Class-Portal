package com.app.classportal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
    var newfirstName by remember { mutableStateOf("") }
    var newlastName by remember { mutableStateOf("") }
    var studentFound by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("  Edit Student",
                    fontFamily = RobotoMono) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Box(modifier = Modifier

                            .border(
                                width = 1.dp,
                                color = textColor,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                            .size(50.dp),
                            contentAlignment = Alignment.Center){
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = textColor,
                            )
                        }
                    }
                },
                actions = {
                    // Add any additional actions (e.g., settings icon) here
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = textColor
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(backbrush)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Your main content goes here
            TextField(
                value = studentId, textStyle = TextStyle(
                    fontFamily = RobotoMono
                ),
                onValueChange = { studentId = it },
                label = { Text("Enter Student ID", fontFamily = RobotoMono) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = primaryColor,
                    unfocusedContainerColor = primaryColor,
                    focusedIndicatorColor = focused,
                    unfocusedIndicatorColor = unfocused,
                    focusedLabelColor = textColor,
                    cursorColor = textColor,
                    unfocusedLabelColor = textColor,
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor
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
                        newfirstName = student.firstName
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
                    .width(275.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(secondaryColor),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Find Student",
                    style = myTextStyle,
                    fontWeight = FontWeight.Bold,
                    
                )
            }

            if (studentFound) {
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = newfirstName,
                    textStyle = TextStyle(
                        fontFamily = RobotoMono
                    ),
                    onValueChange = { newfirstName = it },
                    label = { Text("New First Name", fontFamily = RobotoMono) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = primaryColor,
                        unfocusedContainerColor = primaryColor,
                        focusedIndicatorColor = focused,
                        unfocusedIndicatorColor = unfocused,
                        focusedLabelColor = textColor,
                        cursorColor = textColor,
                        unfocusedLabelColor = textColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor),
                    singleLine = true,
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(10.dp),
                            clip = true
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = newlastName,
                    textStyle = TextStyle(
                        fontFamily = RobotoMono
                    ),
                    onValueChange = { newlastName = it },
                    label = { Text("New Last Name", fontFamily = RobotoMono) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = primaryColor,
                        unfocusedContainerColor = primaryColor,
                        focusedIndicatorColor = focused,
                        unfocusedIndicatorColor = unfocused,
                        focusedLabelColor = textColor,
                        cursorColor = textColor,
                        unfocusedLabelColor = textColor,
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor),
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
                        FileUtil.editStudent(context, Student(studentId, newfirstName, newlastName))
                        studentFound = false
                        newfirstName = ""
                        newlastName = ""
                        Toast.makeText(context, "Student updated successfully", Toast.LENGTH_SHORT).show()
                        onBack()
                    },
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(10.dp),
                            clip = true
                        )
                        .width(275.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(secondaryColor),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Update Student",
                        style = myTextStyle,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            // Dialog for student not found feedback
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(primaryColor),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()) {
                            Text("OK",
                                style = myTextStyle,)
                        }
                    },
                    title = { Text("Student Not Found",
                        style = myTextStyle,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold) },
                    text = { Text("The student with ID $studentId was not found.",
                        style = myTextStyle,) },
                    containerColor = secondaryColor,
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
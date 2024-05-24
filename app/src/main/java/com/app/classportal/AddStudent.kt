package com.app.classportal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.app.classportal.ui.theme.RobotoMono


val textfieldColor = Color(0xff89CFF3)
@OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun AddStudentScreen(onStudentAdded: () -> Unit, context: Context, navController: NavController) {
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var studentId by remember { mutableStateOf("") }
        val pattern = Regex("^[A-Za-z]{4}/\\d{3}[A-Za-z]/\\d{4}$")
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "   Add Student",
                        fontFamily = RobotoMono,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp)},
                    navigationIcon = {
                        IconButton(onClick = { navController.navigate("welcome") },
                            modifier = Modifier.absolutePadding(left = 10.dp)) {
                            Box(modifier = Modifier

                                .border(
                                    width = 1.dp,
                                    color = Color.White,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                                .size(50.dp),
                                contentAlignment = Alignment.Center){
                                Icon(
                                    imageVector = Icons.Default.ArrowBackIosNew,
                                    contentDescription = "Back",
                                    tint = Color.White,
                                )
                            }

                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
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
                        .background(Color.Black)
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
                                value = firstName,

                                onValueChange = { firstName = it },
                                placeholder = "Student name"
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // Instructions
                            Text(
                                text = "Enter Student ID",
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
                                    if (firstName.isNotEmpty() && pattern.matches(studentId)) {
                                        val students = FileUtil.loadStudents(context).toMutableList()
                                        students.add(Student(registrationID = studentId, firstName = firstName, lastName = lastName))
                                        FileUtil.saveStudents(context, students)
                                        firstName = ""
                                        studentId = ""
                                        Toast.makeText(context, "Student added successfully", Toast.LENGTH_SHORT).show()
                                        onStudentAdded()
                                    } else {
                                        Toast.makeText(context, "Please enter a valid student ID", Toast.LENGTH_SHORT).show()
                                    }
                                },
                                modifier = Modifier
                                    .width(350.dp)
                                    .height(70.dp),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(Color.Transparent)
                            ) {

                                    Row(modifier = Modifier
                                        .background(brush, shape = RoundedCornerShape(10.dp))
                                        .height(50.dp)
                                        .width(300.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically) {

                                        Text(
                                            text = "Add Student",
                                            color = color4,
                                            fontWeight = FontWeight.Normal,
                                            fontSize = 15.sp,
                                            fontFamily = RobotoMono
                                        )}

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
            color = Color.White,
            fontFamily = RobotoMono,
            fontSize = 16.sp
        ),
        onValueChange = onValueChange,
        modifier = modifier
            .height(50.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = focused,
            unfocusedIndicatorColor = unfocused,
            focusedLabelColor = Color.White,
            cursorColor = Color.Black,
            unfocusedLabelColor = Color.White,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
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


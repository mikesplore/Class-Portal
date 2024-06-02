package com.app.classportal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.graphics.Brush
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
fun AddStudentScreen(onStudentAdded: () -> Unit, context: Context, navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }
    val addbackbrush = remember {
        mutableStateOf(
            Brush.verticalGradient(
                colors = listOf(
                    GlobalColors.primaryColor,
                    GlobalColors.secondaryColor,
                    GlobalColors.primaryColor
                )
            )
        )
    }.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "   Add Student",
                        fontFamily = RobotoMono,
                        color = GlobalColors.textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("dashboard") },
                        modifier = Modifier.absolutePadding(left = 10.dp)
                    ) {
                        Box(
                            modifier = Modifier

                                .border(
                                    width = 1.dp,
                                    color = GlobalColors.textColor,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                                .size(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = GlobalColors.textColor,
                            )
                        }

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GlobalColors.primaryColor)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(addbackbrush)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(addbackbrush)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Instructions
                    Text(
                        text = "Enter First Name",
                        fontFamily = RobotoMono,
                        fontSize = 16.sp,
                        color = GlobalColors.textColor
                    )

                    // CustomTextField for student name
                    TextFields(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = "First name"
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enter Last Name",
                        fontFamily = RobotoMono,
                        fontSize = 16.sp,
                        color = GlobalColors.textColor
                    )

                    // CustomTextField for student name
                    TextFields(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = "Last name"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Instructions
                    Text(
                        text = "Enter Student ID",
                        fontFamily = RobotoMono,
                        fontSize = 16.sp,
                        color = GlobalColors.textColor
                    )

                    // CustomTextField for student ID
                    TextFields(
                        value = studentId,
                        onValueChange = { studentId = it },
                        label = "Student ID"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Button to add student
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val isValid = firstName.isNotEmpty()
                            if (isValid) {
                                FileUtil.loadStudents(context).toMutableList().apply {
                                    add(Student(studentId, firstName, lastName, global.username.value,global.password.value))
                                    FileUtil.saveStudents(
                                        context,
                                        this
                                    ) 
                                }
                                // Clear fields & show success message (combined)
                                firstName = ""
                                lastName = ""
                                studentId = ""
                                Toast.makeText(
                                    context,
                                    "Student added successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                onStudentAdded()
                            } else {
                                // Show invalid ID message
                                Toast.makeText(
                                    context,
                                    "Please enter a valid student ID",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        modifier = Modifier
                            .width(275.dp)
                            .background(
                                addbackbrush,
                                RoundedCornerShape(10.dp)
                            ), // Background moved to outer Modifier
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Text(
                            text = "Add Student",
                            color = GlobalColors.textColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            fontFamily = RobotoMono,
                            modifier = Modifier.padding(10.dp) // Add padding to the Text
                        )

                    }
                }
            }
        }
    }
}

@Composable
fun TextFields(
    value: String,
    onValueChange: (String) -> Unit,

    label: String = ""
) {
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
        value = value.trimEnd(),
        textStyle = TextStyle(
            color = GlobalColors.textColor,
            fontFamily = RobotoMono,
            fontSize = 16.sp
        ),
        label = {
            Text(
                text = label,
                style = TextStyle(
                    color = GlobalColors.textColor,
                    fontSize = 16.sp,
                    fontFamily = RobotoMono
                )
            )
        },
        onValueChange = onValueChange,


        colors = TextFieldDefaults.colors(
            focusedContainerColor = GlobalColors.primaryColor,
            unfocusedContainerColor = GlobalColors.primaryColor,
            focusedIndicatorColor = GlobalColors.textColor,
            unfocusedIndicatorColor = GlobalColors.primaryColor,
            focusedLabelColor = GlobalColors.textColor,
            cursorColor = GlobalColors.textColor,
            unfocusedLabelColor = GlobalColors.textColor,
            focusedTextColor = GlobalColors.textColor,
            unfocusedTextColor = GlobalColors.textColor

        ),
        singleLine = true,
        shape = RoundedCornerShape(10.dp),

        )
}


@Preview
@Composable
fun AddStudentScreenPreview() {
    AddStudentScreen(
        onStudentAdded = {},
        navController = rememberNavController(),
        context = LocalContext.current
    )
}


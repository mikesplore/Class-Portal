package com.app.classportal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.CommonComponents as CC


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(onStudentAdded: () -> Unit, context: Context, navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var studentId by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "   Add Student",
                        style = CC.titleTextStyle
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("dashboard") },
                        modifier = Modifier.absolutePadding(left = 10.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = GlobalColors.textColor,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GlobalColors.primaryColor)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(CC.backbrush)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(CC.backbrush)
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

                    CC.SingleLinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = "First name",
                        singleLine = true
                    )

                    CC.SingleLinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = "Last name",
                        singleLine = true
                    )

                    CC.SingleLinedTextField(
                        value = studentId,
                        onValueChange = { studentId = it },
                        label = "Student ID",
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val isValid = firstName.isNotEmpty()
                            if (isValid) {
                                FileUtil.loadStudents(context).toMutableList().apply {
                                    add(
                                        Student(
                                            studentId,
                                            firstName,
                                            lastName,
                                            global.username.value,
                                            global.password.value
                                        )
                                    )
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
                                CC.backbrush,
                                RoundedCornerShape(10.dp)
                            ), // Background moved to outer Modifier
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Text(
                            text = "Add Student",
                            style = CC.descriptionTextStyle,
                            modifier = Modifier.padding(10.dp) // Add padding to the Text
                        )
                    }
                }
            }
        }
    }
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


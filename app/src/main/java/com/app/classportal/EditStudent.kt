package com.app.classportal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.CommonComponents as CC


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditStudentScreen(onBack: () -> Unit, context: Context, navController: NavController) {
    var studentId by remember { mutableStateOf("") }
    var newfirstName by remember { mutableStateOf("") }
    var newlastName by remember { mutableStateOf("") }
    var studentFound by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                "  Edit Student", style = CC.titleTextStyle
            )
        }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = GlobalColors.textColor,
                )
            }
        }, actions = {
            // Add any additional actions (e.g., settings icon) here
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = GlobalColors.primaryColor,
            titleContentColor = GlobalColors.textColor
        )
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .background(CC.backbrush)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Your main content goes here
            CC.SingleLinedTextField(
                value = studentId,
                onValueChange = { studentId = it },
                label = "Student ID",
                singleLine = true
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
                        elevation = 10.dp, shape = RoundedCornerShape(10.dp), clip = true
                    )
                    .width(275.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(GlobalColors.secondaryColor),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    "Find Student",
                    style = CC.descriptionTextStyle,
                )
            }

            if (studentFound) {
                Spacer(modifier = Modifier.height(16.dp))
                CC.SingleLinedTextField(
                    value = newfirstName,
                    onValueChange = { newfirstName = it },
                    label = "New First Name",
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))
                CC.SingleLinedTextField(
                    value = newlastName,
                    onValueChange = { newlastName = it },
                    label = "New Last Name",
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        FileUtil.editStudent(
                            context,
                            Student(
                                studentId,
                                newfirstName,
                                newlastName,
                                global.username.value,
                                global.password.value
                            )
                        )
                        studentFound = false
                        newfirstName = ""
                        newlastName = ""
                        Toast.makeText(context, "Student updated successfully", Toast.LENGTH_SHORT)
                            .show()
                        onBack()
                    },
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp, shape = RoundedCornerShape(10.dp), clip = true
                        )
                        .width(275.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(GlobalColors.secondaryColor),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        "Update Student",
                        style = CC.descriptionTextStyle,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }

            // Dialog for student not found feedback
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(GlobalColors.primaryColor),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                "OK", style = CC.descriptionTextStyle
                            )
                        }
                    },
                    title = {
                        Text(
                            "Student Not Found",
                            style = CC.descriptionTextStyle,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    text = {
                        Text(
                            "The student with ID $studentId was not found.",
                            style = CC.descriptionTextStyle
                        )
                    },
                    containerColor = GlobalColors.secondaryColor,
                )
            }
        }
    }
}

@Preview
@Composable
fun EditStudentScreenPreview() {
    EditStudentScreen(
        onBack = {},
        context = LocalContext.current,
        navController = rememberNavController()
    )
}
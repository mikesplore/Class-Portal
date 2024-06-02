package com.app.classportal

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.CommonComponents as CC

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordResetScreen(navController: NavController, context: Context) {
    var regID by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var step by remember { mutableIntStateOf(1) }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(text = "Password Reset", style = CC.titleTextStyle)
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = GlobalColors.primaryColor,
                titleContentColor = GlobalColors.textColor
            )
        )
    }) {
        Column(
            modifier = Modifier
                .background(CC.backbrush)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (step == 1) {
                Text("Verify Details", style = CC.descriptionTextStyle)

                Spacer(modifier = Modifier.height(16.dp))

                CC.SingleLinedTextField(
                    value = regID,
                    onValueChange = { regID = it.trimEnd() },
                    label = "Registration ID",
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(16.dp))

                CC.SingleLinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it.trimEnd() },
                    label = "First Name",
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                CC.SingleLinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it.trimEnd() },
                    label = "Last Name",
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val students = FileUtil.loadStudents(context)
                        val student =
                            students.find { it.registrationID == regID && it.firstName == firstName && it.lastName == lastName }

                        if (student != null) {
                            step = 2
                        } else {
                            Toast.makeText(
                                context, "Details do not match our records.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.width(300.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(GlobalColors.primaryColor)
                ) {
                    Text("Verify")
                }
            } else if (step == 2) {
                Text("Enter New Password", style = CC.descriptionTextStyle)

                Spacer(modifier = Modifier.height(16.dp))

                CC.PasswordTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = "New Password",
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                CC.PasswordTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirm Password",
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (newPassword == confirmPassword) {
                            val students = FileUtil.loadStudents(context).toMutableList()
                            val studentIndex = students.indexOfFirst { it.registrationID == regID }

                            if (studentIndex != -1) {
                                students[studentIndex] =
                                    students[studentIndex].copy(password = newPassword)
                                FileUtil.saveStudents(context, students)
                                Toast.makeText(
                                    context, "Password updated successfully.", Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack() // Navigate back after successful password reset
                            }
                        } else {
                            Toast.makeText(
                                context, "Passwords do not match.", Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.width(300.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(GlobalColors.primaryColor)

                ) {
                    Text("Reset Password", style = CC.descriptionTextStyle)
                }
            }
        }
    }
}

@Preview
@Composable
fun PasswordResetScreenPreview() {
    PasswordResetScreen(navController = rememberNavController(), context = LocalContext.current)
}
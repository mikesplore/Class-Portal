package com.app.classportal

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono

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
    var passwordVisibility by remember { mutableStateOf(false) }
    val addbackbrush = remember {
        mutableStateOf(
            Brush.verticalGradient(
                colors = listOf(
                    globalcolors.primaryColor,
                    globalcolors.secondaryColor,
                    globalcolors.primaryColor
                )
            )
        )
    }.value

    Scaffold(
        topBar ={
            TopAppBar(title = {
                Text(text = "Password Reset", style = myTextStyle, fontSize = 20.sp)
            },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = globalcolors.primaryColor,
                    titleContentColor = globalcolors.textColor
                ))

        }
    ) {
        Column(
            modifier = Modifier
                .background(addbackbrush)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (step == 1) {
                Text("Verify Details", style = myTextStyle)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = regID,
                    textStyle = TextStyle(fontFamily = RobotoMono),
                    onValueChange = { regID = it.trimEnd() },
                    label = { Text(text = "Registration ID", fontFamily = RobotoMono, fontSize = 15.sp) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = globalcolors.primaryColor,
                        unfocusedContainerColor = globalcolors.primaryColor,
                        focusedIndicatorColor = globalcolors.tertiaryColor,
                        unfocusedIndicatorColor = globalcolors.primaryColor,
                        focusedLabelColor = globalcolors.textColor,
                        cursorColor = globalcolors.textColor,
                        unfocusedLabelColor = globalcolors.textColor,
                        focusedTextColor = globalcolors.textColor,
                        unfocusedTextColor = globalcolors.textColor
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),

                            )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = firstName,
                    textStyle = TextStyle(fontFamily = RobotoMono),
                    onValueChange = { firstName = it.trimEnd() },
                    label = { Text(text = "First Name", fontFamily = RobotoMono, fontSize = 15.sp) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = globalcolors.primaryColor,
                        unfocusedContainerColor = globalcolors.primaryColor,
                        focusedIndicatorColor = globalcolors.tertiaryColor,
                        unfocusedIndicatorColor = globalcolors.primaryColor,
                        focusedLabelColor = globalcolors.textColor,
                        cursorColor = globalcolors.textColor,
                        unfocusedLabelColor = globalcolors.textColor,
                        focusedTextColor = globalcolors.textColor,
                        unfocusedTextColor = globalcolors.textColor
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),

                            )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = lastName,
                    textStyle = TextStyle(fontFamily = RobotoMono),
                    onValueChange = { lastName = it.trimEnd() },
                    label = { Text(text = "Last Name", fontFamily = RobotoMono, fontSize = 15.sp) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = globalcolors.primaryColor,
                        unfocusedContainerColor = globalcolors.primaryColor,
                        focusedIndicatorColor = globalcolors.tertiaryColor,
                        unfocusedIndicatorColor = globalcolors.primaryColor,
                        focusedLabelColor = globalcolors.textColor,
                        cursorColor = globalcolors.textColor,
                        unfocusedLabelColor = globalcolors.textColor,
                        focusedTextColor = globalcolors.textColor,
                        unfocusedTextColor = globalcolors.textColor
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),

                            )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val students = FileUtil.loadStudents(context)
                        val student = students.find { it.registrationID == regID && it.firstName == firstName && it.lastName == lastName }

                        if (student != null) {
                            step = 2
                        } else {
                            Toast.makeText(
                                context,
                                "Details do not match our records.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.width(300.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(globalcolors.primaryColor)
                ) {
                    Text("Verify")
                }
            } else if (step == 2) {
                Text("Enter New Password", style = myTextStyle)

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = newPassword,
                    textStyle = TextStyle(fontFamily = RobotoMono),
                    onValueChange = { newPassword = it },
                    label = { Text(text = "Password", fontFamily = RobotoMono) },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                tint = globalcolors.textColor,
                                contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = globalcolors.primaryColor,
                        unfocusedContainerColor = globalcolors.primaryColor,
                        focusedIndicatorColor = globalcolors.tertiaryColor,
                        unfocusedIndicatorColor = globalcolors.primaryColor,
                        focusedLabelColor = globalcolors.textColor,
                        cursorColor = globalcolors.textColor,
                        unfocusedLabelColor = globalcolors.textColor,
                        focusedTextColor = globalcolors.textColor,
                        unfocusedTextColor = globalcolors.textColor
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),

                            )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = confirmPassword,
                    textStyle = TextStyle(fontFamily = RobotoMono),
                    onValueChange = { confirmPassword = it },
                    label = { Text(text = "Password", fontFamily = RobotoMono) },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                tint = globalcolors.textColor,
                                contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                            )
                        }
                    },
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = globalcolors.primaryColor,
                        unfocusedContainerColor = globalcolors.primaryColor,
                        focusedIndicatorColor = globalcolors.tertiaryColor,
                        unfocusedIndicatorColor = globalcolors.primaryColor,
                        focusedLabelColor = globalcolors.textColor,
                        cursorColor = globalcolors.textColor,
                        unfocusedLabelColor = globalcolors.textColor,
                        focusedTextColor = globalcolors.textColor,
                        unfocusedTextColor = globalcolors.textColor
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),

                            )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (newPassword == confirmPassword) {
                            val students = FileUtil.loadStudents(context).toMutableList()
                            val studentIndex = students.indexOfFirst { it.registrationID == regID }

                            if (studentIndex != -1) {
                                students[studentIndex] = students[studentIndex].copy(password = newPassword)
                                FileUtil.saveStudents(context, students)
                                Toast.makeText(
                                    context,
                                    "Password updated successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack() // Navigate back after successful password reset
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Passwords do not match.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier.width(300.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(globalcolors.primaryColor)

                ) {
                    Text("Reset Password", style = myTextStyle)
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
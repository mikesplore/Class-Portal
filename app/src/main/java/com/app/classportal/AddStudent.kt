package com.app.classportal

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono
import kotlinx.coroutines.delay

val buttonBrush = Brush.linearGradient(
    listOf(
        primaryColor,
        secondaryColor,
        tertiaryColor

    )
)

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
                title = {
                    Text(
                        text = "   Add Student",
                        fontFamily = RobotoMono,
                        color = textColor,
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
                                    color = textColor,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                                .size(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = textColor,
                            )
                        }

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryColor)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(backbrush)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // main content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(backbrush)
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
                        color = textColor
                    )

                    // CustomTextField for student name
                    CustomTextField(
                        value = firstName,

                        onValueChange = { firstName = it },
                        label = "First name"
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Enter Last Name",
                        fontFamily = RobotoMono,
                        fontSize = 16.sp,
                        color = textColor
                    )

                    // CustomTextField for student name
                    CustomTextField(
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
                        color = textColor
                    )

                    // CustomTextField for student ID
                    CustomTextField(
                        value = studentId,
                        onValueChange = { studentId = it },
                        label = "Student ID"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Button to add student
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {
                            val isValid = firstName.isNotEmpty() && pattern.matches(studentId)
                            if (isValid) {
                                FileUtil.loadStudents(context).toMutableList().apply {
                                    add(Student(studentId, firstName, lastName))
                                    FileUtil.saveStudents(
                                        context,
                                        this
                                    ) // Save directly within 'apply'
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
                                backbrush,
                                RoundedCornerShape(10.dp)
                            ), // Background moved to outer Modifier
                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                    ) {
                        Text(
                            text = "Add Student",
                            color = color4,
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
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = ""
) {
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
        value = value,
        textStyle = TextStyle(
            color = textColor,
            fontFamily = RobotoMono,
            fontSize = 16.sp
        ),
        label = {
            Text(
                text = label,
                style = TextStyle(color = textColor, fontSize = 16.sp, fontFamily = RobotoMono)
            )
        },
        onValueChange = onValueChange,
        modifier = modifier
            .height(50.dp),
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


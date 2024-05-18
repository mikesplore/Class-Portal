package com.app.fitnessapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono

// Data Classes
data class Student(val id: String, val name: String, val attendanceRecords: List<AttendanceRecord> = mutableListOf())

val textfieldcolor= Color(0xffA0E9FF)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddStudentScreen(navController: NavController) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }

    val typography = MaterialTheme.typography
    val colors = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ADD NEW STUDENT",
                        fontFamily = RobotoMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = color
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Add New Student",
                style = typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name", fontFamily = RobotoMono) },
                singleLine = true,
                modifier = Modifier
                    .shadow(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .fillMaxWidth(0.8f),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = textfieldcolor,
                    unfocusedIndicatorColor = textfieldcolor,
                    focusedContainerColor = textfieldcolor,
                    unfocusedContainerColor = textfieldcolor,

                ),
                shape = RoundedCornerShape(10.dp)

            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = id,
                onValueChange = { id = it },
                label = { Text("Admission", fontFamily = RobotoMono) },
                singleLine = true,
                modifier = Modifier
                    .shadow(
                        elevation = 3.dp,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .fillMaxWidth(0.8f),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = textfieldcolor,
                    unfocusedIndicatorColor = textfieldcolor,
                    focusedContainerColor = textfieldcolor,
                    unfocusedContainerColor = textfieldcolor,

                    ),
                shape = RoundedCornerShape(10.dp)

            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (name.isNotEmpty() && id.isNotEmpty()) {
                        name = ""
                        id = ""
                    }
                    Toast.makeText(context, "Student added successfully", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    color),
                shape = RoundedCornerShape(10.dp)

            ) {
                Text(
                    text = "Add Student",
                    fontFamily = RobotoMono,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}


// Preview function
@Preview(showBackground = true)
@Composable
fun AddStudentScreenPreview() {
    AddStudentScreen(rememberNavController()) // Provide an empty lambda for onStudentAdded since it's not used in the preview
}

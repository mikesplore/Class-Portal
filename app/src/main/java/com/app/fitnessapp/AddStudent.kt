package com.app.fitnessapp

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialogDefaults.containerColor
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
import androidx.compose.ui.res.painterResource
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
    fun AddStudentScreen(onStudentAdded: (Student) -> Unit,navController: NavController) {
        var name by remember { mutableStateOf("") }// Replace 'MyScreen' with your screen name
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("") },
                    navigationIcon = {
                        IconButton(onClick = { /* Handle back navigation */ }) {
                            Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back",
                                modifier = Modifier.clickable {
                                    navController.popBackStack()

                                })
                        }
                    },
                    actions = {
                        // Add any additional actions (e.g., settings icon) here
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = color,
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
                        .background(background)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Add Student",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontFamily = RobotoMono
                    )
                    Column (modifier = Modifier.size(290.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){

                        Image(painter = painterResource(id = R.drawable.add), contentDescription = "Add Student",
                            modifier = Modifier
                                .fillMaxSize())
                    }
                    Column (modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center){

                    TextField(
                        value = name,
                        onValueChange = { name = it },

                        modifier = Modifier
                            .width(300.dp)
                            .height(50.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = textfieldColor,
                            unfocusedContainerColor = textfieldColor,
                            focusedIndicatorColor = textfieldColor,
                            unfocusedIndicatorColor = textfieldColor,

                            ),
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp)

                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (name.isNotEmpty()) {
                                onStudentAdded(
                                    Student(
                                        id = System.currentTimeMillis().toInt(),
                                        name = name
                                    )
                                )
                                name = ""
                            }
                        },
                        modifier = Modifier
                            .width(200.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(color)
                    ) {
                        Text("Add Student",
                            color = Color.White,
                            fontFamily = RobotoMono,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold)
                    }
                }
                }

            }
        }
    }




@Preview
@Composable
fun AddStudentScreenPreview() {
    AddStudentScreen(onStudentAdded = {},navController = rememberNavController())
}
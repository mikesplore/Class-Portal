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
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteStudentScreen(context: Context, navController: NavController) {
    var students by remember { mutableStateOf(FileUtil.loadStudents(context)) }
    var studentIdToDelete by remember { mutableStateOf("") }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var firstNameToDelete by remember { mutableStateOf("") }
    var lastNameToDelete by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(" Delete Student",
                    fontWeight = FontWeight.Bold,
                    fontFamily = RobotoMono) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() },
                        modifier = Modifier.absolutePadding(10.dp)) {
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = textColor,
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .background(backbrush)
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn (modifier = Modifier
                .border(1.dp, secondaryColor)){
                item {
                    Text("Total Students: ${students.size}",
                        modifier = Modifier.padding(16.dp),
                        fontWeight = FontWeight.Bold,
                        fontFamily = RobotoMono,
                        color = textColor)
                }
                itemsIndexed(students) { _, student ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {
                                studentIdToDelete = student.registrationID
                                firstNameToDelete = student.firstName
                                lastNameToDelete = student.lastName
                                showConfirmationDialog = true
                            },
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier
                            .width(200.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,) {
                            Text(student.firstName,
                                fontWeight = FontWeight.Normal,
                                color = textColor,
                                fontSize = 16.sp,
                                fontFamily = RobotoMono,)
                            Text(student.lastName,
                                fontWeight = FontWeight.Normal,
                                color = textColor,
                                fontSize = 16.sp,
                                fontFamily = RobotoMono,)
                        }

                        Text(student.registrationID,
                            fontFamily = RobotoMono,
                            color = textColor,
                            fontSize = 16.sp,)
                    }
                }
            }
            Text("Select Student to Delete", modifier = Modifier.padding(16.dp), fontWeight = FontWeight.Bold,color = color4)

            if (showConfirmationDialog) {
                AlertDialog(
                    onDismissRequest = { showConfirmationDialog = false },
                    confirmButton = {

                        Button(onClick = {
                            FileUtil.deleteStudent(context, studentIdToDelete)
                            showConfirmationDialog = false
                            students = FileUtil.loadStudents(context) // Reload the list of students after deletion
                            Toast.makeText(context, "Student details deleted!", Toast.LENGTH_SHORT).show()
                        },
                            colors = ButtonDefaults.buttonColors(containerColor = textcolor)) {
                            Text("Delete",
                                fontFamily = RobotoMono,)
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showConfirmationDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = textcolor)) {
                            Text("Cancel",
                                fontFamily = RobotoMono,)
                        }
                    },

                    title = { Text("Delete Student", fontFamily = RobotoMono) },
                    text = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("You are about to delete this student",fontFamily = RobotoMono,color = color1)
                            Text("$firstNameToDelete$lastNameToDelete", fontWeight = FontWeight.Bold,fontFamily = RobotoMono,fontSize = 20.sp)
                        }

                        },
                    containerColor = color3

                )
            }
        }

    }
}


@Preview
@Composable
fun DeleteStudentScreenPreview() {
    val navController = rememberNavController()
    DeleteStudentScreen(LocalContext.current, navController)
}






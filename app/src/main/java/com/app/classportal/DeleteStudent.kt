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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val originalStudents = remember { students.toList() } // Store a copy of original data
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selected by remember{mutableStateOf(false)}
    val selectedItem = remember { mutableStateOf<Student?>(null) }
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
                        " Delete Student",
                        fontWeight = FontWeight.Bold,
                        fontFamily = RobotoMono
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.absolutePadding(10.dp)
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
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GlobalColors.primaryColor,
                    titleContentColor = GlobalColors.textColor,
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .background(addbackbrush)
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            LazyColumn(
                modifier = Modifier
                    .border(1.dp, GlobalColors.secondaryColor),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { query ->
                            searchQuery = query
                            students = if (query.text.isNotBlank()) {
                                originalStudents.filter {
                                    it.firstName.contains(query.text, ignoreCase = true) ||
                                            it.lastName.contains(query.text, ignoreCase = true) ||
                                            it.registrationID.contains(query.text, ignoreCase = true)
                                }
                            } else {
                                originalStudents
                            }
                        },
                        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search",
                            tint = GlobalColors.textColor) },
                        placeholder = { Text("Search", fontFamily = RobotoMono, color = GlobalColors.textColor) },
                        modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth(0.9f),
                        shape = RoundedCornerShape(8.dp),
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
                        textStyle = TextStyle(fontFamily = RobotoMono, color = GlobalColors.textColor)
                    )

                }
                itemsIndexed(students) { _, student ->
                    val isSelected = student == selectedItem.value  // Determine if this row is selected
                    val background = if (isSelected) GlobalColors.secondaryColor else Color.Transparent
                    Row(
                        modifier = Modifier
                            .background(background)
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {
                                selected = !selected
                                studentIdToDelete = student.registrationID
                                firstNameToDelete = student.firstName
                                lastNameToDelete = student.lastName
                                showConfirmationDialog = true
                            },
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .width(200.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                student.firstName,
                                style = myTextStyle
                            )
                            Text(
                                student.lastName,
                                style = myTextStyle
                            )
                        }

                        Text(
                            student.registrationID,
                            style = myTextStyle
                        )
                    }
                }
            }
            Button(onClick = {
                FileUtil.deleteStudent(context, studentIdToDelete)
                showConfirmationDialog = false
                students =
                    FileUtil.loadStudents(context) // Reload the list of students after deletion
                Toast.makeText(
                    context,
                    "Student details deleted!",
                    Toast.LENGTH_SHORT
                ).show()
                selected = false
            },
                colors = ButtonDefaults.buttonColors(containerColor = if(selected) GlobalColors.secondaryColor else GlobalColors.primaryColor)) {
                Text("Delete this student",
                    style = myTextStyle)
                
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






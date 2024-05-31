package com.app.classportal

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowStudentsScreen(context: Context, navController: NavController) {
    var students by remember { mutableStateOf(FileUtil.loadStudents(context)) }
    val originalStudents = remember { students.toList() } // Store a copy of original data
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround,

                    ) {
                        Text(
                            "Students",
                            fontWeight = FontWeight.Bold,
                            fontFamily = RobotoMono,
                            color = GlobalColors.textColor,
                            modifier = Modifier.clickable{
                                navController.navigate("students")
                            }
                        )

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
                                .width(200.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = GlobalColors.secondaryColor,
                                unfocusedBorderColor = GlobalColors.primaryColor,
                                cursorColor = GlobalColors.primaryColor,
                                containerColor = GlobalColors.primaryColor,
                                focusedLabelColor = GlobalColors.primaryColor,
                                unfocusedLabelColor = GlobalColors.primaryColor,
                            ),
                            textStyle = TextStyle(fontFamily = RobotoMono, color = GlobalColors.primaryColor)
                        )
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
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Sort by: ",
                style = myTextStyle,
                modifier = Modifier.absolutePadding(8.dp,5.dp))
            Row (modifier = Modifier
                .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,){
                Button(
                    onClick = {
                        students = students.sortedBy { it.registrationID }
                    },
                    modifier = Modifier
                        .width(150.dp)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GlobalColors.primaryColor,
                        contentColor = GlobalColors.textColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Reg ID", fontFamily = RobotoMono)
                }
                Button(
                    onClick = {
                        students = students.sortedBy { it.firstName }
                    },
                    modifier = Modifier
                        .width(170.dp)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GlobalColors.primaryColor,
                        contentColor = GlobalColors.textColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("First Name", fontFamily = RobotoMono)
                }
                Button(
                    onClick = {
                        students = students.sortedBy { it.lastName }
                    },
                    modifier = Modifier
                        .width(170.dp)
                        .padding(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GlobalColors.primaryColor,
                        contentColor = GlobalColors.textColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Last Name", fontFamily = RobotoMono)
                }

            }

            LazyColumn(modifier = Modifier.border(1.dp, GlobalColors.secondaryColor)) {
                itemsIndexed(students) { index, student ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(
                                width = 1.dp,
                                color = GlobalColors.tertiaryColor,
                            ),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            (index + 1).toString(),
                            fontWeight = FontWeight.Normal,
                            color = GlobalColors.textColor,
                            fontSize = 16.sp,
                            fontFamily = RobotoMono,
                            modifier = Modifier.width(30.dp)
                        )
                        Row(
                            modifier = Modifier.width(200.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                student.firstName,
                                fontWeight = FontWeight.Normal,
                                color = GlobalColors.textColor,
                                fontSize = 16.sp,
                                fontFamily = RobotoMono,
                            )
                            Text(
                                student.lastName,
                                fontWeight = FontWeight.Normal,
                                color = GlobalColors.textColor,
                                fontSize = 16.sp,
                                fontFamily = RobotoMono,
                            )
                        }
                        Text(
                            student.registrationID,
                            fontFamily = RobotoMono,
                            color = GlobalColors.textColor,
                            fontSize = 16.sp,
                        )
                    }
                    Divider(color = GlobalColors.tertiaryColor, thickness = 1.dp)
                }
            }
            Text(
                text = "Total Students: ${students.size}",
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Preview
@Composable
fun ShowStudents() {
    ShowStudentsScreen(LocalContext.current, rememberNavController())
}

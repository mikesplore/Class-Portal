package com.app.classportal

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.classportal.ui.theme.RobotoMono

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowStudentsScreen(context: Context) {
    var students by remember { mutableStateOf(FileUtil.loadStudents(context)) }
    val originalStudents = remember { students.toList() } // Store a copy of original data
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

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
                            fontFamily = RobotoMono
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
                                tint = textColor) },
                            placeholder = { Text("Search", fontFamily = RobotoMono, color = textColor) },
                            modifier = Modifier
                                .height(50.dp)
                                .width(200.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = secondaryColor,
                                unfocusedBorderColor = primaryColor,
                                cursorColor = textColor,
                                containerColor = primaryColor,
                                focusedLabelColor = textColor,
                                unfocusedLabelColor = textColor,
                            ),
                            textStyle = TextStyle(fontFamily = RobotoMono, color = textColor)
                        )
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
            verticalArrangement = Arrangement.Top
        ) {
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
                        containerColor = primaryColor,
                        contentColor = textColor
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
                        containerColor = primaryColor,
                        contentColor = textColor
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
                        containerColor = primaryColor,
                        contentColor = textColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Last Name", fontFamily = RobotoMono)
                }

            }

            LazyColumn(modifier = Modifier.border(1.dp, secondaryColor)) {
                itemsIndexed(students) { index, student ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                            ),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            (index + 1).toString(),
                            fontWeight = FontWeight.Normal,
                            color = textColor,
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
                                color = textColor,
                                fontSize = 16.sp,
                                fontFamily = RobotoMono,
                            )
                            Text(
                                student.lastName,
                                fontWeight = FontWeight.Normal,
                                color = textColor,
                                fontSize = 16.sp,
                                fontFamily = RobotoMono,
                            )
                        }
                        Text(
                            student.registrationID,
                            fontFamily = RobotoMono,
                            color = textColor,
                            fontSize = 16.sp,
                        )
                    }
                    Divider(color = Color.Gray, thickness = 1.dp)
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
    ShowStudentsScreen(LocalContext.current)
}

package com.app.fitnessapp
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecordAttendanceScreen(
    onAttendanceRecorded: () -> Unit,
    navController: NavController,
    context: Context
) {
    val students = FileUtil.loadStudents(context)
    val attendanceRecords = remember { mutableStateListOf<AttendanceRecord>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance",
                    fontFamily = RobotoMono,
                    color = color4,
                    fontSize = 20.sp,) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back",
                            tint = color4,
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            })
                    }
                },
                actions = {
                    Button(onClick = {
                        val allRecords = FileUtil.loadAttendanceRecords(context).toMutableList()
                        allRecords.addAll(attendanceRecords)
                        FileUtil.saveAttendanceRecords(context, allRecords)
                        onAttendanceRecorded()
                    },
                        colors = ButtonDefaults.buttonColors(Transparent)) {
                        Text("Save",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = RobotoMono,
                            color = color4)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = color1,
                    titleContentColor = color4
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(color1)
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text("Attendance Sheet",
                color = textcolor,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = RobotoMono,
                modifier = Modifier.padding(vertical = 16.dp)
            )
            LazyColumn(modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                )) {

                itemsIndexed(students) { index,student ->
                    var present by remember { mutableStateOf(false) }
                    val rowlist = if (index % 2 == 0) color2 else color3

                    Row(
                        modifier = Modifier
                            .background(rowlist)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Display student name
                        Text(student.name, color = Color.Black)

                        // Checkbox for marking attendance
                        Checkbox(
                            colors = CheckboxDefaults.colors(Color.Black),
                            checked = present,
                            onCheckedChange = {
                                present = it
                                attendanceRecords.add(AttendanceRecord(student.studentid, "2024-05-17", present))
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecordAttendanceScreenPreview() {
        RecordAttendanceScreen(
        onAttendanceRecorded = {},
        navController = rememberNavController(),
        context = LocalContext.current
    )
}

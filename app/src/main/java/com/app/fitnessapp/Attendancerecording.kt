package com.app.fitnessapp
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
fun RecordAttendanceScreen(onAttendanceRecorded: () -> Unit, navController: NavController,context: Context) {
    val students = FileUtil.loadStudents(context)
    val attendanceRecords = remember { mutableStateListOf<AttendanceRecord>() }
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
                        Button(onClick = {
                            val allRecords = FileUtil.loadAttendanceRecords(context).toMutableList()
                            allRecords.addAll(attendanceRecords)
                            FileUtil.saveAttendanceRecords(context, allRecords)
                            onAttendanceRecorded()
                        }) {
                            Text("Save",
                                color = Color.Black)
                        }
                        // Add any additional actions (e.g., settings icon) here
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
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Column(modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center){
                    Text("Record Attendance",
                        color = Color.Black,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = RobotoMono
                    )
                }
                LazyColumn {
                    items(students) { student ->
                        var present by remember { mutableStateOf(false) }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            //index to increment by one for each name
                            Text(student.name,
                                color = Color.Black)
                            Checkbox(
                                colors = CheckboxDefaults.colors(Color.Black),
                                checked = present,
                                onCheckedChange = {
                                    present = it
                                    attendanceRecords.add(AttendanceRecord(student.id, "2024-05-17", present))
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
    val sampleStudents = listOf(
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnson"),
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnson"),
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnson"),
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnson"),
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnso")
    )
    RecordAttendanceScreen(
        onAttendanceRecorded = {},
        navController = rememberNavController(),
        context = LocalContext.current
    )
}

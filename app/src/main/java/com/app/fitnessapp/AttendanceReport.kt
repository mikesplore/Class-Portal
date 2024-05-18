package com.app.fitnessapp
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceReportScreen(students: List<Student>, attendanceRecords: List<AttendanceRecord>,navController: NavController) {

    val sortedStudents = students.sortedByDescending { it.name }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance Report") },
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
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
                .padding(innerPadding) // Use innerPadding for proper content positioning
        ) {
            LazyColumn(modifier = Modifier.border(width = 1.dp, color = color)) {
                item {
                    Row(
                        modifier = Modifier
                            .border(width = 1.dp, color = color)
                            .background(color)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("No.", modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center,fontFamily = RobotoMono,fontWeight = FontWeight.Bold,color = Color.Black)
                        Text("Name", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = RobotoMono,fontWeight = FontWeight.Bold,color = Color.Black)
                        Text("Total Present", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = RobotoMono,fontWeight = FontWeight.Bold,color = Color.Black)
                        Text("Total Absent", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = RobotoMono,fontWeight = FontWeight.Bold,color = Color.Black)
                        Text("Percent", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = RobotoMono,fontWeight = FontWeight.Bold,color = Color.Black)
                    }
                }
                itemsIndexed(sortedStudents) { index, student ->
                    val totalPresent = attendanceRecords.count { it.studentId == student.id && it.present }
                    val totalAbsent = attendanceRecords.count { it.studentId == student.id && !it.present }
                    val totalSessions = totalPresent + totalAbsent
                    val attendancePercentage = if (totalSessions > 0) (totalPresent * 100 / totalSessions) else 0

                    Row(
                        modifier = Modifier
                            .background(Color(0xff89CFF3))
                            .border(width = 1.dp, color = color)
                            .padding(16.dp)
                    ) {
                        val percentagecolor = if (attendancePercentage >= 75) Color(0xff51b541)
                        else if (attendancePercentage >= 50) Color.Yellow
                        else Color.Red

                        Text("${index + 1}", modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center, fontFamily = RobotoMono,color = Color.Black)
                        Text(student.name, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontFamily = RobotoMono,color = Color.Black)
                        Text("$totalPresent", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = RobotoMono,color = Color.Black)
                        Text("$totalAbsent", modifier = Modifier.weight(1f), textAlign = TextAlign.Center,fontFamily = RobotoMono,color = Color.Black)
                        Text("$attendancePercentage%", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = percentagecolor, fontFamily = RobotoMono,fontWeight = FontWeight.Bold)
                    }
                }

            }
        }
    }
}



@Preview()
@Composable
fun AttendanceReportScreenPreview() {
    val sampleStudents = listOf(
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnson"),
        Student(4, "Bob Johnson"),
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnson"),
        Student(4, "Bob Johnson"),
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnson"),
        Student(4, "Bob Johnson"),
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnson"),
        Student(4, "Bob Johnson"),
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnson"),
        Student(4, "Bob Johnson"),
        Student(1, "John Doe"),
        Student(2, "Jane Smith"),
        Student(3, "Alice Johnson"),
        Student(4, "Bob Johnson")
    )

    val sampleAttendanceRecords = listOf(
        AttendanceRecord(1, "2023-11-15", false),
        AttendanceRecord(2, "2023-11-16", false),
        AttendanceRecord(3, "2023-11-15", true),
        AttendanceRecord(4, "2023-11-16", true),
        AttendanceRecord(1, "2023-11-15", true),
        AttendanceRecord(2, "2023-11-16", false),
        AttendanceRecord(3, "2023-11-15", true),
        AttendanceRecord(4, "2023-11-16", true),
        AttendanceRecord(3, "2023-11-15", true),
        AttendanceRecord(4, "2023-11-16", false),
        AttendanceRecord(1, "2023-11-15", true),
        AttendanceRecord(2, "2023-11-16", false),
        AttendanceRecord(3, "2023-11-15", true),
        AttendanceRecord(4, "2023-11-16", true),
        AttendanceRecord(1, "2023-11-15", true),
        AttendanceRecord(2, "2023-11-16", false),
        AttendanceRecord(3, "2023-11-15", true),
        AttendanceRecord(4, "2023-11-16", true),
        AttendanceRecord(1, "2023-11-15", true),
        AttendanceRecord(2, "2023-11-16", false),
        AttendanceRecord(3, "2023-11-15", true),
        AttendanceRecord(4, "2023-11-16", true),
    )

    AttendanceReportScreen(students = sampleStudents, attendanceRecords = sampleAttendanceRecords,navController = rememberNavController())
}

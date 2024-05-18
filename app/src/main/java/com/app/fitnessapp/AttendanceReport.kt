package com.app.fitnessapp
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceReportScreen(students: List<Student>) {

    val columnHeaders = listOf("Name", "Total", "Present", "Absent", "%")

        Scaffold(
        modifier = Modifier.background(background),
        topBar = {
            TopAppBar(
                title = { Text("ATTENDANCE REPORT",
                    color = Color.Black, fontSize = 20.sp,
                    fontWeight = FontWeight.Bold) },
                    modifier = Modifier.background(color)
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Header Row
            item {
                Row(
                    modifier = Modifier
                        .background(color)
                        .height(50.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    columnHeaders.forEach { header ->
                        Text(
                            text = header,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp)
                        )
                    }
                }
            }

            // Data Rows
            items(students.size) { index ->
                val student = students[index]
                val totalClasses = student.attendanceRecords.size
                val presentDays = student.attendanceRecords.count { it.isPresent }
                val absentDays = totalClasses - presentDays
                val attendancePercentage =
                    if (totalClasses > 0) (presentDays.toFloat() / totalClasses) * 100 else 0f

                val rowColor = if (index % 2 == 0) Color(0xffA0E9FF) else Color(0xff89CFF3)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(rowColor)
                        .border(
                            width = 1.dp,
                            color = color,

                        )
                ) {
                    Text(
                        text = student.name,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                    Text(
                        text = totalClasses.toString(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                    Text(
                        text = presentDays.toString(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                    Text(
                        text = absentDays.toString(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                    Text(
                        text = "%.2f%%".format(attendancePercentage),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewAttendanceReportScreen() {
    val students = listOf(
        Student(
            id = "1",
            name = "John Doe",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", false),
                AttendanceRecord("2024-01-03", true)
            )
        ),
        Student(
            id = "2",
            name = "Jane Smith",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),
        Student(
            id = "2",
            name = "Jane Smith",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),
        Student(
            id = "2",
            name = "Jane Smith",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),
        Student(
            id = "2",
            name = "Michael Odhiambo",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),
        Student(
            id = "2",
            name = "Jane Smith",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),
        Student(
            id = "2",
            name = "Jane Smith",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),
        Student(
            id = "2",
            name = "Jane Smith",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),
        Student(
            id = "2",
            name = "Jane Smith",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),
        Student(
            id = "2",
            name = "Jane Smith",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),
        Student(
            id = "2",
            name = "Jane Smith",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),
        Student(
            id = "2",
            name = "Jane Smith",
            attendanceRecords = listOf(
                AttendanceRecord("2024-01-01", true),
                AttendanceRecord("2024-01-02", true),
                AttendanceRecord("2024-01-03", false)
            )
        ),

    )
    AttendanceReportScreen(students = students)
}

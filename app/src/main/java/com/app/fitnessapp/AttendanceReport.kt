package com.app.fitnessapp
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceReportScreen(context: Context, navController: NavController) {
    val students = FileUtil.loadStudents(context)
    val attendanceRecords = FileUtil.loadAttendanceRecords(context)
    var showConfirmationDialog by remember { mutableStateOf(false) }

    val studentAttendance = students.map { student ->
        val totalPresent = attendanceRecords.count { it.studentId == student.studentid && it.present }
        val totalAbsent = attendanceRecords.count { it.studentId == student.studentid && !it.present }
        val totalSessions = totalPresent + totalAbsent
        val attendancePercentage = if (totalSessions > 0) (totalPresent * 100 / totalSessions) else 0
        StudentAttendance(student, totalPresent, totalAbsent, attendancePercentage)
    }.sortedByDescending { it.attendancePercentage }

    if (showConfirmationDialog) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            confirmButton = {
                Button(onClick = {
                    FileUtil.clearAttendance(context)
                    showConfirmationDialog = false

                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmationDialog = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Clear Attendance Sheet") },
            text = { Text("Are you sure you want to clear the attendance sheet?") }
        )
    }

    var showMenu by remember { mutableStateOf(false) } // State for the menu visibility

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance Report") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
                    }
                },
                actions = {
                    // This is where the menu icon goes
                    Box(modifier = Modifier.padding(end = 8.dp)) {
                        IconButton(onClick = { showMenu = !showMenu }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                             Column(modifier = Modifier
                                .width(100.dp),
                                 ) {
                                    Text(text = "Clear All",
                                        modifier = Modifier
                                            .clickable {
                                                showConfirmationDialog = true

                                            })


                             }
                        }
                    }
                }
            )
        }
    )

    { innerPadding ->
        Column(
            modifier = Modifier
                .background(background)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(modifier = Modifier.border(width = 1.dp, color = color)) {
                item {
                    Row(
                        modifier = Modifier
                            .border(width = 1.dp, color = color)
                            .background(color = color)
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("No.", modifier = Modifier.weight(0.4f), textAlign = TextAlign.Center, fontFamily = RobotoMono, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("Name", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontFamily = RobotoMono, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("Total Present", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontFamily = RobotoMono, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("Total Absent", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontFamily = RobotoMono, fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("Percent", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontFamily = RobotoMono, fontWeight = FontWeight.Bold, color = Color.Black)
                    }
                }
                itemsIndexed(studentAttendance) { index, studentAttendance ->
                    val backgroundColor = if (index % 2 == 0) Color(0xff89CFF3) else Color(0xffA0E9FF)

                    Row(
                        modifier = Modifier
                            .background(backgroundColor)
                            .border(width = 1.dp, color = MaterialTheme.colorScheme.onBackground)
                            .padding(16.dp)
                    ) {
                        val percentageColor = when {
                            studentAttendance.attendancePercentage >= 75 -> Color(0xff51b541)
                            studentAttendance.attendancePercentage >= 50 -> Color.Yellow
                            else -> Color.Red
                        }

                        Text("${index + 1}", modifier = Modifier.weight(0.5f), textAlign = TextAlign.Center, fontFamily = RobotoMono, color = Color.Black)
                        Text(studentAttendance.student.name, modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontFamily = RobotoMono, color = Color.Black)
                        Text("${studentAttendance.totalPresent}", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontFamily = RobotoMono, color = Color.Black)
                        Text("${studentAttendance.totalAbsent}", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, fontFamily = RobotoMono, color = Color.Black)
                        Text("${studentAttendance.attendancePercentage}%", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = percentageColor, fontFamily = RobotoMono, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

data class StudentAttendance(
    val student: Student,
    val totalPresent: Int,
    val totalAbsent: Int,
    val attendancePercentage: Int
)

@Preview
@Composable
fun AttendanceReportScreenPreview() {
    val navController = rememberNavController()
    AttendanceReportScreen(LocalContext.current, navController)
}

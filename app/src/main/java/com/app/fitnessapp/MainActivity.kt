package com.app.fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.FitnessAPPTheme

data class Student(val id: Int, val name: String)
data class AttendanceRecord(val studentId: Int, val date: String, val present: Boolean)

class MainActivity : ComponentActivity() {
    private val studentFile = "students.json"
    private val attendanceFile = "attendance.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                FitnessAPPTheme {
                    val navController = rememberNavController()
                    val students = remember { mutableStateOf(loadStudents()) }
                    val attendanceRecords = remember { mutableStateOf(loadAttendanceRecords()) }

                    NavigationComponent(
                        navController = navController,
                        students = students,
                        attendanceRecords = attendanceRecords,
                        onSaveStudents = { saveStudents(it) },
                        onSaveAttendance = { saveAttendanceRecords(it) }
                    )
                }
        }
    }


    @Composable
    fun NavigationComponent(
        navController: NavHostController,
        students: MutableState<List<Student>>,
        attendanceRecords: MutableState<List<AttendanceRecord>>,
        onSaveStudents: (List<Student>) -> Unit,
        onSaveAttendance: (List<AttendanceRecord>) -> Unit
    ) {

        NavHost(navController, startDestination = "main") {
            composable("main") {
                MainScreen(onNavigate = { navController.navigate(it) },navController)
            }
            composable("AddStudent") {
                AddStudentScreen(onStudentAdded = { student ->
                    students.value += student
                    onSaveStudents(students.value)

                },navController = navController)
            }
            composable("RecordAttendance") {
                RecordAttendanceScreen(students = students.value, onAttendanceRecorded = { records ->
                    attendanceRecords.value = records
                    onSaveAttendance(records)

                },navController = navController)
            }
            composable("AttendanceReport") {
                AttendanceReportScreen(students = students.value, attendanceRecords = attendanceRecords.value,navController = navController)
            }
        }
    }


    @Preview
    @Composable
    fun MainScreenPreview() {
        FitnessAPPTheme {
            val navController = rememberNavController()
            val students = remember { mutableStateOf(emptyList<Student>()) }
            val attendanceRecords = remember { mutableStateOf(emptyList<AttendanceRecord>()) }
            NavigationComponent(
                navController = navController,
                students = students,
                attendanceRecords = attendanceRecords,
                onSaveStudents = {},
                onSaveAttendance = {}
            )
        }
    }
    private fun loadStudents(): List<Student> {
        return FilePersistence.loadData(this, studentFile)
    }

    private fun loadAttendanceRecords(): List<AttendanceRecord> {
        return FilePersistence.loadData(this, attendanceFile)
    }

    private fun saveStudents(students: List<Student>) {
        FilePersistence.saveData(this, studentFile, students)
    }

    private fun saveAttendanceRecords(records: List<AttendanceRecord>) {
        FilePersistence.saveData(this, attendanceFile, records)
    }
}

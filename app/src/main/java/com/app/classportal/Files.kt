package com.app.classportal

import android.content.Context
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

data class Student(val registrationID: String, val firstName: String, val lastName: String, val username: String, var password: String)
data class AttendanceRecord(
    val studentId: String, val date: String, val present: Boolean, val unit: String
)


data class TimetableItem(

    val unit: String,
    val startTime: String,
    val duration: String,
    val lecturer: String,
    val venue: String,
    val day: String,
)


data class UnitData(
    var name: String,
    val assignments: MutableList<Assignment> = mutableListOf()
)

data class Assignment(
    val title: String,
    val description: String
)


data class Announcement(
    val id: Int, val date: String, val title: String, val description: String, val student: String
)

fun getCurrentDateFormatted(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    return dateFormat.format(Date())
}

object FileUtil {
    private const val ASSIGNMENTS_FILE = "assignments.json"
    private const val STUDENT_FILE = "students file.json"
    private const val TIMETABLE_FILE = "timetable.json"
    private const val ATTENDANCE_FILE = "attendance records.json"
    private const val ANNOUNCEMENT_FILE = "announcement.json"
    private const val UNITS_ASSIGNMENTS_FILE = "units_assignments.json"
    private val gson = Gson()



    fun loadUnitsAndAssignments(context: Context): MutableList<UnitData> {
        val gson = Gson()
        val file = File(context.filesDir, UNITS_ASSIGNMENTS_FILE)
        return if (file.exists()) {
            val json = file.readText()
            val type = object : TypeToken<MutableList<UnitData>>() {}.type
            gson.fromJson(json, type)
        } else {
            // Initialize with default units if the file doesn't exist
            mutableListOf(
                UnitData("Unit 1"),
                UnitData("Unit 2"),
                UnitData("Unit 3"),
                UnitData("Unit 4"),
                UnitData("Unit 5"),
                UnitData("Unit 6"),
                UnitData("Unit 7")
            )
        }
    }

    fun saveUnitsAndAssignments(context: Context, data: List<UnitData>) {
        val gson = Gson()
        val json = gson.toJson(data)
        val file = File(context.filesDir, "units_assignments.json")
        file.writeText(json)
    }

    fun saveStudents(context: Context, students: List<Student>) {
        val file = File(context.filesDir, STUDENT_FILE)
        file.writeText(gson.toJson(students))
    }

    fun loadStudents(context: Context): List<Student> {
        val file = File(context.filesDir, STUDENT_FILE)
        if (!file.exists()) return emptyList()
        val type = object : TypeToken<List<Student>>() {}.type
        return gson.fromJson(file.readText(), type)
    }

    fun saveAttendanceRecords(context: Context, records: List<AttendanceRecord>) {
        val file = File(context.filesDir, ATTENDANCE_FILE)
        file.writeText(gson.toJson(records))
    }

    fun loadAttendanceRecords(context: Context): List<AttendanceRecord> {
        val file = File(context.filesDir, ATTENDANCE_FILE)
        if (!file.exists()) return emptyList()
        val type = object : TypeToken<List<AttendanceRecord>>() {}.type
        return gson.fromJson(file.readText(), type)
    }

    fun editStudent(context: Context, updatedStudent: Student) {
        val students = loadStudents(context).toMutableList()
        val index = students.indexOfFirst { it.registrationID == updatedStudent.registrationID }
        if (index != -1) {
            students[index] = updatedStudent
            saveStudents(context, students)
        }
    }

    fun deleteStudent(context: Context, studentId: String) {
        val students = loadStudents(context).toMutableList()
        val index = students.indexOfFirst { it.registrationID == studentId }
        if (index != -1) {
            students.removeAt(index)
            saveStudents(context, students)
        }
    }

    fun clearAttendance(context: Context) {
        val file = File(context.filesDir, ATTENDANCE_FILE)
        if (file.exists()) {
            file.writeText("")
        }
    }

    fun saveAnnouncement(context: Context, announcements: List<Announcement>) {
        val file = File(context.filesDir, ANNOUNCEMENT_FILE)
        file.writeText(gson.toJson(announcements))
    }

    fun loadAnnouncement(context: Context): List<Announcement> {
        val file = File(context.filesDir, ANNOUNCEMENT_FILE)
        if (!file.exists()) return emptyList()
        val type = object : TypeToken<List<Announcement>>() {}.type
        return gson.fromJson(file.readText(), type)
    }

    fun saveTimetable(context: Context, timetable: List<List<TimetableItem>>) {
        val file = File(context.filesDir, TIMETABLE_FILE)
        file.writeText(gson.toJson(timetable))
    }

    fun loadTimetable(context: Context): List<List<TimetableItem>> {
        val file = File(context.filesDir, TIMETABLE_FILE)
        if (!file.exists()) return List(7) { emptyList() } // Return a list of 5 empty lists for Mon-Fri
        val type = object : TypeToken<List<List<TimetableItem>>>() {}.type
        return gson.fromJson(file.readText(), type)
    }


    fun loadAssignments(context: Context): List<List<Assignment>> {
        val file = File(context.filesDir, ASSIGNMENTS_FILE)
        return if (file.exists()) {
            val json = file.readText()
            val type = object : TypeToken<List<List<Assignment>>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun saveAssignments(context: Context, assignments: List<List<Assignment>>) {
        val file = File(context.filesDir, ASSIGNMENTS_FILE)
        val json = Gson().toJson(assignments)
        file.writeText(json)
    }

    fun getAssignment(context: Context, unitIndex: Int, assignmentIndex: Int): Assignment? {
        val assignmentData = loadAssignments(context)

        // Check if the unit index is valid
        if (unitIndex < 0 || unitIndex >= assignmentData.size) {
            return null // Unit not found
        }

        val unitAssignments = assignmentData[unitIndex]

        // Check if the assignment index is valid
        if (assignmentIndex < 0 || assignmentIndex >= unitAssignments.size) {
            return null // Assignment not found
        }

        return unitAssignments[assignmentIndex]
    }


}

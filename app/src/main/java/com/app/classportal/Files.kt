package com.app.classportal

import android.content.Context
import java.io.File

object FileUtil {

    private const val STUDENT_FILE = "studentsfile.txt"
    private const val ATTENDANCE_FILE = "attendancefile.txt"
    private const val ANNOUNCEMENT_FILE = "announcement.txt"

    fun saveStudents(context: Context, students: List<Student>) {
        val file = File(context.filesDir, STUDENT_FILE)
        file.writeText(students.joinToString("\n") { "${it.registrationID},${it.studentname}" })
    }



    fun loadStudents(context: Context): List<Student> {
        val file = File(context.filesDir, STUDENT_FILE)
        if (!file.exists()) return emptyList()
        return file.readLines().map { line ->
            val parts = line.split(",")
            Student(parts[0], parts[1])
        }
    }

    fun saveAttendanceRecords(context: Context, records: List<AttendanceRecord>) {
        val file = File(context.filesDir, ATTENDANCE_FILE)
        file.writeText(records.joinToString("\n") { "${it.studentId},${it.date},${it.present}" })
    }

    fun loadAttendanceRecords(context: Context): List<AttendanceRecord> {
        val file = File(context.filesDir, ATTENDANCE_FILE)
        if (!file.exists()) return emptyList()
        return file.readLines().map { line ->
            val parts = line.split(",")
            AttendanceRecord(parts[0], parts[1], parts[2].toBoolean())
        }
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
        file.writeText(announcements.joinToString("\n") { "${it.id},${it.date},${it.title},${it.description}" })
    }

    fun loadAnnouncement(context: Context): List<Announcement> {
        val file = File(context.filesDir, ANNOUNCEMENT_FILE)
        if (!file.exists()) return emptyList()
        return file.readLines().map { line ->
            val parts = line.split(",")
            Announcement(parts[0].toInt(), parts[1], parts[2], parts[3])
        }
    }
}



package com.app.fitnessapp

import android.content.Context
import java.io.File

object FileUtil {

    private const val STUDENT_FILE = "students.txt"
    private const val ATTENDANCE_FILE = "attendance.txt"

    fun saveStudents(context: Context, students: List<Student>) {
        val file = File(context.filesDir, STUDENT_FILE)
        file.writeText(students.joinToString("\n") { "${it.id},${it.name}" })
    }

    fun loadStudents(context: Context): List<Student> {
        val file = File(context.filesDir, STUDENT_FILE)
        if (!file.exists()) return emptyList()
        return file.readLines().map { line ->
            val parts = line.split(",")
            Student(parts[0].toInt(), parts[1])
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
            AttendanceRecord(parts[0].toInt(), parts[1], parts[2].toBoolean())
        }
    }
}

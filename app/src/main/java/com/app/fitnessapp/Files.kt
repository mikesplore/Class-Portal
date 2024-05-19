package com.app.fitnessapp

import android.content.Context
import java.io.File

object FileUtil {

    private const val STUDENT_FILE = "students.txt"
    private const val ATTENDANCE_FILE = "attendance.txt"

    fun saveStudents(context: Context, students: List<Student>) {
        val file = File(context.filesDir, STUDENT_FILE)
        file.writeText(students.joinToString("\n") { "${it.studentid},${it.name}" })
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
        val index = students.indexOfFirst { it.studentid == updatedStudent.studentid }
        if (index != -1) {
            students[index] = updatedStudent
            saveStudents(context, students)
        }
    }

    fun deleteStudent(context: Context, studentId: String) {
        val students = loadStudents(context).toMutableList()
        students.removeIf { it.studentid == studentId }
        saveStudents(context, students)
    }


    fun editAttendanceRecord(context: Context, updatedRecord: AttendanceRecord) {
        val records = loadAttendanceRecords(context).toMutableList()
        val index = records.indexOfFirst { it == updatedRecord }
        if (index != -1) {
            records[index] = updatedRecord
            saveAttendanceRecords(context, records)
        }
    }

    fun deleteAttendanceRecord(context: Context, record: AttendanceRecord) {
        val records = loadAttendanceRecords(context).toMutableList()
        records.remove(record)
        saveAttendanceRecords(context, records)
    }
}

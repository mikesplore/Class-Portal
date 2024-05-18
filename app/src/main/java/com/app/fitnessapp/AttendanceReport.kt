package com.app.fitnessapp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AttendanceReportScreen(students: List<Student>, attendanceRecords: List<AttendanceRecord>) {
    LazyColumn {
        item {
            Row(modifier = Modifier.padding(16.dp)) {
                Text("Name", modifier = Modifier.weight(1f))
                Text("Total Present", modifier = Modifier.weight(1f))
                Text("Total Absent", modifier = Modifier.weight(1f))
                Text("Percentage", modifier = Modifier.weight(1f))
            }
        }
        items(students) { student ->
            val totalPresent = attendanceRecords.count { it.studentId == student.id && it.present }
            val totalAbsent = attendanceRecords.count { it.studentId == student.id && !it.present }
            val totalSessions = totalPresent + totalAbsent
            val attendancePercentage = if (totalSessions > 0) (totalPresent * 100 / totalSessions) else 0

            Row(modifier = Modifier.padding(16.dp)) {
                Text(student.name, modifier = Modifier.weight(1f))
                Text("$totalPresent", modifier = Modifier.weight(1f))
                Text("$totalAbsent", modifier = Modifier.weight(1f))
                Text("$attendancePercentage%", modifier = Modifier.weight(1f))
            }
        }
    }
}

package com.app.classportal

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AttendanceReportScreen(context: Context, navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "   Attendance Report",
                        fontFamily = RobotoMono,
                        color = textColor
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.absolutePadding(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = textColor,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                                .size(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = textColor,
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryColor)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(primaryColor)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AttendanceReportContent(context)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AttendanceReportContent(context: Context) {
    var students by remember { mutableStateOf(FileUtil.loadStudents(context)) }
    val attendanceRecords = FileUtil.loadAttendanceRecords(context)
    val units =
        listOf("Calculus II", "Linear Algebra", "Statistics I", "Probability and Statistics")
    val pagerState = rememberPagerState()
    val originalStudents = remember { students.toList() } // Store a copy of original data
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .background(backbrush)
            .fillMaxSize()
    ) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 0.dp
        ) {
            units.forEachIndexed { index, unit ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        CoroutineScope(Dispatchers.Main).launch {
                            pagerState.scrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            unit,
                            style = myTextStyle,
                            color = if (pagerState.currentPage == index) textColor else Color.Gray
                        )
                    },
                    selectedContentColor = textColor,
                    unselectedContentColor = Color.Gray,
                    modifier = Modifier.background(primaryColor),
                )
            }
        }
        HorizontalPager(
            count = units.size,
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val filteredAttendanceRecords = attendanceRecords.filter { it.unit == units[page] }

            val studentAttendance = students.map { student ->
                val totalPresent =
                    filteredAttendanceRecords.count { it.studentId == student.registrationID && it.present }
                val totalAbsent =
                    filteredAttendanceRecords.count { it.studentId == student.registrationID && !it.present }
                val totalSessions = totalPresent + totalAbsent
                val attendancePercentage =
                    if (totalSessions > 0) (totalPresent * 100 / totalSessions) else 0
                StudentAttendance(student, totalPresent, totalAbsent, attendancePercentage)
            }.sortedByDescending { it.attendancePercentage }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                item {
                    Column {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { query ->
                                searchQuery = query
                                students = if (query.text.isNotBlank()) {
                                    originalStudents.filter {
                                        it.firstName.contains(query.text, ignoreCase = true) ||
                                                it.lastName.contains(
                                                    query.text,
                                                    ignoreCase = true
                                                ) ||
                                                it.registrationID.contains(
                                                    query.text,
                                                    ignoreCase = true
                                                )
                                    }
                                } else {
                                    originalStudents
                                }
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search",
                                    tint = textColor
                                )
                            },
                            placeholder = {
                                Text(
                                    "Search",
                                    fontFamily = RobotoMono,
                                    color = textColor
                                )
                            },
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = primaryColor,
                                unfocusedContainerColor = primaryColor,
                                focusedIndicatorColor = focused,
                                unfocusedIndicatorColor = unfocused,
                                focusedLabelColor = textColor,
                                cursorColor = textColor,
                                unfocusedLabelColor = textColor,
                                focusedTextColor = textColor,
                                unfocusedTextColor = textColor

                            ),
                            textStyle = TextStyle(fontFamily = RobotoMono, color = textColor)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .background(color = primaryColor)
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Name",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Start,
                                fontFamily = RobotoMono,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                            Text(
                                "Present",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontFamily = RobotoMono,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                            Text(
                                "Absent",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontFamily = RobotoMono,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                            Text(
                                "Percent",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontFamily = RobotoMono,
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }
                        Divider(color = Color.Gray, thickness = 1.dp)
                    }
                }

                itemsIndexed(studentAttendance) { _, studentAttendance ->
                    Row(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        val percentageColor = when {
                            studentAttendance.attendancePercentage >= 75 -> Color(0xff51b541)
                            studentAttendance.attendancePercentage >= 50 -> Color.Yellow
                            else -> Color.Red
                        }

                        Text(
                            studentAttendance.student.firstName,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start,
                            fontFamily = RobotoMono,
                            color = textColor
                        )
                        Text(
                            "${studentAttendance.totalPresent}",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontFamily = RobotoMono,
                            color = textColor
                        )
                        Text(
                            "${studentAttendance.totalAbsent}",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            fontFamily = RobotoMono,
                            color = textColor
                        )
                        Text(
                            "${studentAttendance.attendancePercentage}%",
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            color = percentageColor,
                            fontFamily = RobotoMono,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Divider(color = Color.Gray, thickness = 1.dp)

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

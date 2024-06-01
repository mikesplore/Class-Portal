package com.app.classportal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceReportScreen(context: Context, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        " Attendance Report",
                        fontFamily = RobotoMono,
                        color = GlobalColors.textColor
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
                                    color = GlobalColors.textColor,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                                .size(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = GlobalColors.textColor,
                            )
                        }
                    }
                },
                actions = {
                          Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more", tint = GlobalColors.textColor,
                              modifier = Modifier.clickable { expanded = !expanded })
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GlobalColors.primaryColor)
            )
            if (expanded) {
                androidx.compose.material.DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    androidx.compose.material.DropdownMenuItem(onClick = {
                        expanded = false
                        FileUtil.clearAttendance(context)
                    Toast.makeText(context, "Attendance Cleared", Toast.LENGTH_SHORT).show()}) {
                        Text("Clear Attendance")
                        
                    }
                    
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(GlobalColors.primaryColor)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AttendanceReportContent(context)
        }
    }
}


@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AttendanceReportContent(context: Context) {
    var students by remember { mutableStateOf(FileUtil.loadStudents(context)) }
    val originalStudents = remember { students.toList() }
    val allAttendanceRecords = remember { FileUtil.loadAttendanceRecords(context) }
    val units = remember { mutableStateOf(FileUtil.loadUnitsAndAssignments(context).map { it.name }) }
    val pagerState = rememberPagerState()
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedDate by remember { mutableStateOf(LocalDate.now().dayOfWeek.toString()) }
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    var expanded by remember { mutableStateOf(false) }
    val addbackbrush = remember {
        mutableStateOf(
            Brush.verticalGradient(
                colors = listOf(
                    GlobalColors.primaryColor,
                    GlobalColors.secondaryColor,
                    GlobalColors.primaryColor
                )
            )
        )
    }.value

    // Available dates for filtering
    val availableDates = allAttendanceRecords.map { it.date }.distinct().sortedDescending()

    Column(
        modifier = Modifier
            .background(addbackbrush)
            .fillMaxSize()
    ) {
        if (units.value.isNotEmpty()) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp
            ) {
                units.value.forEachIndexed { index, unit ->
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
                                color = if (pagerState.currentPage == index) GlobalColors.textColor else GlobalColors.tertiaryColor
                            )
                        },
                        selectedContentColor = GlobalColors.textColor,
                        unselectedContentColor = GlobalColors.tertiaryColor,
                        modifier = Modifier.background(GlobalColors.primaryColor),
                    )
                }
            }
            HorizontalPager(
                count = units.value.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val filteredAttendanceRecords = allAttendanceRecords.filter { it.unit == units.value[page] && it.date == selectedDate }

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
                            Row{
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { query ->
                                    searchQuery = query
                                    students = if (query.text.isNotBlank()) {
                                        originalStudents.filter {
                                            it.firstName.contains(query.text, ignoreCase = true) ||
                                                    it.lastName.contains(query.text, ignoreCase = true) ||
                                                    it.registrationID.contains(query.text, ignoreCase = true)
                                        }
                                    } else {
                                        originalStudents
                                    }
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Search,
                                        contentDescription = "Search",
                                        tint = GlobalColors.textColor
                                    )
                                },
                                placeholder = {
                                    Text(
                                        "Search",
                                        style = myTextStyle,
                                    )
                                },
                                modifier = Modifier
                                    .background(GlobalColors.primaryColor)
                                    .height(50.dp)
                                    .width(200.dp),
                                shape = RoundedCornerShape(8.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = GlobalColors.textColor,
                                    unfocusedIndicatorColor = GlobalColors.primaryColor,
                                    focusedLabelColor = GlobalColors.textColor,
                                    cursorColor = GlobalColors.textColor,
                                    unfocusedLabelColor = GlobalColors.textColor,
                                    focusedTextColor = GlobalColors.textColor,
                                    unfocusedTextColor = GlobalColors.textColor,
                                    focusedContainerColor = GlobalColors.primaryColor,
                                    unfocusedContainerColor = GlobalColors.primaryColor
                                ),
                                textStyle = TextStyle(fontFamily = RobotoMono, color = GlobalColors.textColor)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = !expanded },
                            ) {
                                OutlinedTextField(
                                    value = selectedDate.format(dateFormatter),
                                    onValueChange = {}, // Prevent direct text input
                                    readOnly = true,
                                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Date", tint = GlobalColors.textColor)},
                                    modifier = Modifier
                                        .menuAnchor()
                                        .background(GlobalColors.primaryColor)
                                        .height(50.dp)
                                        .width(200.dp),  // Adjust width as needed
                                    shape = RoundedCornerShape(8.dp),
                                    colors = TextFieldDefaults.colors(
                                        focusedIndicatorColor = GlobalColors.textColor,
                                        unfocusedIndicatorColor = GlobalColors.primaryColor,
                                        focusedLabelColor = GlobalColors.textColor,
                                        cursorColor = GlobalColors.textColor,
                                        unfocusedLabelColor = GlobalColors.textColor,
                                        focusedTextColor = GlobalColors.textColor,
                                        unfocusedTextColor = GlobalColors.textColor,
                                        focusedContainerColor = GlobalColors.primaryColor,
                                        unfocusedContainerColor = GlobalColors.primaryColor
                                    ),
                                    textStyle = TextStyle(fontFamily = RobotoMono, color = GlobalColors.textColor)
                                )
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    availableDates.forEach { date ->
                                        DropdownMenuItem(
                                            text = { Text(date.format(dateFormatter)) },
                                            onClick = {
                                                selectedDate = date
                                                expanded = false

                                                // Update the displayed attendance records
                                                students = if (searchQuery.text.isNotBlank()) {
                                                    originalStudents.filter {
                                                        it.firstName.contains(searchQuery.text, ignoreCase = true) ||
                                                                it.lastName.contains(searchQuery.text, ignoreCase = true) ||
                                                                it.registrationID.contains(searchQuery.text, ignoreCase = true)
                                                    }
                                                } else {
                                                    originalStudents
                                                }
                                            }
                                        )
                                    }
                                }
                            }}
                            Spacer(modifier = Modifier.height(16.dp))
                            Row(
                                modifier = Modifier
                                    .background(color = GlobalColors.primaryColor)
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
                                    color = GlobalColors.textColor
                                )
                                Text(
                                    "Present",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                    fontFamily = RobotoMono,
                                    fontWeight = FontWeight.Bold,
                                    color = GlobalColors.textColor
                                )
                                Text(
                                    "Absent",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                    fontFamily = RobotoMono,
                                    fontWeight = FontWeight.Bold,
                                    color = GlobalColors.textColor
                                )
                                Text(
                                    "Percent",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                    fontFamily = RobotoMono,
                                    fontWeight = FontWeight.Bold,
                                    color = GlobalColors.textColor
                                )
                            }
                            Divider(color = GlobalColors.tertiaryColor, thickness = 1.dp)
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
                                color = GlobalColors.textColor
                            )
                            Text(
                                "${studentAttendance.totalPresent}",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontFamily = RobotoMono,
                                color = GlobalColors.textColor
                            )
                            Text(
                                "${studentAttendance.totalAbsent}",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontFamily = RobotoMono,
                                color = GlobalColors.textColor
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
                        Divider(color = GlobalColors.tertiaryColor, thickness = 1.dp)
                    }
                }
            }
        } else {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = GlobalColors.textColor)
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

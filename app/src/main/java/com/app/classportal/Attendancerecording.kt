package com.app.classportal

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono
import com.google.accompanist.pager.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RecordAttendanceScreen(
    onAttendanceRecorded: () -> Unit,
    navController: NavController,
    context: Context
) {
    val units = remember { mutableStateOf(FileUtil.loadUnitsAndAssignments(context).map { it.name }) }
    val students = FileUtil.loadStudents(context)
    val pagerState = rememberPagerState()
    val attendanceRecords = remember { mutableStateMapOf<String, MutableState<Boolean>>() }
    val checkboxStates = remember { mutableStateMapOf<String, MutableState<Boolean>>() }
    val addbackbrush = remember {
        mutableStateOf(
            Brush.verticalGradient(
                colors = listOf(
                    globalcolors.primaryColor,
                    globalcolors.secondaryColor,
                    globalcolors.primaryColor
                )
            )
        )
    }.value

    // Initialize the attendance and checkbox states
    units.value.forEach { unit ->
        students.forEach { student ->
            val key = "${student.registrationID}-$unit"
            if (key !in attendanceRecords) {
                attendanceRecords[key] = mutableStateOf(false)
                checkboxStates[key] = mutableStateOf(true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        " Sign Attendance",
                        fontFamily = RobotoMono,
                        color = globalcolors.textColor,
                        fontSize = 20.sp
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
                                    color = globalcolors.textColor,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                                .size(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = globalcolors.textColor,
                            )
                        }
                    }
                },
                actions = {
                    Button(onClick = {
                        val allRecords = FileUtil.loadAttendanceRecords(context).toMutableList()
                        units.value.forEach { unit ->
                            students.forEach { student ->
                                val key = "${student.registrationID}-$unit"
                                val isPresent = attendanceRecords[key]?.value ?: false
                                allRecords.add(
                                    AttendanceRecord(
                                        student.registrationID,
                                        "2024-05-17",
                                        isPresent,
                                        unit
                                    )
                                )
                            }
                        }
                        FileUtil.saveAttendanceRecords(context, allRecords)
                        onAttendanceRecorded()
                    }, colors = ButtonDefaults.buttonColors(Color.Transparent)) {
                        Text(
                            "Save",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = RobotoMono,
                            color = globalcolors.textColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = globalcolors.primaryColor,
                    titleContentColor = globalcolors.textColor
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(addbackbrush)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
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
                                fontFamily = RobotoMono,
                                color = if (pagerState.currentPage == index) globalcolors.textColor else globalcolors.tertiaryColor
                            )
                        },
                        selectedContentColor = globalcolors.textColor,
                        unselectedContentColor = globalcolors.tertiaryColor,
                        modifier = Modifier.background(globalcolors.primaryColor)
                    )
                }
            }
            HorizontalPager(
                count = units.value.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    itemsIndexed(students) { _, student ->
                        val key = "${student.registrationID}-${units.value[page]}"
                        val isPresent = attendanceRecords[key] ?: mutableStateOf(false)
                        val checkboxEnabled = checkboxStates[key] ?: mutableStateOf(true)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(student.firstName, color = globalcolors.textColor)

                            Checkbox(
                                colors = CheckboxDefaults.colors(globalcolors.textColor),
                                enabled = checkboxEnabled.value,
                                checked = isPresent.value,
                                onCheckedChange = { isChecked ->
                                    if (isChecked) {
                                        checkboxEnabled.value = false
                                        isPresent.value = true
                                    }
                                }
                            )
                        }
                        Divider(color = globalcolors.tertiaryColor, thickness = 1.dp)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordAttendanceScreenPreview() {
    RecordAttendanceScreen(
        onAttendanceRecorded = {},
        navController = rememberNavController(),
        context = LocalContext.current
    )
}

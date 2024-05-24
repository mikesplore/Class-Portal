package com.app.classportal
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color.Companion.Transparent
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
    val students = FileUtil.loadStudents(context)
    val attendanceRecords = remember { mutableStateListOf<AttendanceRecord>() }
    val units = listOf("Calculus II", "Linear Algebra", "Statistics I", "Probability and Statistics") // Replace with actual units
    val pagerState = rememberPagerState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(" Sign Attendance", fontFamily = RobotoMono, color = color4, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = color4)
                    }
                },
                actions = {
                    Button(onClick = {
                        val allRecords = FileUtil.loadAttendanceRecords(context).toMutableList()
                        allRecords.addAll(attendanceRecords)
                        FileUtil.saveAttendanceRecords(context, allRecords)
                        onAttendanceRecorded()
                    }, colors = ButtonDefaults.buttonColors(Transparent)) {
                        Text("Save", fontSize = 16.sp, fontWeight = FontWeight.Bold, fontFamily = RobotoMono, color = color4)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = color1, titleContentColor = color4)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(color1)
                .fillMaxSize()
                .padding(innerPadding)
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
                        text = { Text(unit,
                            color = if (pagerState.currentPage == index) color else Color.Gray) },
                        selectedContentColor = Color.White,
                        unselectedContentColor = Color.Gray,

                        modifier = Modifier
                            .background(color1)
                    )


                }
            }
            HorizontalPager(
                count = units.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    itemsIndexed(students) { index, student ->
                        var present by remember { mutableStateOf(false) }
                        var checkboxEnabled by remember { mutableStateOf(true) } // Track enabled/disabled state of checkbox

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(student.studentname, color = Color.White)
                            Checkbox(
                                colors = CheckboxDefaults.colors(Color.White),
                                enabled = checkboxEnabled, // Set enabled state of checkbox
                                checked = present,
                                onCheckedChange = { isChecked ->
                                    if (!isChecked) {
                                        // If checkbox is unchecked, enable it
                                        checkboxEnabled = true
                                    } else {
                                        // If checkbox is checked, disable it and update present state
                                        checkboxEnabled = false
                                        present = true
                                        attendanceRecords.add(AttendanceRecord(student.registrationID, "2024-05-17", true, units[page]))
                                    }
                                }
                            )

                        }
                        Divider(color = Color.Gray, thickness = 1.dp)

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecordAttendanceScreenPreview() {
    RecordAttendanceScreen(onAttendanceRecorded = {}, navController = rememberNavController(), context = LocalContext.current)
}


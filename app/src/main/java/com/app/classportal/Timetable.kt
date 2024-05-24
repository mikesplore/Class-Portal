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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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


data class TimetableItem(
    val unit: String,
    val startTime: String,
    val duration: String,
    val lecturer: String,
    val venue: String
)

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Timetable() {
    val units = listOf("Mon", "Tue", "Wed", "Thur", "Fri")
    val pagerState = rememberPagerState()

    // Mock data for timetable items
    val timetableData = remember {
        listOf(
            listOf(
                TimetableItem("Unit 1", "9:00 AM", "1 hour", "Dr. Smith", "Room A"),
                TimetableItem("Unit 2", "10:30 AM", "2 hours", "Prof. Johnson", "Room B"),
                TimetableItem("Unit 3", "1:00 PM", "1.5 hours", "Dr. Brown", "Room C")
            ),
            listOf(
                TimetableItem("Unit 4", "9:00 AM", "1 hour", "Dr. Smith", "Room A"),
                TimetableItem("Unit 5", "11:00 AM", "2 hours", "Prof. Johnson", "Room B"),
                TimetableItem("Unit 6", "2:00 PM", "1.5 hours", "Dr. Brown", "Room C")
            ),
            // Similar data for other days
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Timetable", fontFamily = RobotoMono, color = color4, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = color4)
                    }
                },
                actions = {
                    Button(onClick = {
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
                        text = { Text(unit) },
                        selectedContentColor = Color.White,
                        modifier = Modifier.background(color1)
                    )
                }
            }
            HorizontalPager(
                count = units.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                // Display content based on selected tab
                val timetableItems = timetableData[page]
                LazyColumn {
                    itemsIndexed(timetableItems) { index, item ->
                        TimetableItemRow(item)
                    }
                }
            }
        }
    }
}


@Composable
fun TimetableItemRow(item: TimetableItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Style for unit
        Text(
            text = item.unit,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = color1
        )

        // Style for start time
        Text(
            text = item.startTime,
            fontSize = 14.sp,
            color = Color.Gray
        )

        // Style for duration
        Text(
            text = item.duration,
            fontSize = 14.sp,
            color = Color.Gray
        )

        // Style for lecturer
        Text(
            text = item.lecturer,
            fontSize = 14.sp,
            color = Color.Gray
        )

        // Style for venue
        Text(
            text = item.venue,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimetablePreview() {
    Timetable()
}



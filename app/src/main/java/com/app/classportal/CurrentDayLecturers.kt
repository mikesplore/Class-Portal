package com.app.classportal


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.classportal.FileUtil.loadTimetable
import java.util.Calendar
import com.app.classportal.CommonComponents as CC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrentDayEventsScreen() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7
    val timetableData = remember { loadTimetable(context) }
    val lecturesToday = timetableData[dayOfWeek]

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    Text("Today's Lectures", style = CC.titleTextStyle)
                }

            },
            actions = {},
            colors = TopAppBarDefaults.topAppBarColors(containerColor = GlobalColors.primaryColor)
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .background(CC.backbrush)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (lecturesToday.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No activities Scheduled for today", style = CC.titleTextStyle
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                  /*  itemsIndexed(lecturesToday) { _, item ->
                        TimetableItemRow(item, onEdit = {
                            Toast.makeText(
                                context,
                                "Please go to the timetable screen to perform this action",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, onDelete = {
                            Toast.makeText(
                                context,
                                "Please go to the timetable screen to perform this action",
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                        Divider(color = GlobalColors.tertiaryColor, thickness = 1.dp)
                    }*/
                }
            }
        }
    }
}


@Preview
@Composable
fun CurrentEventPreview() {
    CurrentDayEventsScreen()
}
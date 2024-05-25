package com.app.classportal


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.FileUtil.loadTimetable
import com.app.classportal.FileUtil.saveTimetable
import com.app.classportal.ui.theme.RobotoMono
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.util.Calendar

val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun Timetable(navController: NavController) {
    val context = LocalContext.current

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    // Load timetable data from file
    var timetableData by remember { mutableStateOf(loadTimetable(context)) }

    // Dialog state
    var showDialog by remember { mutableStateOf(false) }
    var currentDayIndex by remember { mutableIntStateOf(0) }
    var editItemIndex by remember { mutableStateOf(-1) }
    var currentItem by remember { mutableStateOf(TimetableItem("", "", "", "", "","")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Timetable", fontFamily = RobotoMono, color = textColor, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("dashboard") }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = textColor)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        currentItem = TimetableItem("", "", "", "", "", "")
                        editItemIndex = -1
                        showDialog = true
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add", tint = textColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryColor)

            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(backbrush)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp
            ) {
                days.forEachIndexed { index, unit ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = { Text(unit, color = if (pagerState.currentPage == index) textColor else secondaryColor) },
                        selectedContentColor = Color.White,
                        modifier = Modifier.background(primaryColor)
                    )
                }
            }
            HorizontalPager(
                count = days.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val timetableItems = timetableData[page]
                LazyColumn(
                    modifier = Modifier
                        .background(backbrush)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    itemsIndexed(timetableItems) { index, item ->
                        TimetableItemRow(item,
                            onEdit = {
                                currentItem = item
                                editItemIndex = index
                                currentDayIndex = page


                                showDialog = true
                            },
                            onDelete = {
                                timetableData = timetableData.toMutableList().apply {
                                    this[page] = this[page].toMutableList().apply {
                                        removeAt(index)
                                    }
                                }
                                saveTimetable(context, timetableData)
                            }
                        )
                        Divider(color = Color.Gray, thickness = 1.dp)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddEditTimetableItemDialog(
            day = days[currentDayIndex],
            item = currentItem,
            onDismiss = { showDialog = false },
            onSave = { item ->
                timetableData = timetableData.toMutableList().apply {
                    if (editItemIndex >= 0) {
                        this[currentDayIndex] = this[currentDayIndex].toMutableList().apply {
                            set(editItemIndex, item)
                        }
                    } else {
                        this[currentDayIndex] = this[currentDayIndex].toMutableList().apply {
                            add(item)
                        }
                    }
                }
                saveTimetable(context, timetableData)
                showDialog = false
            }
        )
    }
}

@Composable
fun TimetableItemRow(item: TimetableItem, onEdit: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(secondaryColor, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.unit,
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = "Start Time",
                        tint = textColor,
                        modifier = Modifier.size(20.dp).padding(end = 4.dp)
                    )
                    Text(
                        text = "Start Time: ",
                        style = myTextStyle
                    )
                    Text(
                        text = item.startTime,
                        style = myTextStyle
                    )
                }
                Row(
                    modifier = Modifier.padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Duration",
                        tint = textColor,
                        modifier = Modifier.size(20.dp).padding(end = 4.dp)
                    )
                    Text(
                        text = "Duration: ",
                        style = myTextStyle
                    )
                    Text(
                        text = item.duration,
                        style = myTextStyle
                    )
                }
                Row(
                    modifier = Modifier.padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Lecturer",
                        tint = textColor,
                        modifier = Modifier.size(20.dp).padding(end = 4.dp)
                    )
                    Text(
                        text = "Lecturer: ",
                        style = myTextStyle
                    )
                    Text(
                        text = item.lecturer,
                        style = myTextStyle
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Venue",
                        tint = textColor,
                        modifier = Modifier.size(20.dp).padding(end = 4.dp)
                    )
                    Text(
                        text = "Venue: ",
                        style = myTextStyle
                    )
                    Text(
                        text = item.venue,
                        style = myTextStyle
                    )
                }
            }
        }
        Column {
            IconButton(onClick = onEdit) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = textColor)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color(0xFFD32F2F))
            }
        }
    }
}

@Composable
fun AddEditTimetableItemDialog(
    item: TimetableItem,
    day: String,
    onDismiss: () -> Unit,
    onSave: (TimetableItem) -> Unit
) {
    var unit by remember { mutableStateOf(TextFieldValue(item.unit)) }
    var startTime by remember { mutableStateOf(TextFieldValue(item.startTime)) }
    var duration by remember { mutableStateOf(TextFieldValue(item.duration)) }
    var lecturer by remember { mutableStateOf(TextFieldValue(item.lecturer)) }
    var venue by remember { mutableStateOf(TextFieldValue(item.venue)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add/Edit Timetable Item") },
        text = {
            Column {
                OutlinedTextField(
                    value = unit,
                    onValueChange = { unit = it },
                    label = { Text("Unit") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Start Time") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = duration,
                    onValueChange = { duration = it },
                    label = { Text("Duration") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = lecturer,
                    onValueChange = { lecturer = it },
                    label = { Text("Lecturer") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = venue,
                    onValueChange = { venue = it },
                    label = { Text("Venue") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    TimetableItem(
                        unit.text,
                        startTime.text,
                        duration.text,
                        lecturer.text,
                        venue.text,
                        day

                    )
                )
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}




@Preview(showBackground = true)
@Composable
fun TimetablePreview() {
    Timetable(navController = rememberNavController())
}


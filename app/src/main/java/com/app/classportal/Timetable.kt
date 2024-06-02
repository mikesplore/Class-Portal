package com.app.classportal

import android.content.Context
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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import java.util.Calendar
import com.app.classportal.CommonComponents as CC



@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Timetable(navController: NavController, context: Context) {
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val calendar = Calendar.getInstance()
    val dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) + 5) % 7
    val pagerState = rememberPagerState(initialPage = dayOfWeek)
    val coroutineScope = rememberCoroutineScope()
    var timetableData by remember { mutableStateOf(loadTimetable(context)) }
    var showDialog by remember { mutableStateOf(true) }
    var currentDayIndex by remember { mutableIntStateOf(dayOfWeek) }
    var editItemIndex by remember { mutableIntStateOf(-1) }
    var currentItem by remember { mutableStateOf(TimetableItem("", "", "", "", "", "")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Timetable", style = CC.titleTextStyle) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("dashboard") }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = GlobalColors.textColor)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        currentItem = TimetableItem("", "", "", "", "", "")
                        editItemIndex = -1
                        showDialog = true
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add", tint = GlobalColors.textColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GlobalColors.primaryColor)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(CC.backbrush)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp
            ) {
                days.forEachIndexed { index, day ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = { Text(day, style = CC.descriptionTextStyle, color = if (pagerState.currentPage == index) GlobalColors.textColor else GlobalColors.secondaryColor) },
                        selectedContentColor = GlobalColors.textColor,
                        modifier = Modifier.background(GlobalColors.primaryColor)
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
                        .background(CC.backbrush)
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
                        Divider(color = GlobalColors.secondaryColor, thickness = 1.dp)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddEditTimetableItemDialog(
            day = days[pagerState.currentPage], // Pass the current page's day
            item = currentItem,
            context = context,
            onDismiss = { showDialog = false },
            onSave = { item ->
                timetableData = timetableData.toMutableList().apply {
                    if (editItemIndex >= 0) {
                        this[currentDayIndex] = this[currentDayIndex].toMutableList().apply {
                            set(editItemIndex, item)
                        }
                    } else {
                        this[pagerState.currentPage] = this[pagerState.currentPage].toMutableList().apply { // Add to the selected page
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
            .background(GlobalColors.secondaryColor, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = item.unit,
                style = CC.descriptionTextStyle,
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
                        tint = GlobalColors.textColor,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = "Start Time: ",
                        style = CC.descriptionTextStyle
                    )
                    Text(
                        text = item.startTime,
                        style = CC.descriptionTextStyle
                    )
                }
                Row(
                    modifier = Modifier.padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Duration",
                        tint = GlobalColors.textColor,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = "Duration: ",
                        style = CC.descriptionTextStyle
                    )
                    Text(
                        text = item.duration,
                        style = CC.descriptionTextStyle
                    )
                }
                Row(
                    modifier = Modifier.padding(bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Lecturer",
                        tint = GlobalColors.textColor,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = "Lecturer: ",
                        style = CC.descriptionTextStyle
                    )
                    Text(
                        text = item.lecturer,
                        style = CC.descriptionTextStyle
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Venue",
                        tint = GlobalColors.textColor,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 4.dp)
                    )
                    Text(
                        text = "Venue: ",
                        style = CC.descriptionTextStyle
                    )
                    Text(
                        text = item.venue,
                        style = CC.descriptionTextStyle
                    )
                }
            }
        }
        Column {
            IconButton(onClick = onEdit) {
                Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = GlobalColors.textColor)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color(0xFFD32F2F))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTimetableItemDialog(
    item: TimetableItem,
    day: String,
    onDismiss: () -> Unit,
    context: Context,
    onSave: (TimetableItem) -> Unit
) {
    var unit by remember { mutableStateOf(TextFieldValue(item.unit)) }
    var startTime by remember { mutableStateOf((item.startTime)) }
    var duration by remember { mutableStateOf((item.duration)) }
    var lecturer by remember { mutableStateOf((item.lecturer)) }
    var venue by remember { mutableStateOf((item.venue)) }
    val units by remember { mutableStateOf(FileUtil.loadUnitsAndAssignments(context).map { it.name }) }
    var expanded by remember { mutableStateOf(true) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add/Edit Timetable Item", style = CC.descriptionTextStyle, fontSize = 20.sp) },
        text = {
            Column {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.background(GlobalColors.primaryColor,
                        shape = RoundedCornerShape(8.dp))
                ) {
                    OutlinedTextField(
                        value = unit,
                        onValueChange = { unit = it },
                        label = { Text("Unit") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = CC.appTextFieldColors(),
                        shape = RoundedCornerShape(8.dp)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(GlobalColors.secondaryColor)
                    ) {
                        units.forEach { unitName ->
                            DropdownMenuItem(
                                text = { Text(text = unitName,style = CC.descriptionTextStyle) },
                                onClick = {
                                    unit = TextFieldValue(unitName)
                                    expanded = false
                                },
                            )
                        }
                    }
                }
                CC.SingleLinedTextField(value = startTime,onValueChange = {startTime = it},label = "Start Time",singleLine = false)
                CC.SingleLinedTextField(value = duration, onValueChange = {duration = it}, label = "Duration" , singleLine = true )
                CC.SingleLinedTextField(value = lecturer, onValueChange = {lecturer = it}, label = "Lecturer" , singleLine = true )
                CC.SingleLinedTextField(value = venue, onValueChange = {venue = it}, label = "Venue" , singleLine = true )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    TimetableItem(
                        unit.text,
                        startTime,
                        duration,
                        lecturer,
                        venue,
                        day
                    )
                )
                showNotification(context, global.loggedinuser.value, "Added or Edited Timetable Item")
            }) {
                Text("Save", style = CC.descriptionTextStyle)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", style = CC.descriptionTextStyle)
            }
        },
        containerColor = GlobalColors.secondaryColor
    )
}


@Preview
@Composable
fun TimetablePreview() {
    Timetable(rememberNavController(), LocalContext.current)
}


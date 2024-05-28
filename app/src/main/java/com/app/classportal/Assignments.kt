package com.app.classportal

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.classportal.ui.theme.RobotoMono
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File



@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AssignmentScreen(navController: NavController, context: Context) {
    var unitData by remember { mutableStateOf(FileUtil.loadUnitsAndAssignments(context)) }
    var showDialog by remember { mutableStateOf(false) }
    var showUnitDialog by remember { mutableStateOf(false) }
    var currentUnitIndex by remember { mutableIntStateOf(0) }
    var editItemIndex by remember { mutableStateOf(-1) }
    var currentItem by remember { mutableStateOf(Assignment("", "")) }
    var currentUnit by remember { mutableStateOf(UnitData("")) }
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()

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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assignments", fontFamily = RobotoMono, color = globalcolors.textColor, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("dashboard") }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = globalcolors.textColor)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        currentItem = Assignment("", "")
                        editItemIndex = -1
                        showDialog = true
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add", tint = globalcolors.textColor)
                    }
                    IconButton(onClick = {
                        currentUnit = UnitData("")
                        showUnitDialog = true
                    }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Manage Units", tint = globalcolors.textColor)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = globalcolors.primaryColor)
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
                unitData.forEachIndexed { index, unit ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = { Text(unit.name, fontFamily = RobotoMono, color = if (pagerState.currentPage == index) globalcolors.textColor else globalcolors.secondaryColor) },
                        selectedContentColor = Color.White,
                        modifier = Modifier.background(globalcolors.primaryColor)
                    )
                }
            }
            HorizontalPager(
                count = unitData.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val unitAssignments = unitData[page].assignments
                LazyColumn(
                    modifier = Modifier
                        .background(addbackbrush)
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    itemsIndexed(unitAssignments) { index, item ->
                        AssignmentItemRow(item,
                            onEdit = {
                                currentItem = item
                                editItemIndex = index
                                showUnitDialog = true
                            },
                            onDelete = {
                                unitData[page].assignments.removeAt(index)
                                FileUtil.saveUnitsAndAssignments(context, unitData)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddEditAssignmentDialog(
            unit = unitData[pagerState.currentPage].name,
            item = currentItem,
            onDismiss = { showDialog = false },
            onSave = { item ->
                if (editItemIndex >= 0) {
                    unitData[currentUnitIndex].assignments[editItemIndex] = item
                } else {
                    unitData[pagerState.currentPage].assignments.add(item)
                }
                FileUtil.saveUnitsAndAssignments(context, unitData)
                showDialog = false
            }
        )
    }

    if (showUnitDialog) {
        AddEditUnitDialog(
            unit = currentUnit,
            onDismiss = { showUnitDialog = false },
            onSave = { unit ->
                if (currentUnit.name.isEmpty()) {
                    unitData.add(unit)
                } else {
                    val index = unitData.indexOfFirst { it.name == currentUnit.name }
                    if (index >= 0) {
                        unitData[index] = unit
                    }
                }
                FileUtil.saveUnitsAndAssignments(context, unitData)
                showUnitDialog = false
            }
        )
    }
}


@Composable
fun AssignmentItemRow(item: Assignment, onEdit: () -> Unit, onDelete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var expandedmenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(globalcolors.primaryColor)
                .fillMaxWidth()
                .padding(16.dp),
        ) { // Use Column instead of Row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.title,
                    style = myTextStyle,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { expandedmenu = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More", tint = globalcolors.textColor)
                }
                DropdownMenu(expanded = expandedmenu,
                    onDismissRequest = { expandedmenu = false },
                    offset = DpOffset(0.dp, 48.dp),
                    modifier = Modifier
                        .align(Alignment.Bottom)
                        .background(globalcolors.secondaryColor),
                ) {
                    DropdownMenuItem(text = { Text("Edit", style = myTextStyle) }, onClick = {
                        onEdit()
                        expandedmenu = false
                    })
                    DropdownMenuItem(text = { Text("Delete", style = myTextStyle) }, onClick = {
                        onDelete()
                        expandedmenu = false
                    })
                }
            }
            AnimatedVisibility(expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.description,
                        style = myTextStyle
                    )
                }
            }
        }
    }
}

@Composable
fun AddEditAssignmentDialog(
    item: Assignment,
    onDismiss: () -> Unit,
    onSave: (Assignment) -> Unit,
    unit: String
) {
    var title by remember { mutableStateOf(TextFieldValue(item.title)) }
    var description by remember { mutableStateOf(TextFieldValue(item.description)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Add or Edit Assignment",
                style = myTextStyle, fontSize = 20.sp,
                color = globalcolors.primaryColor,
                fontWeight = FontWeight.Bold)
        },
        text = {
            Column(modifier = Modifier) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Assignment Title", style = myTextStyle) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = globalcolors.primaryColor,
                        unfocusedContainerColor = globalcolors.primaryColor,
                        focusedIndicatorColor = globalcolors.textColor,
                        unfocusedIndicatorColor = globalcolors.primaryColor,
                        focusedLabelColor = globalcolors.textColor,
                        cursorColor = globalcolors.textColor,
                        unfocusedLabelColor = globalcolors.textColor,
                        focusedTextColor = globalcolors.textColor,
                        unfocusedTextColor = globalcolors.textColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Content", style = myTextStyle) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = globalcolors.primaryColor,
                        unfocusedContainerColor = globalcolors.primaryColor,
                        focusedIndicatorColor = globalcolors.textColor,
                        unfocusedIndicatorColor = globalcolors.primaryColor,
                        focusedLabelColor = globalcolors.textColor,
                        cursorColor = globalcolors.textColor,
                        unfocusedLabelColor = globalcolors.textColor,
                        focusedTextColor = globalcolors.textColor,
                        unfocusedTextColor = globalcolors.textColor
                    ),
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    Assignment(
                        title = title.text,
                        description = description.text
                    )
                )
            }) {
                Text("Save", style = myTextStyle, color = globalcolors.primaryColor)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", style = myTextStyle, color = globalcolors.primaryColor)
            }
        },
        containerColor = globalcolors.tertiaryColor,
    )
}

@Composable
fun AddEditUnitDialog(
    unit: UnitData,
    onDismiss: () -> Unit,
    onSave: (UnitData) -> Unit
) {
    var unitName by remember { mutableStateOf(TextFieldValue(unit.name)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (unit.name.isEmpty()) "Add Unit" else "Edit Unit",
                style = myTextStyle, fontSize = 20.sp,
                color = globalcolors.primaryColor,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(modifier = Modifier) {
                OutlinedTextField(
                    value = unitName,
                    onValueChange = { unitName = it },
                    label = { Text("Unit Name", style = myTextStyle) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = globalcolors.primaryColor,
                        unfocusedContainerColor = globalcolors.primaryColor,
                        focusedIndicatorColor = globalcolors.textColor,
                        unfocusedIndicatorColor = globalcolors.primaryColor,
                        focusedLabelColor = globalcolors.textColor,
                        cursorColor = globalcolors.textColor,
                        unfocusedLabelColor = globalcolors.textColor,
                        focusedTextColor = globalcolors.textColor,
                        unfocusedTextColor = globalcolors.textColor
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(
                    UnitData(
                        name = unitName.text
                    )
                )
            }) {
                Text("Save", style = myTextStyle, color = globalcolors.primaryColor)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", style = myTextStyle, color = globalcolors.primaryColor)
            }
        },
        containerColor = globalcolors.tertiaryColor,
    )
}







@Preview
@Composable
fun AssignmentScreenPreview() {
    AssignmentScreen(navController = NavController(LocalContext.current), LocalContext.current)
}


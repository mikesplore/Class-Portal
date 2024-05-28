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
import androidx.navigation.compose.rememberNavController
import com.app.classportal.FileUtil.loadAssignments
import com.app.classportal.FileUtil.saveAssignments
import com.app.classportal.ui.theme.RobotoMono
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AssignmentScreen(navController: NavController, context: Context) {
    val loadedUnits = FileUtil.loadUnits(context)
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()
    var assignmentData by remember { mutableStateOf(loadAssignments(context)) }
    var showDialog by remember { mutableStateOf(false) }
    var currentUnitIndex by remember { mutableIntStateOf(0) }
    var editItemIndex by remember { mutableStateOf(-1) }
    var currentItem by remember { mutableStateOf(Assignment("", "")) }
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
                edgePadding = 0.dp,
                // Custom Indicator to Remove the Underline
                indicator = { }
            ) {
                loadedUnits.forEachIndexed { index, unit ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                unit.unitname,
                                fontFamily = RobotoMono,
                                color = if (pagerState.currentPage == index) globalcolors.textColor else globalcolors.secondaryColor
                            )
                        },
                        selectedContentColor = Color.White,
                        modifier = Modifier.background(globalcolors.primaryColor),
                        enabled = true // Add enabled = true

                    )
                }
            }
            HorizontalPager(
                count = loadedUnits.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val unitAssignments = assignmentData.getOrElse(page) { emptyList() }
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
                                currentUnitIndex = page
                                showDialog = true
                            },
                            onDelete = {
                                assignmentData = assignmentData.toMutableList().apply {
                                    this[page] = this[page].toMutableList().apply {
                                        removeAt(index)
                                    }
                                }
                                saveAssignments(context, assignmentData)
                            }
                        )

                    }
                }
            }
        }
    }

    if (showDialog) {
        AddEditAssignmentDialog(
            unit = loadedUnits[pagerState.currentPage].toString(),
            item = currentItem,
            onDismiss = { showDialog = false },
            onSave = { item ->
                val currentPage = pagerState.currentPage // Get the index of the current page (selected tab)

                // Update the assignmentData for the current page
                assignmentData = assignmentData.toMutableList().also { updatedData ->
                    if (updatedData.size <= currentPage) {
                        // Add new lists if necessary to accommodate the new unit/page
                        repeat(currentPage - updatedData.size + 1) {
                            updatedData.add(mutableStateListOf())
                        }
                    }
                    // Add the new assignment to the list for the current page
                    updatedData[currentPage]+(item)
                }
                saveAssignments(context, assignmentData)
                showDialog = false
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
                        .align(
                            Alignment.Bottom
                        )
                        .background(globalcolors.secondaryColor),


                    ) {
                    DropdownMenuItem(text = { Text("Edit", style = myTextStyle) }, onClick = {
                        onEdit()
                        expandedmenu = false
                    })
                    DropdownMenuItem(text = { Text("Delete", style = myTextStyle) }, onClick = {
                        onEdit()
                        expandedmenu = false
                    })


                }

            }
            // Show/hide description based on expanded state
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
    unit: String,
    onDismiss: () -> Unit,
    onSave: (Assignment) -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue(item.title)) }
    var description by remember { mutableStateOf(TextFieldValue(item.description)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add or Edit Assignment",
            style = myTextStyle, fontSize = 20.sp,
            color = globalcolors.primaryColor,
            fontWeight = FontWeight.Bold)
                },
        text = {
            Column(
                modifier = Modifier
            ) {
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
                Text("Save",
                    style = myTextStyle,
                    color = globalcolors.primaryColor,)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel",
                    style = myTextStyle,
                    color = globalcolors.primaryColor)
            }
        },
        containerColor = globalcolors.tertiaryColor,

    )
}


@Preview(showBackground = true)
@Composable
fun AssignmentScreenPreview() {
    AssignmentScreen(navController = rememberNavController(), LocalContext.current)
}

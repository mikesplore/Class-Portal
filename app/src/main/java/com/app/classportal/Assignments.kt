import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.app.classportal.Assignment
import com.app.classportal.FileUtil
import com.app.classportal.UnitData
import com.app.classportal.global
import com.app.classportal.globalcolors
import com.app.classportal.myTextStyle
import com.app.classportal.showNotification
import com.app.classportal.ui.theme.RobotoMono
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun AssignmentScreen(navController: NavController, context: Context) {
    var units by remember { mutableStateOf(FileUtil.loadUnitsAndAssignments(context)) }
    var showUnitDialog by remember { mutableStateOf(false) }
    var showAssignmentDialog by remember { mutableStateOf(false) }
    var currentUnit by remember { mutableStateOf(UnitData("")) }
    var currentAssignment by remember { mutableStateOf(Assignment("", "")) }
    var editUnitIndex by remember { mutableIntStateOf(-1) }
    var editAssignmentIndex by remember { mutableStateOf(-1) }
    var showwarning by remember { mutableStateOf(false) }
    var expandedMenu by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        currentUnit = if (pagerState.currentPage < units.size) {
            units[pagerState.currentPage]
        } else {
            UnitData("")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assignments", style = myTextStyle, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("dashboard") }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = globalcolors.textColor)
                    }
                },
                actions = {
                    IconButton(onClick = { expandedMenu = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More", tint = globalcolors.textColor)
                    }
                    DropdownMenu(
                        expanded = expandedMenu,
                        onDismissRequest = { expandedMenu = false },
                        modifier = Modifier.background(globalcolors.secondaryColor)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Add Unit",
                                    color = globalcolors.textColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    fontFamily = RobotoMono
                                )
                            },
                            onClick = {
                                currentUnit = UnitData("")
                                editUnitIndex = -1
                                showUnitDialog = true
                                expandedMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = "Add Unit",
                                    tint = globalcolors.textColor
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Edit Unit",
                                    color = globalcolors.textColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    fontFamily = RobotoMono
                                )
                            },
                            onClick = {
                                if (units.isNotEmpty() && currentUnit.name.isNotEmpty()) {
                                    editUnitIndex = pagerState.currentPage
                                    showUnitDialog = true
                                } else {
                                    showwarning = true
                                }
                                expandedMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Edit,
                                    contentDescription = "Edit Unit",
                                    tint = globalcolors.textColor
                                )
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Delete Unit",
                                    color = globalcolors.textColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    fontFamily = RobotoMono
                                )
                            },
                            onClick = {
                                if (units.isNotEmpty() && currentUnit.name.isNotEmpty()) {
                                    val unitIndex = pagerState.currentPage
                                    units = units.toMutableList().apply { removeAt(unitIndex) }
                                    FileUtil.saveUnitsAndAssignments(context, units)
                                    coroutineScope.launch {
                                        pagerState.scrollToPage(0)  // Reset to the first page if the current page is deleted
                                    }
                                } else {
                                    showwarning = true
                                }
                                expandedMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = "Delete Unit",
                                    tint = globalcolors.textColor
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Add Assignment",
                                    color = globalcolors.textColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    fontFamily = RobotoMono
                                )
                            },
                            onClick = {
                                if (units.isNotEmpty() && currentUnit.name.isNotEmpty()) {
                                    currentAssignment = Assignment("", "")
                                    editAssignmentIndex = -1
                                    showAssignmentDialog = true
                                } else {
                                    showwarning = true
                                }
                                expandedMenu = false
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Add,
                                    contentDescription = "Add Assignment",
                                    tint = globalcolors.textColor
                                )
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = globalcolors.primaryColor)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(globalcolors.primaryColor)
                .padding(innerPadding)
        ) {
            if (showwarning) {
                AlertDialog(
                    title = { Text(text = "Warning", style = myTextStyle) },
                    text = { Text(text = "Please select a unit before adding an assignment.", style = myTextStyle) },
                    onDismissRequest = { showwarning = false },
                    confirmButton = {
                        Button(
                            onClick = { showwarning = false },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(globalcolors.primaryColor)
                        ) {
                            Text(text = "Ok", style = myTextStyle)
                        }
                    },
                    containerColor = globalcolors.secondaryColor
                )
            }

            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.background(globalcolors.primaryColor),
                contentColor = globalcolors.textColor,
                edgePadding = 0.dp, // Remove edge padding
                divider = { Divider(color = Color.Transparent) } // Remove or customize divider
            ) {
                units.forEachIndexed { index, unit ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch { pagerState.scrollToPage(index) }
                        },
                        text = {
                            Text(unit.name, style = myTextStyle)
                        },
                        // Remove fillMaxWidth to let tabs take their natural size
                        selectedContentColor = globalcolors.textColor,
                        unselectedContentColor = globalcolors.textColor,
                        modifier = Modifier.background(globalcolors.primaryColor)
                    )
                }
            }


            HorizontalPager(
                count = units.size,
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { pageIndex ->
                val unit = units[pageIndex]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    unit.assignments.forEachIndexed { index, assignment ->
                        AssignmentItemRow(
                            item = assignment,
                            onEdit = {
                                currentAssignment = assignment
                                editAssignmentIndex = index
                                currentUnit = unit
                                showAssignmentDialog = true
                            },
                            onDelete = {
                                val unitIndex = units.indexOf(unit)
                                units = units.toMutableList().apply {
                                    this[unitIndex].assignments.removeAt(index)
                                }
                                FileUtil.saveUnitsAndAssignments(context, units)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showUnitDialog) {
        AddEditUnitDialog(
            unit = currentUnit,
            onDismiss = { showUnitDialog = false },
            onSave = { unit ->
                if (editUnitIndex >= 0) {
                    units = units.toMutableList().apply { set(editUnitIndex, unit) }
                } else {
                    units += unit
                }
                FileUtil.saveUnitsAndAssignments(context, units)
                showUnitDialog = false
            }
        )
    }

    if (showAssignmentDialog) {
        AddEditAssignmentDialog(
            context = context,
            item = currentAssignment,
            unit = currentUnit.name,
            onDismiss = { showAssignmentDialog = false },
            onSave = { assignment ->
                val unitIndex = units.indexOf(currentUnit)
                units = units.toMutableList().apply {
                    if (editAssignmentIndex >= 0) {
                        this[unitIndex].assignments[editAssignmentIndex] = assignment
                    } else {
                        this[unitIndex].assignments.add(assignment)
                    }
                }
                FileUtil.saveUnitsAndAssignments(context, units)
                showAssignmentDialog = false
            }
        )
    }
}

@Composable
fun AssignmentItemRow(item: Assignment, onEdit: () -> Unit, onDelete: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var expandedMenu by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = globalcolors.primaryColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .background(globalcolors.secondaryColor)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.title,
                    style = myTextStyle,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { expandedMenu = true }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = "More", tint = globalcolors.textColor)
                }
                DropdownMenu(
                    expanded = expandedMenu,
                    onDismissRequest = { expandedMenu = false },
                    modifier = Modifier.background(globalcolors.secondaryColor)
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Edit",
                                color = globalcolors.textColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                fontFamily = RobotoMono
                            )
                        },
                        onClick = {
                            onEdit()
                            expandedMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Edit,
                                contentDescription = "Edit Assignment",
                                tint = globalcolors.textColor
                            )
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Delete",
                                color = globalcolors.textColor,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                fontFamily = RobotoMono
                            )
                        },
                        onClick = {
                            onDelete()
                            expandedMenu = false
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = "Delete Assignment",
                                tint = globalcolors.textColor
                            )
                        }
                    )
                }
            }
            if (expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = item.description, style = myTextStyle)
                }
            }
        }
    }
}

@Composable
fun AddEditAssignmentDialog(
    context: Context,
    item: Assignment,
    unit: String,
    onDismiss: () -> Unit,
    onSave: (Assignment) -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue(item.title)) }
    var description by remember { mutableStateOf(TextFieldValue(item.description)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add or Edit Assignment",
                style = myTextStyle, fontSize = 20.sp,
                color = globalcolors.primaryColor,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
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
                showNotification(
                    context,
                    "New Assignment",
                    "${global.loggedinuser.value} added a new assignment to $unit",

                )
                onSave(Assignment(title.text, description.text))
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
            Column {
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
                onSave(UnitData(unitName.text))
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

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.app.classportal.globalcolors
import com.app.classportal.myTextStyle
import com.app.classportal.ui.theme.RobotoMono


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentScreen(navController: NavController, context: Context) {
    var units by remember { mutableStateOf(FileUtil.loadUnitsAndAssignments(context)) }
    var showUnitDialog by remember { mutableStateOf(false) }
    var showAssignmentDialog by remember { mutableStateOf(false) }
    var currentUnit by remember { mutableStateOf(UnitData("")) }
    var currentAssignment by remember { mutableStateOf(Assignment("", "")) }
    var editUnitIndex by remember { mutableIntStateOf(-1) }
    var editAssignmentIndex by remember { mutableStateOf(-1) }

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
                    IconButton(onClick = {
                        currentUnit = UnitData("")
                        editUnitIndex = -1
                        showUnitDialog = true
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Unit", tint = globalcolors.textColor)
                    }
                    IconButton(onClick = {
                        if (currentUnit.name.isNotEmpty()) {
                            currentAssignment = Assignment("", "")
                            editAssignmentIndex = -1
                            showAssignmentDialog = true
                        }
                    }) {
                        Icon(Icons.Filled.Add, contentDescription = "Add Assignment", tint = globalcolors.textColor)
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
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                units.forEachIndexed { index, unit ->
                    Button(
                        onClick = {
                            currentUnit = unit
                            editUnitIndex = index
                            showUnitDialog = true
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = globalcolors.secondaryColor)
                    ) {
                        Text(text = unit.name, style = myTextStyle)
                    }
                    IconButton(onClick = {
                        units = units.toMutableList().apply { removeAt(index) }
                        FileUtil.saveUnitsAndAssignments(context, units)
                    }) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete Unit", tint = globalcolors.textColor)
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                items(units) { unit ->
                    Text(
                        text = unit.name,
                        style = myTextStyle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
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
                    Spacer(modifier = Modifier.height(8.dp).background(globalcolors.secondaryColor))
                    Text(text = item.description, style = myTextStyle)
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



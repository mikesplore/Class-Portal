package com.app.classportal

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.classportal.ui.theme.RobotoMono
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import com.app.classportal.CommonComponents as CC

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun AssignmentScreen(navController: NavController, context: Context) {
    var units by remember { mutableStateOf(FileUtil.loadUnitsAndAssignments(context)) }
    var showUnitDialog by remember { mutableStateOf(false) }
    var showAssignmentDialog by remember { mutableStateOf(false) }
    var currentUnit by remember { mutableStateOf(UnitData("")) }
    var currentAssignment by remember { mutableStateOf(Assignment("", "")) }
    var editUnitIndex by remember { mutableIntStateOf(-1) }
    var editAssignmentIndex by remember { mutableIntStateOf(-1) }
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

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Assignments", style = CC.descriptionTextStyle, fontSize = 20.sp) },
            navigationIcon = {
                IconButton(onClick = { navController.navigate("dashboard") }) {
                    Icon(
                        Icons.Filled.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = GlobalColors.textColor
                    )
                }
            },
            actions = {
                IconButton(onClick = { expandedMenu = true }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "More",
                        tint = GlobalColors.textColor
                    )
                }
                DropdownMenu(
                    expanded = expandedMenu,
                    onDismissRequest = { expandedMenu = false },
                    modifier = Modifier.background(GlobalColors.secondaryColor)
                ) {
                    DropdownMenuItem(text = {
                        Text(
                            "Add Unit",
                            color = GlobalColors.textColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            fontFamily = RobotoMono
                        )
                    }, onClick = {
                        currentUnit = UnitData("")
                        editUnitIndex = -1
                        showUnitDialog = true
                        expandedMenu = false
                    }, leadingIcon = {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Add Unit",
                            tint = GlobalColors.textColor
                        )
                    })
                    DropdownMenuItem(text = {
                        Text(
                            "Edit Unit",
                            color = GlobalColors.textColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            fontFamily = RobotoMono
                        )
                    }, onClick = {
                        if (units.isNotEmpty() && currentUnit.name.isNotEmpty()) {
                            editUnitIndex = pagerState.currentPage
                            showUnitDialog = true
                        } else {
                            showwarning = true
                        }
                        expandedMenu = false
                    }, leadingIcon = {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit Unit",
                            tint = GlobalColors.textColor
                        )
                    })
                    DropdownMenuItem(text = {
                        Text(
                            "Delete Unit",
                            color = GlobalColors.textColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            fontFamily = RobotoMono
                        )
                    }, onClick = {
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
                    }, leadingIcon = {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete Unit",
                            tint = GlobalColors.textColor
                        )
                    })

                    DropdownMenuItem(text = {
                        Text(
                            "Add Assignment",
                            color = GlobalColors.textColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            fontFamily = RobotoMono
                        )
                    }, onClick = {
                        if (units.isNotEmpty() && currentUnit.name.isNotEmpty()) {
                            currentAssignment = Assignment("", "")
                            editAssignmentIndex = -1
                            showAssignmentDialog = true
                        } else {
                            showwarning = true
                        }
                        expandedMenu = false
                    }, leadingIcon = {
                        Icon(
                            Icons.Filled.Add,
                            contentDescription = "Add Assignment",
                            tint = GlobalColors.textColor
                        )
                    })
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = GlobalColors.primaryColor)
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GlobalColors.primaryColor)
                .padding(innerPadding)
        ) {
            if (showwarning) {
                AlertDialog(title = { Text(text = "Warning", style = CC.descriptionTextStyle) },
                    text = {
                        Text(
                            text = "Please select a unit before adding an assignment.",
                            style = CC.descriptionTextStyle
                        )
                    },
                    onDismissRequest = { showwarning = false },
                    confirmButton = {
                        Button(
                            onClick = { showwarning = false },
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(GlobalColors.primaryColor)
                        ) {
                            Text(text = "Ok", style = CC.descriptionTextStyle)
                        }
                    },
                    containerColor = GlobalColors.secondaryColor
                )
            }

            ScrollableTabRow(selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.background(GlobalColors.primaryColor),
                contentColor = GlobalColors.textColor,
                edgePadding = 0.dp, // Remove edge padding
                divider = { Divider(color = Color.Transparent) } // Remove or customize divider
            ) {
                units.forEachIndexed { index, unit ->
                    Tab(selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch { pagerState.scrollToPage(index) }
                        },
                        text = {
                            Text(unit.name, style = CC.descriptionTextStyle)
                        },
                        // Remove
                        selectedContentColor = GlobalColors.textColor,
                        unselectedContentColor = GlobalColors.textColor,
                        modifier = Modifier.background(GlobalColors.primaryColor)
                    )
                }
            }


            HorizontalPager(
                count = units.size, state = pagerState, modifier = Modifier.weight(1f)
            ) { pageIndex ->
                val unit = units[pageIndex]
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    unit.assignments.forEachIndexed { index, assignment ->
                        AssignmentItemRow(item = assignment, onEdit = {
                            currentAssignment = assignment
                            editAssignmentIndex = index
                            currentUnit = unit
                            showAssignmentDialog = true
                        }, onDelete = {
                            val unitIndex = units.indexOf(unit)
                            units = units.toMutableList().apply {
                                this[unitIndex].assignments.removeAt(index)
                            }
                            FileUtil.saveUnitsAndAssignments(context, units)
                        })
                    }
                }
            }
        }
    }

    if (showUnitDialog) {
        AddEditUnitDialog(unit = currentUnit,
            onDismiss = { showUnitDialog = false },
            onSave = { unit ->
                if (editUnitIndex >= 0) {
                    units = units.toMutableList().apply { set(editUnitIndex, unit) }
                } else {
                    units += unit
                }
                FileUtil.saveUnitsAndAssignments(context, units)
                showUnitDialog = false
            })
    }

    if (showAssignmentDialog) {
        AddEditAssignmentDialog(context = context,
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
            })
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
        colors = CardDefaults.cardColors(containerColor = GlobalColors.primaryColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .background(GlobalColors.secondaryColor)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.title, style = CC.descriptionTextStyle, fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { expandedMenu = true }) {
                    Icon(
                        Icons.Filled.MoreVert,
                        contentDescription = "More",
                        tint = GlobalColors.textColor
                    )
                }
                DropdownMenu(
                    expanded = expandedMenu,
                    onDismissRequest = { expandedMenu = false },
                    modifier = Modifier.background(GlobalColors.secondaryColor)
                ) {
                    DropdownMenuItem(text = {
                        Text(
                            "Edit",
                            color = GlobalColors.textColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            fontFamily = RobotoMono
                        )
                    }, onClick = {
                        onEdit()
                        expandedMenu = false
                    }, leadingIcon = {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit Assignment",
                            tint = GlobalColors.textColor
                        )
                    })
                    DropdownMenuItem(text = {
                        Text(
                            "Delete",
                            color = GlobalColors.textColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            fontFamily = RobotoMono
                        )
                    }, onClick = {
                        onDelete()
                        expandedMenu = false
                    }, leadingIcon = {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete Assignment",
                            tint = GlobalColors.textColor
                        )
                    })
                }
            }
            if (expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = item.description, style = CC.descriptionTextStyle)
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
    var title by remember { mutableStateOf((item.title)) }
    var description by remember { mutableStateOf((item.description)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Add or Edit Assignment",
                style = CC.descriptionTextStyle,
                fontSize = 20.sp,
                color = GlobalColors.primaryColor,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                CC.SingleLinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = "Title",
                    singleLine = true
                )
                CC.SingleLinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = "Description",
                    singleLine = false

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
                onSave(Assignment(title, description))
            }) {
                Text("Save", style = CC.descriptionTextStyle, color = GlobalColors.primaryColor)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", style = CC.descriptionTextStyle, color = GlobalColors.primaryColor)
            }
        },
        containerColor = GlobalColors.tertiaryColor,
    )
}

@Composable
fun AddEditUnitDialog(
    unit: UnitData, onDismiss: () -> Unit, onSave: (UnitData) -> Unit
) {
    var unitName by remember { mutableStateOf((unit.name)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (unit.name.isEmpty()) "Add Unit" else "Edit Unit",
                style = CC.descriptionTextStyle,
                fontSize = 20.sp,
                color = GlobalColors.primaryColor,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                CC.SingleLinedTextField(
                    value = unitName,
                    onValueChange = { unitName = it },
                    label = "Unit Name",
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onSave(UnitData(unitName))
            }) {
                Text("Save", style = CC.descriptionTextStyle, color = GlobalColors.primaryColor)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", style = CC.descriptionTextStyle, color = GlobalColors.primaryColor)
            }
        },
        containerColor = GlobalColors.tertiaryColor,
    )
}

@Preview
@Composable
fun AssignmentScreenPreview() {
    AssignmentScreen(navController = NavController(LocalContext.current), LocalContext.current)
}
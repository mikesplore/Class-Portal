package com.app.classportal

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
fun AssignmentScreen(navController: NavController) {
    val context = LocalContext.current
    val units = listOf("Unit 1", "Unit 2", "Unit 3", "Unit 4", "Unit 5", "Unit 6", "Unit 7")
    val pagerState = rememberPagerState(initialPage = 0)
    val coroutineScope = rememberCoroutineScope()
    var assignmentData by remember { mutableStateOf(loadAssignments(context)) }
    var showDialog by remember { mutableStateOf(false) }
    var currentUnitIndex by remember { mutableIntStateOf(0) }
    var editItemIndex by remember { mutableStateOf(-1) }
    var currentItem by remember { mutableStateOf(Assignment("", "", "")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assignments", fontFamily = RobotoMono, color = textColor, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("dashboard") }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back", tint = textColor)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        currentItem = Assignment("", "", "")
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
                units.forEachIndexed { index, unit ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = { Text(unit, fontFamily = RobotoMono, color = if (pagerState.currentPage == index) textColor else secondaryColor) },
                        selectedContentColor = Color.White,
                        modifier = Modifier.background(primaryColor)
                    )
                }
            }
            HorizontalPager(
                count = units.size,
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                val unitAssignments = assignmentData.getOrElse(page) { emptyList() }
                LazyColumn(
                    modifier = Modifier
                        .background(backbrush)
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
                        Divider(color = Color.Gray, thickness = 1.dp)
                    }
                }
            }
        }
    }

    if (showDialog) {
        AddEditAssignmentDialog(
            unit = units[pagerState.currentPage],
            item = currentItem,
            onDismiss = { showDialog = false },
            onSave = { item ->
                if (assignmentData.size <= pagerState.currentPage) {
                    assignmentData = assignmentData + listOf(listOf(item))
                } else {
                    assignmentData = assignmentData.toMutableList().apply {
                        if (editItemIndex >= 0) {
                            this[currentUnitIndex] = this[currentUnitIndex].toMutableList().apply {
                                set(editItemIndex, item)
                            }
                        } else {
                            this[pagerState.currentPage] = this[pagerState.currentPage].toMutableList().apply {
                                add(item)
                            }
                        }
                    }
                }
                saveAssignments(context, assignmentData)
                showDialog = false
            }
        )
    }
}


@Composable
fun AssignmentItemRow(item: Assignment, onEdit: () -> Unit, onDelete: () -> Unit) {
    val context = LocalContext.current
    var showUnsupportedFileTypeAlert by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .background(primaryColor)
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .background(secondaryColor)
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = myTextStyle,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                val fileName = if (item.filePath.isNotEmpty()) { "Selected File: ${item.filePath}" } else { "No File Selected"}
                Text(
                    text = fileName,
                    style = myTextStyle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(horizontalAlignment = Alignment.End) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(item.filePath)
                            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                        }
                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(intent)
                        } else {
                            showUnsupportedFileTypeAlert = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text("View File", color = Color.White)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Filled.Edit, contentDescription = "Edit", tint = textColor)
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                }
            }
        }
    }

    // Alert dialog for unsupported file type
    if (showUnsupportedFileTypeAlert) {
        AlertDialog(
            onDismissRequest = { showUnsupportedFileTypeAlert = false },
            title = { Text("Unsupported File Type") },
            text = { Text("There is no app installed to handle this file type.") },
            confirmButton = {
                TextButton(onClick = { showUnsupportedFileTypeAlert = false }) {
                    Text("OK")
                }
            }
        )
    }
}


    // Alert dialog for unsupported file type




@Composable
fun AddEditAssignmentDialog(
    item: Assignment,
    unit: String,
    onDismiss: () -> Unit,
    onSave: (Assignment) -> Unit
) {
    var title by remember { mutableStateOf(TextFieldValue(item.title)) }
    var description by remember { mutableStateOf(TextFieldValue(item.description)) }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf("") }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedFileUri = uri
        selectedFileName = uri?.let { getFileName(context, it) } ?: ""
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Add/Edit Assignment") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(
                        text = "Selected File: $selectedFileName", // Display selected file name
                        style = myTextStyle,
                        modifier = Modifier.weight(1f)
                    )
                    Button(onClick = { launcher.launch("*/*") }) {
                        Text("Select File")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val filePath = selectedFileUri?.let { FileUtil.getPath(context, it) }
                if (filePath.isNullOrEmpty()) {
                    // Show an error message or handle the case where file path is empty
                    // For simplicity, we'll just log it here
                    println("File path is empty or null")
                } else {
                    onSave(
                        Assignment(
                            title.text,
                            description.text,
                            filePath
                        )
                    )
                }
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

fun getFileName(context: Context, uri: Uri): String {
    var result: String? = null
    if (uri.scheme == "content") {
        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (columnIndex != -1) {
                    result = cursor.getString(columnIndex)
                }
            }
        }
    }
    if (result == null) {
        result = uri.path
        val cut = result?.lastIndexOf('/')
        if (cut != -1 && cut != null) {
            result = result?.substring(cut + 1)
        }
    }
    return result ?: "unknown"
}


@Preview(showBackground = true)
@Composable
fun AssignmentScreenPreview() {
    AssignmentItemRow(
        item = Assignment("Assignment 1", "Description of Assignment 1", ""),
        onEdit = {},
        onDelete = {}
    )
}

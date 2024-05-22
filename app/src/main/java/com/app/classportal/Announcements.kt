package com.app.classportal

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.FileUtil.loadAnnouncement
import com.app.classportal.FileUtil.saveAnnouncement
import com.app.classportal.ui.theme.RobotoMono
import kotlinx.coroutines.launch
import java.io.File


const val ANNOUNCEMENT_FILE = "announcements.txt"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ComingSoon(navController: NavController, context: Context) {
    val coroutineScope = rememberCoroutineScope()

    val announcements = remember { mutableStateListOf<Announcement>() }
    var expanded by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var currentId by remember { mutableIntStateOf(0) }
    var selectedAnnouncementIndex by remember { mutableStateOf(-1) }
    var clickedIndex by remember { mutableStateOf(-1) } // Track which announcement is clicked

    // Load announcements initially
    LaunchedEffect(Unit) {
        val loadedAnnouncements = loadAnnouncement(context)
        announcements.addAll(loadedAnnouncements)
        currentId = (announcements.maxOfOrNull { it.id } ?: 0) + 1
    }

    fun addAnnouncement(newAnnouncement: Announcement) {
        announcements.add(newAnnouncement)
        saveAnnouncement(context, announcements)
    }

    fun deleteAnnouncement(index: Int) {
        announcements.removeAt(index)
        saveAnnouncement(context, announcements)
    }

    fun editAnnouncement(index: Int, updatedAnnouncement: Announcement) {
        announcements[index] = updatedAnnouncement
        saveAnnouncement(context, announcements)
    }

    @Composable
    fun AnnouncementDialog(
        onDismiss: () -> Unit,
        onConfirm: (date: String, title: String, description: String) -> Unit,
        initialDate: String = "",
        initialTitle: String = "",
        initialDescription: String = ""
    ) {
        var date by remember { mutableStateOf(initialDate) }
        var title by remember { mutableStateOf(initialTitle) }
        var description by remember { mutableStateOf(initialDescription) }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Announcement", style = titleTextStyle()) },
            text = {
                Column {
                    BasicTextField(
                        value = date,
                        onValueChange = { date = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 16.sp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(color2, shape = MaterialTheme.shapes.small)
                                    .padding(8.dp)
                            ) {
                                if (date.isEmpty()) {
                                    Text("Date", style = descriptionTextStyle())
                                }
                                innerTextField()
                            }
                        }

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 16.sp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(color2, shape = MaterialTheme.shapes.small)
                                    .padding(8.dp)
                            ) {
                                if (title.isEmpty()) {
                                    Text("Title", style = descriptionTextStyle())
                                }
                                innerTextField()
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 16.sp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(color2, shape = MaterialTheme.shapes.small)
                                    .padding(8.dp)
                            ) {
                                if (description.isEmpty()) {
                                    Text("Description", style = descriptionTextStyle())
                                }
                                innerTextField()
                            }
                        }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm(date, title, description)
                    onDismiss()
                }) {
                    Text("Confirm",
                        fontSize = 15.sp,
                        style = descriptionTextStyle())
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel",
                        fontSize = 15.sp,
                        style = descriptionTextStyle())
                }
            },
            containerColor = color1,

        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Announcements") },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More",
                            tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Add Announcement") },
                            onClick = {
                                showAddDialog = true
                                expanded = false
                            }
                        )

                        DropdownMenuItem(
                            text = { Text("Exit") },
                            onClick = {
                                // Handle Delete Announcement
                                navController.navigate("dashboard")
                                expanded = false
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = color1,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true },
                containerColor = color2,) {
                Icon(Icons.Filled.Add, contentDescription = "Add Announcement",
                    tint = Color.White)
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .background(color1)
                    .fillMaxSize()
            ) {
                // Display Announcements
                announcements.forEachIndexed { index, announcement ->
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(color2, shape = MaterialTheme.shapes.small)
                            .clickable {
                                clickedIndex = if (clickedIndex == index) -1 else index
                            }
                            .padding(8.dp)
                    ) {
                        Column(
                            modifier = Modifier

                                .height(60.dp)

                                .fillMaxWidth()
                        ){
                        Text(text = "Announcement: ${announcement.id}",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(start = 8.dp))
                        Text(text = announcement.date, style = descriptionTextStyle(),modifier = Modifier.padding(start = 8.dp))}
                        Column(
                            modifier = Modifier

                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){

                        
                        // Animated Visibility for Title and Description
                        AnimatedVisibility(
                            visible = clickedIndex == index,
                            enter = fadeIn() + expandVertically(),
                            exit = fadeOut() + shrinkVertically()
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = announcement.title, fontSize = 20.sp, color = textcolor, style = titleTextStyle())
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = announcement.description, style = descriptionTextStyle(), modifier = Modifier.padding(10.dp))
                            }
                        }}

                        // Row for buttons and read/unread status
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Buttons visible only when expanded
                            if (clickedIndex == index) {
                                Row(
                                    modifier = Modifier

                                        .background(color1, shape = RoundedCornerShape(8.dp))
                                        .width(200.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                ) {
                                    TextButton(onClick = {
                                        selectedAnnouncementIndex = index
                                        showEditDialog = true
                                    }) {
                                        Text("Edit",
                                            style = descriptionTextStyle())
                                    }
                                    TextButton(onClick = {
                                        deleteAnnouncement(index)
                                        Toast.makeText(context, "Announcement deleted", Toast.LENGTH_SHORT).show()
                                    }) {
                                        Text("Delete",
                                            color = Color.Red,
                                            style = descriptionTextStyle())
                                    }
                                }
                            }
                            // Read/unread status text
                            Text(
                                text = if(clickedIndex == index) "Read" else announcement.title,
                                style = titleTextStyle(),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            }
        },
        containerColor = color1
    )

    if (showAddDialog) {
        AnnouncementDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { date, title, description ->
                val newAnnouncement = Announcement(currentId++, date, title, description)
                addAnnouncement(newAnnouncement)
            }
        )
    }

    if (showEditDialog && selectedAnnouncementIndex >= 0) {
        val announcementToEdit = announcements[selectedAnnouncementIndex]
        AnnouncementDialog(
            onDismiss = { showEditDialog = false },
            onConfirm = { date, title, description ->
                val updatedAnnouncement = Announcement(announcementToEdit.id, date, title, description)
                editAnnouncement(selectedAnnouncementIndex, updatedAnnouncement)
            },
            initialDate = announcementToEdit.date,
            initialTitle = announcementToEdit.title,
            initialDescription = announcementToEdit.description
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewComingSoon() {
    ComingSoon(navController = rememberNavController(), context = LocalContext.current)
}

@Composable
fun titleTextStyle() = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
    color = Color.White,
    fontFamily = RobotoMono

)

@Composable
fun descriptionTextStyle() = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    color = Color.White,
    fontFamily = RobotoMono)




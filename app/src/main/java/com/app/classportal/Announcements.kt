package com.app.classportal

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.FileUtil.loadAnnouncement
import com.app.classportal.FileUtil.saveAnnouncement
import com.app.classportal.CommonComponents as CC


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AnnouncementsScreen(navController: NavController, context: Context) {
    val announcements = remember { mutableStateListOf<Announcement>() }
    var expanded by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }
    var currentId by remember { mutableIntStateOf(0) }
    val student = global.loggedinuser.value
    var selectedAnnouncementIndex by remember { mutableIntStateOf(-1) }
    var clickedIndex by remember { mutableIntStateOf(-1) }
    val notificationVisibleState = remember { mutableStateOf(false) }

    // Load announcements initially
    LaunchedEffect(Unit) {
        val loadedAnnouncements = loadAnnouncement(context)
        announcements.addAll(loadedAnnouncements)
        currentId = (announcements.maxOfOrNull { it.id } ?: 0) + 1
    }

    fun addAnnouncement(newAnnouncement: Announcement) {
        announcements.add(0, newAnnouncement) // Add at the beginning
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
        initialDate: String = getCurrentDateFormatted(),
        initialTitle: String = "",
        initialDescription: String = ""
    ) {
        val date by remember { mutableStateOf(initialDate) }
        var title by remember { mutableStateOf(initialTitle) }
        var description by remember { mutableStateOf(initialDescription) }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = "Announcement", style = CC.titleTextStyle) },
            text = {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    CC.BasicTextField(
                        title = title,
                        onTitleChange = { title = it },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    BasicTextField(
                        value = description,
                        onValueChange = { description = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = CC.descriptionTextStyle,
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        GlobalColors.tertiaryColor,
                                        shape = MaterialTheme.shapes.small
                                    )
                                    .height(100.dp)
                                    .padding(8.dp)
                            ) {
                                if (description.isEmpty()) {
                                    Text("Description", style = CC.descriptionTextStyle)
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
                    Toast.makeText(context, "Announcement added", Toast.LENGTH_SHORT).show()
                    //  showNotification(context, global.loggedinuser.value,description)
                }) {
                    Text(
                        "Confirm",
                        style = CC.descriptionTextStyle
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        "Cancel",
                        style = CC.descriptionTextStyle
                    )
                }
            },
            containerColor = GlobalColors.primaryColor,
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Announcements") },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            Icons.Filled.MoreVert, contentDescription = "More",
                            tint = GlobalColors.textColor
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(GlobalColors.secondaryColor)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Home",
                                    style = CC.descriptionTextStyle
                                )
                            },
                            onClick = {
                                navController.navigate("dashboard")
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Clear All",
                                    style = CC.descriptionTextStyle
                                )
                            },
                            onClick = {
                                announcements.removeAll(announcements)
                                saveAnnouncement(context, announcements)
                                Toast.makeText(
                                    context,
                                    "All announcements deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                                expanded = false
                            }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GlobalColors.primaryColor,
                    titleContentColor = GlobalColors.textColor
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddDialog = true
                },
                containerColor = GlobalColors.secondaryColor
            ) {
                Icon(
                    Icons.Filled.Add, contentDescription = "Add Announcement",
                    tint = GlobalColors.textColor
                )
            }
        },
        content = { paddingValues ->
            // Display Announcements
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .background(CC.backbrush)
                    .fillMaxSize()
            ) {
                // Display Notification
                val notification = announcements.firstOrNull()

                  NotificationCard(
                      title = notification?.title ?: "",
                      message = notification?.description ?: "",
                      visibleState = notificationVisibleState
                  )

                if (announcements.isNotEmpty()) {
                    announcements.forEachIndexed { index, announcement ->
                        Card(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = GlobalColors.textColor,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    clickedIndex = if (clickedIndex == index) -1 else index
                                },
                            colors = CardDefaults.cardColors(GlobalColors.tertiaryColor),
                            shape = MaterialTheme.shapes.medium,
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 4.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.announcement),
                                        contentDescription = "Announcement",
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column {
                                        Text(
                                            text = announcement.student,
                                            color = GlobalColors.textColor
                                        )
                                        Text(
                                            text = announcement.date,
                                            style = CC.descriptionTextStyle,
                                            color = GlobalColors.textColor
                                        )
                                    }
                                }

                                AnimatedVisibility(
                                    visible = clickedIndex == index,
                                    enter = fadeIn() + expandVertically(),
                                    exit = fadeOut() + shrinkVertically()
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .padding(top = 16.dp)
                                            .fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = announcement.title,
                                            style = CC.titleTextStyle,
                                            color = GlobalColors.textColor
                                        )
                                        Text(
                                            text = announcement.description,
                                            style = CC.descriptionTextStyle,
                                            modifier = Modifier.padding(10.dp)
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    if (clickedIndex == index) {
                                        Row(
                                            modifier = Modifier
                                                .background(
                                                    GlobalColors.primaryColor,
                                                    shape = RoundedCornerShape(8.dp)
                                                )
                                                .padding(horizontal = 8.dp),
                                            horizontalArrangement = Arrangement.SpaceEvenly
                                        ) {
                                            TextButton(onClick = {
                                                selectedAnnouncementIndex = index
                                                showEditDialog = true
                                            }) {
                                                Text(
                                                    "Edit",
                                                    style = CC.descriptionTextStyle
                                                )
                                            }
                                            TextButton(onClick = {
                                                deleteAnnouncement(index)
                                                Toast.makeText(
                                                    context,
                                                    "Announcement deleted",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }) {
                                                Text(
                                                    "Delete",
                                                    color = Color.Red,
                                                    style = TextStyle(color = Color.Red)
                                                )
                                            }
                                        }
                                    }
                                    AnimatedVisibility(clickedIndex != index) {
                                        Text(
                                            text = announcement.title,
                                            style = CC.titleTextStyle,
                                            modifier = Modifier.padding(start = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(GlobalColors.primaryColor),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No announcements found",
                            style = CC.titleTextStyle
                        )
                        Text(
                            text = "Tap the + icon to add",
                            style = CC.descriptionTextStyle
                        )
                    }
                }
            }
        },
        containerColor = GlobalColors.primaryColor
    )

    if (showAddDialog) {
        AnnouncementDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { date, title, description ->
                val newAnnouncement = Announcement(currentId++, date, title, description, student)
                addAnnouncement(newAnnouncement)
                notificationVisibleState.value = true // Show notification
            }
        )
    }

    if (showEditDialog && selectedAnnouncementIndex >= 0) {
        val announcementToEdit = announcements[selectedAnnouncementIndex]
        AnnouncementDialog(
            onDismiss = { showEditDialog = false },
            onConfirm = { date, title, description ->
                val updatedAnnouncement =
                    Announcement(announcementToEdit.id, date, title, description, student)
                editAnnouncement(selectedAnnouncementIndex, updatedAnnouncement)
                notificationVisibleState.value = true // Show notification
            },
            initialDate = announcementToEdit.date,
            initialTitle = announcementToEdit.title,
            initialDescription = announcementToEdit.description
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnnouncementsScreenPreview() {
    AnnouncementsScreen(navController = rememberNavController(), context = LocalContext.current)
}




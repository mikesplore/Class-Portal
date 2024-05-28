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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    val announcementbackbrush = remember {
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
            title = { Text(text = "Announcement", style = titleTextStyle()) },
            text = {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    BasicTextField(
                        value = title,
                        onValueChange = { title = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = TextStyle(fontSize = 16.sp),
                        decorationBox = { innerTextField ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        globalcolors.tertiaryColor,
                                        shape = MaterialTheme.shapes.small
                                    )
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
                                    .background(
                                        globalcolors.tertiaryColor,
                                        shape = MaterialTheme.shapes.small
                                    )
                                    .height(100.dp)
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
                    Text(
                        "Confirm",
                        fontSize = 15.sp,
                        style = descriptionTextStyle()
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(
                        "Cancel",
                        fontSize = 15.sp,
                        style = descriptionTextStyle()
                    )
                }
            },
            containerColor = globalcolors.primaryColor,
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
                            tint = globalcolors.textColor
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(globalcolors.secondaryColor)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    "Home",
                                    style = descriptionTextStyle()
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
                                    style = descriptionTextStyle()
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
                    containerColor = globalcolors.primaryColor,
                    titleContentColor = globalcolors.textColor
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddDialog = true
                },
                containerColor = globalcolors.secondaryColor
            ) {
                Icon(
                    Icons.Filled.Add, contentDescription = "Add Announcement",
                    tint = globalcolors.textColor
                )
            }
        },
        content = { paddingValues ->
            // Display Announcements
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .background(announcementbackbrush)
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
                                    color = globalcolors.textColor,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    clickedIndex = if (clickedIndex == index) -1 else index
                                },
                            colors = CardDefaults.cardColors(globalcolors.tertiaryColor),
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
                                            fontSize = 16.sp,
                                            color = globalcolors.textColor
                                        )
                                        Text(
                                            text = announcement.date,
                                            style = descriptionTextStyle(),
                                            color = globalcolors.textColor
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
                                            style = titleTextStyle(),
                                            color = globalcolors.textColor
                                        )
                                        Text(
                                            text = announcement.description,
                                            style = descriptionTextStyle(),
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
                                                    globalcolors.primaryColor,
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
                                                    style = descriptionTextStyle()
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
                                            style = titleTextStyle(),
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
                            .background(globalcolors.primaryColor),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "No announcements found",
                            style = titleTextStyle()
                        )
                        Text(
                            text = "Tap the + icon to add",
                            style = descriptionTextStyle()
                        )
                    }
                }
            }
        },
        containerColor = globalcolors.primaryColor
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

@Composable
fun titleTextStyle() = TextStyle(
    fontSize = 24.sp,
    fontWeight = FontWeight.Bold,
    color = globalcolors.textColor,
    fontFamily = RobotoMono
)

@Composable
fun descriptionTextStyle() = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    color = globalcolors.textColor,
    fontFamily = RobotoMono
)


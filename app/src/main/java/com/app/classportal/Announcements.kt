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

val primaryColor = Color(0xff003C43)
val secondaryColor = Color(0xff135D66)
val tertiaryColor = Color(0xff77B0AA)
val textColor = Color(0xffE3FEF7)

val backbrush = Brush.verticalGradient(
    listOf(
        primaryColor,
        secondaryColor,
        tertiaryColor
    )
)

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
                                    .background(tertiaryColor, shape = MaterialTheme.shapes.small)
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
                                    .background(tertiaryColor, shape = MaterialTheme.shapes.small)
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
            containerColor = primaryColor,

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
                            tint = textColor
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(secondaryColor)
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
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor,
                    titleContentColor = textColor
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = secondaryColor
            ) {
                Icon(
                    Icons.Filled.Add, contentDescription = "Add Announcement",
                    tint = textColor
                )
            }
        },
        content = { paddingValues ->
            // Display Announcements
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .background(backbrush)
                    .fillMaxSize()
            ) {
                // Display Announcements
                if (announcements.isNotEmpty()) {
                    announcements.asReversed().forEachIndexed { index, announcement ->
                        Card(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = textColor,
                                    shape = MaterialTheme.shapes.medium
                                )
                                .padding(8.dp)
                                .fillMaxWidth()
                                .clickable {
                                    clickedIndex = if (clickedIndex == index) -1 else index
                                },
                            colors = CardDefaults.cardColors(tertiaryColor),
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
                                        loadAnnouncement(context)
                                        Text(
                                            text = announcement.student,
                                            fontSize = 16.sp,
                                            color = textColor
                                        )
                                        Text(
                                            text = announcement.date,
                                            style = descriptionTextStyle(),
                                            color = textColor
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
                                            color = textColor
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
                                                    primaryColor,
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
                                                    style = descriptionTextStyle()
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
                            .background(primaryColor),
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
        containerColor = primaryColor
    )

    if (showAddDialog) {
        AnnouncementDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { date, title, description ->
                val newAnnouncement = Announcement(currentId++, date, title, description, student)
                addAnnouncement(newAnnouncement)
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
    color = textColor,
    fontFamily = RobotoMono

)

@Composable
fun descriptionTextStyle() = TextStyle(
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    color = textColor,
    fontFamily = RobotoMono
)




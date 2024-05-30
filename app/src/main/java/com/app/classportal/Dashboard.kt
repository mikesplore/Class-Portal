package com.app.classportal

import Assignments
import WebViewScreen
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.Feed
import androidx.compose.material.icons.automirrored.filled.NoteAdd
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddAlert
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono
import kotlinx.coroutines.delay
import coil.compose.AsyncImage
import com.app.classportal.FileUtil.getAssignment
import com.app.classportal.FileUtil.loadAnnouncement
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.Calendar


val imageUrls = listOf(
    "https://images.template.net/wp-content/uploads/2019/07/Certificate-of-attendance-Format1.jpg",
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRDw9hpory-3_RlKQZOuaWZybojpUjO3X0RQ&usqp=CAU",
    "https://www.shutterstock.com/shutterstock/photos/1908794089/display_1500/stock-photo-academic-concept-smiling-junior-asian-school-girl-sitting-at-desk-in-classroom-writing-in-1908794089.jpg",
    "https://st2.depositphotos.com/1037987/10995/i/450/depositphotos_109959356-stock-photo-teacher-helping-elementary-school-boy.jpg",
    "https://cdn.create.vista.com/api/media/small/567482940/stock-photo-cute-little-children-reading-books-floor-classroom",
    "https://interiordesign.net/wp-content/uploads/2023/03/Interior-Design-Beaverbrook-Art-Gallery-idx230301_intervention02-1024x580.jpg",
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTknkpvaMofoem9OZv0toFmWNDgfeHliHMY1A&usqp=CAU",
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRInVw4qxvUo72Ndlw8iqnfYjiABqgjQLcDag&usqp=CAU",
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSOfrTVFZxNPUYN-zSlUqqdS-OTE6Rm3nLiPw&usqp=CAU"

)
fun getGreetingMessage(): String {
    val currentTime = LocalTime.now()
    return when (currentTime.hour) {
        in 5..11 -> "Good Morning"
        in 12..17 -> "Good Afternoon"
        in 18..21 -> "Good Evening"
        else -> "Good Night"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Dashboard(navController: NavController, context: Context) {
    val horizontalScrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) }
    val announcements = loadAnnouncement(context)
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val tabRowHorizontalScrollState by remember { mutableStateOf(ScrollState(0)) }
    var palleteDialog by remember { mutableStateOf(false) }
    var showrestarting by remember { mutableStateOf(false) }
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


    val greetingMessage by remember { mutableStateOf(getGreetingMessage()) }

    val firstAnnouncement =
        if (announcements.isNotEmpty()) announcements[announcements.lastIndex] else null
    val date = if (firstAnnouncement == null) "Looks like there is no announcement"
    else "You have new announcement from ${firstAnnouncement.student}"
    // Define the list of boxes
    val assignment = if(announcements.isNotEmpty()) "Hey ${global.loggedinuser.value}, check posted assignments" else "No assignment posted"
    val timetable = if(announcements.isNotEmpty()) "Yoh ${global.loggedinuser.value} check timetable" else "No timetable event posted"
    val boxes = listOf(
        R.drawable.announcement to date to "announcements",
        R.drawable.attendance to "Have you updated attendance sheet?" to "RecordAttendance",
        R.drawable.assignment to assignment to "assignments",
        R.drawable.timetable to timetable to "timetable"
    )

    val students = FileUtil.loadStudents(context)
    val student = students.find { it.registrationID == global.regID.value }
    if (student != null) {
        global.firstname.value = student.firstName
    }

    val totalDuration = 10000
    val delayDuration = 5000L
    val boxCount = boxes.size
    val boxScrollDuration = (totalDuration / boxCount)

    LaunchedEffect(Unit) {
        while (true) {
            for (i in 0 until boxCount) {
                val targetScrollPosition = i * (horizontalScrollState.maxValue / (boxCount - 1))
                horizontalScrollState.animateScrollTo(
                    targetScrollPosition,
                    animationSpec = tween(durationMillis = boxScrollDuration, easing = EaseInOut)
                )
                delay(delayDuration)
            }
            horizontalScrollState.scrollTo(0)
        }
    }

    //modal navigation drawer
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "$greetingMessage, ${global.loggedinuser.value.ifEmpty { "Anonymous" }}!",
                        color = globalcolors.textColor,
                        fontWeight = FontWeight.Normal,
                        style = myTextStyle,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(10.dp)
                            )

                    )
                },

                actions = {
                    Box {
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.Menu,
                                contentDescription = "Profile",
                                tint = globalcolors.textColor,
                                modifier = Modifier.size(35.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier
                                .width(180.dp)
                                .background(globalcolors.primaryColor)
                        ) {
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Quick Tasks",
                                        color = globalcolors.textColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        fontFamily = RobotoMono,

                                        )
                                },
                                onClick = {

                                },

                                )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Settings",
                                        color = globalcolors.textColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = RobotoMono
                                    )
                                },
                                onClick = {
                                    navController.navigate("settings")

                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Settings,
                                        contentDescription = "Account Settings",
                                        tint = globalcolors.textColor
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Sign Attendance",
                                        color = globalcolors.textColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = RobotoMono
                                    )
                                },
                                onClick = {
                                    navController.navigate("RecordAttendance")

                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Edit,
                                        contentDescription = "Sign Attendance",
                                        tint = globalcolors.textColor
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "View Students",
                                        color = globalcolors.textColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = RobotoMono
                                    )
                                },
                                onClick = {
                                    navController.navigate("students")

                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Visibility,
                                        contentDescription = "view students",
                                        tint = globalcolors.textColor
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Add Timetable",
                                        color = globalcolors.textColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = RobotoMono
                                    )
                                },
                                onClick = {
                                    navController.navigate("timetable")

                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.Schedule,
                                        contentDescription = "timetable",
                                        tint = globalcolors.textColor
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Make Announcement",
                                        color = globalcolors.textColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = RobotoMono
                                    )
                                },
                                onClick = {
                                    navController.navigate("announcements")

                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Filled.AddAlert,
                                        contentDescription = "announcement",
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
                                    navController.navigate("assignments")

                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.AutoMirrored.Filled.Assignment,
                                        contentDescription = "Add Assignment",
                                        tint = globalcolors.textColor
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Add Student",
                                        color = globalcolors.textColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = RobotoMono
                                    )
                                },
                                onClick = {
                                    navController.navigate("AddStudent")

                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.AutoMirrored.Default.NoteAdd,
                                        contentDescription = "Add student",
                                        tint = globalcolors.textColor
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Color pallete",
                                        color = globalcolors.textColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = RobotoMono
                                    )
                                },
                                onClick = {
                                    palleteDialog = true

                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.AutoMirrored.Filled.Feed,
                                        contentDescription = "color",
                                        tint = globalcolors.textColor
                                    )
                                }
                            )
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        "Logout",
                                        color = globalcolors.textColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        fontFamily = RobotoMono
                                    )
                                },
                                onClick = {
                                    navController.navigate("login")

                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.AutoMirrored.Filled.ExitToApp,
                                        contentDescription = "Logout",
                                        tint = globalcolors.textColor
                                    )
                                }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = globalcolors.primaryColor,
                    navigationIconContentColor = globalcolors.textColor,
                    titleContentColor = globalcolors.textColor,
                    actionIconContentColor = globalcolors.textColor
                )
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .background(globalcolors.primaryColor)
                    .padding(top = 70.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
            ) {
                LaunchedEffect(Unit) {
                    globalcolors.currentScheme = globalcolors.loadColorScheme(context)
                }
                if (palleteDialog) {
                    AlertDialog(
                        title = { Text(text = "Colors Palette", style = myTextStyle) },
                        text = {
                            ColorSettings(context,

                                onsave = {
                                    palleteDialog = false
                                         showrestarting = true},
                                onrevert = {palleteDialog = false
                                showrestarting = true})
                        },
                        onDismissRequest = { palleteDialog = true },
                        confirmButton = {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                                ){
                                val uriHandler = LocalUriHandler.current
                                Text(
                                    text = "Choose Color palette ",
                                    color = globalcolors.tertiaryColor,
                                    style = myTextStyle,
                                    modifier = Modifier.clickable {
                                        uriHandler.openUri("https://colorhunt.co/") // Open URI in browser
                                    }
                                )
                                Icon(imageVector = Icons.Outlined.ArrowOutward, contentDescription = "Choose Color palette",
                                    tint = globalcolors.tertiaryColor,
                                    modifier = Modifier.clickable {
                                        uriHandler.openUri("https://colorhunt.co/") // Open URI in browser
                                    })
                            }

                        },

                        containerColor = globalcolors.secondaryColor
                    )
                }
                if (showrestarting) {
                    AlertDialog(
                        title = { Text(text = "App refresh required", style = myTextStyle) },
                        text = {
                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.SpaceBetween
                            ){
                                Text(text = "The app will refresh for the colors to load properly",
                                    style = myTextStyle)

                            }

                        },
                        onDismissRequest = {  },
                        confirmButton = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ){

                                Button(onClick = {

                                    showrestarting = false
                                    navController.navigate("welcome")
                                    Toast.makeText(context, "Refreshing screens", Toast.LENGTH_SHORT).show()
                                },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(globalcolors.primaryColor)) {
                                    Text(text = "Ok",
                                        style = myTextStyle,)
                                }}
                        },

                        containerColor = globalcolors.secondaryColor
                    )
                }

                Spacer(modifier = Modifier.height(22.dp))

                Row(
                    modifier = Modifier
                        .requiredHeight(200.dp)
                        .fillMaxWidth()
                        .horizontalScroll(horizontalScrollState),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Spacer(modifier = Modifier.width(10.dp))

                    boxes.forEach { item ->
                        TopBoxes(
                            image = painterResource(id = item.first.first),
                            description = item.first.second,
                            route = item.second,
                            navController = navController,
                            context = context
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Column(
                    modifier = Modifier
                        .background(
                            addbackbrush,
                            shape = RoundedCornerShape(30.dp, 30.dp)
                        )
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    //start of the bottom tabs
                    Spacer(modifier = Modifier.height(27.dp))
                    val tabTitles =
                        listOf(
                            "Announcements",
                            "Attendance",
                            "Timetable",
                            "Assignments",
                            "Manage Students",
                            "Documentation",

                        )
                    val indicator = @Composable { tabPositions: List<TabPosition> ->
                        Box(
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                .height(4.dp)
                                .width(screenWidth / tabTitles.size) // Divide by the number of tabs
                                .background(globalcolors.textColor, CircleShape)
                        )
                    }


                    val coroutineScope = rememberCoroutineScope()

                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        modifier = Modifier.background(globalcolors.secondaryColor),
                        contentColor = globalcolors.primaryColor,
                        indicator = indicator,
                        edgePadding = 0.dp,

                        ) {
                        tabTitles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = {
                                    selectedTabIndex = index
                                    coroutineScope.launch {
                                        tabRowHorizontalScrollState.animateScrollTo(
                                            (screenWidth.value / tabTitles.size * index).toInt()
                                        )
                                    }
                                },
                                text = {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                if (selectedTabIndex == index) globalcolors.primaryColor else globalcolors.secondaryColor,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .padding(8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = title,
                                            fontFamily = RobotoMono,
                                            fontSize = 13.sp,
                                            color = if (selectedTabIndex == index) globalcolors.textColor else globalcolors.tertiaryColor,
                                        )
                                    }
                                },
                                modifier = Modifier.background(addbackbrush)

                            )
                        }
                    }


                    // Content based on selected tab
                    when (selectedTabIndex) {
                        0 -> AnnouncementTabContent(navController, context)
                        1 -> AttendanceTabContent(context, navController)
                        2 -> TimetableTabContent(context)
                        3 -> AssignmentsTabContent(navController, context)
                        4 -> StudentsTabContent(navController, context)
                        5 -> DocumentationTab()

                    }
                }
            }


        }

    )
}


@Composable
fun DocumentationTab(){
        Column(modifier = Modifier
            .background(globalcolors.primaryColor)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()) {
            WebViewScreen(link = "https://github.com/mikesplore/Class-Portal/blob/master/app/src/main/java/com/app/classportal/Updates.md")
        }


    }




@Composable
fun LatestAnnouncement(context: Context) {
    LaunchedEffect(Unit) {
        globalcolors.currentScheme = globalcolors.loadColorScheme(context)
    }
    val announcements = loadAnnouncement(LocalContext.current)
    val latestAnnouncement = announcements.lastOrNull()
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

    Column(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = globalcolors.primaryColor,
                shape = RoundedCornerShape(30.dp)
            )
            .height(300.dp)
            .fillMaxWidth()
            .background(addbackbrush, shape = RoundedCornerShape(30.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = latestAnnouncement?.title ?: "NEW APP FEATURES",
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = globalcolors.textColor,
                modifier = Modifier.padding(10.dp)
            )
        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = latestAnnouncement?.description
                    ?: ("1. You can now filter out attendance records based on date\n\n" +
                            "2. System notifications now available! To enable system notifications, go to your system settings > Apps > Class Portal > Enable notifications."),
                fontWeight = FontWeight.Normal,
                style = myTextStyle,
                textAlign = TextAlign.Left,
                fontSize = 15.sp,
                modifier = Modifier.padding(10.dp)
            )

        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.Right
        ) {
            Text(text = "Posted by", style = myTextStyle)
            Text(
                text = latestAnnouncement?.student ?: "Developer Mike",
                style = myTextStyle,
                color = globalcolors.textColor,
                modifier = Modifier.padding(10.dp)
            )

        }
    }
}


@Composable
fun TopBoxes(image: Painter, description: String, route: String, navController: NavController, context: Context) {
    Row(
        modifier = Modifier
            .clickable {
                navController.navigate(route)
            }
            .background(Color.Transparent, shape = RoundedCornerShape(30.dp))
            .fillMaxHeight()
            .width(350.dp)
    ) {
        Box(modifier = Modifier) {
            LaunchedEffect(Unit) {
                globalcolors.currentScheme = globalcolors.loadColorScheme(context)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .fillMaxSize()
            ) {
                Image(
                    painter = image,
                    contentDescription = "sample",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter) // Position at the bottom
                        .background(
                            globalcolors.secondaryColor.copy(alpha = 0.3f), // Semi-transparent black background
                            shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                        )
                        .padding(16.dp),
                ) {
                    Text(
                        text = description,
                        color = globalcolors.textColor,
                        style = TextStyle(
                            fontFamily = RobotoMono,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = globalcolors.primaryColor.copy(alpha = 0.9f),
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )

                        ),
                    )
                }
            }
        }
    }
}


@Composable
fun AttendanceBox(
    context: Context,
    imageUrl: String,
    content: String,
    route: String,
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
            .fillMaxHeight()
            .width(200.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            LaunchedEffect(Unit) {
                globalcolors.currentScheme = globalcolors.loadColorScheme(context)
            }
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate(route)
                    }
                    .clip(RoundedCornerShape(20.dp))
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "sample",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.error_image),
                    placeholder = painterResource(id = R.drawable.loading_image)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart) // Position at the bottom
                        .background(globalcolors.primaryColor.copy(0.5f))
                        .padding(16.dp)
                ) {
                    Text(
                        text = content,
                        color = globalcolors.textColor,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = globalcolors.primaryColor.copy(alpha = 0.9f),
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun AnnouncementTabContent(navController: NavController, context: Context) {

        LaunchedEffect(Unit) {
            globalcolors.currentScheme = globalcolors.loadColorScheme(context)
        }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        val announcements = loadAnnouncement(LocalContext.current)
        Text(
            "Top Announcements",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = globalcolors.textColor,
            fontFamily = RobotoMono,
            modifier = Modifier.padding(16.dp)
        )
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .height(200.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Always display 3 AnnouncementBoxes
            repeat(3) { index ->
                val announcement = announcements.asReversed().getOrNull(index)
                AnnouncementBoxes(
                    date = announcement?.date ?: "No announcement",
                    title = announcement?.title ?: "No announcement",
                    student = announcement?.student ?: "No announcement",
                    content = announcement?.description ?: "No announcement",
                    route = "announcements",
                    navController = navController,
                    context = context
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            "Latest Announcement",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = globalcolors.textColor,
            fontFamily = RobotoMono,
            modifier = Modifier.padding(16.dp)
        )
        LatestAnnouncement(context)


    }
}

@Composable
fun AttendanceTabContent(context: Context, navController: NavController) {
    LaunchedEffect(Unit) {
        globalcolors.currentScheme = globalcolors.loadColorScheme(context)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(globalcolors.primaryColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)

        ) {
            Box(
                modifier = Modifier
                    .height(250.dp),
                contentAlignment = Alignment.BottomCenter // Change alignment
            ) {
                AttendanceReportContent(context = context)

                Box(
                    modifier = Modifier
                        .clickable { navController.navigate("AttendanceReport") }
                        .fillMaxWidth()
                        .background(globalcolors.secondaryColor) // Semi-transparent background
                        .padding(8.dp),
                    contentAlignment = Alignment.Center // Center text in the box
                ) {
                    Text(text = "View full attendance", style = myTextStyle)
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Sign or view the attendance",
            style = myTextStyle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )
        Row(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            AttendanceBox(
                context = context,
                imageUrl = imageUrls[0],
                content = "Sign Attendance",
                "RecordAttendance",
                navController,


            )
            Spacer(modifier = Modifier.width(10.dp))
            AttendanceBox(
                context = context,
                imageUrl = imageUrls[1],
                content = "View Attendance Report",
                "AttendanceReport",
                navController,

            )
            Spacer(modifier = Modifier.width(10.dp))


        }
    }
}


@Composable
fun TimetableTabContent(context: Context) {
    LaunchedEffect(Unit) {
        globalcolors.currentScheme = globalcolors.loadColorScheme(context)
    }
    CurrentDayEventsScreen()

}

@Composable
fun AssignmentsTabContent(navController: NavController, context: Context) {
    LaunchedEffect(Unit) {
        globalcolors.currentScheme = globalcolors.loadColorScheme(context)
    }
    Assignments(navController = navController, context = context)
}


@Composable
fun StudentsTabContent(navController: NavController, context: Context) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(globalcolors.primaryColor),
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .height(250.dp)
        ) {
            ShowStudentsScreen(context = context, navController)
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Manage Student",
            style = myTextStyle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(10.dp)
        )
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(10.dp)
                .fillMaxWidth()
                .height(200.dp)
        ) {

            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate("AddStudent")
                    }
                    .clip(RoundedCornerShape(20.dp))
                    .width(200.dp)
                    .fillMaxHeight()
            ) {
                AsyncImage(
                    model = imageUrls[6],
                    contentDescription = "sample",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.error_image),
                    placeholder = painterResource(id = R.drawable.loading_image)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart) // Position at the bottom
                        .background(globalcolors.primaryColor.copy(0.5f))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Add Student",
                        color = globalcolors.textColor,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = globalcolors.primaryColor.copy(alpha = 0.9f),
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(15.dp))
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate("EditStudent")
                    }
                    .clip(RoundedCornerShape(20.dp))
                    .width(200.dp)
                    .fillMaxHeight()
            ) {
                AsyncImage(
                    model = imageUrls[8],
                    contentDescription = "sample",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.error_image),
                    placeholder = painterResource(id = R.drawable.loading_image)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart) // Position at the bottom
                        .background(globalcolors.primaryColor.copy(0.5f))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Edit Student",
                        color = globalcolors.textColor,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = globalcolors.primaryColor.copy(alpha = 0.9f),
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.width(15.dp))
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate("DeleteStudent")
                    }
                    .clip(RoundedCornerShape(20.dp))
                    .width(200.dp)
                    .fillMaxHeight()
            ) {
                AsyncImage(
                    model = imageUrls[7],
                    contentDescription = "sample",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.error_image),
                    placeholder = painterResource(id = R.drawable.loading_image)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart) // Position at the bottom
                        .background(globalcolors.primaryColor.copy(0.5f))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Delete Student",
                        color = globalcolors.textColor,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = globalcolors.primaryColor.copy(alpha = 0.9f),
                                offset = Offset(4f, 4f),
                                blurRadius = 4f
                            )
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}


@Composable
fun AnnouncementBoxes(
    context: Context,
    date: String,
    student: String,
    title: String,
    content: String,
    route: String,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        globalcolors.currentScheme = globalcolors.loadColorScheme(context)
    }
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
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp, 0.dp, 20.dp, 0.dp),
                ambientColor = globalcolors.primaryColor,
                spotColor = globalcolors.tertiaryColor
            )
            .background(addbackbrush, shape = RoundedCornerShape(30.dp, 0.dp, 30.dp, 0.dp))
            .fillMaxHeight()
            .clickable { navController.navigate(route) }
            .width(200.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Student Image at the top with a colored background
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp, 0.dp, 20.dp, 0.dp),
                        ambientColor = globalcolors.primaryColor,
                        spotColor = globalcolors.tertiaryColor
                    )
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(addbackbrush, RoundedCornerShape(20.dp, 0.dp, 30.dp, 0.dp)), // Use a background color for the image
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.student),
                    contentDescription = "student",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp)
                )
            }

            // Student name with larger font and spacing
            Text(
                text = student,
                style = myTextStyle.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                modifier = Modifier.padding(top = 12.dp)
            )

            // Date as a subtitle with a smaller font
            Text(
                text = date,
                color = globalcolors.textColor,
                style = myTextStyle.copy(fontSize = 12.sp) // Smaller font for date
            )

            // Title and content with visual distinction
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp), // Add more padding above title
                horizontalAlignment = Alignment.Start // Align text to the start
            ) {
                Text(
                    text = title,
                    style = myTextStyle.copy(fontWeight = FontWeight.Bold, fontSize = 16.sp),
                    maxLines = 2, // Allow 2 lines for longer titles
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = content,
                    style = myTextStyle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController()
    Dashboard(navController, LocalContext.current)
}


val myTextStyle = TextStyle(
    fontFamily = RobotoMono,
    color = globalcolors.textColor,
    fontSize = 15.sp
)

package com.app.classportal

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
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
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono
import kotlinx.coroutines.delay
import coil.compose.AsyncImage
import com.app.classportal.FileUtil.loadAnnouncement
import kotlinx.coroutines.launch

val imageUrls = listOf(
    "https://images.template.net/wp-content/uploads/2019/07/Certificate-of-attendance-Format1.jpg",
    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRRDw9hpory-3_RlKQZOuaWZybojpUjO3X0RQ&usqp=CAU",
    "https://www.shutterstock.com/shutterstock/photos/1908794089/display_1500/stock-photo-academic-concept-smiling-junior-asian-school-girl-sitting-at-desk-in-classroom-writing-in-1908794089.jpg",
    "https://st2.depositphotos.com/1037987/10995/i/450/depositphotos_109959356-stock-photo-teacher-helping-elementary-school-boy.jpg",
    "https://cdn.create.vista.com/api/media/small/567482940/stock-photo-cute-little-children-reading-books-floor-classroom",
    "https://interiordesign.net/wp-content/uploads/2023/03/Interior-Design-Beaverbrook-Art-Gallery-idx230301_intervention02-1024x580.jpg",
    "https://media.istockphoto.com/id/911030028/photo/group-photo-at-school.jpg?s=612x612&w=0&k=20&c=iteKL8IJfHntwPsOqGVpwJQOIck3YCeSvf0lJoJL_Wo="
)
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

    val firstAnnouncement = if (announcements.isNotEmpty()) announcements[announcements.lastIndex] else null
    val date = if (firstAnnouncement == null) "Looks like there is no announcement"
    else "You have new announcement from ${firstAnnouncement.student}"
    // Define the list of boxes
    val boxes = listOf(
        R.drawable.announcement to date to "soon",
        R.drawable.attendance to "Have you updated attendance sheet?" to "RecordAttendance",
        R.drawable.assignment to "No due assignments" to "soon",
        R.drawable.timetable to "Yooh, you have new timetable" to "timetable"
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
                    title = { Text("Welcome, ${global.loggedinuser.value.ifEmpty { "Anonymous" }}",
                        color = textColor,
                        fontWeight = FontWeight.Normal,
                        style = myTextStyle,
                        fontSize = 20.sp,) },

                    actions = {
                        Box {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    imageVector = Icons.Filled.AccountCircle,
                                    contentDescription = "Profile",
                                    tint = textColor,
                                    modifier = Modifier.size(35.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(primaryColor)
                            ) {
                                Row(modifier = Modifier.fillMaxWidth()){
                                DropdownMenuItem(
                                    text = { Text("Account Settings",color = textColor, fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = RobotoMono) },
                                    onClick = {
                                        Toast.makeText(context, "Feature coming soon!", Toast.LENGTH_SHORT).show()
                                        expanded = false }
                                )}
                                DropdownMenuItem(
                                    text = { Text("Logout", color = textColor, fontWeight = FontWeight.Bold, fontSize = 16.sp, fontFamily = RobotoMono) },
                                    onClick = {
                                        expanded = false
                                        navController.navigate("login")
                                    }
                                )
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = primaryColor,
                        navigationIconContentColor = textColor,
                        titleContentColor = textColor,
                        actionIconContentColor = textColor
                    )
                )
            },
            content = {
                Column(
                    modifier = Modifier
                        .background(primaryColor)
                        .padding(top = 70.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center,
                ) {
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
                                navController = navController
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier
                            .background(secondaryColor, shape = RoundedCornerShape(30.dp, 30.dp))
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        //start of the bottom tabs
                        Spacer(modifier = Modifier.height(27.dp))
                        val tabTitles = listOf("Announcements", "Attendance", "Timetable", "Resources", "Gallery")
                        val indicator = @Composable { tabPositions: List<TabPosition> ->
                            Box(
                                modifier = Modifier
                                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                                    .height(4.dp)
                                    .width(screenWidth / tabTitles.size) // Divide by the number of tabs
                                    .background(textColor, CircleShape)
                            )
                        }


                        val coroutineScope = rememberCoroutineScope()

                        ScrollableTabRow(
                            selectedTabIndex = selectedTabIndex,
                            modifier = Modifier.background(secondaryColor),
                            contentColor = primaryColor,
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
                                                    if (selectedTabIndex == index) primaryColor else secondaryColor,
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .padding(8.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = title,
                                                fontFamily = RobotoMono,
                                                fontSize = 13.sp,
                                                color = if (selectedTabIndex == index) textColor else Color.LightGray
                                            )
                                        }
                                    },
                                    modifier = Modifier.background(secondaryColor)

                                )
                            }
                        }




                        // Content based on selected tab
                        when (selectedTabIndex) {
                            0 -> AnnouncementTabContent(navController)
                            1 -> AttendanceTabContent(context, navController)
                            2 -> TimetableTabContent()
                            3 -> ResourcesTabContent()
                            4 -> GalleryTabContent()

                        }
                    }
                }

            }
        )
    }


@Composable
fun LatestAnnouncement() {
    val announcements = loadAnnouncement(LocalContext.current)
    val latestAnnouncement = announcements.firstOrNull()

    Column(
        modifier = Modifier
            .border(width = 1.dp, color = primaryColor, shape = RoundedCornerShape(30.dp))
            .height(200.dp)
            .fillMaxWidth()
            .background(buttonBrush, shape = RoundedCornerShape(30.dp))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = latestAnnouncement?.title ?: "New UI",
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = textColor,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = latestAnnouncement?.date ?: "25/05/2024",
                color = textColor,
                style = myTextStyle,
                modifier = Modifier.padding(10.dp)
            )
        }
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = latestAnnouncement?.description ?: "I decided to re-design the User Interface, how do you rate it out of 10?",
                fontWeight = FontWeight.Normal,
                style = myTextStyle,
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                modifier = Modifier.padding(10.dp)
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "Posted by", style = myTextStyle)
            Text(
                text = latestAnnouncement?.student ?: "Developer",
                style = myTextStyle,
                color = textColor,
                modifier = Modifier.padding(10.dp)
            )
        }
    }
}





@Composable
fun TopBoxes(image: Painter, description: String,route: String,navController: NavController) {
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
                            secondaryColor.copy(alpha = 0.3f), // Semi-transparent black background
                            shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                        )
                        .padding(16.dp),
                ) {
                    Text(
                        text = description,
                        color = textColor,
                        style = TextStyle(
                            fontFamily = RobotoMono,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.9f),
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
                        .background(Color.Transparent)
                        .padding(16.dp)
                ) {
                    Text(
                        text = content,
                        color = textColor,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = primaryColor.copy(alpha = 0.9f),
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
fun AnnouncementTabContent(navController: NavController) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        val announcements = loadAnnouncement(LocalContext.current)
        Text("Top Announcements",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontFamily = RobotoMono,
            modifier = Modifier.padding(16.dp))
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
                val announcement = announcements.getOrNull(index)
                AnnouncementBoxes(
                    date = announcement?.date ?: "No announcement",
                    title = announcement?.title ?: "No announcement",
                    student = announcement?.student ?: "No announcement",
                    content =  announcement?.description ?: "No announcement",
                    route = "soon",
                    navController = navController
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text("Latest Announcement",
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            fontFamily = RobotoMono,
            modifier = Modifier.padding(16.dp))
        LatestAnnouncement()


    }
}

@Composable
fun AttendanceTabContent(context: Context, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor)
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
            ){
                AttendanceReportContent(context = context)

                Box(
                    modifier = Modifier
                        .clickable { navController.navigate("AttendanceReport") }
                        .fillMaxWidth()
                        .background(secondaryColor) // Semi-transparent background
                        .padding(8.dp),
                    contentAlignment = Alignment.Center // Center text in the box
                ) {
                    Text(text = "View full attendance", style = myTextStyle)
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            AttendanceBox(imageUrl = imageUrls[0], content = "Sign Attendance","RecordAttendance",navController)
            Spacer(modifier = Modifier.width(10.dp))
            AttendanceBox(imageUrl = imageUrls[1], content = "View Attendance Report","AttendanceReport",navController)
            Spacer(modifier = Modifier.width(10.dp))





            }
        }
    }






@Composable
fun TimetableTabContent() {
    CurrentDayEventsScreen()

}
@Composable
fun ResourcesTabContent() {
   Column(
       modifier = Modifier
           .fillMaxSize()
           .background(primaryColor),
       horizontalAlignment = Alignment.CenterHorizontally,
       verticalArrangement = Arrangement.Center
   ) {
       Text(text = "Resources Content Coming Soon!",
           style = myTextStyle,
           modifier = Modifier.padding(16.dp))

   }
}
@Composable
fun GalleryTabContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Gallery Content Coming Soon!",
            style = myTextStyle,
            modifier = Modifier.padding(16.dp))

    }
}


@Composable
fun AnnouncementBoxes(date: String, student: String, title: String,content: String, route: String, navController: NavController){
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = primaryColor,
                spotColor = tertiaryColor
            )
            .background(buttonBrush, shape = RoundedCornerShape(30.dp))
            .fillMaxHeight()
            .clickable { navController.navigate(route) }
            .width(200.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = date,
                color = textColor,
                style = myTextStyle,
                modifier = Modifier.padding(10.dp))
            Row(modifier = Modifier
                .absolutePadding(7.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically){
                Image(painter = painterResource(id = R.drawable.student), contentDescription = "student",
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(50.dp))
                Text(
                    text = student,
                    color = textColor,
                    style = myTextStyle,
                    modifier = Modifier
                        .padding(top = 10.dp)
                )

            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (title.length > 10) "${title.take(10)}..." else title, // Truncate with ellipsis
                    style = myTextStyle,
                    modifier = Modifier.padding(10.dp)
                )
                Text(
                    text = if (content.length > 10) "${content.take(10)}..." else content,
                    fontWeight = FontWeight.Bold,
                    style = myTextStyle,
                    modifier = Modifier.padding(10.dp),
                    maxLines = 1,
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
    Dashboard(navController,LocalContext.current)
}


val myTextStyle = TextStyle(
    fontFamily = RobotoMono,
    color = textColor,
    fontSize = 15.sp
)
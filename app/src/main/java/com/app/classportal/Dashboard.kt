package com.app.classportal

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono
import kotlinx.coroutines.delay
import coil.compose.AsyncImage
import com.app.classportal.FileUtil.loadAnnouncement


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Dashboard(navController: NavController, context: Context) {
    val primaryColor = Color(0xFF27374D)
    val textColor = Color.White
    val horizontalScrollState = rememberScrollState()
    var expanded by remember { mutableStateOf(false) } // Changed to false initially
    val announcements = loadAnnouncement(context)

    val firstAnnouncement = if (announcements.isNotEmpty()) announcements[announcements.lastIndex] else null
    val date = if (firstAnnouncement == null) "Looks like there is no announcement"
    else "You have new announcement (${firstAnnouncement.title})"
    // Define the list of boxes
    val boxes = listOf(
        R.drawable.announcement to date to "soon",
        R.drawable.attendance to "Did you sign your attendance?" to "RecordAttendanceScreen",
        R.drawable.assignment to "Check your assignments" to "soon",
        R.drawable.timetable to "Yooh, check your timetable" to "soon"
    )

    val imageUrls = listOf(
        "https://mrslamsmusings.files.wordpress.com/2016/03/file_005.jpeg?w=763&resize=763%2C572&h=572",
        "https://img.freepik.com/free-photo/medium-shot-women-correcting-grammar-mistakes_23-2150171141.jpg",
        "https://www.shutterstock.com/shutterstock/photos/1908794089/display_1500/stock-photo-academic-concept-smiling-junior-asian-school-girl-sitting-at-desk-in-classroom-writing-in-1908794089.jpg",
        "https://st2.depositphotos.com/1037987/10995/i/450/depositphotos_109959356-stock-photo-teacher-helping-elementary-school-boy.jpg",
        "https://cdn.create.vista.com/api/media/small/567482940/stock-photo-cute-little-children-reading-books-floor-classroom",
        "https://interiordesign.net/wp-content/uploads/2023/03/Interior-Design-Beaverbrook-Art-Gallery-idx230301_intervention02-1024x580.jpg",
        "https://media.istockphoto.com/id/911030028/photo/group-photo-at-school.jpg?s=612x612&w=0&k=20&c=iteKL8IJfHntwPsOqGVpwJQOIck3YCeSvf0lJoJL_Wo="
    )

    val students = FileUtil.loadStudents(context)
    val student = students.find { it.registrationID == global.regID.value }
    if (student != null) {
        global.firstname.value = student.firstName
    }

    // Calculate the duration for each box
    val totalDuration = 10000 // Total duration for the entire scroll
    val delayDuration = 5000L // Duration to delay at each box
    val boxCount = boxes.size
    val boxScrollDuration = (totalDuration / boxCount)




    LaunchedEffect(Unit) {
        while (true) {
            for (i in 0 until boxCount) {
                // Calculate the target scroll position for each box
                val targetScrollPosition = i * (horizontalScrollState.maxValue / (boxCount - 1))

                // Animate to the target position
                horizontalScrollState.animateScrollTo(
                    targetScrollPosition,
                    animationSpec = tween(durationMillis = boxScrollDuration, easing = EaseInOut)
                )
                // Delay at each box
                delay(delayDuration)
            }

            // Instantaneous return to the start for a seamless loop
            horizontalScrollState.scrollTo(0)
        }
    }

     //modal navigation drawer
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Hello, ${global.firstname.value}", color = textColor, fontFamily = RobotoMono) },

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
                                modifier = Modifier.background(color1)
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
                        .background(color1)
                        .padding(top = 70.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Horizontally scrollable row below the top app bar
                    Row(
                        modifier = Modifier
                            .requiredHeight(200.dp)
                            .fillMaxWidth()
                            .horizontalScroll(horizontalScrollState)
                            .padding(10.dp),
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
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Announcements",
                        modifier = Modifier.padding(10.dp),
                        fontFamily = RobotoMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = textColor)
                    Column(
                        modifier = Modifier
                            .clickable { navController.navigate("soon") }
                            .padding(10.dp)
                            .fillMaxWidth()
                            .height(200.dp)
                            .border(
                                width = 2.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(color2, shape = RoundedCornerShape(20.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                    ){
                        Row(modifier = Modifier
                            .border(
                                width = 2.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .background(color, shape = RoundedCornerShape(20.dp))
                            .height(50.dp)
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically){
                            if (firstAnnouncement != null) {
                                Text(text = firstAnnouncement.title,
                                    fontFamily = RobotoMono,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = textColor,
                                    )
                            }else{
                                Text(text = "No Announcements (Tap to add)",
                                    fontFamily = RobotoMono,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = textColor,
                                    style = TextStyle(
                                        shadow = Shadow(
                                            color = Color.Black.copy(alpha = 0.9f),
                                            offset = Offset(4f, 4f),
                                            blurRadius = 4f
                                        )
                                    )
                                    )
                            }

                        }

                        Spacer(modifier = Modifier.height(5.dp))
                        Column(modifier = Modifier
                            .height(130.dp),

                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top){

                            if (firstAnnouncement != null) {
                                Column(verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {

                                Image(painter = painterResource(id = R.drawable.student), contentDescription = "dp",
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .size(40.dp))
                                    Text(text = firstAnnouncement.student,
                                        style = descriptionTextStyle())}
                                Text(text = firstAnnouncement.date,
                                    fontFamily = RobotoMono,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = textColor)
                                }
                            }else{
                                Text(text = "New announcements will appear here",
                                    fontFamily = RobotoMono,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    color = textColor)
                            }
                            Spacer(modifier = Modifier.height(5.dp))

                            if (firstAnnouncement != null) {
                                Text(text =firstAnnouncement.description,
                                    fontFamily = RobotoMono,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 15.sp,
                                    color = textColor,
                                    textAlign = TextAlign.Center)
                            }else{
                                Text(text = "I have completed the implementation of  the Announcements and Attendance system " +
                                        "I'm now working on the study resources and gallery system(partially done)",
                                    fontFamily = RobotoMono,
                                    fontWeight = FontWeight.Light,
                                    fontSize = 15.sp,
                                    color = textColor,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(5.dp))
                            }
                        }

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Attendance",
                        modifier = Modifier.padding(10.dp),
                        fontFamily = RobotoMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = textColor)
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                    ){
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRows(
                            route = "AttendanceReport",
                            content = "View Attendance report",
                            navController = navController,
                            link = "https://img.freepik.com/free-vector/appointment-booking-with-calendar_23-2148553008.jpg"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRows(
                            route = "RecordAttendance",
                            content = "Record Attendance",
                            navController = navController,
                            link =  "https://media.licdn.com/dms/image/D4D12AQEdDcCKmY1fhg/article-cover_image-shrink_720_1280/0/1688121586524?e=2147483647&v=beta&t=LaOWGAh5B6MP0JxXhqaTrtz0qkJJexFS7MjzaRXkqEw")
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRows(
                            route = "DeleteStudent",
                            content = "Delete Student",
                            navController = navController,
                            link = "https://d1nhio0ox7pgb.cloudfront.net/_img/v_collection_png/512x512/shadow/businessman_delete.png")
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRows(
                            route = "AddStudent",
                            content = "Add Student",
                            navController = navController,
                            link = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSf3aESFZ0LL4RlleixQf4Rn42ZuRxGsvRrGA&usqp=CAU")
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRows(
                            route = "EditStudent",
                            content = "Edit Student",
                            navController = navController,
                            link = "https://d1nhio0ox7pgb.cloudfront.net/_img/v_collection_png/512x512/shadow/businessman_edit.png")
                        Spacer(modifier = Modifier.width(10.dp))


                    }




                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Class Gallery",
                        modifier = Modifier
                            .clickable {
                                navController.navigate("gallery")
                            }
                            .padding(10.dp),
                        fontFamily = RobotoMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = textColor,

                        )


                    Row (modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .fillMaxWidth()
                        .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                        .height(200.dp)){
                        Spacer(modifier = Modifier.width(10.dp))

                        MiddleRowsOnline(
                            imageUrl = imageUrls[0],
                            content = "Online Notes"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRowsOnline(
                            imageUrl = imageUrls[1],
                            content = "Online Quiz"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRowsOnline(
                            imageUrl = imageUrls[2],
                            content = "Online Exam"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRowsOnline(
                            imageUrl = imageUrls[3],
                            content = "Online Assignment"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRowsOnline(
                            imageUrl = imageUrls[4],
                            content = "Online Library"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRowsOnline(
                            imageUrl = imageUrls[5],
                            content = "Online Gallery"
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        MiddleRowsOnline(
                            imageUrl = imageUrls[6],
                            content = "Online School"
                        )

                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "Study Resources",
                        modifier = Modifier.padding(10.dp),
                        fontFamily = RobotoMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = textColor)
                    Column {
                        Text(text = "Coming Soon!!!",
                            modifier = Modifier.padding(10.dp),
                            fontFamily = RobotoMono,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = textColor)
                    }

                }
            }
        )
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
            .width(300.dp)
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
                            Color.Black.copy(alpha = 0.3f), // Semi-transparent black background
                            shape = RoundedCornerShape(bottomStart = 30.dp, bottomEnd = 30.dp)
                        )
                        .padding(16.dp),
                ) {
                    Text(
                        text = description,
                        color = Color.White,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.9f),
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
fun MiddleRows(link: String, content: String, route: String, navController: NavController) {
    Row(
        modifier = Modifier
            .clickable { navController.navigate(route) }
            .background(Color.Transparent, shape = RoundedCornerShape(30.dp))
            .fillMaxHeight()
            .width(150.dp)
    ) {

        Box(modifier = Modifier) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .fillMaxSize()
            ) {

                AsyncImage(
                    model = link,
                    contentDescription = "sample",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(30.dp)), // Clip the image to match the Box's shape
                    contentScale = ContentScale.Crop,
                    error = painterResource(id = R.drawable.error_image),
                    placeholder = painterResource(id = R.drawable.loading_image)// Ensure the image fills the space and is cropped if necessary
                )

                // Text Overlay with Improved Readability
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(color.copy(alpha = 0.3f)) // Semi-transparent black background for contrast
                        .padding(8.dp),
                ) {
                    Text(
                        text = content,
                        color = Color.Black, // White text for better contrast
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.9f),
                                offset = Offset(4f, 4f),
                                blurRadius = 4f)
                        )
                    )
                }
            }
        }
    }
}



@Composable
fun MiddleRowsOnline(
    imageUrl: String,
    content: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    shadowColor: Color = Color.Black
) {
    Row(
        modifier = modifier
            .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
            .fillMaxHeight()
            .width(350.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
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
                                color = shadowColor.copy(alpha = 0.9f),
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



@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    val navController = rememberNavController()
    Dashboard(navController,LocalContext.current)
}

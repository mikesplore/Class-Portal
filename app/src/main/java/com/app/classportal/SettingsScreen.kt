package com.app.classportal

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, context: Context) {
    var notificationsEnabled by remember { mutableStateOf(false) }
    var darkThemeEnabled by remember { mutableStateOf(false) }
    var expandedColumn by remember { mutableStateOf(false) }
    var newfirstname by remember { mutableStateOf("") }
    var newlastname by remember { mutableStateOf("") }
    var newusername by remember { mutableStateOf("") }
    var studentFound by remember { mutableStateOf(false) }
    var showPaletteDialog by remember { mutableStateOf(false) }
    var showusername by remember { mutableStateOf(false) }

    val students = FileUtil.loadStudents(context)
    val student = students.find { it.registrationID == global.loggedinregID.value }
    if (student != null && !expandedColumn) {
        newfirstname = student.firstName
        newlastname = student.lastName
        newusername = student.username
        studentFound = true
    } else if (student == null) {
        Toast.makeText(context, "Student not found", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("welcome") }) {
                        Box(modifier = Modifier

                            .border(
                                width = 1.dp,
                                color = globalcolors.textColor,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                            .size(50.dp),
                            contentAlignment = Alignment.Center){
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = globalcolors.textColor,
                            )
                        }
                    }
                },
                title = { Text("Settings", style = myTextStyle, fontWeight = FontWeight.Bold, fontSize = 30.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = globalcolors.primaryColor,
                    titleContentColor = globalcolors.textColor
                )
            )
        },
        containerColor = globalcolors.primaryColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(globalcolors.primaryColor)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Account",
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = globalcolors.textColor
            )

            Column(
                modifier = Modifier
                    .background(globalcolors.secondaryColor, RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row {
                        Text(
                            text = global.loggedinuser.value,
                            style = TextStyle(color = globalcolors.textColor),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = global.loggedinlastname.value,
                            style = TextStyle(color = globalcolors.textColor),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    IconButton(onClick = { expandedColumn = !expandedColumn }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "edit",
                            tint = globalcolors.textColor
                        )
                    }
                }

                AnimatedVisibility(visible = expandedColumn) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CustomTextField(value = newfirstname, onValueChange = { newfirstname = it }, label = "First Name")
                        CustomTextField(value = newlastname, onValueChange = { newlastname = it }, label = "Last Name")
                        CustomTextField(value = newusername, onValueChange = { newusername = it }, label = "Username")

                        if (newfirstname.isNotEmpty() && newlastname.isNotEmpty() && newusername.isNotEmpty()) {
                            IconButton(onClick = {
                                val updatedStudent = Student(
                                    registrationID = global.loggedinregID.value,
                                    firstName = newfirstname,
                                    lastName = newlastname,
                                    username = newusername
                                )
                                FileUtil.editStudent(context, updatedStudent)
                                global.loggedinuser.value = newfirstname
                                global.loggedinlastname.value = newlastname
                                global.usernames.value = newusername
                                studentFound = false
                                expandedColumn = false // Collapse the column after saving
                                Toast.makeText(context, "Student updated successfully", Toast.LENGTH_SHORT).show()
                            }) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = "Save", tint = globalcolors.textColor)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Preferences",
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = globalcolors.textColor
            )

            PreferenceItem(
                label = "Enable Notifications(Coming soon)",
                checked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            PreferenceItem(
                label = "Show Username",
                checked = showusername,
                onCheckedChange = { checked ->
                    showusername = checked

                    if (checked) { // Only check when the switch is turned ON
                        if (student != null) {
                            if (student.username.isNotEmpty()) {
                                global.loggedinuser.value = student.username
                            } else {
                                Toast.makeText(context, "You have not set your username", Toast.LENGTH_SHORT).show()
                                showusername = false // Reset the switch if no username is set
                            }
                        }
                    }
                }
            )



            PreferenceItem(
                label = "Enable Edge To Edge",
                checked = global.enableEdgeToEdge.value,
                onCheckedChange = { global.enableEdgeToEdge.value = it }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("Use Custom Color Pallete",
                    style = myTextStyle,
                    )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { showPaletteDialog = true }) {
                    Icon(imageVector = Icons.Default.ArrowOutward, contentDescription = "edit", tint = globalcolors.textColor)
                    if (showPaletteDialog) {
                        AlertDialog(
                            title = { Text(text = "Colors Settings", style = myTextStyle) },
                            text = {
                                ColorSettings(context)
                            },
                            onDismissRequest = { showPaletteDialog = false },
                            confirmButton = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    Button(onClick = {
                                        globalcolors.resetToDefaultColors(context)
                                        showPaletteDialog = false
                                    },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(globalcolors.primaryColor)) {
                                        Text(text = "Default colors", style = myTextStyle)
                                    }
                                    Button(onClick = {
                                        Toast.makeText(context, "Color pallete updated! If you still see some old colors, please navigate back and forth to refresh", Toast.LENGTH_SHORT).show()
                                        showPaletteDialog = false },
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(globalcolors.primaryColor)) {
                                        Text(text = "Ok",
                                            style = myTextStyle,)
                                    }}
                            },
                            modifier = Modifier.height(420.dp),
                            containerColor = globalcolors.secondaryColor
                        )
                    }
                    
                }

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "About",
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = globalcolors.textColor
            )

            Text(
                text = "Version 1.2.5",
                style = myTextStyle,
                color = globalcolors.textColor
            )
        }
    }
}

@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(color = globalcolors.textColor, fontSize = 18.sp),
        modifier = Modifier
            .background(globalcolors.primaryColor, RoundedCornerShape(10.dp))
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        if (value.isEmpty()) {
            Text(text = label, color = globalcolors.textColor.copy(alpha = 0.5f))
        }
        it()
    }
}

@Composable
fun PreferenceItem(label: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = myTextStyle,
            color = globalcolors.textColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = globalcolors.secondaryColor,
                uncheckedThumbColor = globalcolors.textColor,
                checkedTrackColor = globalcolors.tertiaryColor,
                uncheckedTrackColor = globalcolors.primaryColor
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    SettingsScreen(navController, LocalContext.current)
}

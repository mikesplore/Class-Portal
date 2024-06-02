package com.app.classportal

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.CommonComponents as CC

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController, context: Context) {
    var notificationsEnabled by remember { mutableStateOf(false) }
    var expandedColumn by remember { mutableStateOf(false) }
    var newfirstname by remember { mutableStateOf("") }
    var newlastname by remember { mutableStateOf("") }
    var newusername by remember { mutableStateOf("") }
    var studentFound by remember { mutableStateOf(false) }
    var palleteDialog by remember { mutableStateOf(false) }
    var showusername by remember { mutableStateOf(false) }
    var showrestarting by remember { mutableStateOf(false) }
    var newpassword by remember { mutableStateOf("") }
    var confirmnewpassword by remember { mutableStateOf("") }
    var oldpassword by remember { mutableStateOf("") }
    var expandedpassword by remember { mutableStateOf(false) }
    var anonymousMode by remember { mutableStateOf(false) }

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
                    IconButton(onClick = { navController.navigate("dashboard") }) {
                        Box(
                            modifier = Modifier

                                .border(
                                    width = 1.dp,
                                    color = GlobalColors.textColor,
                                    shape = RoundedCornerShape(10.dp)
                                )
                                .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                                .size(50.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = GlobalColors.textColor,
                            )
                        }
                    }
                },
                title = {
                    Text(
                        "Settings",
                        style = myTextStyle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GlobalColors.primaryColor,
                    titleContentColor = GlobalColors.textColor
                )
            )
        },
        containerColor = GlobalColors.primaryColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(GlobalColors.primaryColor)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Account",
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = GlobalColors.textColor
            )

            Column(
                modifier = Modifier
                    .background(GlobalColors.secondaryColor, RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Text(
                            text = global.loggedinuser.value,
                            style = TextStyle(color = GlobalColors.textColor),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp

                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = global.loggedinlastname.value,
                            style = TextStyle(color = GlobalColors.textColor),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    IconButton(onClick = { expandedColumn = !expandedColumn }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "edit",
                            tint = GlobalColors.textColor
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
                        if (student != null) {
                            CustomTextField(
                                value = student.registrationID,
                                onValueChange = { },
                                label = ""
                            )
                        }
                        CustomTextField(
                            value = newfirstname,
                            onValueChange = { newfirstname = it },
                            label = "First Name"
                        )
                        CustomTextField(
                            value = newlastname,
                            onValueChange = { newlastname = it },
                            label = "Last Name"
                        )
                        CustomTextField(
                            value = newusername,
                            onValueChange = { newusername = it },
                            label = "Username"
                        )

                        if (newfirstname.isNotEmpty() && newlastname.isNotEmpty() && newusername.isNotEmpty()) {
                            IconButton(onClick = {
                                val updatedStudent = Student(
                                    registrationID = global.loggedinregID.value,
                                    firstName = newfirstname,
                                    lastName = newlastname,
                                    username = newusername,
                                    password = student?.password ?: ""
                                )
                                FileUtil.editStudent(context, updatedStudent)
                                global.loggedinuser.value = newfirstname
                                global.loggedinlastname.value = newlastname
                                global.username.value = newusername
                                studentFound = false
                                expandedColumn = false // Collapse the column after saving
                                Toast.makeText(
                                    context,
                                    "Student updated successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }) {

                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Save",
                                    tint = GlobalColors.textColor
                                )


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
                color = GlobalColors.textColor
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
                                Toast.makeText(
                                    context,
                                    "You have not set your username",
                                    Toast.LENGTH_SHORT
                                ).show()
                                showusername = false // Reset the switch if no username is set
                            }
                        }
                    } else {
                        if (student != null) {
                            //use your name instead of username
                            global.loggedinuser.value = student.firstName

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
            ) {
                Text(
                    "Use Custom Color Pallete",
                    style = myTextStyle,
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { palleteDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.ArrowOutward,
                        contentDescription = "edit",
                        tint = GlobalColors.textColor
                    )


                    if (palleteDialog) {
                        AlertDialog(
                            title = { Text(text = "Colors Palette", style = myTextStyle) },
                            text = {
                                ColorSettings(context,

                                    onsave = {
                                        palleteDialog = false
                                        showrestarting = true
                                    },
                                    onrevert = {
                                        palleteDialog = false
                                        showrestarting = true
                                    })
                            },
                            onDismissRequest = { palleteDialog = true },
                            confirmButton = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    val uriHandler = LocalUriHandler.current
                                    Text(
                                        text = "Choose Color palette ",
                                        color = GlobalColors.tertiaryColor,
                                        style = myTextStyle,
                                        modifier = Modifier.clickable {
                                            uriHandler.openUri("https://colorhunt.co/") // Open URI in browser
                                        }
                                    )
                                    Icon(imageVector = Icons.Outlined.ArrowOutward,
                                        contentDescription = "Choose Color palette",
                                        tint = GlobalColors.tertiaryColor,
                                        modifier = Modifier.clickable {
                                            uriHandler.openUri("https://colorhunt.co/") // Open URI in browser
                                        })
                                }

                            },

                            containerColor = GlobalColors.secondaryColor
                        )
                    }
                    if (showrestarting) {
                        AlertDialog(
                            title = { Text(text = "App refresh required", style = myTextStyle) },
                            text = {
                                Column(
                                    modifier = Modifier,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "The app will refresh for the colors to load properly",
                                        style = myTextStyle
                                    )

                                }

                            },
                            onDismissRequest = { },
                            confirmButton = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Button(
                                        onClick = {

                                            showrestarting = false
                                            navController.navigate("welcome")
                                            Toast.makeText(
                                                context,
                                                "Refreshing screens",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(GlobalColors.primaryColor)
                                    ) {
                                        Text(
                                            text = "Ok",
                                            style = myTextStyle,
                                        )
                                    }
                                }
                            },

                            containerColor = GlobalColors.secondaryColor
                        )
                    }
                }
            }
            Text(
                text = "Security",
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = GlobalColors.textColor
            )
            Column(
                modifier = Modifier
                    .clickable { expandedpassword = !expandedpassword }
                    .background(GlobalColors.secondaryColor, RoundedCornerShape(10.dp))
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Text(
                            text = "Change Password",
                            style = myTextStyle,

                            )
                    }
                }

                AnimatedVisibility(visible = expandedpassword) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CustomTextField(value = oldpassword,
                            onValueChange = { oldpassword = it }, label = "Old Password"
                        )
                        CustomTextField(
                            value = newpassword,
                            onValueChange = { newpassword = it },
                            label = "New Password"
                        )
                        CustomTextField(
                            value = confirmnewpassword,
                            onValueChange = { confirmnewpassword = it },
                            label = "Confirm Password"
                        )
                        Button(
                            onClick = {
                                if (oldpassword.isNotEmpty() && newpassword.isNotEmpty()) {
                                    if (student != null) {
                                        if (student.password == oldpassword && newpassword == confirmnewpassword) {
                                            student.password = newpassword
                                            FileUtil.editStudent(context, student)
                                            student.password = newpassword
                                            Toast.makeText(
                                                context,
                                                "Password Changed",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            expandedpassword = false
                                            newpassword = ""
                                            oldpassword = ""
                                            confirmnewpassword = ""
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Wrong Password",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Student not found",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                } else {
                                    Toast.makeText(context, "Blank Spaces!", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GlobalColors.primaryColor
                            )
                        ) {
                            Text(text = "Save Password", style = myTextStyle)
                        }


                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Privacy",
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = GlobalColors.textColor
            )

            PreferenceItem(label = "Go Anonymous",
                checked = anonymousMode,
                onCheckedChange = { anonymousMode = it })

            if (anonymousMode) {
                global.loggedinuser.value = "Anonymous"
                global.lastname.value = ""

            } else {
                global.loggedinuser.value = student?.firstName ?: ""
                global.lastname.value = student?.lastName ?: ""

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "About",
                style = myTextStyle,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = GlobalColors.textColor
            )

            Text(
                text = "Version 1.2.5",
                style = myTextStyle,
                color = GlobalColors.textColor
            )
            val uriHandler = LocalUriHandler.current
            Text(
                text = "Report Bugs ",
                color = GlobalColors.tertiaryColor,
                style = myTextStyle,
                modifier = Modifier.clickable {
                    uriHandler.openUri("https://wa.me/+254799013845?text=Hi%20Mike") // Open URI in browser
                }
            )
        }
    }
}

@Composable
fun CustomTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        textStyle = myTextStyle,
        modifier = Modifier
            .background(GlobalColors.primaryColor, RoundedCornerShape(10.dp))
            .padding(8.dp)
            .fillMaxWidth(),
        singleLine = true
    ) {
        if (value.isEmpty()) {
            Text(
                text = label,
                color = GlobalColors.textColor.copy(alpha = 0.5f),
                style = myTextStyle
            )
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
            color = GlobalColors.textColor
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = GlobalColors.secondaryColor,
                uncheckedThumbColor = GlobalColors.textColor,
                checkedTrackColor = GlobalColors.tertiaryColor,
                uncheckedTrackColor = GlobalColors.primaryColor
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

package com.app.classportal

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono
import java.util.Locale
import com.app.classportal.CommonComponents as CC

val unselected = Color.Transparent
val center = Offset(0.5f, 0.5f)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController, context: Context) {
    LaunchedEffect(Unit) {
        GlobalColors.currentScheme = GlobalColors.loadColorScheme(context)
    }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val username by remember { mutableStateOf("") }
    var input by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isRegistering by remember { mutableStateOf(true) }
    val addbackbrush = remember {
        mutableStateOf(
            Brush.verticalGradient(
                colors = listOf(
                    GlobalColors.primaryColor,
                    GlobalColors.secondaryColor,
                    GlobalColors.primaryColor
                )
            )
        )
    }.value

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = if (isRegistering) "   Register" else "   Login",
                    fontFamily = RobotoMono,
                    color = GlobalColors.textColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = GlobalColors.primaryColor)
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(addbackbrush),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxWidth()
                    .height(200.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isRegistering) "Register as one of the following" else "Login as one of the following",
                    fontFamily = RobotoMono,
                    color = GlobalColors.textColor,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {

                    Box(modifier = Modifier

                        .border(
                            width = 1.dp,
                            color = GlobalColors.textColor,
                            shape = RoundedCornerShape(10.dp)

                        )
                        .clickable {
                            global.selectedcategory.value = "Class Rep"

                        }
                        .background(
                            if (global.selectedcategory.value == "Class Rep") GlobalColors.primaryColor else unselected,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .fillMaxHeight()
                        .width(130.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Class Rep",
                            fontFamily = RobotoMono,
                            color = GlobalColors.textColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                        )
                    }
                    Box(modifier = Modifier
                        .clickable {
                            global.selectedcategory.value = "Student"
                        }
                        .background(
                            if (global.selectedcategory.value == "Student") GlobalColors.primaryColor else unselected,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = GlobalColors.textColor,
                            shape = RoundedCornerShape(10.dp)

                        )
                        .fillMaxHeight()
                        .width(130.dp), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Student",
                            fontFamily = RobotoMono,
                            color = GlobalColors.textColor,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                        )
                    }

                }

            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isRegistering) 300.dp else 200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                AnimatedVisibility(visible = isRegistering) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,

                        ) {
                        CC.SingleLinedTextField(
                            value = firstName,
                            onValueChange = { firstName = it },
                            label = "First Name",
                            singleLine = true,
                            modifier = Modifier.width(130.dp)
                        )
                        CC.SingleLinedTextField(
                            value = lastName,
                            onValueChange = {lastName = it},
                            label = "Last Name",
                            singleLine = true,
                            modifier = Modifier.width(130.dp)
                        )
                    }
                }

                CC.SingleLinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = "Username or Registration ID",
                    singleLine = true
                )
                CC.PasswordTextField(
                    value = password, onValueChange = { password = it }, label = "Password"
                )
                AnimatedVisibility(visible = !isRegistering) {
                    Row(
                        modifier = Modifier.width(300.dp), horizontalArrangement = Arrangement.End
                    ) {
                        Text("Forgot Password?", style = myTextStyle)
                        Text(
                            " Reset",
                            modifier = Modifier
                                .absolutePadding(0.dp, 0.dp, 20.dp, 0.dp)
                                .clickable { navController.navigate("password") },
                            style = myTextStyle,
                            color = GlobalColors.tertiaryColor
                        )
                    }
                }

                AnimatedVisibility(visible = isRegistering) {
                    CC.PasswordTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = "Confirm Password"
                    )

                }
            }

            Row(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        if (isRegistering) {
                            //check if any of the fields are empty
                            if (input.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty()) {
                                if (password == confirmPassword) {
                                    val students = FileUtil.loadStudents(context)

                                    // Check if regID already exists
                                    if (students.any { it.registrationID == input }) {
                                        Toast.makeText(context, "${
                                            global.selectedcategory.value.replaceFirstChar {
                                                if (it.isLowerCase()) it.titlecase(
                                                    Locale.ROOT
                                                ) else it.toString()
                                            }
                                        } ID already exists", Toast.LENGTH_SHORT).show()
                                    } else {
                                        val updatedStudents = students.toMutableList()
                                        updatedStudents.add(
                                            Student(
                                                registrationID = input,
                                                firstName = firstName,
                                                lastName = lastName,
                                                username = username,
                                                password = password
                                            )
                                        )

                                        FileUtil.saveStudents(context, updatedStudents)
                                        Toast.makeText(context,
                                            "${
                                                global.selectedcategory.value.replaceFirstChar {
                                                    if (it.isLowerCase()) it.titlecase(
                                                        Locale.ROOT
                                                    ) else it.toString()
                                                }
                                            } registered successfully! Login to continue",
                                            Toast.LENGTH_SHORT).show()
                                        isRegistering = !isRegistering
                                    }
                                } else {
                                    Toast.makeText(
                                        context, "Passwords do not match", Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } else {
                                Toast.makeText(context, "Please enter a valid ${
                                    global.selectedcategory.value.replaceFirstChar {
                                        if (it.isLowerCase()) it.titlecase(
                                            Locale.ROOT
                                        ) else it.toString()
                                    }
                                } ID and fill in all fields", Toast.LENGTH_SHORT).show()
                            }
                        } else { // Login logic
                            val students = FileUtil.loadStudents(context)
                            val student =
                                students.find { it.registrationID == input || it.username == input }


                            // Check if student exists and credentials match
                            if (student != null) {
                                when {
                                    password.isBlank() -> {
                                        Toast.makeText(
                                            navController.context,
                                            "Password cannot be blank",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    password != student.password -> {
                                        Toast.makeText(
                                            navController.context,
                                            "Invalid credentials",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                    else -> {
                                        Toast.makeText(
                                            navController.context,
                                            "Logged in successfully",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        global.loggedinuser.value = student.firstName
                                        global.loggedinlastname.value = student.lastName
                                        global.loggedinregID.value = student.registrationID
                                        navController.navigate("dashboard") // Navigate after successful login
                                    }
                                }
                            } else {
                                Toast.makeText(
                                    navController.context,
                                    "Username or Registration ID does not exist",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GlobalColors.primaryColor,

                        )

                ) {
                    LaunchedEffect(Unit) {
                        GlobalColors.currentScheme = GlobalColors.loadColorScheme(context)
                    }

                    Text(
                        text = if (isRegistering) "Register" else "Login",
                        style = myTextStyle,

                        )
                }
            }

            Column(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LaunchedEffect(Unit) {
                        GlobalColors.currentScheme = GlobalColors.loadColorScheme(context)
                    }
                    Text(
                        text = if (isRegistering) "Already have an account? " else "Don't have an account? ",
                        style = myTextStyle,
                    )
                    LaunchedEffect(Unit) {
                        GlobalColors.currentScheme = GlobalColors.loadColorScheme(context)
                    }
                    Text(

                        text = if (isRegistering) "Login" else "Register",
                        style = myTextStyle,
                        color = GlobalColors.tertiaryColor,
                        modifier = Modifier.clickable {
                            isRegistering = !isRegistering
                        })
                }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LaunchedEffect(Unit) {
                        GlobalColors.currentScheme = GlobalColors.loadColorScheme(context)
                    }
                    val text = if (isRegistering) "Register" else "Login"
                    val category =
                        if (global.selectedcategory.value == "Student") "Class Rep" else "Student"
                    LaunchedEffect(Unit) {
                        GlobalColors.currentScheme = GlobalColors.loadColorScheme(context)
                    }
                    Text(text = "$text as a $category? ", style = myTextStyle)
                    Text(text = "Click here",
                        style = myTextStyle,
                        color = GlobalColors.tertiaryColor,
                        modifier = Modifier.clickable {
                            global.selectedcategory.value = category
                        })
                }

            }

            Text("Developed by Mike", style = myTextStyle, fontSize = 10.sp)
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController(), LocalContext.current)
}


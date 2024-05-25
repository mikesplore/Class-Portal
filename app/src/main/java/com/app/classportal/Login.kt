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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono
import java.util.Locale


val brush = Brush.linearGradient(
    listOf(
        Color(0xff850F8D),
        Color(0xffC738BD)
    )
)
val focused = Color(0xff850F8D)
val unfocused = Color.Gray
val unselected  = Color.Transparent

val gradientColors = listOf(
    Color(0xff850F8D),
    Color(0xffC738BD)
)
val center = Offset(0.5f, 0.5f)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavController,context: Context) {
    var password by remember { mutableStateOf(TextFieldValue()) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue()) }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    var isRegistering by remember { mutableStateOf(false) }
    val pattern = Regex("^[A-Za-z]{4}/\\d{3}[A-Za-z]/\\d{4}$")
    val boxselected = remember { mutableStateOf(false) }
Scaffold(
    topBar = {
        TopAppBar(
            title = { Text(text = if (isRegistering) "   Register" else "   Login",
                fontFamily = RobotoMono,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp)},
            navigationIcon = {
                IconButton(onClick = { navController.navigate("welcome") },
                    modifier = Modifier.absolutePadding(left = 10.dp)) {
                    Box(modifier = Modifier

                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                        .size(50.dp),
                        contentAlignment = Alignment.Center){
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = Color.White,
                        )
                    }

                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
        )
    }
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
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
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ){

                Box(modifier = Modifier

                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)

                    )
                    .clickable {
                        global.selectedcategory.value = "Class Rep"

                    }
                    .background(if(global.selectedcategory.value == "Class Rep") focused else unselected, shape = RoundedCornerShape(10.dp))
                    .fillMaxHeight()
                    .width(130.dp),
                    contentAlignment = Alignment.Center){
                    Text(
                        text = "Class Rep",
                        fontFamily = RobotoMono,
                        color = Color.White,
                        fontWeight = FontWeight.Normal,
                        fontSize = 15.sp,
                    )
                }
                Box(modifier = Modifier
                    .clickable {
                        global.selectedcategory.value = "student"

                    }
                    .background(if(global.selectedcategory.value == "student") focused else unselected, shape = RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)

                    )
                    .fillMaxHeight()
                    .width(130.dp),
                    contentAlignment = Alignment.Center){
                    Text(
                        text = "Student",
                        fontFamily = RobotoMono,
                        color = Color.White,
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
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,

                    ) {
                    OutlinedTextField(
                        value = global.firstname.value,
                        textStyle = TextStyle(fontFamily = RobotoMono),
                        onValueChange = { global.firstname.value = it },
                        label = {
                            Text(
                                text = "First Name",
                                fontFamily = RobotoMono,
                                color = Color.White,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = focused,
                            unfocusedIndicatorColor = unfocused,
                            focusedLabelColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,

                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .height(60.dp)
                            .width(130.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
                    OutlinedTextField(
                        value = global.lastname.value,
                        textStyle = TextStyle(fontFamily = RobotoMono),
                        onValueChange = { global.lastname.value = it },
                        label = {
                            Text(
                                text = "Last Name",
                                fontFamily = RobotoMono,
                                color = Color.White,
                                fontWeight = FontWeight.Normal,
                                fontSize = 15.sp
                            )
                        },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = focused,
                            unfocusedIndicatorColor = unfocused,
                            focusedLabelColor = Color.White,
                            cursorColor = Color.White,
                            unfocusedLabelColor = Color.White,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,

                            ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier
                            .height(60.dp)
                            .width(130.dp)
                            .shadow(
                                elevation = 10.dp,
                                shape = RoundedCornerShape(20.dp)
                            )
                    )
            }
        }
            OutlinedTextField(
                value = global.regID.value,
                textStyle = TextStyle(fontFamily = RobotoMono),
                onValueChange = { global.regID.value = it },
                label = { Text(text = "Registration ID", fontFamily = RobotoMono) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedIndicatorColor = focused,
                    unfocusedIndicatorColor = unfocused,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(300.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp),

                        )
            )

            OutlinedTextField(
                value = password,
                textStyle = TextStyle(fontFamily = RobotoMono),
                onValueChange = { password = it },
                label = { Text(text = "Password", fontFamily = RobotoMono) },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            tint = color4,
                            contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Black,
                    unfocusedContainerColor = Color.Black,
                    focusedIndicatorColor = focused,
                    unfocusedIndicatorColor = unfocused,
                    focusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(300.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp),

                        )
            )

            AnimatedVisibility(visible = isRegistering) {
                OutlinedTextField(
                    value = confirmPassword,
                    textStyle = TextStyle(fontFamily = RobotoMono),
                    onValueChange = { confirmPassword = it },
                    label = { Text(text = "Confirm Password", fontFamily = RobotoMono) },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            confirmPasswordVisibility = !confirmPasswordVisibility
                        }) {
                            Icon(
                                imageVector = if (confirmPasswordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                tint = color4,
                                contentDescription = if (confirmPasswordVisibility) "Hide password" else "Show password"
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Black,
                        unfocusedContainerColor = Color.Black,
                        focusedIndicatorColor = focused,
                        unfocusedIndicatorColor = unfocused,
                        focusedLabelColor = Color.White,
                        cursorColor = Color.White,
                        unfocusedLabelColor = Color.White,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),

                            )
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
                        if (global.regID.value.isNotEmpty() && password.text.isNotEmpty() && confirmPassword.text.isNotEmpty() && global.firstname.value.isNotEmpty() && pattern.matches(
                                global.regID.value
                            ) && password.text == global.regID.value
                        ) {
                            if (password.text == confirmPassword.text) {
                                val students = FileUtil.loadStudents(context)

                                // Check if regID already exists
                                if (students.any { it.registrationID == global.regID.value }) {
                                    Toast.makeText(
                                        context,
                                        "${global.selectedcategory.value.capitalize(Locale.ROOT)} ID already exists",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    val updatedStudents = students.toMutableList()
                                    updatedStudents.add(
                                        Student(
                                            registrationID = global.regID.value,
                                            firstName = global.firstname.value,
                                            lastName = global.lastname.value,

                                        )
                                    )
                                    FileUtil.saveStudents(context, updatedStudents)
                                    Toast.makeText(
                                        context,
                                        "${global.selectedcategory.value.capitalize(Locale.ROOT)} registered successfully! Login to continue",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    isRegistering = !isRegistering
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Passwords do not match",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                context,
                                "Please enter a valid ${
                                    global.selectedcategory.value.capitalize(Locale.ROOT)
                                } ID and fill in all fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else { // Login logic
                        val students = FileUtil.loadStudents(context)
                        val student =
                            students.find { it.registrationID == global.regID.value } // Use regID.text directly

                        // Check if student exists and credentials match
                        if (student != null && password.text == student.registrationID && pattern.matches(
                                global.regID.value
                            )
                        ) {
                            Toast.makeText(
                                navController.context,
                                "Logged in successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            global.firstname.value =
                                global.firstname.value // Update global username
                            navController.navigate("dashboard") // Navigate after successful login
                        } else {
                            Toast.makeText(
                                navController.context,
                                "Invalid credentials or ${
                                    global.selectedcategory.value.capitalize(Locale.ROOT)
                                } not found or Blank spaces",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                modifier = Modifier
                    .width(350.dp)
                    .height(70.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp),
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                )





            ) {
                Row(modifier = Modifier
                    .background(brush, shape = RoundedCornerShape(10.dp))
                    .height(50.dp)
                    .width(300.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = if (isRegistering) "Register" else "Login",
                    color = color4,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = RobotoMono
                )}
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
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isRegistering) "Already have an account? " else "Don't have an account? ",
                    fontFamily = RobotoMono,
                    color = color4
                )
                Text(
                    text = if (isRegistering) "Login" else "Register",
                    color = textcolor,
                    fontFamily = RobotoMono,
                    modifier = Modifier.clickable {
                        isRegistering = !isRegistering
                    }
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val text = if (isRegistering) "Register" else "Login"
                val category =
                    if (global.selectedcategory.value == "student") "Class Rep" else "student"
                Text(text = "$text as a $category? ", fontFamily = RobotoMono, color = color4)
                Text(
                    text = "Click here",
                    fontFamily = RobotoMono,
                    color = textcolor,
                    modifier = Modifier.clickable {
                        global.selectedcategory.value = category
                    }
                )
            }
        }
    }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController(),LocalContext.current)
}


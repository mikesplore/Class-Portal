package com.app.fitnessapp

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.app.fitnessapp.ui.theme.RobotoMono
import java.util.Locale

@Composable
fun LoginScreen(navController: NavController,context: Context) {
    var registrationID by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue()) }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    var isRegistering by remember { mutableStateOf(false) }
    var firstname by remember { mutableStateOf(TextFieldValue()) }
    val pattern = Regex("^[A-Za-z]{4}/\\d{3}[A-Za-z]/\\d{4}$")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color1),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = if(global.selectedcategory.value == "student") painterResource(id = R.drawable.student) else painterResource(id = R.drawable.teacher),
                    contentDescription = "teacher",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = if (isRegistering) "Register as a ${global.selectedcategory.value.capitalize(
                        Locale.ROOT)}" else "Login as a ${global.selectedcategory.value.capitalize(
                        Locale.ROOT)}",
                    color = color4,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = RobotoMono
                )
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
                TextField(
                    value = firstname,
                    textStyle = TextStyle(fontFamily = RobotoMono),
                    onValueChange = { firstname = it },
                    label = { Text(text = "First Name", fontFamily = RobotoMono) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = color2,
                        unfocusedContainerColor = color2,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = color4,
                        cursorColor = color4,
                        unfocusedLabelColor = color4,
                        focusedTextColor = color4,
                        unfocusedTextColor = color4
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(20.dp),

                            )
                )
            }
            TextField(
                value = registrationID,
                textStyle = TextStyle(fontFamily = RobotoMono),
                onValueChange = { registrationID = it },
                label = { Text(text = "Registration ID", fontFamily = RobotoMono) },
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = color2,
                    unfocusedContainerColor = color2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = color4,
                    cursorColor = color4,
                    unfocusedLabelColor = color4,
                    focusedTextColor = color4,
                    unfocusedTextColor = color4
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp),

                    )
            )

            TextField(
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
                    focusedContainerColor = color2,
                    unfocusedContainerColor = color2,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLabelColor = color4,
                    cursorColor = color4,
                    unfocusedLabelColor = color4,
                    focusedTextColor = color4,
                    unfocusedTextColor = color4
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp),

                        )
            )

            AnimatedVisibility(visible = isRegistering) {
                TextField(
                    value = confirmPassword,
                    textStyle = TextStyle(fontFamily = RobotoMono),
                    onValueChange = { confirmPassword = it },
                    label = { Text(text = "Confirm Password", fontFamily = RobotoMono) },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisibility = !confirmPasswordVisibility }) {
                            Icon(
                                imageVector = if (confirmPasswordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                tint = color4,
                                contentDescription = if (confirmPasswordVisibility) "Hide password" else "Show password"
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = color2,
                        unfocusedContainerColor = color2,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = color4,
                        cursorColor = color4,
                        unfocusedLabelColor = color4,
                        focusedTextColor = color4,
                        unfocusedTextColor = color4
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
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
                        // Registration logic
                        if (registrationID.text.isNotEmpty() &&  password.text.isNotEmpty() && confirmPassword.text.isNotEmpty() && firstname.text.isNotEmpty() && pattern.matches(registrationID.text))
                        {
                            if (password.text == confirmPassword.text) {
                             Toast.makeText(context, "${global.selectedcategory.value.capitalize(Locale.ROOT)} registered successfully! Login to continue",
                             Toast.LENGTH_SHORT).show()
                            isRegistering = !isRegistering}
                            else{
                                Toast.makeText(context, "Passwords do not match",
                                Toast.LENGTH_SHORT).show()
                            }
                        }else{
                            Toast.makeText(context, "Please enter a valid ${global.selectedcategory.value.capitalize(Locale.ROOT)} ID and fill in all fields",
                            Toast.LENGTH_SHORT).show()
                        }



                        } else {
                            // Login logic
                            // Proceed with login
                            if(registrationID.text.isNotEmpty() && password.text.isNotEmpty() && pattern.matches(registrationID.text)){
                            Toast.makeText(navController.context, "Logged in successfully",
                            Toast.LENGTH_SHORT).show()
                                global.username.value = firstname.text
                            navController.navigate("dashboard")}
                        else{
                            Toast.makeText(navController.context, "Please enter a valid ${global.selectedcategory.value.capitalize(Locale.ROOT)} ID and dont leave blank space",
                            Toast.LENGTH_SHORT).show()
                        }
                        }
                },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp),
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = textcolor)
            ) {
                Text(
                    text = if (isRegistering) "Register" else "Login",
                    color = color4,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = RobotoMono
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
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = if (isRegistering) "Already have an account? " else "Don't have an account? ", fontFamily = RobotoMono, color = color4)
                Text(
                    text = if (isRegistering) "Sign in" else "Register",
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
                val category = if (global.selectedcategory.value == "student") "teacher" else "student"
                Text(text = "Register as a $category? ", fontFamily = RobotoMono, color = color4)
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

@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController(),LocalContext.current)
}


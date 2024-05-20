package com.app.fitnessapp

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Visibility
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

@Composable
fun TeacherLogin(navController: NavController) {
    var email by remember { mutableStateOf("") }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(color1),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)

        ) {
            Column(
                modifier = Modifier
                    .background(Color.Transparent, shape = RoundedCornerShape(20.dp))
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Image(
                    painter = painterResource(id = R.drawable.teacher),
                    contentDescription = "teacher",
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Sign in as Teacher",
                    color = color4,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = RobotoMono
                )
            }

        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween) {
            TextField(value = email, textStyle = TextStyle(fontFamily = RobotoMono),
                onValueChange = {
                email = it  },
                label = {Text(text = "Email", fontFamily = RobotoMono)},
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = color4,
                    unfocusedIndicatorColor = color4,
                    focusedTextColor = color4,
                    unfocusedTextColor = color4,
                    focusedLabelColor = color4,
                    unfocusedLabelColor = color4

                )
            )

            var password by remember { mutableStateOf(TextFieldValue()) }
            var passwordVisibility by remember { mutableStateOf(false) }

            TextField(
                value = password,textStyle = TextStyle(fontFamily = RobotoMono),
                onValueChange = { password = it },
                label = { Text(text = "Password", fontFamily = RobotoMono) },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = if (passwordVisibility) Icons.Filled.Clear else Icons.Filled.Visibility,
                            tint = color4,
                            contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = color4,
                    unfocusedIndicatorColor = color4,
                    focusedTextColor = color4,
                    unfocusedTextColor = color4,
                    focusedLabelColor = color4,
                    unfocusedLabelColor = color4
                )
            )


        }
        Row (modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Button(onClick = { navController.navigate("teacherdashboard") },
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp)
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(20.dp),
                    ),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = color2)
                ) {
                Text(text = "Sign in",
                    color = color4,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    fontFamily = RobotoMono
                    )
                
            }
        }
        Column(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Don't have an account? ", fontFamily = RobotoMono,color = color4)
            Text(text = "Register",
                color = textcolor,
                fontFamily = RobotoMono,
                modifier = Modifier.clickable{
                    navController.navigate("teacherregister")
                })}
            Column(modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Are you a student? ", fontFamily = RobotoMono,color = color4)
                Text(text = "Click here", fontFamily = RobotoMono,
                    color = textcolor,
                    modifier = Modifier.clickable{
                        navController.navigate("studentlogin")
                    }

                )
            }


        }

    }

}

@Preview
@Composable
fun TeacherloginPreview(){
    TeacherLogin(rememberNavController())


}
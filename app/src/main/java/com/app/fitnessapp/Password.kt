package com.app.fitnessapp

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.fitnessapp.ui.theme.RobotoMono
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PasswordScreen() {
    Scaffold(
        topBar = { NotificationTopAppBar() },
        containerColor = background,
        content = {
            Column(modifier = Modifier
                .fillMaxSize()) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)) {
                    Column(
                        modifier = Modifier
                            .padding(top = 50.dp)
                            .height(150.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Step 2/7",
                            color = color,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = RobotoMono
                        )
                        Column {
                            Text(
                                text = "Set your password",
                                color = Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 25.sp,
                                fontFamily = RobotoMono,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }
                Box(modifier = Modifier
                    .height(50.dp)
                    .fillMaxWidth()){
                    PasswordInputField()

                }

            }

        })
}

@Composable
fun PasswordInputField() {
    var password by remember { mutableStateOf("Michael") }
    var passwordVisible by remember { mutableStateOf(false) }

    val trailingIcon = @Composable {
        val image = if (passwordVisible) {
            Icons.Filled.Visibility
        } else {
            Icons.Filled.VisibilityOff
        }

        IconButton(onClick = { passwordVisible = !passwordVisible }) {
            Icon(imageVector = image, contentDescription = null)
        }
    }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Password") },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = trailingIcon,
        singleLine = true,
        modifier = Modifier
            .width(300.dp)
            .height(50.dp)
    )
}






@Preview
@Composable
fun PasswordScreenPreview() {
    PasswordScreen()
}

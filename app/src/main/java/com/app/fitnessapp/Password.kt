package com.app.fitnessapp

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.ui.draw.shadow
import androidx.compose.material.*
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.TextStyle


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PasswordScreen() {
    Scaffold(
        topBar = { NotificationTopAppBar() },
        containerColor = background,
        content = {
            Column(modifier = Modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround) {
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
                            text = "Step 3/7",
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
                PasswordInputField()

                Column {
                    Text(text = "Your password should match the rules stated above. You can exceed the number of characters specified",
                        color = DarkGray,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        fontFamily = RobotoMono,
                        textAlign = TextAlign.Center)
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(300.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(color)
                    ) {
                        Text(text = "Continue",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            fontFamily = RobotoMono)
                        
                    }
                    Text(text = "Generate Strong Password",
                        color = color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        fontFamily = RobotoMono)
                }




            }

        })
}


@Composable
fun PasswordInputField() {
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val passwordRules = listOf(
        "8 characters",
        "Uppercase letter",
        "Lowercase letter",
        "A digit"
    )

    val conditionsMet = listOf(
        password.length >= 8,
        password.any { it.isUpperCase() },
        password.any { it.isLowerCase() },
        password.any { it.isDigit() }
    )

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

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = trailingIcon,
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
            ),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color(0xffA0E9FF),
                focusedContainerColor = Color(0xffA0E9FF),
                disabledContainerColor = Color(0xffA0E9FF)
            ),
            modifier = Modifier
                .height(50.dp)
                .width(300.dp)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(20.dp)
                )
        )

        Spacer(modifier = Modifier.height(8.dp))

        val progress = conditionsMet.count { it } / 4f
        val progressColor = if (progress == 1f) color else color

        LinearProgressIndicator(
            trackColor = Color(0xffA0E9FF),
            progress = progress,
            color = progressColor,
            modifier = Modifier

                .clip(RoundedCornerShape(10.dp))
                .width(300.dp)
                .height(10.dp)
        )


        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .height(100.dp)
                .width(300.dp),
            contentAlignment = Alignment.Center

        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = DarkGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .fillMaxHeight()
                        .weight(1f),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.End) {
                        passwordRules.subList(0, 2).forEachIndexed { index, rule ->
                            val conditionMet = conditionsMet[index]
                            RuleItem(rule, conditionMet)
                        }
                    }

                    Column(modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = DarkGray,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .fillMaxHeight()
                        .weight(1f),
                        verticalArrangement = Arrangement.SpaceEvenly) {
                        passwordRules.subList(2, 4).forEachIndexed { index, rule ->
                            val conditionMet = conditionsMet[index + 2]
                            RuleItem(rule, conditionMet)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RuleItem(rule: String, conditionMet: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = rule,
            fontSize = 14.sp,
            color = Black,
            fontWeight = FontWeight.Normal,
            fontFamily = RobotoMono,
            modifier = Modifier.weight(1f)
        )
        if (conditionMet) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}






@Preview
@Composable
fun PasswordScreenPreview() {
    PasswordScreen()
}

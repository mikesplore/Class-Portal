package com.app.fitnessapp

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GenderScreen(navController: NavController,context: Context) {
    var selectedMale by remember { mutableStateOf(false) }
    var selectedFemale by remember { mutableStateOf(false) }
    var showgaydialogue by remember { mutableStateOf(false) }

    val maleBackground: Color = if (selectedMale) Color(0xff00A9FF) else Color(0xffA0E9FF)
    val femaleBackground: Color = if (selectedFemale) Color(0xffFF00A9) else Color(0xffE9A0FF)


    Scaffold(
        topBar = { NotificationTopAppBar() },
        containerColor = color1,
        content = {
            Column(modifier = Modifier
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly) {
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
                            text = "Which one are you?",
                            color = color4,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            fontFamily = RobotoMono,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .absolutePadding(20.dp, 20.dp, 20.dp, 0.dp)
                        .fillMaxWidth()
                        .height(230.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .shadow(
                                elevation = 5.dp,
                                shape = RoundedCornerShape(10.dp),
                                clip = true,
                                ambientColor = Black,
                                spotColor = Black
                            )
                            .background(maleBackground, RoundedCornerShape(10.dp))
                            .border(
                                border = BorderStroke(2.dp, color = Black),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                selectedMale = !selectedMale
                                if (selectedMale) selectedFemale = false
                            }
                            .padding(20.dp)
                    ) {
                        Image(painter = painterResource(id = R.drawable.male), contentDescription = "male")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .shadow(
                                elevation = 5.dp,
                                shape = RoundedCornerShape(10.dp),
                                clip = true,
                                ambientColor = Black,
                                spotColor = Black
                            )
                            .fillMaxHeight()
                            .background(femaleBackground, RoundedCornerShape(10.dp))
                            .border(
                                border = BorderStroke(2.dp, color = Black),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                selectedFemale = !selectedFemale
                                if (selectedFemale) selectedMale = false
                            }
                            .padding(20.dp)
                    ) {
                        Image(painter = painterResource(id = R.drawable.female), contentDescription = "female")

                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = "Male",
                        fontFamily = RobotoMono,
                        fontSize = 22.sp,
                        color = maleBackground,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = "Female",
                        fontFamily = RobotoMono,
                        fontSize = 22.sp,
                        color = femaleBackground,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = "To give you a customer experience, we need to know your gender",
                        fontFamily = RobotoMono,
                        fontSize = 22.sp,
                        color = color3,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                        )
                }

                Button(onClick = {
                    Toast.makeText(context, "registration complete!", Toast.LENGTH_SHORT).show()
                    navController.navigate("logincategory") },
                    modifier = Modifier
                        .shadow(
                            elevation = 5.dp,
                            shape = RoundedCornerShape(10.dp),
                            clip = true,
                            ambientColor = Black,
                            spotColor = Black
                        )
                        .height(50.dp)
                        .width(250.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(color)) {
                    Text(text = "Continue",
                        fontFamily = RobotoMono,
                        fontSize = 15.sp,
                        color = color4,
                        fontWeight = FontWeight.Bold)}

                Text(text = "Prefer not to choose (GAY)",
                    fontFamily = RobotoMono,
                    fontSize = 15.sp,
                    color = color,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .clickable {
                        showgaydialogue = !showgaydialogue

                        }
                        .padding(bottom = 50.dp)
                    )
            }
        }
        

    )
    val questions = listOf(
        "Why are you gay?",
        "Are you gay?",
        "Should I call you Mr?",
        "You said you are gay?",
        "So I should call you gay?",
        "Are you gay?",
        "Sure?",
        "Really sure?"
    )

    var currentQuestion by remember { mutableStateOf(questions.random()) }

    fun shuffleQuestion() {
        currentQuestion = questions.random()
    }

    if (showgaydialogue) {
        AlertDialog(
            onDismissRequest = { showgaydialogue = false },
            confirmButton = {
                Button(onClick = {
                    shuffleQuestion()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = {
                    shuffleQuestion()
                }) {
                    Text("No")
                }
            },
            title = { Text("Confirmation") },
            text = { Text(currentQuestion) }
        )
    }

}




@Preview
@Composable
fun GenderScreenPreview() {
    GenderScreen(rememberNavController(), context = LocalContext.current)
}

@Composable
fun GayPlay(show: MutableState<Boolean>) {
    val questions = listOf(
        "Why are you gay?",
        "Are you gay?",
        "Should I call you Mr?",
        "You said you are gay?",
        "So I should call you gay?",
        "Are you gay?",
        "Are you gay?",
    )

    var currentQuestion by remember { mutableStateOf(questions.random()) }

    fun shuffleQuestion() {
        currentQuestion = questions.random()
    }

    if (show.value) {
        AlertDialog(
            onDismissRequest = { show.value = false },
            confirmButton = {
                Button(onClick = {
                    shuffleQuestion()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = {
                    shuffleQuestion()
                }) {
                    Text("No")
                }
            },
            title = { Text("Confirmation") },
            text = { Text(currentQuestion) }
        )
    }
}

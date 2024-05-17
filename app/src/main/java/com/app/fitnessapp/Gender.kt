package com.app.fitnessapp

import android.annotation.SuppressLint
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.fitnessapp.ui.theme.RobotoMono

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GenderScreen() {
    var selectedMale by remember { mutableStateOf(false) }
    var selectedFemale by remember { mutableStateOf(false) }

    val maleBackground: Color = if (selectedMale) Color(0xff00A9FF) else Color(0xffA0E9FF)
    val femaleBackground: Color = if (selectedFemale) Color(0xff00A9FF) else Color(0xffA0E9FF)

    Scaffold(
        topBar = { NotificationTopAppBar() },
        containerColor = background,
        content = {
            Column(modifier = Modifier.fillMaxSize()) {
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
                        fontSize = 12.sp,
                        fontFamily = RobotoMono
                    )
                    Column {
                        Text(
                            text = "Which one are you?",
                            color = Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 25.sp,
                            fontFamily = RobotoMono,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .height(230.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(maleBackground, RoundedCornerShape(10.dp))
                            .border(
                                border = BorderStroke(2.dp, color = Black),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedMale = !selectedMale }
                            .padding(20.dp)
                    ) {
                        Image(painter = painterResource(id = R.drawable.male), contentDescription = "male")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(femaleBackground, RoundedCornerShape(10.dp))
                            .border(
                                border = BorderStroke(2.dp, color = Black),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedFemale = !selectedFemale }
                            .padding(20.dp)
                    ) {
                        Image(painter = painterResource(id = R.drawable.female), contentDescription = "female")

                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween){
                    Text(text = "To give you a customer experience, we need to know your gender",
                        fontFamily = RobotoMono,
                        fontSize = 22.sp,
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                        )
                }

                Button(onClick = { /*TODO*/ }) {

                    
                }
            }
        }
    )
}





@Preview
@Composable
fun GenderScreenPreview() {
    GenderScreen()
}

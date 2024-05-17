package com.app.fitnessapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.fitnessapp.ui.theme.RobotoMono
val color = Color(0xff00A9FF)
@Composable
fun WelcomeScreen(){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Box(modifier = Modifier
                .padding(20.dp)
                .background(Color.White, shape = CircleShape)
                .size(100.dp)){
                Image(painter = painterResource(id = R.drawable.logo), contentDescription = "logo")
            }
        }

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome to",
                fontFamily = RobotoMono,
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 30.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                ) {
                Text(text = "Mike ",
                    fontFamily = RobotoMono,
                    fontSize = 30.sp,
                    color = color,
                    fontWeight = FontWeight.Bold)
                Text(text = "Fitness App",
                    fontFamily = RobotoMono,
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold)
            }
        }

        Column(modifier = Modifier.fillMaxWidth(),) {
            Text(text = "Customer service is our number one  priority Enjoy our service",
                fontFamily = RobotoMono,
                fontSize = 15.sp,
                color = Black,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                )

        }

        Column(modifier = Modifier
            .height(100.dp)
            .fillMaxWidth(),) {


        }

    }

}
@Preview
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen()
}
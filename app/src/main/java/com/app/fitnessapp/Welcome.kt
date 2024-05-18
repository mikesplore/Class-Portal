package com.app.fitnessapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.fitnessapp.ui.theme.RobotoMono
val color = Color(0xff00A9FF)
val background = Color(0xffCDF5FD)
@Composable
fun WelcomeScreen(navController: NavController){
    Column(
        modifier = Modifier
            .background(background)
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {


        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Welcome to",
                fontFamily = RobotoMono,
                fontSize = 30.sp,
                color = color,
                fontWeight = FontWeight.Bold,
            )
            Text(text = "COMPUTER SCIENCE PORTAL APP",
                fontFamily = RobotoMono,
                fontSize = 30.sp,
                color = Black,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,)

            Text(text = "Customer service is our number one  priority Enjoy our service",
                fontFamily = RobotoMono,
                fontSize = 15.sp,
                color = Black,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
            )
        }



        Column(modifier = Modifier
            .height(250.dp)
            .fillMaxWidth(),) {
            Image(painter = painterResource(
                id = R.drawable.donalduck),
                contentDescription = "image",
                modifier = Modifier
                    .fillMaxSize())


        }

        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { /*TODO*/ },
                modifier = Modifier

                    .height(50.dp)
                    .width(250.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(color)) {
                Text(text = "Get Started",
                    fontFamily = RobotoMono,
                    fontSize = 15.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold)

            }

            Row (modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center){
                Text(text = "Already have an account? ",
                    fontFamily = RobotoMono,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal)
                Text(text = "Sign in",
                    fontFamily = RobotoMono,
                    fontSize = 15.sp,
                    color = color,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable {
                        navController.navigate("loginCategory")

                    })

            }

        }

    }

}
@Preview
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen(rememberNavController())
}
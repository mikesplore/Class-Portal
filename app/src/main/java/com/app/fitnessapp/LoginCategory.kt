package com.app.fitnessapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun LoginCategory(navController: NavController){
    Column(modifier = Modifier
        .fillMaxSize()
        .background(background),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(text = "Choose your role",
            color = Color.Black,
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold
            )


        Column(modifier = Modifier
            .height(500.dp)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally) {
            //student part
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate("studentlogin")

                    }
                    .size(200.dp)
                    .shadow(10.dp, shape = RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xffCDF5FD), shape = RoundedCornerShape(20.dp))
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.student),
                        contentDescription = "student",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Student",
                        color = Color.Black,
                        fontSize = 20.sp,
                    )
                }

            }

            //teacher part
            Box(
                modifier = Modifier
                    .clickable {
                        navController.navigate("teacherlogin")

                    }
                    .size(200.dp)
                    .shadow(10.dp, shape = RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xffCDF5FD), shape = RoundedCornerShape(20.dp))
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.teacher),
                        contentDescription = "teacher",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "Teacher",
                        color = Color.Black,
                        fontSize = 20.sp,
                    )
                }

            }

        }
    }

}


@Preview
@Composable
fun LoginPreview(){
    LoginCategory(rememberNavController())
}
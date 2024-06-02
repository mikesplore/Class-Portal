package com.app.classportal

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono
import kotlinx.coroutines.delay
import com.app.classportal.CommonComponents as CC
@SuppressLint("UnrememberedMutableState")
@Composable
fun WelcomeScreen(navController: NavHostController, context: Context) {
    LaunchedEffect(Unit) {
        GlobalColors.currentScheme = GlobalColors.loadColorScheme(context)
    }
    var startAnimation by remember { mutableStateOf(false) }
    var fadeOut by remember { mutableStateOf(false) }
    var showProgress by remember { mutableStateOf(false) } // State to show/hide progress

    //Animation
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation && !fadeOut) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000,
            easing = LinearEasing
        ), label = ""
    )
    //Progress Value
    val progress by animateFloatAsState(
        targetValue = if (showProgress) 1f else 0f,
        animationSpec = tween(6000), label = "Loading"
    )


    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(5000)
        showProgress = true
        delay(3000)
        fadeOut = true
        delay(2000)
        navController.navigate("login") {
            popUpTo("splash") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GlobalColors.primaryColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "CLASS PORTAL",
            style = TextStyle(
                color = GlobalColors.tertiaryColor,
                fontSize = 60.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = RobotoMono,
                textAlign = TextAlign.Center,
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.5f), // Shadow color (semi-transparent black)
                    offset = Offset(4f, 4f), // Shadow offset (x, y)
                    blurRadius = 8f // Shadow blur radius
                )
            ),
            modifier = Modifier.alpha(alphaAnim.value)
        )
        val counter by derivedStateOf { (progress * 100).toInt() }
        AnimatedVisibility(visible = showProgress) {
            Column(modifier = Modifier
                .height(250.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally){
                Box(contentAlignment = Alignment.Center){
                    CircularProgressIndicator(
                        modifier = Modifier.size(70.dp),
                        color = GlobalColors.secondaryColor,
                        strokeWidth = 5.dp,
                        progress = progress
                    )
                    Text(
                        text = "$counter%",
                        style = CC.descriptionTextStyle,
                        fontSize = 18.sp
                    )

                }

                Text(
                    text = when (counter) {
                        0 -> "Loading..."
                        in 1..20 -> "Please wait..."
                        in 21..50 -> "Fetching data..."
                        in 51..80 -> "Almost there..."
                        else -> "Finishing up..."
                    },
                    style = CC.descriptionTextStyle,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    WelcomeScreen(navController = rememberNavController(), context = LocalContext.current)
}
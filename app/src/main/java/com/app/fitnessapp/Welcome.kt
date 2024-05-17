package com.app.fitnessapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun WelcomeScreen(){
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize(),
    ) {

    }

}
@Preview
@Composable
fun WelcomeScreenPreview(){
    WelcomeScreen()
}
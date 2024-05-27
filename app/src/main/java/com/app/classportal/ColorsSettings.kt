package com.app.classportal

import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.classportal.ui.theme.RobotoMono

fun parseColor(hex: String): Color {
    return try {
        Color(android.graphics.Color.parseColor("#$hex"))
    } catch (e: IllegalArgumentException) {
        Color.Unspecified
    }
}

@Composable
fun ColorSettings() {
    var primaryColor by remember { mutableStateOf("003C43") }
    var secondaryColor by remember { mutableStateOf("135D66") }
    var tertiaryColor by remember { mutableStateOf("77B0AA") }
    var textColor by remember { mutableStateOf("E3FEF7") }

    Column(modifier = Modifier.height(350.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
    horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = primaryColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = {
                primaryColor = it
                globalcolors.primaryColor = parseColor(it)
            },
            label = { Text(text = "Primary color", fontFamily = RobotoMono) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = parseColor(primaryColor),
                unfocusedContainerColor = parseColor(primaryColor),
                focusedIndicatorColor = parseColor(primaryColor),
                unfocusedIndicatorColor = parseColor(primaryColor),
                focusedLabelColor = parseColor(textColor),
                cursorColor = parseColor(textColor),
                unfocusedLabelColor = parseColor(textColor),
                focusedTextColor = parseColor(textColor),
                unfocusedTextColor = parseColor(textColor)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier

                .width(300.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp),
                )
        )
        OutlinedTextField(
            value = secondaryColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = {
                secondaryColor = it
                globalcolors.secondaryColor = parseColor(it)
            },
            label = { Text(text = "Secondary color", fontFamily = RobotoMono) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = parseColor(secondaryColor),
                unfocusedContainerColor = parseColor(primaryColor),
                focusedIndicatorColor = parseColor(secondaryColor),
                unfocusedIndicatorColor = parseColor(secondaryColor),
                focusedLabelColor = parseColor(textColor),
                cursorColor = parseColor(textColor),
                unfocusedLabelColor = parseColor(textColor),
                focusedTextColor = parseColor(textColor),
                unfocusedTextColor = parseColor(textColor)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .width(300.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp),
                )
        )
        OutlinedTextField(
            value = tertiaryColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = {
                tertiaryColor = it
                globalcolors.tertiaryColor = parseColor(it)
            },
            label = { Text(text = "Tertiary color", fontFamily = RobotoMono) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = parseColor(secondaryColor),
                unfocusedContainerColor = parseColor(primaryColor),
                focusedIndicatorColor = parseColor(secondaryColor),
                unfocusedIndicatorColor = parseColor(secondaryColor),
                focusedLabelColor = parseColor(textColor),
                cursorColor = parseColor(textColor),
                unfocusedLabelColor = parseColor(textColor),
                focusedTextColor = parseColor(textColor),
                unfocusedTextColor = parseColor(textColor)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .width(300.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp),
                )
        )
        OutlinedTextField(
            value = textColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = {
                textColor = it
                globalcolors.textColor = parseColor(it)
            },
            label = { Text(text = "Text color", fontFamily = RobotoMono) },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = parseColor(secondaryColor),
                unfocusedContainerColor = parseColor(primaryColor),
                focusedIndicatorColor = parseColor(secondaryColor),
                unfocusedIndicatorColor = parseColor(secondaryColor),
                focusedLabelColor = parseColor(textColor),
                cursorColor = parseColor(textColor),
                unfocusedLabelColor = parseColor(textColor),
                focusedTextColor = parseColor(textColor),
                unfocusedTextColor = parseColor(textColor)
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .width(300.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp),
                )
        )
    }
}

// Define global colors
object globalcolors {
    var primaryColor by mutableStateOf(parseColor("003C43"))
    var secondaryColor by mutableStateOf(parseColor("135D66"))
    var tertiaryColor by mutableStateOf(parseColor("77B0AA"))
    var textColor by mutableStateOf(parseColor("E3FEF7"))
}

@Preview
@Composable
fun ColorSettingsPreview() {
    ColorSettings()
}

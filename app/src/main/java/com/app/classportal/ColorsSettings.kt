package com.app.classportal

import android.content.Context
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
import com.google.gson.Gson
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.io.File

data class ColorScheme(
    val primaryColor: String,
    val secondaryColor: String,
    val tertiaryColor: String,
    val textColor: String
)

fun parseColor(hex: String): Color {
    return try {
        Color(android.graphics.Color.parseColor("#$hex"))
    } catch (e: IllegalArgumentException) {
        Color.Unspecified
    }
}


object globalcolors {
    private const val COLORS_FILE_NAME = "color_scheme.json"

    var currentScheme by mutableStateOf(ColorScheme("003C43", "135D66", "77B0AA", "E3FEF7"))

    fun loadColorScheme(context: Context): ColorScheme {
        val file = File(context.filesDir, COLORS_FILE_NAME)
        return if (file.exists()) {
            val json = file.readText()
            Gson().fromJson(json, ColorScheme::class.java)
        } else {
            ColorScheme("003C43", "135D66", "77B0AA", "E3FEF7") // Default colors
        }
    }

    fun saveColorScheme(context: Context, scheme: ColorScheme) {
        val json = Gson().toJson(scheme)
        val file = File(context.filesDir, COLORS_FILE_NAME)
        file.writeText(json)
        currentScheme = scheme
    }

    val primaryColor: Color
        get() = parseColor(currentScheme.primaryColor)

    val secondaryColor: Color
        get() = parseColor(currentScheme.secondaryColor)

    val tertiaryColor: Color
        get() = parseColor(currentScheme.tertiaryColor)

    val textColor: Color
        get() = parseColor(currentScheme.textColor)
}



@Composable
fun ColorSettings() {
    val context = LocalContext.current
    var primaryColor by remember { mutableStateOf(globalcolors.loadColorScheme(context).primaryColor) }
    var secondaryColor by remember { mutableStateOf(globalcolors.loadColorScheme(context).secondaryColor) }
    var tertiaryColor by remember { mutableStateOf(globalcolors.loadColorScheme(context).tertiaryColor) }
    var textColor by remember { mutableStateOf(globalcolors.loadColorScheme(context).textColor) }

    Column(
        modifier = Modifier.height(350.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedColorTextField(
            label = "Primary color",
            colorValue = primaryColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = { newValue ->
                primaryColor = newValue
                val newScheme = globalcolors.currentScheme.copy(primaryColor = newValue)
                globalcolors.saveColorScheme(context, newScheme)
            }
        )

        OutlinedColorTextField(
            label = "Secondary color",
            colorValue = secondaryColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = { newValue ->
                secondaryColor = newValue
                val newScheme = globalcolors.currentScheme.copy(secondaryColor = newValue)
                globalcolors.saveColorScheme(context, newScheme)
            }
        )

        OutlinedColorTextField(
            label = "Tertiary color",
            colorValue = tertiaryColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = { newValue ->
                tertiaryColor = newValue
                val newScheme = globalcolors.currentScheme.copy(tertiaryColor = newValue)
                globalcolors.saveColorScheme(context, newScheme)
            }
        )

        OutlinedColorTextField(
            label = "Text color",
            colorValue = textColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = { newValue ->
                textColor = newValue
                val newScheme = globalcolors.currentScheme.copy(textColor = newValue)
                globalcolors.saveColorScheme(context, newScheme)
            }
        )
    }
}

@Composable
fun OutlinedColorTextField(
    label: String,
    colorValue: String,
    textStyle: TextStyle,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = colorValue,
        textStyle = textStyle,
        onValueChange = onValueChange,
        label = { Text(text = label, fontFamily = RobotoMono) },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = parseColor(colorValue),
            unfocusedContainerColor = parseColor(globalcolors.currentScheme.primaryColor),
            focusedIndicatorColor = parseColor(colorValue),
            unfocusedIndicatorColor = parseColor(colorValue),
            focusedLabelColor = parseColor(globalcolors.currentScheme.textColor),
            cursorColor = parseColor(globalcolors.currentScheme.textColor),
            unfocusedLabelColor = parseColor(globalcolors.currentScheme.textColor),
            focusedTextColor = parseColor(globalcolors.currentScheme.textColor),
            unfocusedTextColor = parseColor(globalcolors.currentScheme.textColor)
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .width(300.dp)
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp)
            )
    )
}

@Preview
@Composable
fun ColorSettingsPreview() {
    ColorSettings()
}







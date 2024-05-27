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

    private val defaultScheme = ColorScheme("003C43", "135D66", "77B0AA", "E3FEF7")

    var currentScheme by mutableStateOf(defaultScheme)

    fun loadColorScheme(context: Context): ColorScheme {
        val file = File(context.filesDir, COLORS_FILE_NAME)
        return if (file.exists()) {
            val json = file.readText()
            Gson().fromJson(json, ColorScheme::class.java)
        } else {
            defaultScheme
        }
    }

    fun saveColorScheme(context: Context, scheme: ColorScheme) {
        val json = Gson().toJson(scheme)
        val file = File(context.filesDir, COLORS_FILE_NAME)
        file.writeText(json)
        currentScheme = scheme
    }

    fun resetToDefaultColors(context: Context) {
        saveColorScheme(context, defaultScheme)
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
fun ColorSettings(context: Context) {
    var primaryColor by remember { mutableStateOf(globalcolors.currentScheme.primaryColor) }
    var secondaryColor by remember { mutableStateOf(globalcolors.currentScheme.secondaryColor) }
    var tertiaryColor by remember { mutableStateOf(globalcolors.currentScheme.tertiaryColor) }
    var textColor by remember { mutableStateOf(globalcolors.currentScheme.textColor) }

    // Listen to changes in global color scheme and update local states
    LaunchedEffect(globalcolors.currentScheme) {
        primaryColor = globalcolors.currentScheme.primaryColor
        secondaryColor = globalcolors.currentScheme.secondaryColor
        tertiaryColor = globalcolors.currentScheme.tertiaryColor
        textColor = globalcolors.currentScheme.textColor
    }

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
            focusedContainerColor = parseColor(globalcolors.currentScheme.primaryColor),
            unfocusedContainerColor = parseColor(globalcolors.currentScheme.primaryColor),
            focusedIndicatorColor = globalcolors.textColor,
            unfocusedIndicatorColor = globalcolors.primaryColor,
            focusedLabelColor = globalcolors.textColor,
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
    val context = LocalContext.current

    // Load the color scheme when the composable is launched
    LaunchedEffect(Unit) {
        globalcolors.currentScheme = globalcolors.loadColorScheme(context)
    }

    ColorSettings(context = context)
}

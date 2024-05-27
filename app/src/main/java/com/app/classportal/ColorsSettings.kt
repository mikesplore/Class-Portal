package com.app.classportal

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.classportal.ui.theme.RobotoMono
import com.google.gson.Gson
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
fun ColorSettings(context: Context, onSave: (ColorScheme) -> Unit, onReset: () -> Unit) {
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
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedColorTextField(
            label = "Primary color",
            colorValue = primaryColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = { newValue -> primaryColor = newValue }
        )

        OutlinedColorTextField(
            label = "Secondary color",
            colorValue = secondaryColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = { newValue -> secondaryColor = newValue }
        )

        OutlinedColorTextField(
            label = "Tertiary color",
            colorValue = tertiaryColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = { newValue -> tertiaryColor = newValue }
        )

        OutlinedColorTextField(
            label = "Text color",
            colorValue = textColor,
            textStyle = TextStyle(fontFamily = RobotoMono),
            onValueChange = { newValue -> textColor = newValue }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onReset() }) {
                Text(text = "Revert to Default")
            }
            Button(onClick = {
                val newScheme = ColorScheme(primaryColor, secondaryColor, tertiaryColor, textColor)
                onSave(newScheme)
            }) {
                Text(text = "Save")
            }
        }
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
            focusedContainerColor = globalcolors.primaryColor,
            unfocusedContainerColor = globalcolors.primaryColor,
            focusedIndicatorColor = globalcolors.tertiaryColor,
            unfocusedIndicatorColor = globalcolors.primaryColor,
            focusedLabelColor = globalcolors.textColor,
            cursorColor = globalcolors.textColor,
            unfocusedLabelColor = globalcolors.textColor,
            focusedTextColor = globalcolors.textColor,
            unfocusedTextColor = globalcolors.textColor
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(context: Context) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Color Settings") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = globalcolors.primaryColor,
                    titleContentColor = globalcolors.textColor
                ),

            )
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                ColorSettings(
                    context = context,
                    onSave = { newScheme ->
                        globalcolors.saveColorScheme(context, newScheme)
                    },
                    onReset = {
                        globalcolors.resetToDefaultColors(context)
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun MainScreenPreview() {
    val context = LocalContext.current

    // Load the color scheme when the composable is launched
    LaunchedEffect(Unit) {
        globalcolors.currentScheme = globalcolors.loadColorScheme(context)
    }

    MainScreen(context = context)
}



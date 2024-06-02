package com.app.classportal.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.app.classportal.R

private val DarkColorScheme = darkColorScheme(
    primary = Darkblue,
    secondary = PaleBlue,
    tertiary = SkyBlue
)

private val LightColorScheme = lightColorScheme(
    primary = Darkblue,
    secondary = PaleBlue,
    tertiary = SkyBlue





/* Other default colors to override
background = Color(0xFFFFFBFE),
surface = Color(0xFFFFFBFE),
onPrimary = Color.White,
onSecondary = Color.White,
onTertiary = Color.White,
onBackground = Color(0xFF1C1B1F),
onSurface = Color(0xFF1C1B1F),
*/
)



val RobotoMono = FontFamily(
    Font(R.font.robotomono_regular, FontWeight.Normal),
    Font(R.font.robotomono_bold, FontWeight.Bold),
    Font(R.font.robotomono_italic, FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.robotomono_light, FontWeight.Light),
    Font(R.font.robotomono_medium, FontWeight.Medium),
    Font(R.font.robotomono_mediumitalic, FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.robotomono_thin, FontWeight.Thin),
    Font(R.font.robotomono_thinitalic, FontWeight.Thin, style = FontStyle.Italic),
    Font(R.font.robotomono_extralight, FontWeight.ExtraLight),
    Font(R.font.robotomono_extralightitalic, FontWeight.ExtraLight, style = FontStyle.Italic),
    Font(R.font.robotomono_semibold, FontWeight.SemiBold),
    Font(R.font.robotomono_semibolditalic, FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.robotomono_bolditalic, FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.robotomono_lightitalic, FontWeight.Light, style = FontStyle.Italic)
)

@Composable
fun ClassPortalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}


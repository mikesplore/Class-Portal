package com.app.classportal


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.classportal.ui.theme.RobotoMono

object CommonComponents {
    @Composable
    fun PasswordTextField(
        modifier: Modifier = Modifier,
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        enabled: Boolean = true,
        isError: Boolean = false
    ) {
        var passwordVisibility by remember { mutableStateOf(false) } // Moved inside function for each instance

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontFamily = RobotoMono),
            label = { Text(text = label, fontFamily = RobotoMono) },
            singleLine = true,
            enabled = enabled,
            isError = isError,
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        tint = GlobalColors.textColor,
                        contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                    )
                }
            },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = GlobalColors.primaryColor,
                unfocusedContainerColor = GlobalColors.primaryColor,
                focusedIndicatorColor = GlobalColors.tertiaryColor,
                unfocusedIndicatorColor = GlobalColors.primaryColor,
                focusedLabelColor = GlobalColors.textColor,
                cursorColor = GlobalColors.textColor,
                unfocusedLabelColor = GlobalColors.textColor,
                focusedTextColor = GlobalColors.textColor,
                unfocusedTextColor = GlobalColors.textColor
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



    @Composable
    fun SingleLinedTextField(
        modifier: Modifier = Modifier,
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        enabled: Boolean = true,
        isError: Boolean = false,
        singleLine: Boolean
    ) {

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(fontFamily = RobotoMono),
            label = { Text(text = label, fontFamily = RobotoMono, fontSize = 14.sp) },
            singleLine = singleLine,
            enabled = enabled,
            isError = isError,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = GlobalColors.primaryColor,
                unfocusedContainerColor = GlobalColors.primaryColor,
                focusedIndicatorColor = GlobalColors.tertiaryColor,
                unfocusedIndicatorColor = GlobalColors.primaryColor,
                focusedLabelColor = GlobalColors.textColor,
                cursorColor = GlobalColors.textColor,
                unfocusedLabelColor = GlobalColors.textColor,
                focusedTextColor = GlobalColors.textColor,
                unfocusedTextColor = GlobalColors.textColor
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

    @Composable
    fun BasicTextField(title: String, onTitleChange: (String) -> Unit,singleLine: Boolean){
        androidx.compose.foundation.text.BasicTextField(
            value = title,
            singleLine = singleLine,
            onValueChange = { onTitleChange },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(fontSize = 16.sp),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(
                            GlobalColors.tertiaryColor,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(8.dp)
                ) {
                    if (title.isEmpty()) {
                        Text("Title", style = descriptionTextStyle())
                    }
                    innerTextField()
                }
            }
        )
    }
    val titleTextStyle = TextStyle(
        fontFamily = RobotoMono,
        color = GlobalColors.textColor,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp
    )

    val descriptionTextStyle = TextStyle(
        fontFamily = RobotoMono,
        color = GlobalColors.textColor,
        fontSize = 15.sp
    )
    val backbrush = Brush.verticalGradient(
        colors = listOf(
        GlobalColors.primaryColor,
        GlobalColors.secondaryColor,
        GlobalColors.primaryColor
            )
        )
    @Composable
    fun Backbrush(globalColors: GlobalColors): Brush {
        val colors = listOf(
            globalColors.primaryColor,
            globalColors.secondaryColor,
            globalColors.primaryColor
        )

        return Brush.verticalGradient(colors)
    }




}



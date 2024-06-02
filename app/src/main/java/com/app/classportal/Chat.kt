package com.app.classportal

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.ui.theme.RobotoMono
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

data class Message(
    val text: String,
    val isSentByCurrentUser: Boolean,
    val senderName: String,
    val timestamp: String
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {
    var messages by remember { mutableStateOf(listOf<Message>()) }
    var currentText by remember { mutableStateOf(TextFieldValue("")) }
    val time = getCurrentTime()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Chat",
                        fontFamily = RobotoMono,
                        color = GlobalColors.textColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("dashboard") },
                        modifier = Modifier.padding(start = 10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back",
                            tint = GlobalColors.textColor,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GlobalColors.primaryColor)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .background(GlobalColors.primaryColor)
                .fillMaxSize()
                .padding(top = 90.dp, start = 5.dp, end = 5.dp)
        ) {
            CurrentDayDisplay()
            Spacer(modifier = Modifier.height(8.dp))

            MessageList(messages)
            Spacer(modifier = Modifier.height(8.dp))

            MessageInput(
                text = currentText,
                onTextChange = { currentText = it },
                onSend = {
                    if (currentText.text.isNotBlank()) {
                        messages = messages + Message(
                            currentText.text, true, global.loggedinlastname.value, time                       )
                        currentText = TextFieldValue("") // Clear the input field
                    }
                }
            )
        }
    }
}

@Composable
fun MessageList(messages: List<Message>) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        messages.forEach { message ->
            MessageBubble(message)
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalAlignment = if (message.isSentByCurrentUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = if (message.isSentByCurrentUser) GlobalColors.tertiaryColor else GlobalColors.secondaryColor,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomEnd = if (message.isSentByCurrentUser) 0.dp else 16.dp,
                        bottomStart = if (message.isSentByCurrentUser) 16.dp else 0.dp
                    )
                )
                .padding(8.dp)
        ) {
            Column() {
                // Sender's name
                if (message.isSentByCurrentUser) {
                    Text(
                        text = "Michael",
                        style = MaterialTheme.typography.bodySmall,
                        color = GlobalColors.primaryColor, // Use appropriate color for contrast
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

                // Message text
                Text(
                    text = "Chat feature coming Soon!",//message.text,
                    color = if (message.isSentByCurrentUser) GlobalColors.primaryColor else GlobalColors.secondaryColor,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(top = 4.dp, bottom = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Timestamp
                Text(
                    text = "1:20 AM",//message.timestamp,
                    style = MaterialTheme.typography.bodySmall,
                    color = GlobalColors.primaryColor,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}




@Composable
fun MessageInput(
    text: TextFieldValue,
    onTextChange: (TextFieldValue) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        BasicTextField(
            value = text,
            textStyle = myTextStyle,
            onValueChange = onTextChange,
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .background(GlobalColors.secondaryColor, shape = MaterialTheme.shapes.small)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = onSend,
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GlobalColors.tertiaryColor
            )
        ) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = GlobalColors.primaryColor)
        }
    }
}

@Composable
fun CurrentDayDisplay() {
    val currentDate = remember { Date() }  // Get the current date once
    val formattedDate = remember(currentDate) {
        SimpleDateFormat("EEEE dd/MM", Locale.getDefault()).format(currentDate)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(30.dp)
                .width(120.dp)
                .background(GlobalColors.secondaryColor, shape = RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = formattedDate,
                style = myTextStyle
            )
        }

    }
}

@Composable
fun getCurrentTime(): String {
    return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
}

@Preview
@Composable
fun ChatScreenPreview() {
ChatScreen(navController = rememberNavController())
}

package com.app.classportal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(navController: NavController) {
    val users = remember { mutableStateListOf<User>() }
    val getUsers = MyDatabase

    LaunchedEffect(key1 = true) {
        getUsers.getAllUsers { fetchedUsers ->
            users.addAll(fetchedUsers)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "User List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff164863),
                    titleContentColor = Color.White
                )
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it)
        ) {
            // Header Row
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Email", fontWeight = FontWeight.Bold)
                    Text("Username", fontWeight = FontWeight.Bold)
                }
            }

            // Data Rows
            itemsIndexed(users) { index, user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            if (index % 2 == 0) Color.LightGray.copy(alpha = 0.2f) else Color.Transparent
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(user.email ?: "Unknown")
                    Text(user.username ?: "Unknown")
                }
            }
        }
    }
}

@Preview
@Composable
fun UserListScreenPreview() {
    UserListScreen(navController = NavController(LocalContext.current))
}


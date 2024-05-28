package com.app.classportal

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.app.classportal.FileUtil.loadUnits
import com.app.classportal.FileUtil.saveUnits
import com.app.classportal.ui.theme.RobotoMono

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageUnitsScreen(context: Context, navController: NavController) {
    var units by remember { mutableStateOf(loadUnits(context)) }
    var newUnitCount by remember { mutableStateOf("") }
    var unitNames by remember { mutableStateOf(List(units.size) { units[it].unitname }) }
    var showmenu by remember { mutableStateOf(false) }
    val addbackbrush = remember {
        mutableStateOf(
            Brush.verticalGradient(
                colors = listOf(
                    globalcolors.primaryColor,
                    globalcolors.secondaryColor,
                    globalcolors.primaryColor
                )
            )
        )
    }.value

    LaunchedEffect(units) {
        if (units.isEmpty()) {
            unitNames = (1..7).map { "Unit $it" }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "   Manage Units",
                        fontWeight = FontWeight.Bold,
                        fontFamily = RobotoMono
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigate("dashboard") },
                        modifier = Modifier.absolutePadding(10.dp)
                    ) {
                        Box(modifier = Modifier

                            .border(
                                width = 1.dp,
                                color = globalcolors.textColor,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                            .size(50.dp),
                            contentAlignment = Alignment.Center){
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = globalcolors.textColor,
                            )
                        }
                    }
                },
                
                actions = {
                    IconButton(onClick = {
                        showmenu = !showmenu
                    }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = globalcolors.textColor
                        )
                    }
                    DropdownMenu(expanded = showmenu, onDismissRequest = { showmenu = false },
                        modifier = Modifier.background(globalcolors.secondaryColor)) {
                        DropdownMenuItem(text = { Text("Clear Units") }, onClick = {
                            units = emptyList()
                            unitNames = emptyList()
                            saveUnits(context, units)
                            Toast.makeText(context, "Units cleared!", Toast.LENGTH_SHORT).show()
                            showmenu = false
                        })
                        
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = globalcolors.primaryColor,
                    titleContentColor = globalcolors.textColor,
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .background(addbackbrush)
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                item {
                    if (units.isEmpty()) {
                        OutlinedTextField(
                            value = newUnitCount,
                            onValueChange = { newUnitCount = it },
                            label = { Text("Number of Units", fontFamily = RobotoMono, color = globalcolors.textColor) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = globalcolors.textColor,
                                unfocusedBorderColor = globalcolors.textColor,
                                cursorColor = globalcolors.textColor
                            ),
                            textStyle = TextStyle(fontFamily = RobotoMono, color = globalcolors.textColor)
                        )

                        Button(
                            onClick = {
                                val count = newUnitCount.toIntOrNull()
                                if (count != null && count > 0) {
                                    unitNames = (1..count).map { "Unit $it" }
                                    units = unitNames.map { Units(it) }
                                    saveUnits(context, units)
                                    newUnitCount = ""
                                } else {
                                    Toast.makeText(context, "Enter a valid number", Toast.LENGTH_SHORT).show()
                                }
                            },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(containerColor = globalcolors.primaryColor)
                        ) {
                            Text("Create Units", fontFamily = RobotoMono, color = globalcolors.textColor)
                        }
                    }
                }

                itemsIndexed(units) { index, unit ->
                    var unitName by remember { mutableStateOf(unit.unitname) }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = unitName,
                            onValueChange = {
                                unitName = it
                                unitNames = unitNames.toMutableList().apply { this[index] = it }
                            },
                            textStyle = TextStyle(fontFamily = RobotoMono, color = globalcolors.textColor, fontSize = 18.sp),
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(onClick = {
                            units = units.toMutableList().apply { removeAt(index) }
                            unitNames = unitNames.toMutableList().apply { removeAt(index) }
                            saveUnits(context, units)
                        }) {
                            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Delete", tint = globalcolors.textColor)
                        }
                    }
                }
            }

            if (units.isNotEmpty()) {
                Button(
                    onClick = {
                        saveUnits(context, units.mapIndexed { index, unit -> unit.copy(unitname = unitNames[index]) })
                        Toast.makeText(context, "Units saved!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = globalcolors.primaryColor),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Save Units", fontFamily = RobotoMono, color = globalcolors.textColor)
                }
            }
        }
    }
}

@Preview
@Composable
fun ManageUnitsScreenPreview() {
    val navController = rememberNavController()
    ManageUnitsScreen(LocalContext.current, navController)
}

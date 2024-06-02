package com.app.classportal

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.app.classportal.CommonComponents as CC


@SuppressLint("MutableCollectionMutableState", "AutoboxingStateCreation")
@Composable
fun Assignments(navController: NavController, context: Context) {
    val units = remember { mutableStateOf(FileUtil.loadUnitsAndAssignments(context)) }
    var selectedUnitIndex by remember { mutableStateOf(0) }

    val currentAssignments = units.value.getOrNull(selectedUnitIndex)?.assignments ?: emptyList()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(GlobalColors.secondaryColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .background(GlobalColors.primaryColor, RoundedCornerShape(10.dp))
                .clickable { navController.navigate("assignments") }
                .padding(16.dp)
        ) {
            Text(
                text = "Add Assignment",
                style = CC.titleTextStyle
            )
        }

        // Filter Button Row
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            units.value.forEachIndexed { index, unit ->
                Button(
                    onClick = { selectedUnitIndex = index },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (index == selectedUnitIndex) GlobalColors.primaryColor else Color.Transparent
                    )
                ) {
                    Text(
                        text = unit.name,
                        style = CC.descriptionTextStyle
                    )
                }
            }
        }

        if (currentAssignments.isNotEmpty()) {
            currentAssignments.forEach { assignment ->
                AssignmentItem(assignment)
            }
        } else {
            Text(
                text = "No assignment found for ${units.value[selectedUnitIndex].name}",
                style = CC.descriptionTextStyle
            )
        }
    }
}

@Composable
fun AssignmentItem(assignment: Assignment) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(GlobalColors.primaryColor, RoundedCornerShape(8.dp))
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = assignment.title,
            style = CC.titleTextStyle,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = assignment.description,
            style = CC.descriptionTextStyle,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun AssignmentsTabContentPreview() {
    Assignments(navController = NavController(LocalContext.current), context = LocalContext.current)
}
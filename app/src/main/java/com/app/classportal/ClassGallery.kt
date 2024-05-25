package com.app.classportal

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Gallery(navController: NavController) {
    val imageUrls = listOf(
        "https://github.com/mikesplore/Class-Portal/blob/master/app/src/main/res/drawable/announcement.jpeg",
        "https://media.istockphoto.com/id/697689066/photo/three-giraffe-in-national-park-of-kenya.jpg?s=612x612&w=0&k=20&c=PkC1FAG_dl35Q89Qrfsr_N7siaC645dy8EmP5SekYCI=",
        "https://www.shutterstock.com/shutterstock/photos/1908794089/display_1500/stock-photo-academic-concept-smiling-junior-asian-school-girl-sitting-at-desk-in-classroom-writing-in-1908794089.jpg",
        "https://st2.depositphotos.com/1037987/10995/i/450/depositphotos_109959356-stock-photo-teacher-helping-elementary-school-boy.jpg",
        "https://cdn.create.vista.com/api/media/small/567482940/stock-photo-cute-little-children-reading-books-floor-classroom",
        "https://interiordesign.net/wp-content/uploads/2023/03/Interior-Design-Beaverbrook-Art-Gallery-idx230301_intervention02-1024x580.jpg",
        "https://media.istockphoto.com/id/911030028/photo/group-photo-at-school.jpg?s=612x612&w=0&k=20&c=iteKL8IJfHntwPsOqGVpwJQOIck3YCeSvf0lJoJL_Wo="
    )
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Storage permission is required to download images", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(" Gallery",style = titleTextStyle()) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("dashboard") },
                        modifier = Modifier.absolutePadding(10.dp)) {
                        Box(modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = textColor,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                            .size(50.dp),
                            contentAlignment = Alignment.Center){
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "Back",
                                tint = textColor,
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                            coroutineScope.launch {
                                // Your download logic here
                            }
                        } else {
                            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        }
                    }) {
                        Icon(imageVector = Icons.Default.AddAPhoto, contentDescription = "Download",
                            tint = textColor,
                            modifier = Modifier.clickable {
                                Toast.makeText(context, "Feature coming soon!", Toast.LENGTH_SHORT).show()
                            })
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = primaryColor
                )
            )

        },
        containerColor = primaryColor
    ) {
        Column(
            modifier = Modifier
                .padding(top = 77.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(primaryColor)
                .padding(16.dp)
        ) {
            imageUrls.forEach { imageUrl ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Gray)
                        .clickable {
                            coroutineScope.launch {
                                downloadImage(context, imageUrl)
                            }
                        }
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageUrl)
                            .build(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.77f)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

private suspend fun downloadImage(context: Context, imageUrl: String) {
    val imageLoader = coil.ImageLoader(context)
    val request = ImageRequest.Builder(context)
        .data(imageUrl)
        .allowHardware(false)
        .build()

    val result = (imageLoader.execute(request) as? SuccessResult)?.drawable

    if (result != null) {
        val bitmap = (result as Drawable).toBitmap()
        val saved = saveBitmapToFile(context, bitmap, "downloaded_image.jpg")
        Toast.makeText(context, if (saved) "Image downloaded" else "Download failed", Toast.LENGTH_SHORT).show()
    }
}

private fun saveBitmapToFile(context: Context, bitmap: Bitmap, fileName: String): Boolean {
    return try {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val file = File(path, fileName)
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

@Preview
@Composable
fun ScrollableImageColumnPreview() {
    Gallery(rememberNavController())
}

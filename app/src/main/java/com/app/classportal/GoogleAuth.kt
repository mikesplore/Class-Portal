package com.app.classportal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider

@Composable
fun GoogleAuth(
    firebaseAuth: FirebaseAuth,
    onSignInSuccess: (String) -> Unit,
    onSignInFailure: (String) -> Unit,
    // Add navController parameter
) {
    val activity = LocalContext.current as Activity
    val provider = OAuthProvider.newBuilder("google.com")
    val context = LocalContext.current
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .clickable {
                isLoading = true
                firebaseAuth
                    .startActivityForSignInWithProvider(activity, provider.build())
                    .addOnSuccessListener {
                        Toast.makeText(context, "Signing you in...", Toast.LENGTH_SHORT).show()
                        val user = it.user
                        if (user != null) {
                            // Get the user's first name
                            val firstName = user.email!!.split("@")
                                .firstOrNull()?:"Unknown"
                            onSignInSuccess(firstName)
                            MyDatabase.writeNewUser(
                                user.uid,
                                firstName,
                                user.email ?: ""
                            )
                            isLoading = false // Stop loading on success
                        } else {
                            onSignInFailure("User is null")
                        }
                    }
                    .addOnFailureListener {
                        isLoading = false // Stop loading on failure
                        onSignInFailure(it.message ?: "Unknown error")
                        Log.e("GoogleAuth", "Sign-in failed", it)
                    }
            }
            .border(
                width = 1.dp,
                color = GlobalColors.textColor,
                shape = RoundedCornerShape(10.dp)
            )
            .background(GlobalColors.secondaryColor, shape = RoundedCornerShape(10.dp))
            .height(60.dp)
            .width(150.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            // Show CircularProgressIndicator when loading
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = GlobalColors.textColor,
                strokeWidth = 4.dp
            )
        } else {
            // Show Google image when not loading
            Image(
                painter = painterResource(R.drawable.google),
                contentDescription = "",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}


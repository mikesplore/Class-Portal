package com.app.classportal

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// Object to handle database operations
object MyDatabase {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun writeNewUser(userId: String, name: String, email: String) {
        val user = User(name, email)
        database.child("users").child(userId).setValue(user)
    }

    fun getUserData(userId: String, onUserDataFetched: (User?) -> Unit) {
        database.child("users").child(userId).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                onUserDataFetched(user)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MyDatabase", "Error fetching user data", error.toException())
                onUserDataFetched(null)
            }
        })
    }
    fun getAllUsers(onUsersFetched: (List<User>) -> Unit) {
        database.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<User>()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(User::class.java)
                    user?.let { userList.add(it) }
                }
                onUsersFetched(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("MyDatabase", "Error fetching users", error.toException())
                onUsersFetched(emptyList())
            }
        })
    }
}





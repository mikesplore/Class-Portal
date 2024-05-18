package com.app.fitnessapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

object FilePersistence {

    val gson = Gson()

    inline fun <reified T> saveData(context: Context, filename: String, data: List<T>) {
        val jsonString = gson.toJson(data)
        context.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(jsonString.toByteArray())
        }
    }

    inline fun <reified T> loadData(context: Context, filename: String): List<T> {
        return try {
            val file = File(context.filesDir, filename)
            if (file.exists()) {
                val jsonString = file.readText()
                gson.fromJson(jsonString, object : TypeToken<List<T>>() {}.type)
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}

package com.example.listreader.util.network

import android.util.Log
import com.example.listreader.model.ListRepository
import com.example.listreader.model.ParsedItem
import javax.inject.Inject

class RemoteListRepository @Inject constructor(
    private val apiService: ListApiService
): ListRepository {
    override suspend fun getList(): List<ParsedItem> {
        val response = apiService.getItems()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            Log.e("API_ERROR", "Error: ${response.code()} ${response.message()}")
            throw Exception("Error: ${response.code()} ${response.message()}")
        }
    }
}
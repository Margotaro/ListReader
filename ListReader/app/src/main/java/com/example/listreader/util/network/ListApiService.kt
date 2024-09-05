package com.example.listreader.util.network

import retrofit2.http.GET
import com.example.listreader.model.ParsedItem
import com.example.listreader.util.Constants
import retrofit2.Response

interface ListApiService {
    @GET(Constants.SOURCE_FILE_NAME)
    suspend fun getItems(): Response<List<ParsedItem>>
}
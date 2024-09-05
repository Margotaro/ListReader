package com.example.listreader.model

interface ListRepository {
    suspend fun getList(): List<ParsedItem>
}
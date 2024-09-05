package com.example.listreader.model

data class ParsedItem(
    val id: Int,
    val listId: Int,
    val name: String? = null
)
package com.example.listreader.util

import com.example.listreader.model.ListRepository
import com.example.listreader.model.ParsedItem
import javax.inject.Inject

class MockListRepository @Inject constructor(): ListRepository {
    companion object {
        val mockItemList = listOf(
            ParsedItem(id = 1, listId = 1, name = "Spongebob"),
            ParsedItem(id = 2, listId = 1, name = "Patrick"),
            ParsedItem(id = 100, listId = 1, name = ""),
            ParsedItem(id = 3, listId = 1, name = "Gary"),
            ParsedItem(id = 4, listId = 2, name = "Daisy"),
            ParsedItem(id = 9, listId = 2, name = null),
            ParsedItem(id = 6, listId = 2, name = "Rose"),
            ParsedItem(id = 5, listId = 2, name = "Lily"),
        )
    }

    override suspend fun getList(): List<ParsedItem> {
        return mockItemList
    }
}
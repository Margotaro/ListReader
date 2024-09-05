package com.example.listreader.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listreader.model.ParsedItem
import com.example.listreader.util.network.Item
import com.example.listreader.util.network.RemoteListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: RemoteListRepository) : ViewModel() {
    var itemMap by mutableStateOf<Map<Int, List<Item>>>(emptyMap())
        private set

    var isLoading by mutableStateOf(true)
        private set

    var error by mutableStateOf<String?>(null)
        private set

    init {
        fetchItems()
    }

    private fun fetchItems() {
        error = null
        viewModelScope.launch {
            try {
                isLoading = true
                val parsedItems = repository.getList()
                val filteredItems = filterItems(parsedItems)
                itemMap = listToSortedMap(filteredItems)
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }

    private fun listToSortedMap(oldList: List<Item>): Map<Int, List<Item>> {
        val newList = oldList.sortedWith(compareBy({ it.listId }, { it.name }))
        return newList.groupBy { it.listId }
    }

    private fun filterItems(oldList: List<ParsedItem>): List<Item> {
        val items = mutableListOf<Item>()
        for (item in oldList) {
            item.name?.let {
                if (it.isEmpty()) {
                    return@let
                }
                items.add(Item(item.id, item.listId, it))
            }
        }
        return items
    }

}
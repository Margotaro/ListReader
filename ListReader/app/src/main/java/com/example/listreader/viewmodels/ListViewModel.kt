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

sealed interface ListState { // reason to refactor: type safety
    data object Loading : ListState
    data class Success(val items: Map<Int, List<Item>>) : ListState
    data class Error(val message: String) : ListState
}

@HiltViewModel
class ListViewModel @Inject constructor(private val repository: RemoteListRepository) : ViewModel() {

    private val _state = mutableStateOf<ListState>(ListState.Loading)
    val state: ListState get() = _state.value

    init {
        fetchItems()
    }

    private fun fetchItems() {
        _state.value = ListState.Loading
        viewModelScope.launch {
            _state.value = try {
                val parsedItems = repository.getList()
                val filteredItems = filterItems(parsedItems)
                val sortedItems = listToSortedMap(filteredItems)
                ListState.Success(sortedItems)
            } catch (e: Exception) {
                ListState.Error(e.message ?: "Unknown error")
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
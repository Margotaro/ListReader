package com.example.listreader.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.listreader.util.network.Item
import com.example.listreader.ui.theme.ListReaderTheme
import com.example.listreader.util.MockListRepository
import com.example.listreader.viewmodels.ListViewModel

@Preview(showBackground = true)
@Composable
fun ListPreview() {
    ListReaderTheme {
        GroupedList(map = MockListRepository.mockItemList
            .filter { it.name != "" && it.name != null }
            .mapNotNull { it.name?.let { name ->
                Item(
                    id = it.id,
                    listId = it.listId,
                    name = name
                )
            }}
            .groupBy { it.listId }
        )
    }
}

@Composable
fun FetchValues(viewModel: ListViewModel = hiltViewModel()) {
    val items = viewModel.itemMap
    val isLoading = viewModel.isLoading
    val error = viewModel.error

    if (isLoading) {
        ProgressIndicator()
    } else if (error != null){
        ShowError()
    } else if (items.isEmpty()) {
        EmptyMap()
    } else {
        GroupedList(items)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroupedList(map: Map<Int, List<Item>>) {
    val groups = map.keys
    LazyColumn {
        groups.forEach { section ->
            stickyHeader {
                Column(
                    modifier = Modifier
                        .height(48.dp)
                        .background(MaterialTheme.colorScheme.inversePrimary)
                ) {
                    Row {
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .wrapContentHeight(align = Alignment.CenterVertically),
                            text = section.toString(),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.headlineLarge
                        )
                    }
                }
            }
            map[section]?.let { items ->
                items(items.size) { i ->
                    Row(
                        modifier = Modifier
                            .background(
                                if (i % 2 == 0)
                                    MaterialTheme.colorScheme.onSecondary
                                else
                                    MaterialTheme.colorScheme.primaryContainer
                            )
                            .height(32.dp)
                    ) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = items[i].name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}
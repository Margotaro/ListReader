package com.example.listreader.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.listreader.R

@Preview
@Composable
fun ShowError(message: String = stringResource(id = R.string.gen_error)) {
    Box(
        modifier = Modifier.fillMaxSize(), // Fill the entire screen
        contentAlignment = Alignment.Center // Center the text in the screen
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}


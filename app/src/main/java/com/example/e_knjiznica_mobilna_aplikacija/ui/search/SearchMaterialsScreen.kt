package com.example.e_knjiznica_mobilna_aplikacija.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchMaterialsScreen(
    onSearch: (String, String, String, String) -> Unit
) {
    var materialName by remember { mutableStateOf("") }
    var authorName by remember { mutableStateOf("") }
    var releaseDate by remember { mutableStateOf("") }
    var materialType by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
    ) {
        // Search by Material Name
        TextField(
            value = materialName,
            onValueChange = { materialName = it },
            label = { Text("Material Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Search by Author
        TextField(
            value = authorName,
            onValueChange = { authorName = it },
            label = { Text("Author") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Search by Release Date
        TextField(
            value = releaseDate,
            onValueChange = { releaseDate = it },
            label = { Text("Release Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Search by Material Type
        TextField(
            value = materialType,
            onValueChange = { materialType = it },
            label = { Text("Material Type (e.g., Book, Video)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Search Button
        Button(
            onClick = {
                onSearch(materialName, authorName, releaseDate, materialType)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Search")
        }
    }
}
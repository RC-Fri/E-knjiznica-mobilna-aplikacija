package com.example.e_knjiznica_mobilna_aplikacija.ui.search

import MaterialItem2
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.e_knjiznica_mobilna_aplikacija.data.model.SearchViewModel
import com.example.e_knjiznica_mobilna_aplikacija.data.model.Material

@Composable
fun SearchScreen(
    userId: Int,
    searchViewModel: SearchViewModel,
    onBorrowMaterial: (Material) -> Unit,
    onExit: () -> Unit
) {
    val searchResults by searchViewModel.searchResults.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search form
        SearchMaterialsScreen { materialName, authorName, releaseDate, materialType ->
            searchViewModel.searchMaterials(
                materialName = materialName,
                authorName = authorName,
                releaseDate = releaseDate,
                materialType = materialType
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display search results
        if (searchResults.isEmpty()) {
            Text(
                text = "No materials found.",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(searchResults) { material ->
                    MaterialItem2(
                        material = material,
                        onBorrowMaterial = { onBorrowMaterial(material) }
                    )
                }
            }
        }

        // Back button
        Button(
            onClick = onExit,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back")
        }
    }
}
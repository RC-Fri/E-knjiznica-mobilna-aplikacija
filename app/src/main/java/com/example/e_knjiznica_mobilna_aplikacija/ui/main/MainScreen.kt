package com.example.e_knjiznica_mobilna_aplikacija.ui.main

import MaterialItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.e_knjiznica_mobilna_aplikacija.data.model.MainViewModel
import com.example.e_knjiznica_mobilna_aplikacija.data.model.Material

@Composable
fun MainScreen(
    userId: Int,
    userName: String,
    mainViewModel: MainViewModel,
    materials: List<Material>,
    onBorrowMaterial: () -> Unit,
    onExtendDate: (Material) -> Unit,
    onView: (Material) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Scrollable Material List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(materials.size) { index ->
                MaterialItem(material = materials[index], onExtendDate = onExtendDate, onView = onView)
            }
        }

        // Borrow Material Button
        Button(
            onClick = onBorrowMaterial,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(text = "Borrow materials")
        }
    }
}

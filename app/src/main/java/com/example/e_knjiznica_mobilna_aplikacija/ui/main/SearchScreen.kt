package com.example.e_knjiznica_mobilna_aplikacija.ui.main

import MaterialItem2
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

@Composable
fun SearchScreen(
    userName: String,
    materials: List<Material>,
    onView: (Material) -> Unit,
    onBorrowMaterial: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Hello User Text
        /*
        Text(
            text = "Hello, $userName!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        */

        // Scrollable Material List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(materials.size) { index ->
                MaterialItem2(material = materials[index], onView = onView)
            }
        }

        // Borrow Material Button
        Button(
            onClick = onBorrowMaterial,
            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
        ) {
            Text(text = userName)
        }
    }
}
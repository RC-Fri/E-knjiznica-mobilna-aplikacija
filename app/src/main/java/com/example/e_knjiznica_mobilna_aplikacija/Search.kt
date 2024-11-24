package com.example.e_knjiznica_mobilna_aplikacija

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity


class Search : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                    SearchMaterialsScreen(
                        onSearch = TODO()
                    )
            }
        }
    }

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

@Composable
fun CombinedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // First Screen - Search Materials
        SearchMaterialsScreen(
            onSearch = { materialName, authorName, releaseDate, materialType ->
                // Handle the search logic here
            }
        )

        // Spacer to separate the two sections
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Found next material:",
            fontSize = 20.sp,
        )

        SearchScreen(
            userName = "Exit",
            materials = listOf(
                Material("Book A", R.drawable.ic_launcher_foreground, "This is Book A details."),
                Material("Book B", R.drawable.ic_launcher_foreground, "This is Book B details."),
                Material("Book C", R.drawable.ic_launcher_foreground, "This is Book C details."),
                Material("Book D", R.drawable.ic_launcher_foreground, "This is Book D details."),
                Material("Book E", R.drawable.ic_launcher_foreground, "This is Book E details."),
                Material("Book F", R.drawable.ic_launcher_foreground, "This is Book F details.")
            ),
            onBorrowMaterial = {},
            onView = {}
        )

        Button(
            onClick = {

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Exit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CombinedScreenPreview() {
    CombinedScreen()
}
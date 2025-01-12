package com.example.e_knjiznica_mobilna_aplikacija.ui.main

import LoginViewModel
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_knjiznica_mobilna_aplikacija.R
import com.example.e_knjiznica_mobilna_aplikacija.databinding.ActivityLoginBinding

data class Material(
    val id: Int,
    val name: String,
    val status: String,
    val imageResId: Int,
    val details: String
)

class MainActivity2 : ComponentActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val username = intent.getStringExtra("USERNAME")

        setContent {

            //TODO: For displaying material
            val viewModel: LoginViewModel = viewModel()
            val uiState = viewModel.uiState.collectAsState().value


            val context = LocalContext.current
            MainScreen(
                userName = username ?: "User",
                materials = listOf(
                    Material(
                        0,
                        "Book A",
                        "Coocked",
                        R.drawable.ic_launcher_foreground,
                        "This is Book A details."
                    ),
                    Material(
                        0,
                        "Book A",
                        "Coocked",
                        R.drawable.ic_launcher_foreground,
                        "This is Book B details."
                    ),
                    Material(
                        0,
                        "Book A",
                        "Coocked",
                        R.drawable.ic_launcher_foreground,
                        "This is Book C details."
                    ),
                    Material(
                        0,
                        "Book A",
                        "Coocked",
                        R.drawable.ic_launcher_foreground,
                        "This is Book D details."
                    ),
                    Material(
                        0,
                        "Book A",
                        "Coocked",
                        R.drawable.ic_launcher_foreground,
                        "This is Book E details."
                    ),
                    Material(
                        0,
                        "Book A",
                        "Coocked",
                        R.drawable.ic_launcher_foreground,
                        "This is Book F details."
                    )
                ),
                onExtendDate = { material ->
                    Toast.makeText(this, "Extending date for ${material.name}", Toast.LENGTH_SHORT)
                        .show()
                },
                onBorrowMaterial = {
                    var materials = listOf(
                        Material(
                            0,
                            "Book A",
                            "Coocked",
                            R.drawable.ic_launcher_foreground,
                            "This is Book A details."
                        ),
                        Material(
                            0,
                            "Book A",
                            "Coocked",
                            R.drawable.ic_launcher_foreground,
                            "This is Book B details."
                        ),
                        Material(
                            0,
                            "Book A",
                            "Coocked",
                            R.drawable.ic_launcher_foreground,
                            "This is Book C details."
                        ),
                        Material(
                            0,
                            "Book A",
                            "Coocked",
                            R.drawable.ic_launcher_foreground,
                            "This is Book D details."
                        ),
                        Material(
                            0,
                            "Book A",
                            "Coocked",
                            R.drawable.ic_launcher_foreground,
                            "This is Book E details."
                        ),
                        Material(
                            0,
                            "Book A",
                            "Coocked",
                            R.drawable.ic_launcher_foreground,
                            "This is Book F details."
                        )
                    )
//                    val intent = Intent(context, SearchScreen(
//                        materials,
//                        onView = {}
//                    ) { }::class.java)
//                    context.startActivity(intent)
                    Toast.makeText(this, "Navigating to Borrow Material screen", Toast.LENGTH_SHORT)
                        .show()
                },
                onView = {
                    //TODO: Finish this
                    //Toast.makeText(this, "Navigating to View Material screen", Toast.LENGTH_SHORT).show()
                },
                userId = TODO(),
                mainViewModel = TODO()
            )
            Toast.makeText(this, "Welcome $username", Toast.LENGTH_SHORT).show()
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//MainScreen(
//userName = "John",
//materials = listOf(
//    Material(0,"Book A", "Coocked", R.drawable.ic_launcher_foreground, "This is Book A details."),
//    Material(0,"Book A", "Coocked", R.drawable.ic_launcher_foreground, "This is Book B details."),
//    Material(0,"Book A", "Coocked", R.drawable.ic_launcher_foreground, "This is Book C details."),
//    Material(0,"Book A", "Coocked", R.drawable.ic_launcher_foreground, "This is Book D details."),
//    Material(0,"Book A", "Coocked", R.drawable.ic_launcher_foreground, "This is Book E details."),
//    Material(0,"Book A", "Coocked", R.drawable.ic_launcher_foreground, "This is Book F details.")
//),
//onExtendDate = {},
//onBorrowMaterial = {},
//onView = {}
//)
//}

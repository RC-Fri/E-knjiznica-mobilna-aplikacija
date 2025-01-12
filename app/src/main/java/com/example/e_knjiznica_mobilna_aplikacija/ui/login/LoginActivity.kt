package com.example.e_knjiznica_mobilna_aplikacija.ui.login

import LoginViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_knjiznica_mobilna_aplikacija.ui.main.MainActivity2

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: LoginViewModel = viewModel()
            val uiState by viewModel.uiState.collectAsState()

            LoginScreen(
                uiState = uiState,
                onLogin = { username, password ->
                    viewModel.login(username, password)
                },
                onLoginSuccess = { username, password ->
                    // Navigate to MainActivity2 with extras
                    val intent = Intent(this, MainActivity2::class.java).apply {
                        putExtra("USERNAME", username)
                        putExtra("PASSWORD", password)
                    }
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}


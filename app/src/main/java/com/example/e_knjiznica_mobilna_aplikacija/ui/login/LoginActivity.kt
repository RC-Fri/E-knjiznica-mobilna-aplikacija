package com.example.e_knjiznica_mobilna_aplikacija.ui.login

import AppLifecycleObserver
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.e_knjiznica_mobilna_aplikacija.AppNavHost

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())

            AppNavHost() }
    }
}



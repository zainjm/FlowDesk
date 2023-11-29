package com.example.flowdeck // Use your package name

import viewmodel.AuthViewModel
import model.FlowDeckRepository
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.flowdeck.ui.theme.FlowDeckTheme
import androidx.navigation.compose.NavHost
import view.LoginScreen

import view.RegisterScreen

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(FlowDeckRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FlowDeckTheme {
                // Initialize NavController
                val navController = rememberNavController()

                // Setup the NavHost
                NavHost(navController = navController, startDestination = "register_screen") {
                    composable("register_screen") {
                        RegisterScreen(navController, authViewModel)
                    }
                    // Define the composable for the login screen
                    composable("login_screen") {
                        LoginScreen(navController, authViewModel) // Define your LoginScreen composable
                    }
                    // ... other composable destinations if you have any
                }
            }
        }
    }
}


class AuthViewModelFactory(private val repository: FlowDeckRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
package view

import androidx.compose.foundation.layout.*
import viewmodel.AuthViewModel
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import viewmodel.Resource
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(navController: NavController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }


    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )

    {
        Text("Register on FlowDesk!",
        fontWeight =  FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        val registrationState = authViewModel.userRegistrationState.collectAsState().value

        if (registrationState is Resource.Success) {
            LaunchedEffect(registrationState) {
                delay(2000)  // Delay for 2 seconds
                navController.navigate("login_screen") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth().padding(16.dp)

        ) {
            when (registrationState) {
                is Resource.Error -> {
                    Text(
                        text = "Error: ${registrationState.message}",
                        modifier = Modifier.padding(16.dp)
                    )
                }
                else -> {
                    // Nothing here, since the Text composables for the form will be shown by default
                }
            }

            Button(onClick = {
                if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    // Show error that fields are empty
                } else if (password != confirmPassword) {
                    // Show error that passwords do not match
                } else {
                    authViewModel.registerUser(email, password)
                }
            }
            ) {
                Text("Sign Up")
            }
            Button(onClick = {
                navController.navigate("login_screen") {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users open and close the screen
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                    // Avoid multiple copies of the same destination when reselecting the same item
                    launchSingleTop = true
                }
            }) {
                Text("Already have an account? Log In",

                    )
            }

        }
    }
}


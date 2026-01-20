package com.example.demo



import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import org.example.export.ui.LoginScreen
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.export.auth.AuthManager

class MainActivity : ComponentActivity() {

    private val authManager = AuthManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                var isLoggedIn by remember { mutableStateOf(false) }
                var userEmail by remember { mutableStateOf("") }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isLoggedIn) {
                        // Home Screen after login
                        HomeScreen(
                            email = userEmail,
                            onLogout = {
                                authManager.logout()
                                isLoggedIn = false
                                userEmail = ""
                                Toast.makeText(
                                    this@MainActivity,
                                    "Logged out successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    } else {
                        // Login Screen from SDK
                        LoginScreen(
                            authManager = authManager,
                            onLoginSuccess = { email ->
                                userEmail = email
                                isLoggedIn = true
                                Toast.makeText(
                                    this@MainActivity,
                                    "Welcome $email!",
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            onLoginError = { error ->
                                Toast.makeText(
                                    this@MainActivity,
                                    "Login failed: $error",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    email: String,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome!",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "You are logged in as:",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = email,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onLogout,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Logout")
        }
    }
}
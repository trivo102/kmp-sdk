// Demo Android App using ExportSDK
// File: MainActivity.kt

package com.example.demoapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.export.auth.AuthManager
import org.example.export.ui.LoginScreen

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

// File: MyApplication.kt

package com.example.demoapp

import android.app.Application
import org.example.export.ExportSDK
import org.example.export.SDKConfig
import org.example.export.Environment

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize ExportSDK
        ExportSDK.initialize(
            SDKConfig(
                apiKey = "demo-api-key-12345",
                environment = Environment.PRODUCTION,
                enableLogging = BuildConfig.DEBUG
            )
        )
    }
}

// File: build.gradle.kts (app level)

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.demoapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.demoapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // ExportSDK
    implementation(files("libs/ExportSDK-1.0.0.aar"))

    // Required dependencies
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.ui:ui:1.6.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // Kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
}

// File: AndroidManifest.xml

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name=".MyApplication"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.AppCompat.DayNight.NoActionBar">

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>

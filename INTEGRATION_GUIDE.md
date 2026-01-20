# How to Use ExportSDK - Example Integration

## Android Example

### Step 1: Add dependency

Add the AAR file to your `libs` folder and add dependency in `build.gradle.kts`:

```kotlin
dependencies {
    implementation(files("libs/ExportSDK-1.0.0.aar"))
    
    // Required dependencies
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.ui:ui:1.6.7")
}
```

### Step 2: Initialize SDK

Create an Application class:

```kotlin
package com.yourcompany.yourapp

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
                apiKey = "your-api-key-here",
                environment = Environment.PRODUCTION,
                enableLogging = BuildConfig.DEBUG
            )
        )
    }
}
```

Add to `AndroidManifest.xml`:
```xml
<application
    android:name=".MyApplication"
    ...>
```

### Step 3: Use Login Screen

In your Activity:

```kotlin
package com.yourcompany.yourapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import org.example.export.auth.AuthManager
import org.example.export.ui.LoginScreen

class MainActivity : ComponentActivity() {
    
    private val authManager = AuthManager()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            MaterialTheme {
                LoginScreen(
                    authManager = authManager,
                    onLoginSuccess = { email ->
                        Toast.makeText(
                            this,
                            "Welcome $email!",
                            Toast.LENGTH_LONG
                        ).show()
                        
                        // Navigate to main screen
                        // startActivity(Intent(this, HomeActivity::class.java))
                    },
                    onLoginError = { error ->
                        Toast.makeText(
                            this,
                            "Login failed: $error",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                )
            }
        }
    }
}
```

### Step 4: Use AuthManager directly (Optional)

For custom UI:

```kotlin
import kotlinx.coroutines.launch
import org.example.export.auth.AuthManager
import org.example.export.auth.AuthResult

class LoginViewModel : ViewModel() {
    private val authManager = AuthManager()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            when (val result = authManager.login(email, password)) {
                is AuthResult.Success -> {
                    // Handle success
                    val user = result.user
                    println("Logged in: ${user.email}")
                }
                is AuthResult.Error -> {
                    // Handle error
                    println("Error: ${result.message}")
                }
            }
        }
    }
    
    fun logout() {
        authManager.logout()
    }
    
    fun isLoggedIn(): Boolean {
        return authManager.isLoggedIn()
    }
}
```

---

## iOS Example

### Step 1: Add Framework

1. Drag `ExportSDK.xcframework` to your Xcode project
2. In "Frameworks, Libraries, and Embedded Content" → Set to "Embed & Sign"

### Step 2: Initialize SDK

In your App file:

```swift
import SwiftUI
import ExportSDK

@main
struct MyApp: App {
    init() {
        // Initialize SDK
        ExportSDKKt.initialize(
            config: SDKConfig(
                apiKey: "your-api-key-here",
                environment: .production,
                enableLogging: false
            )
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
```

### Step 3: Use Login Screen

```swift
import SwiftUI
import ExportSDK

struct ContentView: View {
    @StateObject private var authManager = AuthManager()
    @State private var isLoggedIn = false
    
    var body: some View {
        if isLoggedIn {
            HomeView()
        } else {
            LoginScreenKt.LoginScreen(
                authManager: authManager,
                onLoginSuccess: { email in
                    print("Welcome \(email)!")
                    isLoggedIn = true
                },
                onLoginError: { error in
                    print("Login error: \(error)")
                }
            )
        }
    }
}
```

### Step 4: Use AuthManager (Custom UI)

```swift
import ExportSDK

class LoginViewModel: ObservableObject {
    private let authManager = AuthManager()
    @Published var isLoading = false
    @Published var errorMessage: String?
    
    func login(email: String, password: String) async {
        isLoading = true
        errorMessage = nil
        
        do {
            let result = try await authManager.login(email: email, password: password)
            
            switch result {
            case let success as AuthResult.Success:
                print("Logged in: \(success.user.email)")
            case let error as AuthResult.Error:
                errorMessage = error.message
            default:
                break
            }
        } catch {
            errorMessage = error.localizedDescription
        }
        
        isLoading = false
    }
}
```

---

## Testing

### Test Credentials
For demo purposes, use any email with password >= 6 characters:
- Email: `test@example.com`
- Password: `password123`

---

## API Overview

### ExportSDK
- `initialize(config: SDKConfig)` - Initialize SDK
- `isInitialized(): Boolean` - Check initialization status
- `getVersion(): String` - Get SDK version

### AuthManager
- `login(email, password): AuthResult` - Authenticate user
- `logout()` - Sign out current user
- `getCurrentUser(): User?` - Get current user
- `isLoggedIn(): Boolean` - Check login status
- `setAuthListener(listener)` - Listen to auth changes

### LoginScreen (Composable/SwiftUI)
Pre-built login UI with:
- Email input field
- Password input field
- Login button with loading state
- Error message display

---

## Troubleshooting

### Manifest Merger Errors

If you encounter manifest merger conflicts like:
```
Manifest merger failed : Attribute application@theme value=...
```

**Solution**: The AAR has been updated to remove all application-level attributes. Make sure you're using the latest version of `ExportSDK-1.0.0.aar`.

If the issue persists with an older AAR version, add this to your `AndroidManifest.xml`:
```xml
<application
    ...
    tools:replace="android:theme,android:label,android:icon">
```

And add the tools namespace at the top of your manifest:
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
```

---

## Support

For issues or questions:
- Email: support@example.com
- Documentation: https://docs.example.com

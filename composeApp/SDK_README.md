# ExportSDK - Multiplatform Authentication SDK

A Kotlin Multiplatform SDK providing authentication features for Android and iOS applications.

## Version
1.0.0

## Features
- 🔐 Email/Password Authentication
- 📱 Ready-to-use Login UI Component
- 🎨 Material Design 3 UI
- 🔄 Cross-platform (Android & iOS)
- 💾 User Session Management

## Integration

### Android (AAR)

1. Add the AAR file to your project:
```gradle
dependencies {
    implementation(files("libs/composeApp-release.aar"))
}
```

2. Initialize the SDK in your Application class:
```kotlin
import org.example.export.ExportSDK
import org.example.export.SDKConfig
import org.example.export.Environment

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        ExportSDK.initialize(
            SDKConfig(
                apiKey = "your-api-key",
                environment = Environment.PRODUCTION,
                enableLogging = true
            )
        )
    }
}
```

3. Use the Login Screen in your Activity:
```kotlin
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.example.export.ui.LoginScreen
import org.example.export.auth.AuthManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val authManager = AuthManager()
        
        setContent {
            MaterialTheme {
                LoginScreen(
                    authManager = authManager,
                    onLoginSuccess = { email ->
                        // Handle successful login
                        Toast.makeText(this, "Welcome $email", Toast.LENGTH_SHORT).show()
                    },
                    onLoginError = { error ->
                        // Handle login error
                        Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
```

### iOS (Framework)

1. Add the framework to your Xcode project

2. Import and use in SwiftUI:
```swift
import ComposeApp

struct ContentView: View {
    var body: some View {
        LoginScreenKt.LoginScreen(
            authManager: AuthManager(),
            onLoginSuccess: { email in
                print("Login success: \(email)")
            },
            onLoginError: { error in
                print("Login error: \(error)")
            }
        )
    }
}
```

## API Reference

### ExportSDK

Main SDK entry point.

**Methods:**
- `initialize(config: SDKConfig)` - Initialize the SDK
- `isInitialized(): Boolean` - Check if SDK is initialized
- `getVersion(): String` - Get SDK version

### AuthManager

Handles authentication operations.

**Methods:**
- `suspend fun login(email: String, password: String): AuthResult` - Login with credentials
- `logout()` - Logout current user
- `getCurrentUser(): User?` - Get current user
- `isLoggedIn(): Boolean` - Check if user is logged in
- `setAuthListener(listener: AuthListener)` - Set auth state listener

### LoginScreen (Composable)

Pre-built login UI component.

**Parameters:**
- `authManager: AuthManager` - Authentication manager instance
- `onLoginSuccess: (String) -> Unit` - Callback for successful login
- `onLoginError: (String) -> Unit` - Callback for login errors

## Building the SDK

### Build Android AAR
```bash
./gradlew :composeApp:assembleRelease
```
Output: `composeApp/build/outputs/aar/composeApp-release.aar`

### Build iOS Framework
```bash
./gradlew :composeApp:linkReleaseFrameworkIosArm64
```
Output: `composeApp/build/bin/iosArm64/releaseFramework/ComposeApp.framework`

## Requirements

### Android
- Minimum SDK: 24
- Target SDK: 36
- Kotlin: 2.3.0

### iOS
- iOS 14.0+
- Xcode 15+

## License
MIT License

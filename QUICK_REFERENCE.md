# 📋 ExportSDK - Quick Reference Card

## 🚀 Build Commands

```bash
# Build Android AAR only (fast)
./build-aar.sh

# Build iOS Framework (Device)
./gradlew :composeApp:linkReleaseFrameworkIosArm64

# Build iOS Framework (Simulator)
./gradlew :composeApp:linkReleaseFrameworkIosSimulatorArm64

# Build everything
./build-sdk.sh

# Clean build
./gradlew clean
```

## 📦 Output Locations

```
Android AAR:     output/android/ExportSDK-1.0.0.aar
iOS Framework:   composeApp/build/bin/iosArm64/releaseFramework/ExportSDK.framework
iOS Simulator:   composeApp/build/bin/iosSimulatorArm64/releaseFramework/ExportSDK.framework
```

## 💻 Android Integration (3 Steps)

### 1. Add Dependency
```kotlin
dependencies {
    implementation(files("libs/ExportSDK-1.0.0.aar"))
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.material3:material3:1.2.1")
}
```

### 2. Initialize SDK
```kotlin
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ExportSDK.initialize(
            SDKConfig(
                apiKey = "your-api-key",
                environment = Environment.PRODUCTION,
                enableLogging = BuildConfig.DEBUG
            )
        )
    }
}
```

### 3. Use Login Screen
```kotlin
setContent {
    MaterialTheme {
        LoginScreen(
            authManager = AuthManager(),
            onLoginSuccess = { email -> /* Success */ },
            onLoginError = { error -> /* Error */ }
        )
    }
}
```

## 📱 iOS Integration (3 Steps)

### 1. Add Framework
Drag `ExportSDK.xcframework` to Xcode → Embed & Sign

### 2. Initialize SDK
```swift
import ExportSDK

@main
struct MyApp: App {
    init() {
        ExportSDKKt.initialize(
            config: SDKConfig(
                apiKey: "your-api-key",
                environment: .production,
                enableLogging: false
            )
        )
    }
}
```

### 3. Use Login Screen
```swift
import ExportSDK

LoginScreenKt.LoginScreen(
    authManager: AuthManager(),
    onLoginSuccess: { email in /* Success */ },
    onLoginError: { error in /* Error */ }
)
```

## 🔑 API Reference

### ExportSDK
```kotlin
ExportSDK.initialize(config: SDKConfig)
ExportSDK.isInitialized(): Boolean
ExportSDK.getVersion(): String
```

### AuthManager
```kotlin
suspend fun login(email: String, password: String): AuthResult
fun logout()
fun getCurrentUser(): User?
fun isLoggedIn(): Boolean
fun setAuthListener(listener: AuthListener)
```

### LoginScreen
```kotlin
@Composable
fun LoginScreen(
    authManager: AuthManager = AuthManager(),
    onLoginSuccess: (String) -> Unit = {},
    onLoginError: (String) -> Unit = {}
)
```

## 🧪 Test Credentials

```
Email:    any valid email (e.g., test@example.com)
Password: minimum 6 characters (e.g., password123)
```

## 📄 Files to Distribute

```
✅ ExportSDK-1.0.0.aar          - The AAR library
✅ INTEGRATION_GUIDE.md         - Integration instructions
✅ DEMO_APP_EXAMPLE.kt          - Complete example
✅ SDK_README.md                - API documentation
```

## 🔧 Version Info

```
SDK Version:          1.0.0
Kotlin:              2.1.0
Compose Multiplatform: 1.7.1
Min Android SDK:      24
Target Android SDK:   36
iOS Minimum:         14.0
```

## 🐛 Common Issues

### Issue: "Unresolved reference"
**Solution**: Make sure all required dependencies are added

### Issue: "SDK not initialized"
**Solution**: Call `ExportSDK.initialize()` in Application.onCreate()

### Issue: AAR not found
**Solution**: Place AAR in `libs/` folder and sync Gradle

## 📞 Support

- **Documentation**: See INTEGRATION_GUIDE.md
- **Examples**: See DEMO_APP_EXAMPLE.kt
- **Build Issues**: Check BUILD_SUMMARY.md

---

**Quick Tip**: Start with DEMO_APP_EXAMPLE.kt for a complete working example! 🚀

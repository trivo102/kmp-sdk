# ExportSDK - Kotlin Multiplatform Authentication SDK

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Compose-1.7.1-green.svg)](https://www.jetbrains.com/lp/compose-multiplatform/)
[![Platform](https://img.shields.io/badge/Platform-Android%20%7C%20iOS-orange.svg)](https://kotlinlang.org/docs/multiplatform.html)

A production-ready Kotlin Multiplatform SDK providing authentication features for Android and iOS applications.

## 🚀 Features

- **🔐 Authentication**: Email/password login with validation
- **📱 Ready-to-use UI**: Pre-built Material Design 3 login screen
- **🎨 Customizable**: Use our UI or integrate auth logic into your own
- **🔄 Cross-platform**: Works on both Android and iOS
- **💾 Session Management**: Built-in user session handling
- **🔒 Secure**: ProGuard rules included for Android
- **📦 Lightweight**: Minimal dependencies

## 📦 What's Included

### SDK Components

1. **ExportSDK** - Main SDK class for initialization
2. **AuthManager** - Authentication logic and user management
3. **LoginScreen** - Pre-built Compose UI component
4. **User & AuthResult** - Data models

### Artifacts

- **Android**: AAR library (~119KB)
- **iOS**: Framework/XCFramework
- **Documentation**: Integration guides and examples

## 🛠️ Building the SDK

### Prerequisites

- JDK 11 or higher
- Android SDK (for Android builds)
- Xcode (for iOS builds, macOS only)

### Build Android AAR

```bash
./build-aar.sh
```

Output: `output/android/ExportSDK-1.0.0.aar`

### Build iOS Framework

```bash
./gradlew :composeApp:linkReleaseFrameworkIosArm64
```

Output: `composeApp/build/bin/iosArm64/releaseFramework/ExportSDK.framework`

### Build All Platforms

```bash
./build-sdk.sh
```

This creates:
- Android AAR
- iOS Device Framework
- iOS Simulator Framework  
- iOS XCFramework (universal)

## 📱 Quick Start for Third-Party Developers

### Android Integration

1. **Add the AAR to your project**

```kotlin
// build.gradle.kts
dependencies {
    implementation(files("libs/ExportSDK-1.0.0.aar"))
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.material3:material3:1.2.1")
}
```

2. **Initialize the SDK**

```kotlin
// MyApplication.kt
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

3. **Use the Login Screen**

```kotlin
// MainActivity.kt
setContent {
    MaterialTheme {
        LoginScreen(
            authManager = AuthManager(),
            onLoginSuccess = { email ->
                // Handle successful login
            },
            onLoginError = { error ->
                // Handle error
            }
        )
    }
}
```

See [INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md) for complete examples.

## 📚 Documentation

- **[Integration Guide](INTEGRATION_GUIDE.md)** - Detailed integration instructions
- **[SDK README](composeApp/SDK_README.md)** - API reference
- **[Demo App Example](DEMO_APP_EXAMPLE.kt)** - Complete working example

## 🎯 Use Cases

This SDK is perfect for:
- Mobile apps requiring authentication
- Rapid prototyping with pre-built UI
- Cross-platform projects (Android + iOS)
- Third-party integrations

## 🔧 Development

### Project Structure

```
ExportProject/
├── composeApp/              # Main SDK module
│   ├── src/
│   │   ├── commonMain/      # Shared code
│   │   │   ├── kotlin/
│   │   │   │   └── org/example/export/
│   │   │   │       ├── ExportSDK.kt       # Main SDK class
│   │   │   │       ├── auth/
│   │   │   │       │   └── AuthManager.kt  # Auth logic
│   │   │   │       └── ui/
│   │   │   │           └── LoginScreen.kt  # UI component
│   │   ├── androidMain/     # Android-specific code
│   │   └── iosMain/         # iOS-specific code
│   └── build.gradle.kts     # SDK build configuration
├── build-aar.sh             # Quick AAR build script
├── build-sdk.sh             # Full build script
├── INTEGRATION_GUIDE.md     # Integration documentation
└── README.md                # This file
```

### Testing the SDK

```bash
# Run tests
./gradlew test

# Android instrumented tests
./gradlew connectedAndroidTest

# iOS tests
./gradlew iosTest
```

## 📋 Requirements

### For SDK Development
- Kotlin 2.1.0+
- Gradle 8.14+
- Android Gradle Plugin 8.11+

### For SDK Users (Android)
- Android SDK 24+
- Kotlin 1.9.0+
- Jetpack Compose

### For SDK Users (iOS)
- iOS 14.0+
- Xcode 15+

## 🔐 Security

- Email validation included
- Password minimum length enforced (6 characters)
- ProGuard rules for Android code obfuscation
- Secure session management

## 📄 License

MIT License - See LICENSE file for details

## 🤝 Support

For issues, questions, or feature requests:
- Create an issue on GitHub
- Email: support@example.com
- Documentation: [Integration Guide](INTEGRATION_GUIDE.md)

## 🎉 Version History

### v1.0.0 (Current)
- Initial release
- Email/password authentication
- Pre-built login UI
- Android AAR support
- iOS Framework support
- Session management

## 🚀 Roadmap

- [ ] Social login (Google, Facebook, Apple)
- [ ] Biometric authentication
- [ ] Password reset functionality
- [ ] Remember me feature
- [ ] Multi-language support
- [ ] Dark mode theme

---

**Built with ❤️ using Kotlin Multiplatform & Compose Multiplatform**

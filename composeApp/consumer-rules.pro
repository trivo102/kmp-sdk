# ProGuard rules for ExportSDK

# Keep all public APIs
-keep public class org.example.export.** { *; }

# Keep Compose
-keep class androidx.compose.** { *; }
-keepclassmembers class androidx.compose.** { *; }

# Keep Kotlin metadata
-keep class kotlin.Metadata { *; }

# Keep authentication classes
-keep class org.example.export.auth.** { *; }
-keep interface org.example.export.auth.** { *; }

# Keep UI components
-keep class org.example.export.ui.** { *; }

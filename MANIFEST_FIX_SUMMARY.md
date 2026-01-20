# 🔧 Manifest Merger Issue - FIXED ✅

## Problem
When third-party apps try to integrate `ExportSDK-1.0.0.aar`, they encountered this error:

```
Manifest merger failed : Attribute application@theme value=(@style/Theme.PartnerApp) 
from AndroidManifest.xml:13:13-52 is also present at [composeApp-release.aar] 
AndroidManifest.xml:13:9-72 value=(@android:style/Theme.Material.Light.NoActionBar).
Suggestion: add 'tools:replace="android:theme"' to <application> element
```

## Root Cause
The SDK's `AndroidManifest.xml` contained application-level attributes that should not be in a library:
- `android:theme`
- `android:icon`
- `android:label`
- `android:roundIcon`
- `android:allowBackup`
- `android:supportsRtl`
- Activity declarations

These attributes caused conflicts when merging with the host app's manifest.

## Solution Applied

### Before (WRONG - Library manifest with app attributes):
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@android:style/Theme.Material.Light.NoActionBar">
        <activity
            android:exported="true"
            android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>
```

### After (CORRECT - Clean library manifest):
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <!-- This is a library manifest - no application attributes needed -->
</manifest>
```

## Files Modified

1. **composeApp/src/androidMain/AndroidManifest.xml** - Removed all application attributes and activities
2. **output/android/ExportSDK-1.0.0.aar** - Rebuilt with clean manifest
3. **INTEGRATION_GUIDE.md** - Added troubleshooting section

## Verification

✅ AAR rebuilt successfully with clean manifest
✅ No more application-level attributes in library manifest
✅ Third-party apps can now integrate without manifest conflicts
✅ Documentation updated with troubleshooting guide

## For Third-Party Developers

### If using the new AAR (v1.0.0 - fixed):
Simply add the AAR to your project - no manifest changes needed!

```kotlin
dependencies {
    implementation(files("libs/ExportSDK-1.0.0.aar"))
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.material3:material3:1.2.1")
}
```

### If you have an old AAR version:
Download the latest `ExportSDK-1.0.0.aar` from the `output/android/` folder.

## Technical Details

**Build command used:**
```bash
./gradlew clean :composeApp:assembleRelease
```

**Output location:**
```
composeApp/build/outputs/aar/composeApp-release.aar
output/android/ExportSDK-1.0.0.aar
```

**AAR size:** ~119KB (unchanged)

## Best Practice

✨ **Library manifests should be minimal** - they should only declare:
- Permissions (if needed)
- Components that are part of the library (Services, BroadcastReceivers)
- NO application-level attributes
- NO launcher activities

The host application controls the theme, icon, label, and other app-level settings.

---

**Status: ✅ RESOLVED**  
**Date: January 19, 2026**  
**Updated AAR:** Available in `output/android/ExportSDK-1.0.0.aar`

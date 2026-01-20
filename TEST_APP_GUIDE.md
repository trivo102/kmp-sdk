# Test App - SDK Testing Guide

## Giới thiệu

`demoApp` là một Android application riêng được tạo để test các chức năng của SDK từ `composeApp` mà không cần phải export AAR và add vào project khác.

## Cấu trúc

```
demoApp/
├── src/
│   └── main/
│       ├── kotlin/com/example/demoapp/
│       │   └── MainActivity.kt        # Main activity với Compose UI
│       ├── AndroidManifest.xml         # App manifest
│       └── res/
│           └── values/
│               ├── strings.xml         # String resources
│               └── themes.xml          # Theme resources
└── build.gradle.kts                   # Build configuration
```

## Cách sử dụng

### 1. Build demoApp

```bash
./gradlew :demoApp:assembleDebug
```

APK sẽ được tạo tại:
```
demoApp/build/outputs/apk/debug/demoApp-debug.apk
```

### 2. Install và run trên emulator/device

```bash
./gradlew :demoApp:installDebug
```

Hoặc sử dụng Android Studio:
- Chọn "demoApp" từ run configuration dropdown
- Click "Run" button

### 3. Test các chức năng SDK

Trong file `MainActivity.kt`, bạn có thể:

- Import các class/function từ `composeApp`
- Thêm buttons để test các features
- Xem kết quả trực tiếp trên app

Ví dụ:

```kotlin
// Import từ SDK
import com.example.yoursdkname.YourSDKClass

Button(onClick = {
    val result = YourSDKClass.someFunction()
    testResults.value = "Result: $result"
}) {
    Text("Test My Feature")
}
```

### 4. Debug

Sử dụng Android Studio's debugger:
- Đặt breakpoint trong code
- Click "Debug" button thay vì "Run"
- Inspect variables, step through code, etc.

## Dependencies

`demoApp` có các dependency chính:
- `composeApp` - SDK mà bạn muốn test
- Android/Compose libraries (UI framework)

Bạn có thể thêm dependencies khác nếu cần:

```kotlin
dependencies {
    implementation("com.example:library:1.0.0")
}
```

## Lợi ích

✅ Test SDK ngay lập tức mà không cần export AAR  
✅ Hot reload khi code thay đổi  
✅ Sử dụng Android Studio's full debugging capabilities  
✅ Simulate real-world app usage  
✅ Test UI components trực tiếp  

## Tips

- Thêm nhiều test buttons để test các scenarios khác nhau
- Sử dụng state management (remember, mutableStateOf) để track test results
- Tạo multiple screens để organize tests
- Sử dụng Logcat để debug (./gradlew :demoApp:installDebug && adb logcat)

---

**Happy Testing!** 🚀

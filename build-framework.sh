#!/bin/bash

set -e

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
OUTPUT_DIR="$PROJECT_DIR/output/ios"

echo "================================="
echo "iOS Framework Build"
echo "================================="
echo ""

# Step 1: Create output directory
echo "Step 1: Preparing output directory..."
rm -rf "$OUTPUT_DIR"
mkdir -p "$OUTPUT_DIR/temp"
echo "✅ Ready"
echo ""

# Step 2: Check if frameworks exist, if not build them
SIM_ARM64="$PROJECT_DIR/composeApp/build/bin/iosSimulatorArm64/debugFramework/ExportSDK.framework"
SIM_X64="$PROJECT_DIR/composeApp/build/bin/iosX64/debugFramework/ExportSDK.framework"
DEVICE="$PROJECT_DIR/composeApp/build/bin/iosArm64/debugFramework/ExportSDK.framework"

echo "Step 2: Building frameworks if needed..."

if [ ! -d "$SIM_ARM64" ]; then
    echo "Building arm64 simulator..."
    ./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64 --quiet
fi
echo "✅ arm64 simulator ready"

if [ ! -d "$SIM_X64" ]; then
    echo "Building x86_64 simulator..."
    ./gradlew :composeApp:linkDebugFrameworkIosX64 --quiet
fi
echo "✅ x86_64 simulator ready"

if [ ! -d "$DEVICE" ]; then
    echo "Building arm64 device..."
    ./gradlew :composeApp:linkDebugFrameworkIosArm64 --quiet
fi
echo "✅ arm64 device ready"
echo ""

# Step 3: Create fat binary for simulators
echo "Step 3: Creating fat binary for simulators..."
mkdir -p "$OUTPUT_DIR/temp/ExportSDK.framework"

# Copy structure from arm64 simulator
cp -R "$SIM_ARM64/" "$OUTPUT_DIR/temp/ExportSDK.framework/"

# Create fat binary combining both simulator architectures
lipo -create \
    "$SIM_ARM64/ExportSDK" \
    "$SIM_X64/ExportSDK" \
    -output "$OUTPUT_DIR/temp/ExportSDK.framework/ExportSDK"

echo "✅ Fat simulator framework created"
echo ""

# Step 4: Create XCFramework with fat simulator + device
echo "Step 4: Creating XCFramework..."

xcodebuild -create-xcframework \
    -framework "$OUTPUT_DIR/temp/ExportSDK.framework" \
    -framework "$DEVICE" \
    -output "$OUTPUT_DIR/ExportSDK.xcframework"

if [ $? -eq 0 ]; then
    echo "✅ XCFramework created successfully"
else
    echo "❌ Failed to create XCFramework"
    exit 1
fi
echo ""

# Step 5: Clean up temp
rm -rf "$OUTPUT_DIR/temp"

# Step 6: Copy individual frameworks
echo "Step 5: Copying individual frameworks for reference..."
cp -R "$SIM_ARM64" "$OUTPUT_DIR/ExportSDK-Simulator-arm64.framework"
cp -R "$SIM_X64" "$OUTPUT_DIR/ExportSDK-Simulator-x64.framework"
cp -R "$DEVICE" "$OUTPUT_DIR/ExportSDK-Device.framework"
echo "✅ Individual frameworks copied"
echo ""

# Step 7: Verify
echo "Step 6: Verification..."
if [ -d "$OUTPUT_DIR/ExportSDK.xcframework" ]; then
    echo "✅ XCFramework exists"
    echo ""
    echo "Framework structure:"
    ls -la "$OUTPUT_DIR/ExportSDK.xcframework/"
    echo ""

    # Check architectures
    if [ -f "$OUTPUT_DIR/ExportSDK.xcframework/ios-arm64_x86_64-simulator/ExportSDK.framework/ExportSDK" ]; then
        echo "Simulator architectures:"
        lipo -info "$OUTPUT_DIR/ExportSDK.xcframework/ios-arm64_x86_64-simulator/ExportSDK.framework/ExportSDK"
    fi

    if [ -f "$OUTPUT_DIR/ExportSDK.xcframework/ios-arm64/ExportSDK.framework/ExportSDK" ]; then
        echo "Device architectures:"
        lipo -info "$OUTPUT_DIR/ExportSDK.xcframework/ios-arm64/ExportSDK.framework/ExportSDK"
    fi
else
    echo "❌ XCFramework not created"
    exit 1
fi
echo ""

# Done
echo "================================="
echo "✅ BUILD COMPLETE!"
echo "================================="
echo ""
echo "📍 Framework location:"
echo "   $OUTPUT_DIR/ExportSDK.xcframework"
echo ""
echo "🎯 Architectures supported:"
echo "   ✅ arm64 + x86_64 (Simulators - Intel & M1/M2)"
echo "   ✅ arm64 (iPhone/iPad Devices)"
echo ""
echo "📋 Files created:"
ls -lh "$OUTPUT_DIR/" | grep -v total | grep -v temp
echo ""
echo "🚀 Ready to use!"
echo ""
echo "Next steps:"
echo "1. Copy to your project:"
echo "   cp -R $OUTPUT_DIR/ExportSDK.xcframework ~/YourProject/"
echo ""
echo "2. In Xcode:"
echo "   - Add ExportSDK.xcframework to project"
echo "   - Ensure 'Embed & Sign'"
echo "   - Clean Build Folder (Cmd+Shift+K)"
echo "   - Build & Run"
echo ""

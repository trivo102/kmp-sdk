#!/bin/bash

# Build script for ExportSDK
# This script builds both Android AAR and iOS Framework

set -e

echo "🚀 Building ExportSDK..."

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Clean previous builds
echo -e "${BLUE}📦 Cleaning previous builds...${NC}"
./gradlew clean

# Build Android AAR
echo -e "${BLUE}📱 Building Android AAR...${NC}"
./gradlew :composeApp:assembleRelease

if [ -f "composeApp/build/outputs/aar/composeApp-release.aar" ]; then
    echo -e "${GREEN}✅ Android AAR built successfully!${NC}"
    echo "   Location: composeApp/build/outputs/aar/composeApp-release.aar"

    # Create output directory
    mkdir -p output/android
    cp composeApp/build/outputs/aar/composeApp-release.aar output/android/ExportSDK-1.0.0.aar
    echo -e "${GREEN}   Copied to: output/android/ExportSDK-1.0.0.aar${NC}"
else
    echo "❌ Failed to build Android AAR"
    exit 1
fi

# Build iOS Framework (Arm64 - Real device)
echo -e "${BLUE}📱 Building iOS Framework (Arm64)...${NC}"
./gradlew :composeApp:linkReleaseFrameworkIosArm64

if [ -d "composeApp/build/bin/iosArm64/releaseFramework/ExportSDK.framework" ]; then
    echo -e "${GREEN}✅ iOS Framework (Arm64) built successfully!${NC}"
    echo "   Location: composeApp/build/bin/iosArm64/releaseFramework/ExportSDK.framework"

    # Create output directory
    mkdir -p output/ios
    cp -R composeApp/build/bin/iosArm64/releaseFramework/ExportSDK.framework output/ios/
    echo -e "${GREEN}   Copied to: output/ios/ExportSDK.framework${NC}"
else
    echo "❌ Failed to build iOS Framework"
    exit 1
fi

# Build iOS Framework (Simulator)
echo -e "${BLUE}📱 Building iOS Framework (Simulator)...${NC}"
./gradlew :composeApp:linkReleaseFrameworkIosSimulatorArm64

if [ -d "composeApp/build/bin/iosSimulatorArm64/releaseFramework/ExportSDK.framework" ]; then
    echo -e "${GREEN}✅ iOS Framework (Simulator) built successfully!${NC}"
    echo "   Location: composeApp/build/bin/iosSimulatorArm64/releaseFramework/ExportSDK.framework"

    cp -R composeApp/build/bin/iosSimulatorArm64/releaseFramework/ExportSDK.framework output/ios/ExportSDK-Simulator.framework
    echo -e "${GREEN}   Copied to: output/ios/ExportSDK-Simulator.framework${NC}"
fi

# Create XCFramework (combines device and simulator)
echo -e "${BLUE}📦 Creating XCFramework...${NC}"
if [ -d "composeApp/build/bin/iosArm64/releaseFramework/ExportSDK.framework" ] && \
   [ -d "composeApp/build/bin/iosSimulatorArm64/releaseFramework/ExportSDK.framework" ]; then

    xcodebuild -create-xcframework \
        -framework composeApp/build/bin/iosArm64/releaseFramework/ExportSDK.framework \
        -framework composeApp/build/bin/iosSimulatorArm64/releaseFramework/ExportSDK.framework \
        -output output/ios/ExportSDK.xcframework

    echo -e "${GREEN}✅ XCFramework created successfully!${NC}"
    echo "   Location: output/ios/ExportSDK.xcframework"
fi

# Copy README
cp composeApp/SDK_README.md output/README.md

echo ""
echo -e "${GREEN}🎉 Build completed successfully!${NC}"
echo ""
echo "Output files:"
echo "  📱 Android: output/android/ExportSDK-1.0.0.aar"
echo "  📱 iOS Device: output/ios/ExportSDK.framework"
echo "  📱 iOS Simulator: output/ios/ExportSDK-Simulator.framework"
echo "  📦 iOS XCFramework: output/ios/ExportSDK.xcframework"
echo "  📄 README: output/README.md"
echo ""
echo "You can now distribute these files to third-party developers!"

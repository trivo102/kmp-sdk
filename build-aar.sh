#!/bin/bash

# Quick Android AAR build script for ExportSDK

set -e

echo "🚀 Building ExportSDK for Android..."

# Colors for output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Clean previous builds
echo -e "${BLUE}📦 Cleaning previous builds...${NC}"
./gradlew clean > /dev/null 2>&1

# Build Android AAR
echo -e "${BLUE}📱 Building Android AAR...${NC}"
./gradlew :composeApp:assembleRelease

if [ -f "composeApp/build/outputs/aar/composeApp-release.aar" ]; then
    echo -e "${GREEN}✅ Android AAR built successfully!${NC}"
    echo "   Location: composeApp/build/outputs/aar/composeApp-release.aar"

    # Create output directory
    mkdir -p output/android
    cp composeApp/build/outputs/aar/composeApp-release.aar output/android/ExportSDK-1.0.0.aar

    # Get file size
    SIZE=$(ls -lh output/android/ExportSDK-1.0.0.aar | awk '{print $5}')
    echo -e "${GREEN}   Copied to: output/android/ExportSDK-1.0.0.aar (${SIZE})${NC}"

    # Copy README
    cp composeApp/SDK_README.md output/README.md 2>/dev/null || true
    cp INTEGRATION_GUIDE.md output/ 2>/dev/null || true

    echo ""
    echo -e "${GREEN}🎉 Build completed successfully!${NC}"
    echo ""
    echo "Output files:"
    echo "  📱 Android AAR: output/android/ExportSDK-1.0.0.aar"
    echo "  📄 README: output/README.md"
    echo "  📄 Integration Guide: output/INTEGRATION_GUIDE.md"
    echo ""
    echo "You can now distribute the AAR file to third-party Android developers!"
    echo ""
    echo "To build iOS Framework, run:"
    echo "  ./gradlew :composeApp:linkReleaseFrameworkIosArm64"
else
    echo "❌ Failed to build Android AAR"
    exit 1
fi

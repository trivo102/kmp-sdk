package org.example.export.util

import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

// Cài đặt thực tế (actual): iOS dùng NSString của Objective-C
actual fun Float.format(digits: Int): String {
    return NSString.stringWithFormat("%.${digits}f", this)
}
package org.example.export.util

actual fun Float.format(digits: Int): String {
    return String.format("%.${digits}f", this)
}
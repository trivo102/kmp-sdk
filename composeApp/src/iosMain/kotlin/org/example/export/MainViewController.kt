package org.example.export

import androidx.compose.ui.window.ComposeUIViewController
import org.example.export.theme.AppTheme
import platform.UIKit.UIViewController


fun createMainViewController(): UIViewController =
    ComposeUIViewController {
        AppTheme {
            ExportApp()
        }
    }

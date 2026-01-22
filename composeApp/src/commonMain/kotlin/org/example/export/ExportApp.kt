package org.example.export

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.export.navigation.AppNavController
import org.example.export.navigation.AppNavHost
import org.example.export.theme.AppTheme

@Composable
internal fun ExportApp() {
    AppTheme {
        val navController = remember { AppNavController() }
        AppNavHost(navController)
    }
}
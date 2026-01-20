package org.example.export

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.example.export.navigation.AppNavHost
import org.example.export.theme.AppTheme

@Composable
internal fun ExportApp() {
    AppTheme {
        val navController = rememberNavController()
        AppNavHost(navController)
    }
}
package org.example.export.navigation

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class AppNavController {
    var currentRoute by mutableStateOf(NavRoute.Loan.route)
}
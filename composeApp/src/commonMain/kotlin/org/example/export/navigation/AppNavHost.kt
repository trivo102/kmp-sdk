package org.example.export.navigation

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import org.example.export.screen.ekyc.EkycScreen
import org.example.export.screen.loan.LoanScreen
import org.example.export.screen.result.ResultScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoute.Loan.route
    ) {
        composable(NavRoute.Loan.route) {
            LoanScreen(
                onNext = { navController.navigate(NavRoute.Ekyc.route) }
            )
        }

        composable(NavRoute.Ekyc.route) {
            EkycScreen(
                onSuccess = {
                    println("EkycScreen: onSuccess")
                    navController.navigate(NavRoute.Result.route)
                }
            )
        }

        composable(NavRoute.Result.route) {
            ResultScreen(
                onBackHome = {
                    navController.popBackStack(
                        NavRoute.Loan.route,
                        inclusive = false
                    )
                }
            )
        }
    }
}

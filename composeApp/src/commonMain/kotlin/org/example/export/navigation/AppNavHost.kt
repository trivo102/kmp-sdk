package org.example.export.navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.example.export.screen.ekyc.EkycScreen
import org.example.export.screen.form.FormScreen
import org.example.export.screen.loan.LoanScreen
import org.example.export.screen.nfc.NfcScreen
import org.example.export.screen.result.ResultScreen

@Composable
fun AppNavHost(navController: AppNavController = remember { AppNavController() }) {
    val route = navController.currentRoute

    when (route) {
        NavRoute.Loan.route -> {
            LoanScreen(
                onContinueClick = {
                    navController.currentRoute = NavRoute.Ekyc.route
                },
                onBackClick = {}
            )
        }

        NavRoute.Ekyc.route -> {
            EkycScreen(
                onSuccess = {
                    println("EkycScreen: onSuccess")
                    navController.currentRoute = NavRoute.Form.route
                }
            )
        }

        NavRoute.Form.route -> {
            FormScreen(
                onSubmit = {
                    navController.currentRoute = NavRoute.Nfc.route
                }
            )
        }

        NavRoute.Nfc.route -> {
            NfcScreen(
                onSuccess = {
                    navController.currentRoute = NavRoute.Result.route
                }
            )
        }

        NavRoute.Result.route -> {
            ResultScreen(
                onBackHome = {
                    navController.currentRoute = NavRoute.Loan.route
                }
            )
        }
    }
}

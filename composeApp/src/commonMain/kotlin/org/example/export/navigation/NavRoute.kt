package org.example.export.navigation

sealed class NavRoute(val route: String) {
    object Loan : NavRoute("loan")
    object Ekyc : NavRoute("ekyc")
    object Result : NavRoute("result")

    object Form : NavRoute("form")
}
package org.example.export.screen.loan

import org.example.export.model.LoanPlan

class LoanViewModel {
    val plans = listOf(
        LoanPlan("20.000.000", "1.850.000 / tháng"),
        LoanPlan("30.000.000", "2.700.000 / tháng")
    )
}
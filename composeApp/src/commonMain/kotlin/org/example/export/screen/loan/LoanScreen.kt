package org.example.export.screen.loan

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.export.component.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanScreen(onNext: () -> Unit) {
    val vm = remember { LoanViewModel() }

    Scaffold { paddingValues ->

        MyContent(
            modifier = Modifier.padding(paddingValues),
            vm = vm,
            onNext = onNext
        )
    }

}

@Composable
fun MyContent(modifier: Modifier = Modifier, vm: LoanViewModel, onNext: () -> Unit) {
    Column(
        modifier = modifier.padding(top = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Chọn gói vay", style = MaterialTheme.typography.headlineSmall)

        vm.plans.forEach {
            Card {
                Column(Modifier.padding(16.dp)) {
                    Text("Số tiền vay: ${it.amount}")
                    Text("Thanh toán: ${it.monthly}")
                }
            }
        }

        PrimaryButton(
            text = "Tiếp tục",
            onClick = onNext
        )
    }
}

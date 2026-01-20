package org.example.export.screen.form

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.export.component.PrimaryButton

@Composable
internal fun FormScreen(
    onSubmit: () -> Unit
) {
    Scaffold { scaffoldPadding ->
        FormBody(
            modifier = Modifier.padding(scaffoldPadding),
            onSubmit = onSubmit
        )
    }
}
@Composable
private fun FormBody(
    modifier: Modifier,
    onSubmit: () -> Unit
) {
    val vm = remember { FormViewModel() }
    val state by vm.state.collectAsState()

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Bổ sung thông tin",
            style = MaterialTheme.typography.headlineSmall
        )

        StepIndicator(state.step)

        when (state.step) {
            FormStep.PERSONAL -> PersonalStep(state, vm)
            FormStep.JOB -> JobStep(state, vm)
            FormStep.ADDRESS -> AddressStep(state, vm)
            FormStep.EMERGENCY -> EmergencyStep(state, vm)
            FormStep.REVIEW -> ReviewStep(state)
        }

        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            if (state.step != FormStep.PERSONAL) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    onClick = vm::back
                ) { Text("Quay lại") }
            }

            PrimaryButton(
                modifier = Modifier.weight(1f),
                text = if (state.step == FormStep.REVIEW) "Xác nhận" else "Tiếp tục",
                onClick = {
                    if (state.step == FormStep.REVIEW) onSubmit()
                    else vm.next()
                }
            )
        }
    }
}
@Composable
private fun PersonalStep(state: FormState, vm: FormViewModel) {
    OutlinedTextField(
        value = state.data.fullName,
        onValueChange = { vm.updateData { copy(fullName = it) } },
        label = { Text("Họ và tên") },
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
private fun JobStep(state: FormState, vm: FormViewModel) {
    OutlinedTextField(
        value = state.data.income,
        onValueChange = { vm.updateData { copy(income = it) } },
        label = { Text("Thu nhập hàng tháng") },
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
private fun StepIndicator(step: FormStep) {
    Text(
        text = "Bước: ${step.name}",
        style = MaterialTheme.typography.labelMedium
    )
}
@Composable
private fun AddressStep(
    state: FormState,
    vm: FormViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        OutlinedTextField(
            value = state.data.address,
            onValueChange = {
                vm.updateData { copy(address = it) }
            },
            label = { Text("Địa chỉ làm việc / cư trú") },
            placeholder = { Text("Ví dụ: 58 Trần Hưng Đạo, Quận 1") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            minLines = 2
        )
    }
}
@Composable
private fun EmergencyStep(
    state: FormState,
    vm: FormViewModel
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

        OutlinedTextField(
            value = state.data.emergencyName,
            onValueChange = {
                vm.updateData { copy(emergencyName = it) }
            },
            label = { Text("Họ và tên người liên hệ") },
            placeholder = { Text("Ví dụ: Nguyễn Văn B") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = state.data.emergencyPhone,
            onValueChange = {
                vm.updateData { copy(emergencyPhone = it) }
            },
            label = { Text("Số điện thoại") },
            placeholder = { Text("Ví dụ: 090 888 1234") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
@Composable
private fun ReviewStep(
    state: FormState
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

        Text(
            text = "Xác nhận thông tin",
            style = MaterialTheme.typography.titleMedium
        )

        ReviewItem("Họ và tên", state.data.fullName)
        ReviewItem("Thu nhập", state.data.income)
        ReviewItem("Địa chỉ", state.data.address)
        ReviewItem("Người liên hệ", state.data.emergencyName)
        ReviewItem("SĐT liên hệ", state.data.emergencyPhone)
    }
}
@Composable
private fun ReviewItem(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = if (value.isNotBlank()) value else "--",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

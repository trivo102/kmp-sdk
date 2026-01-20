package org.example.export.screen.form

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.example.export.screen.form.model.FormData

internal class FormViewModel {

    private val _state = MutableStateFlow(FormState())
    val state: StateFlow<FormState> = _state

    fun updateData(block: FormData.() -> FormData) {
        _state.update {
            it.copy(data = block(it.data), error = null)
        }
    }

    fun next() {
        val current = _state.value

        if (!validate(current)) return

        val nextStep = when (current.step) {
            FormStep.PERSONAL -> FormStep.JOB
            FormStep.JOB -> FormStep.ADDRESS
            FormStep.ADDRESS -> FormStep.EMERGENCY
            FormStep.EMERGENCY -> FormStep.REVIEW
            FormStep.REVIEW -> FormStep.REVIEW
        }

        _state.update { it.copy(step = nextStep) }
    }

    fun back() {
        val prev = when (_state.value.step) {
            FormStep.JOB -> FormStep.PERSONAL
            FormStep.ADDRESS -> FormStep.JOB
            FormStep.EMERGENCY -> FormStep.ADDRESS
            FormStep.REVIEW -> FormStep.EMERGENCY
            else -> FormStep.PERSONAL
        }
        _state.update { it.copy(step = prev) }
    }

    private fun validate(state: FormState): Boolean {
        val ok = when (state.step) {
            FormStep.PERSONAL -> state.data.fullName.isNotBlank()
            FormStep.JOB -> state.data.income.isNotBlank()
            FormStep.ADDRESS -> state.data.address.isNotBlank()
            FormStep.EMERGENCY ->
                state.data.emergencyName.isNotBlank()
                        && state.data.emergencyPhone.isNotBlank()
            FormStep.REVIEW -> true
        }

        if (!ok) {
            _state.update { it.copy(error = "Vui lòng nhập đầy đủ thông tin") }
        }

        return ok
    }
}

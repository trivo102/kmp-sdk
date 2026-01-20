package org.example.export.screen.form

import org.example.export.screen.form.model.FormData

data class FormState(
    val step: FormStep = FormStep.PERSONAL,
    val data: FormData = FormData(),
    val error: String? = null
)
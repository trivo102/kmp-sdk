package org.example.export.screen.ekyc

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.random.Random

internal class EkycViewModel {

    private val _state = MutableStateFlow<EkycState>(EkycState.CaptureFront)
    val state: StateFlow<EkycState> = _state

    fun onEvent(event: EkycEvent) {
        when (event) {
            EkycEvent.OnCaptureDone -> next()
            EkycEvent.OnRetry -> _state.value = EkycState.CaptureFront
        }
    }

    private fun next() {
        when (_state.value) {
            EkycState.CaptureFront -> _state.value = EkycState.CaptureBack
            EkycState.CaptureBack -> _state.value = EkycState.CaptureFace
            EkycState.CaptureFace -> process()
            else -> Unit
        }
    }

    private fun process() {
        _state.value = EkycState.Processing

        GlobalScope.launch {
            delay(1500)

            val success = Random.nextBoolean()
            _state.update {
                if (success) EkycState.Success
                else EkycState.Failed("eKYC không thành công, vui lòng thử lại")
            }
        }
    }
}

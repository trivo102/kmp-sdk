package org.example.export.screen.nfc

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

internal class NfcViewModel {

    private val scope = CoroutineScope(Dispatchers.Default)

    private val _state = MutableStateFlow<NfcState>(NfcState.Checking)
    val state: StateFlow<NfcState> = _state

    fun onEvent(event: NfcEvent) {
        when (event) {
            NfcEvent.Start -> checkNfc()
            NfcEvent.StartNfc -> startNfcScan()
            NfcEvent.UseQr -> generateQr()
            NfcEvent.Retry -> checkNfc()
        }
    }

    private fun checkNfc() {
        _state.value = NfcState.Checking

        scope.launch {
            delay(800)
            val hasNfc = Random.nextBoolean()
            _state.value =
                if (hasNfc) NfcState.NfcAvailable
                else NfcState.QrGenerated
        }
    }

    private fun startNfcScan() {
        _state.value = NfcState.NfcScanning

        scope.launch {
            delay(2000)
            val ok = Random.nextBoolean()
            _state.value =
                if (ok) NfcState.Success
                else NfcState.Failed("Quét NFC không thành công")
        }
    }

    private fun generateQr() {
        _state.value = NfcState.QrGenerated

        scope.launch {
            delay(1500)
            _state.value = NfcState.WaitingExternalScan

            delay(3000)
            val ok = true
            _state.value =
                if (ok) NfcState.Success
                else NfcState.Expired
        }
    }
}

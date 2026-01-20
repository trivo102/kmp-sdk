package org.example.export.screen.nfc

sealed class NfcState {
    object Checking : NfcState()
    object NfcAvailable : NfcState()
    object NfcScanning : NfcState()
    object QrGenerated : NfcState()
    object WaitingExternalScan : NfcState()
    data class Failed(val message: String) : NfcState()
    object Success : NfcState()
    object Expired : NfcState()
}
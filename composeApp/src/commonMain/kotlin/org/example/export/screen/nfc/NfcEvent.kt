package org.example.export.screen.nfc

sealed class NfcEvent {
    object Start : NfcEvent()
    object StartNfc : NfcEvent()
    object UseQr : NfcEvent()
    object Retry : NfcEvent()
}
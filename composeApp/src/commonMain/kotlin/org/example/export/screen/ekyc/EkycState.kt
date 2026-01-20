package org.example.export.screen.ekyc

sealed class EkycState {
    object CaptureFront : EkycState()
    object CaptureBack : EkycState()
    object CaptureFace : EkycState()
    object Processing : EkycState()
    data class Failed(val message: String) : EkycState()
    object Success : EkycState()
}
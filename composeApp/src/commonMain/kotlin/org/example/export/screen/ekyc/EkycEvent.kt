package org.example.export.screen.ekyc

sealed class EkycEvent {
    object OnCaptureDone : EkycEvent()
    object OnRetry : EkycEvent()
}
package org.example.export.screen.nfc


import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.export.component.PrimaryButton

@Composable
internal fun NfcScreen(
    onSuccess: () -> Unit
) {
    val vm = remember { NfcViewModel() }
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.onEvent(NfcEvent.Start)
    }

    when (state) {

        NfcState.Checking ->
            LoadingView("Đang kiểm tra NFC...")

        NfcState.NfcAvailable ->
            ActionView(
                title = "Thiết bị hỗ trợ NFC",
                desc = "Đặt CCCD gắn chip vào mặt sau điện thoại",
                primary = "Bắt đầu quét",
                onPrimary = { vm.onEvent(NfcEvent.StartNfc) },
                secondary = "Dùng QR",
                onSecondary = { vm.onEvent(NfcEvent.UseQr) }
            )

        NfcState.NfcScanning ->
            LoadingView("Đang quét CCCD gắn chip...")

        NfcState.QrGenerated ->
            LoadingView("Đang tạo mã QR...")

        NfcState.WaitingExternalScan ->
            ActionView(
                title = "Quét QR bằng thiết bị khác",
                desc = "Mở HPO App hoặc camera để quét mã QR",
                primary = "Chờ quét",
                onPrimary = {}
            )

        is NfcState.Failed ->
            ErrorView(
                message = (state as NfcState.Failed).message,
                onRetry = { vm.onEvent(NfcEvent.Retry) }
            )

        NfcState.Expired ->
            ErrorView(
                message = "Mã QR đã hết hạn",
                onRetry = { vm.onEvent(NfcEvent.Retry) }
            )

        NfcState.Success -> {
            LaunchedEffect(Unit) { onSuccess() }
        }
    }
}
@Composable
private fun LoadingView(text: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(Modifier.height(16.dp))
        Text(text)
    }
}
@Composable
private fun ActionView(
    title: String,
    desc: String,
    primary: String,
    onPrimary: () -> Unit,
    secondary: String? = null,
    onSecondary: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text(desc)

        PrimaryButton(text = primary, onClick = onPrimary)

        if (secondary != null && onSecondary != null) {
            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onSecondary
            ) {
                Text(secondary)
            }
        }
    }
}
@Composable
private fun ErrorView(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("❌ Thất bại", style = MaterialTheme.typography.headlineSmall)
        Text(message)

        PrimaryButton(
            text = "Thử lại",
            onClick = onRetry
        )
    }
}

package org.example.export.screen.ekyc

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.export.component.PrimaryButton
import org.example.export.screen.loan.MyContent

@Composable
internal fun EkycScreen(
    onSuccess: () -> Unit
) {

    Scaffold { paddingValues ->

        EkycBody(
            modifier = Modifier.padding(paddingValues),
            onSuccess = onSuccess,
        )
    }

}

@Composable
private fun EkycBody(
    modifier: Modifier,
    onSuccess: () -> Unit
) {
    val vm = remember { EkycViewModel() }
    val state by vm.state.collectAsState()
    when (state) {
        EkycState.CaptureFront ->
            CaptureStep(
                modifier,
                title = "Chụp CCCD mặt trước",
                onDone = { vm.onEvent(EkycEvent.OnCaptureDone) }
            )

        EkycState.CaptureBack ->
            CaptureStep(
                modifier,
                title = "Chụp CCCD mặt sau",
                onDone = { vm.onEvent(EkycEvent.OnCaptureDone) }
            )

        EkycState.CaptureFace ->
            CaptureStep(
                modifier,
                title = "Chụp khuôn mặt",
                onDone = { vm.onEvent(EkycEvent.OnCaptureDone) }
            )

        EkycState.Processing ->
            ProcessingView(modifier)

        is EkycState.Failed ->
            ErrorView(
                modifier,
                message = (state as EkycState.Failed).message,
                onRetry = { vm.onEvent(EkycEvent.OnRetry) }
            )

        EkycState.Success -> {
            LaunchedEffect(Unit) { onSuccess() }
        }
    }
}

@Composable
private fun CaptureStep(
    modifier: Modifier,
    title: String,
    onDone: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        Text("Giả lập camera – nhấn nút để tiếp tục")

        PrimaryButton(
            text = "Chụp xong",
            onClick = {
                onDone.invoke()
            }
        )
    }
}
@Composable
private fun ProcessingView(
    modifier: Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier.height(16.dp))
        Text("Đang xác thực eKYC...")
    }
}
@Composable
private fun ErrorView(
    modifier: Modifier,
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp),
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

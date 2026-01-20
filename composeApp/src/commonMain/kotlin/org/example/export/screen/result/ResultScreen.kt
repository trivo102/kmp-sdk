package org.example.export.screen.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.export.component.PrimaryButton

@Composable
fun ResultScreen(onBackHome: () -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text("🎉 Thành công", style = MaterialTheme.typography.headlineMedium)
        Text("Hồ sơ của bạn đã được gửi thành công")

        PrimaryButton(
            text = "Về trang chủ",
            onClick = onBackHome
        )
    }
}

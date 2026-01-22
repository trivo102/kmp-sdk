package org.example.export.screen.loan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.export.util.format

val PrimaryRed = Color(0xFFD32F2F)
val PrimaryOrange = Color(0xFFFF9800)
val GradientStart = Color(0xFFFFC107)
val GradientEnd = Color(0xFFD32F2F)
val TextBlue = Color(0xFF1976D2)
val TextGray = Color(0xFF757575)
val BackgroundGray = Color(0xFFF5F5F5)

data class LoanPackage(
    val months: Int,
    val monthlyPayment: String,
    val documents: String,
    val hasInsurance: Boolean,
    val bonusAmount: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanScreen(onBackClick: () -> Unit = {},
               onContinueClick: () -> Unit = {}) {
    // Fake Data
    val packages = listOf(
        LoanPackage(15, "1,850", "CCCD, BLX, BHYT, BHXH", true, "10.000.000 đ"),
        LoanPackage(18, "1,850", "CCCD, BLX, BHYT, BHXH", true, "10.000.000 đ"),
        LoanPackage(22, "1,850", "CCCD, BLX, BHYT, BHXH", true, "10.000.000 đ")
    )

    var selectedPackageIndex by remember { mutableStateOf(0) }
    var paymentAmount by remember { mutableStateOf(1.85f) } // Float slider value
    var loanAmount by remember { mutableStateOf(20f) }      // Float slider value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            BottomActionSection(onContinueClick)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // 1. Slider Section
            Text(
                "Bạn muốn thanh toán hàng tháng bao nhiêu?",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            LoanSlider(
                value = paymentAmount,
                onValueChange = { paymentAmount = it },
                valueRange = 0.5f..5f,
                suffix = "triệu"
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "Số tiền vay",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            LoanSlider(
                value = loanAmount,
                onValueChange = { loanAmount = it },
                valueRange = 10f..50f,
                suffix = "triệu",
                isInteger = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Loan Packages List
            packages.forEachIndexed { index, item ->
                LoanPackageCard(
                    data = item,
                    isSelected = index == selectedPackageIndex,
                    onClick = { selectedPackageIndex = index }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Padding bottom để không bị che bởi button
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

}

// --- Component: Custom Slider ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoanSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    suffix: String,
    isInteger: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Slider chiếm phần lớn diện tích
        Box(modifier = Modifier.weight(1f)) {
            Slider(
                value = value,
                onValueChange = onValueChange,
                valueRange = valueRange,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Transparent, // Ẩn thumb mặc định để dùng custom nếu cần
                    activeTrackColor = GradientEnd,
                    inactiveTrackColor = Color.LightGray
                ),
                thumb = {
                    // Custom Thumb: Giả lập logo HD bằng hình tròn có viền
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .background(Color.White, CircleShape)
                            .border(2.dp, GradientEnd, CircleShape)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("HD", style = MaterialTheme.typography.labelSmall, fontSize = 6.sp, color = PrimaryRed)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Text hiển thị giá trị bên phải
        val displayValue = if (isInteger) value.toInt().toString() else value.format(2).replace('.', ',')

        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)) {
                    append(displayValue)
                }
                withStyle(SpanStyle(fontSize = 14.sp)) {
                    append(" $suffix")
                }
            }
        )
    }
}

// --- Component: Loan Package Card ---
@Composable
fun LoanPackageCard(
    data: LoanPackage,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) GradientStart else Color.Transparent
    val elevation = if (isSelected) 4.dp else 1.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(width = if (isSelected) 1.5.dp else 0.dp, color = borderColor, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            // Phần nội dung chính
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Circle Badge: Số tháng
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            brush = Brush.verticalGradient(listOf(GradientStart, GradientEnd)),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${data.months}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "tháng",
                            color = Color.White,
                            fontSize = 10.sp
                        )
                    }
                    // Badge nhỏ "Có bảo hiểm"
                    if (data.hasInsurance) {
                        // Trong thực tế design này phức tạp, ở đây demo text bên dưới
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Info Text
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Thanh toán\nmỗi tháng", fontSize = 12.sp, color = TextBlue, lineHeight = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "${data.monthlyPayment} triệu",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextBlue
                        )
                    }
                    Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            data.documents,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Info",
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Phần footer màu xám nhạt (Ưu đãi)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackgroundGray)
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Ưu đãi\ntặng kèm",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    lineHeight = 14.sp
                )
                Spacer(modifier = Modifier.width(12.dp))
                // Placeholder cho hình thẻ tín dụng
                Box(modifier = Modifier.size(width = 40.dp, height = 25.dp).background(Color.Blue, RoundedCornerShape(4.dp)))

                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text("Thẻ HD SAISON Visa", fontSize = 10.sp, color = Color.Gray)
                    Text(data.bonusAmount, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = PrimaryRed)
                }
            }
        }
    }
}

// --- Component: Footer (Disclaimer + Button) ---
@Composable
fun BottomActionSection(onContinueClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Disclaimer Text
        val disclaimerText = buildAnnotatedString {
            append("Bằng cách chọn ")
            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Tiếp tục") }
            append(", Quý khách xác nhận đã đọc và đồng ý với toàn bộ nội dung ")
            withStyle(SpanStyle(color = TextBlue)) { append("Điều khoản và Điều kiện sử dụng") }
            append("... (thu gọn)")
        }

        Text(
            text = disclaimerText,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 11.sp,
            color = Color.Black,
            lineHeight = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Gradient Button
        Button(
            onClick = onContinueClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues() // Xóa padding để background gradient phủ kín
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(listOf(GradientStart, GradientEnd)),
                        shape = RoundedCornerShape(25.dp) // Bo tròn button
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tiếp tục",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
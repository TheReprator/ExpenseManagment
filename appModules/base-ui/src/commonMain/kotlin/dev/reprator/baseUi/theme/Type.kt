package dev.reprator.baseUi.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val AccountBookTypography: Typography
    @Composable get() {
        // Eugh, this is gross but there is no defaultFontFamily property in M3
        val default = Typography()
        val fontFamily = DmSansFontFamily
        return Typography(
            displayLarge = default.displayLarge.copy(fontFamily = fontFamily),
            displayMedium = default.displayMedium.copy(fontFamily = fontFamily),
            displaySmall = default.displaySmall.copy(fontFamily = fontFamily),
            headlineLarge = default.headlineLarge.copy(fontFamily = fontFamily),
            headlineMedium = default.headlineMedium.copy(fontFamily = fontFamily),
            headlineSmall = default.headlineSmall.copy(fontFamily = fontFamily),
            titleLarge = default.titleLarge.copy(
                fontFamily = fontFamily, fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            ),
            titleMedium = default.titleMedium.copy(fontFamily = fontFamily),
            titleSmall = default.titleSmall.copy(fontFamily = fontFamily),
            bodyLarge = default.bodyLarge.copy(fontFamily = fontFamily),
            bodyMedium = default.bodyMedium.copy(fontFamily = fontFamily),
            bodySmall = default.bodySmall.copy(fontFamily = fontFamily),
            labelLarge = default.labelLarge.copy(fontFamily = fontFamily),
            labelMedium = default.labelMedium.copy(fontFamily = fontFamily),
            labelSmall = default.labelSmall.copy(fontFamily = fontFamily),
        )
    }

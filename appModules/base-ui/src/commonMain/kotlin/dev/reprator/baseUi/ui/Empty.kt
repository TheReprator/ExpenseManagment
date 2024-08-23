package dev.reprator.baseUi.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyContent(
  graphic: @Composable () -> Unit,
  title: @Composable () -> Unit,
  prompt: @Composable () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(modifier = Modifier
    .fillMaxSize()
    .wrapContentSize(Alignment.Center)) {

    Column {

      val density = LocalDensity.current
      val emojiHeaderGraphicTextStyle = remember(density) {
        TextStyle(
          // We don't want font scaling to affect this size
          fontSize = 96.dp.asEm(density),
          // Any opaque color will work here
          color = Color.Magenta,
        )
      }

      ProvideTextStyle(emojiHeaderGraphicTextStyle) {
        Box(Modifier.align(Alignment.CenterHorizontally)) {
          graphic()
        }
      }
      ProvideTextStyle(MaterialTheme.typography.headlineMedium) {
        title()
      }
      ProvideTextStyle(MaterialTheme.typography.bodyLarge) {
        prompt()
      }
    }
  }
}

private fun Dp.asEm(density: Density): TextUnit = (value / density.fontScale).sp

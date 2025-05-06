package com.sd.demo.kmp.compose_systemui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sd.lib.kmp.compose_systemui.StatusBarDark
import com.sd.lib.kmp.compose_systemui.StatusBarLight
import com.sd.lib.kmp.compose_systemui.SystemUI

@Composable
fun Sample(
  onClickBack: () -> Unit,
) {
  var isLight by remember { mutableStateOf(true) }

  SystemUI {
    RouteScaffold(
      title = "Sample",
      onClickBack = onClickBack,
    ) {
      Button(onClick = { isLight = !isLight }) {
        Text(text = "Change")
      }
    }

    if (isLight) {
      StatusBarLight()
    } else {
      StatusBarDark()
    }
  }
}
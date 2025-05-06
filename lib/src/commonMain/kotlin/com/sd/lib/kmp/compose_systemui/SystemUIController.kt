package com.sd.lib.kmp.compose_systemui

import androidx.compose.runtime.Composable

@Composable
internal expect fun rememberStatusBarController(): SystemUIController

internal interface SystemUIController {
  var isLight: Boolean
  var isVisible: Boolean
}
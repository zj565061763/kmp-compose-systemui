package com.sd.lib.kmp.compose_systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalSystemUI = staticCompositionLocalOf<SystemUI?> { null }

@Composable
fun SystemUI(
  content: @Composable () -> Unit,
) {
  val localSystemUI = LocalSystemUI.current
  if (localSystemUI != null) {
    content()
    return
  }

  val systemUI = remember { SystemUI() }

  // StatusBar
  run {
    val controller = rememberStatusBarController()
    val brightness = systemUI.statusBarBrightnessState.value
    LaunchedEffect(controller, brightness) {
      when (brightness) {
        is Brightness.Light -> controller.isLight = true
        is Brightness.Dark -> controller.isLight = false
        null -> {}
      }
    }
  }

  CompositionLocalProvider(LocalSystemUI provides systemUI) {
    content()
  }
}

@Composable
internal fun requireLocalSystemUI(): SystemUI {
  return LocalSystemUI.current ?: error("Not in SystemUI scope.")
}

internal class SystemUI {
  val statusBarBrightnessState = mutableStateOf<Brightness?>(null)
  val statusBarBrightnessStack = BrightnessStack { statusBarBrightnessState.value = it }
}
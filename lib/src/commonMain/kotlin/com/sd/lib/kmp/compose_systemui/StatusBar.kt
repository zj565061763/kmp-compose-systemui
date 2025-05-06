package com.sd.lib.kmp.compose_systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember

/**
 * 亮色状态栏，暗色内容
 */
@Composable
fun StatusBarLight() {
  val brightness = remember { Brightness.Light() }
  StatusBarBrightness(brightness)
}

/**
 * 暗色状态栏，亮色内容
 */
@Composable
fun StatusBarDark() {
  val brightness = remember { Brightness.Dark() }
  StatusBarBrightness(brightness)
}

@Composable
private fun StatusBarBrightness(brightness: Brightness) {
  val stack = requireLocalSystemUI().statusBarBrightnessStack
  DisposableEffect(stack, brightness) {
    stack.add(brightness)
    onDispose { stack.remove(brightness) }
  }
}
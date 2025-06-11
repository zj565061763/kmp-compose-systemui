package com.sd.lib.kmp.compose_systemui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

@Composable
internal actual fun rememberStatusBarController(): SystemUIController {
  val view = LocalView.current
  val window = findWindow()
  return remember(view, window) { StatusBarController(view, window) }
}

private abstract class BaseSystemUIController(
  val view: View,
  window: Window?,
) : SystemUIController {

  val windowInsetsController = window?.let {
    WindowCompat.getInsetsController(it, view)
  }
}

private class StatusBarController(
  view: View,
  window: Window?,
) : BaseSystemUIController(view, window) {

  override var isVisible: Boolean
    get() {
      return ViewCompat.getRootWindowInsets(view)
        ?.isVisible(WindowInsetsCompat.Type.statusBars()) == true
    }
    set(value) {
      if (value) {
        windowInsetsController?.show(WindowInsetsCompat.Type.statusBars())
      } else {
        windowInsetsController?.hide(WindowInsetsCompat.Type.statusBars())
      }
    }

  override var isLight: Boolean
    get() = windowInsetsController?.isAppearanceLightStatusBars == true
    set(value) {
      windowInsetsController?.isAppearanceLightStatusBars = value
    }
}

private class NavigationBarController(
  view: View,
  window: Window?,
) : BaseSystemUIController(view, window) {

  override var isVisible: Boolean
    get() {
      return ViewCompat.getRootWindowInsets(view)
        ?.isVisible(WindowInsetsCompat.Type.navigationBars()) == true
    }
    set(value) {
      if (value) {
        windowInsetsController?.show(WindowInsetsCompat.Type.navigationBars())
      } else {
        windowInsetsController?.hide(WindowInsetsCompat.Type.navigationBars())
      }
    }

  override var isLight: Boolean
    get() = windowInsetsController?.isAppearanceLightNavigationBars == true
    set(value) {
      windowInsetsController?.isAppearanceLightNavigationBars = value
    }
}

@Composable
private fun findWindow(): Window? =
  (LocalView.current.parent as? DialogWindowProvider)?.window
    ?: LocalView.current.context.findWindow()

private tailrec fun Context.findWindow(): Window? =
  when (this) {
    is Activity -> window
    is ContextWrapper -> baseContext.findWindow()
    else -> null
  }
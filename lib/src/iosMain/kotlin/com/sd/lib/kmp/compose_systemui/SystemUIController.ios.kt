package com.sd.lib.kmp.compose_systemui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import platform.UIKit.UIApplication
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.setStatusBarHidden
import platform.UIKit.setStatusBarStyle

@Composable
internal actual fun rememberStatusBarController(): SystemUIController {
  return remember {
    StatusBarController()
  }
}

private class StatusBarController : SystemUIController {
  override var isLight: Boolean
    get() = UIApplication.sharedApplication().statusBarStyle == UIStatusBarStyleDarkContent
    set(value) {
      if (value) {
        UIApplication.sharedApplication().setStatusBarStyle(UIStatusBarStyleDarkContent)
      } else {
        UIApplication.sharedApplication().setStatusBarStyle(UIStatusBarStyleLightContent)
      }
    }

  override var isVisible: Boolean
    get() = !UIApplication.sharedApplication().isStatusBarHidden()
    set(value) {
      UIApplication.sharedApplication().setStatusBarHidden(!value)
    }
}
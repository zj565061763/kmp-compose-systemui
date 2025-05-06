package com.sd.lib.kmp.compose_systemui

internal sealed interface Brightness {
  class Light : Brightness
  class Dark : Brightness
}

internal class BrightnessStack(
  private val onChange: (Brightness?) -> Unit,
) {
  private val _stack = object : LastStack<Brightness>() {
    override fun onLastItemChanged(item: Brightness?) {
      onChange(item)
    }
  }

  fun add(brightness: Brightness) = _stack.add(brightness)

  fun remove(brightness: Brightness) = _stack.remove(brightness)
}

private abstract class LastStack<T> {
  private val _holder = mutableListOf<T>()

  fun add(item: T) {
    if (item == lastOrNull()) return
    _holder.remove(item)
    _holder.add(item)
    notifyLastItemChanged()
  }

  fun remove(item: T) {
    if (item == lastOrNull()) {
      _holder.removeLastOrNull()
      notifyLastItemChanged()
    } else {
      _holder.remove(item)
    }
  }

  fun lastOrNull(): T? = _holder.lastOrNull()

  private fun notifyLastItemChanged() = onLastItemChanged(lastOrNull())

  abstract fun onLastItemChanged(item: T?)
}
package io.taig.color

import io.taig.color.internal.Macros

trait implicits {
  implicit final class ColorInterpolator(context: StringContext) {
    def hex(arguments: Any*): Color = macro Macros.hexInterpolator
  }
}

object implicits extends implicits
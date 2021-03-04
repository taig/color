package io.taig.color

import scala.quoted._

import io.taig.color.internal.Macros

trait implicits {
  implicit final class ColorInterpolator(val context: StringContext) {
    inline def hex(arguments: Any*): Color = ${Macros.colorInterpolator('context, 'arguments)}
  }
}

object implicits extends implicits
package io.taig.color

import scala.quoted._

object implicits {
  implicit class ColorInterpolator(val context: StringContext) extends AnyVal {
    inline def hex(arguments: Any*): Color = ${ColorInterpolator('context, 'arguments)}

    @deprecated("Use hex instead", "0.3.0")
    inline def rgb(arguments: Any*): Color = ${ColorInterpolator('context, 'arguments)}
  }

  object ColorInterpolator {
    def apply(context: Expr[StringContext], arguments: Expr[Seq[Any]])(using Quotes): Expr[Color] = {
      '{
        Color.parseHex(${context}.s(${arguments}: _*)) match {
          case Right(color) => color
          case Left(error) => throw new RuntimeException(error)
        }
      }
    }
  }
}

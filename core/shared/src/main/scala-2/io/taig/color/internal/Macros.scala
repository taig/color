package io.taig.color.internal

import scala.annotation.nowarn
import scala.reflect.macros.blackbox

import io.taig.color.Color

object Macros {
  @nowarn("msg=never used")
  def hexInterpolator(context: blackbox.Context)(arguments: context.Expr[Any]*): context.Expr[Color] = {
    import context.universe._

    context.prefix.tree match {
      case Apply(_, List(Apply(_, List(literal @ Literal(Constant(value: String)))))) =>
        Color.parseHex(value) match {
          case Right(_) =>
            val expression: context.Expr[String] = context.Expr(literal)
            reify(Color.unsafeParseHex(expression.splice))
          case Left(value) => context.abort(context.enclosingPosition, value)
        }
      case _ => context.abort(context.enclosingPosition, "Invalid color value")
    }
  }
}

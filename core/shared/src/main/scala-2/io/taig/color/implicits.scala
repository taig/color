package io.taig.color

import scala.reflect.macros.blackbox

import com.github.ghik.silencer.silent

object implicits {
  implicit class ColorInterpolator(val context: StringContext) extends AnyVal {
    def hex(arguments: Any*): Color = macro ColorInterpolator.apply

    @deprecated("Use hex instead", "0.3.0")
    def rgb(arguments: Any*): Color = macro ColorInterpolator.apply
  }

  object ColorInterpolator {
    @silent
    def apply(context: blackbox.Context)(arguments: context.Expr[Any]*): context.Expr[Color] = {
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
}

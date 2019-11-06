package io.taig

import java.lang.{Short => JShort}
import java.lang.{Long => JLong}

import scala.language.experimental.macros
import scala.reflect.macros.blackbox

package object color {
  private[color] val parseShort16: String => Option[Short] = { value =>
    try Some(JShort.parseShort(value, 16))
    catch {
      case _: Throwable => None
    }
  }

  private[color] val parseLong16: String => Option[Long] = { value =>
    try Some(JLong.parseLong(value, 16))
    catch {
      case _: Throwable => None
    }
  }

  implicit class ColorInterpolator(val context: StringContext) extends AnyVal {
    def rgb(arguments: Any*): Color = macro ColorInterpolator.rgb_impl
  }

  object ColorInterpolator {
    def rgb_impl(
        context: blackbox.Context
    )(arguments: context.Expr[Any]*): context.Expr[Color] = {
      import context.universe._

      context.prefix.tree match {
        case Apply(
            _,
            List(Apply(_, List(literal @ Literal(Constant(value: String)))))
            ) =>
          Color.parseHex(value) match {
            case Right(_) =>
              val expression: context.Expr[String] = context.Expr(literal)
              reify(Color.unsafeParseHex(expression.splice))
            case Left(value) =>
              context.abort(context.enclosingPosition, value)
          }
        case _ =>
          context.abort(context.enclosingPosition, "Invalid color value")
      }
    }
  }
}

package io.taig.color.internal

import scala.quoted._

import io.taig.color.Color

object Macros {
  def colorInterpolator(context: Expr[StringContext], arguments: Expr[Seq[Any]])(using Quotes): Expr[Color] = {
    '{
      Color.parseHex(${context}.s(${arguments}: _*)) match {
        case Right(color) => color
        case Left(error) => throw new RuntimeException(error)
      }
    }
  }
}

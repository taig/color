package io.taig.color

import cats.{Eq, Show}
import io.taig.testf.{Assertions, Builders}

object dsl extends Builders with Assertions {
  implicit val eqColor: Eq[Color] = Eq.fromUniversalEquals
  implicit val showColor: Show[Color] = Show.fromToString
}

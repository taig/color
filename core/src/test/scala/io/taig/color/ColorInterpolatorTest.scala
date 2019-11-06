package io.taig.color

import io.taig.color.dsl._
import io.taig.testf.{AutoTest, IOAutoTestApp}

@AutoTest
object ColorInterpolatorTest extends IOAutoTestApp {
  test("#000") {
    isEqual(rgb"#000")(Color.Black)
  }

  test("#FFF") {
    isEqual(rgb"#FFF")(Color.White)
  }
}

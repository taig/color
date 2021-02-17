package io.taig.color

import io.taig.color.implicits._
import munit.FunSuite

final class ColorInterpolatorTest extends FunSuite {
  test("#000") {
    assertEquals(expected = Color.Black, obtained = hex"#000")
    assertEquals(expected = Color.Black, obtained = hex"000")
  }

  test("#FFF") {
    assertEquals(expected = Color.White, obtained = hex"#FFF")
    assertEquals(expected = Color.White, obtained = hex"FFF")
  }
}

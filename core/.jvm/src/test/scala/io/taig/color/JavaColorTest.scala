package io.taig.color

import munit.FunSuite
import io.taig.color.implicits._

final class JavaColorTest extends FunSuite {
  test("toAwt / fromAwt round-trip") {
    val color = rgb"#FFAACC33"
    assertEquals(obtained = Color.fromAwt(color.toAwt), expected = color)
  }
}

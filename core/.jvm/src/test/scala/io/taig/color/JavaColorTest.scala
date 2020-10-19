package io.taig.color

import munit.FunSuite
import io.taig.color.implicits._

final class JavaColorTest extends FunSuite {
  test("toAwt / fromAwt round-trip") {
    val color = rgb"#FFAACC33"
    assertEquals(obtained = Color.fromAwt(color.toAwt), expected = color)
  }

  test("darker") {
    val color = rgb"#FFAACC33"
    assertEquals(obtained = color.darker().toAwt, color.toAwt.darker())
  }

  test("brighter") {
    val color = rgb"#FFAACC33"
    assertEquals(obtained = color.brighter().toAwt, color.toAwt.brighter())
  }
}

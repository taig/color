package io.taig.color

import io.taig.color.Channel.{MaxValue, MinValue}
import munit.FunSuite

final class ColorTest extends FunSuite {
  test("fromHex") {
    assert(Color.fromHex(0x00 - 1, digits = 8).isLeft)
    assert(Color.fromHex(0XFFFFFFFFFL + 1, digits = 8).isLeft)
    assert(Color.fromHex(0xFFFFFF, digits = 0).isLeft)
    assert(Color.fromHex(0xFFFFFF, digits = 1).isLeft)
    assert(Color.fromHex(0xFFFFFF, digits = 2).isLeft)
    assert(Color.fromHex(0xFFFFFF, digits = 5).isLeft)
    assert(Color.fromHex(0xFFFFFF, digits = 7).isLeft)
    assert(Color.fromHex(0xFFFFFF, digits = 9).isLeft)
  }

  test("parseHex") {
    assertEquals(obtained = Color.parseHex("#FFF"), expected = Right(Color.White))
    assertEquals(obtained = Color.parseHex("#0FF"), expected = Right(Color.opaque(MinValue, MaxValue, MaxValue)))
    assertEquals(obtained = Color.parseHex("#FFFF"), expected = Right(Color.White.opaque))
    assertEquals(obtained = Color.parseHex("#000F"), expected = Right(Color.Black.opaque))
    assertEquals(obtained = Color.parseHex("#FFFFFF"), expected = Right(Color.White))
    assertEquals(obtained = Color.parseHex("#000000"), expected = Right(Color.Black))
    assertEquals(obtained = Color.parseHex("#ff0000"), expected = Right(Color.opaque(MaxValue, MinValue, MinValue)))
    assertEquals(obtained = Color.parseHex("#FFFFFFFF"), expected = Right(Color.White.opaque))
    assertEquals(obtained = Color.parseHex("#000000FF"), expected = Right(Color.Black.opaque))
    assertEquals(obtained = Color.parseHex("#FFFFFF00"), expected = Right(Color.White.transparent))
    assertEquals(obtained = Color.parseHex("#00000000"), expected = Right(Color.Black.transparent))
  }

  test("toHex") {
    assertEquals(obtained = Color.White.toHex, expected = "#ffffff")
    assertEquals(obtained = Color.White.transparent.toHex, expected = "#ffffff00")
    assertEquals(obtained = Color.Black.toHex, expected = "#000000")
    assertEquals(obtained = Color.Black.transparent.toHex, expected = "#00000000")
  }

  test("toRgb") {
    assertEquals(obtained = Color.White.toRgb, expected = "rgb(255, 255, 255)")
    assertEquals(obtained = Color.White.transparent.toRgb, expected = "rgba(255, 255, 255, 0)")
    assertEquals(obtained = Color.Black.toRgb, expected = "rgb(0, 0, 0)")
    assertEquals(obtained = Color.Black.transparent.toRgb, expected = "rgba(0, 0, 0, 0)")
  }
}

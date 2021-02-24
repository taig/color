package io.taig.color

import io.taig.color.Channel.{MaxValue, MinValue}
import munit.FunSuite

final class ColorTest extends FunSuite {
  test("fromHex") {
    assert(Color.fromHex(0x00 - 1, digits = 8).isLeft)
    assert(Color.fromHex(0xfffffffffL + 1, digits = 8).isLeft)
    assert(Color.fromHex(0xffffff, digits = 0).isLeft)
    assert(Color.fromHex(0xffffff, digits = 1).isLeft)
    assert(Color.fromHex(0xffffff, digits = 2).isLeft)
    assert(Color.fromHex(0xffffff, digits = 5).isLeft)
    assert(Color.fromHex(0xffffff, digits = 7).isLeft)
    assert(Color.fromHex(0xffffff, digits = 9).isLeft)
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

  test("luminance") {
    assertEquals(obtained = Color.White.luminance, expected = Channel.MaxValue)
    assertEquals(obtained = Color.Black.luminance, expected = Channel.MinValue)
    assertEquals(
      obtained = Color.opaque(Channel.unsafeFromInt(255), Channel.MinValue, Channel.MinValue).luminance,
      expected = Channel.unsafeFromInt(54)
    )
  }

  test("contrast") {
    assertEqualsFloat(obtained = Color.White.contrast(Color.White), expected = 1f, delta = 0.03f)
    assertEqualsFloat(obtained = Color.Black.contrast(Color.Black), expected = 1f, delta = 0.03f)
    assertEqualsFloat(obtained = Color.White.contrast(Color.Black), expected = 21f, delta = 0.03f)
    assertEqualsFloat(obtained = Color.Black.contrast(Color.White), expected = 21f, delta = 0.03f)
    assertEqualsFloat(obtained = Color.White.contrast(Color.unsafeParseHex("#ff0000")), expected = 4f, delta = 0.03f)
  }

  test("over") {
    assertEquals(obtained = Color.White.over(Color.White), expected = Color.White)
    assertEquals(obtained = Color.Black.over(Color.Black), expected = Color.Black)
    assertEquals(obtained = Color.Black.transparent.over(Color.White), expected = Color.White)
  }

  test("over with alpha blending") {
    val background = Color(
      red = Channel.unsafeFromInt(255),
      green = Channel.unsafeFromInt(110),
      blue = Channel.unsafeFromInt(40),
      alpha = Channel.fromScale(0.7f)
    )
    val foreground = Color(
      red = Channel.unsafeFromInt(220),
      green = Channel.unsafeFromInt(170),
      blue = Channel.unsafeFromInt(100),
      alpha = Channel.fromScale(0.5f)
    )
    val expected = Color(
      red = Channel.unsafeFromInt(234),
      green = Channel.unsafeFromInt(145),
      blue = Channel.unsafeFromInt(75),
      alpha = Channel.fromScale(0.85f)
    )
    assertEquals(obtained = foreground.over(background), expected)
  }
}

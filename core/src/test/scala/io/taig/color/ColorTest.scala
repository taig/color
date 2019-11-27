package io.taig.color

import io.taig.color.Channel.{MaxValue, MinValue}
import io.taig.color.dsl._
import io.taig.testf._
import io.taig.testf.{AutoTest, IOAutoTestApp}

@AutoTest
object ColorTest extends IOAutoTestApp {
  test("fromHex")(
    test("value")(
      test("0x00 - 1") {
        fromLeft(Color.fromHex(0x00 - 1, digits = 8))
      },
      test("0xFFFFFFFFF + 1") {
        fromLeft(Color.fromHex(0XFFFFFFFFFL + 1, digits = 8))
      }
    ),
    test("digits")(
      test("0")(fromLeft(Color.fromHex(0xFFFFFF, digits = 0))),
      test("1")(fromLeft(Color.fromHex(0xFFFFFF, digits = 1))),
      test("2")(fromLeft(Color.fromHex(0xFFFFFF, digits = 2))),
      test("5")(fromLeft(Color.fromHex(0xFFFFFF, digits = 5))),
      test("7")(fromLeft(Color.fromHex(0xFFFFFF, digits = 7))),
      test("9")(fromLeft(Color.fromHex(0xFFFFFF, digits = 9)))
    )
  )

  test("parseHex")(
    test("#FFF") {
      fromRight(Color.parseHex("#FFF")) assert isEqual(Color.White)
    },
    test("#0FF") {
      fromRight(Color.parseHex("#0FF")) assert
        isEqual(Color.opaque(MinValue, MaxValue, MaxValue))
    },
    test("#FFFF") {
      fromRight(Color.parseHex("#FFFF")) assert
        isEqual(Color.White.opaque)
    },
    test("#000F") {
      fromRight(Color.parseHex("#000F")) assert
        isEqual(Color.Black.opaque)
    },
    test("#FFFFFF") {
      fromRight(Color.parseHex("#FFFFFF")) assert isEqual(Color.White)
    },
    test("#000000") {
      fromRight(Color.parseHex("#000000")) assert isEqual(Color.Black)
    },
    test("#ff0000") {
      fromRight(Color.parseHex("#ff0000")) assert
        isEqual(Color.opaque(MaxValue, MinValue, MinValue))
    },
    test("#FFFFFFFF") {
      fromRight(Color.parseHex("#FFFFFFFF")) assert
        isEqual(Color.White.opaque)
    },
    test("#000000FF") {
      fromRight(Color.parseHex("#000000FF")) assert
        isEqual(Color.Black.opaque)
    },
    test("#FFFFFF00") {
      fromRight(Color.parseHex("#FFFFFF00")) assert
        isEqual(Color.White.transparent)
    },
    test("#00000000") {
      fromRight(Color.parseHex("#00000000")) assert
        isEqual(Color.Black.transparent)
    }
  )

  test("toHex")(
    test("#FFFFFF") {
      isEqual("#ffffff")(Color.White.toHex)
    },
    test("#FFFFFF00") {
      isEqual("#ffffff00")(Color.White.transparent.toHex)
    },
    test("#000000") {
      isEqual("#000000")(Color.Black.toHex)
    },
    test("#00000000") {
      isEqual("#00000000")(Color.Black.transparent.toHex)
    }
  )

  test("toRgb")(
    test("#FFFFFF") {
      isEqual("rgb(255, 255, 255)")(Color.White.toRgb)
    },
    test("#FFFFFF00") {
      isEqual("rgba(255, 255, 255, 0)")(Color.White.transparent.toRgb)
    },
    test("#000000") {
      isEqual("rgb(0, 0, 0)")(Color.Black.toRgb)
    },
    test("#00000000") {
      isEqual("rgba(0, 0, 0, 0)")(Color.Black.transparent.toRgb)
    }
  )
}

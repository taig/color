package io.taig.color

import io.taig.color.Channel.{MaxValue, MinValue}
import io.taig.color.dsl._
import io.taig.testf._
import io.taig.testf.{AutoTest, IOAutoTestApp}

@AutoTest
object ColorTest extends IOAutoTestApp {
  test("parseHex")(
    test("#FFF") {
      fromRight(Color.parseHex("#FFF")) assert isEqual(Color.White)
    },
    test("#0FF") {
      fromRight(Color.parseHex("#0FF")) assert isEqual(
        Color(MinValue, MaxValue, MaxValue, None)
      )
    },
    test("#FFFF") {
      fromRight(Color.parseHex("#FFFF")) assert isEqual(
        Color.White.alpha(MaxValue)
      )
    },
    test("#000F") {
      fromRight(Color.parseHex("#000F")) assert isEqual(
        Color.Black.alpha(MaxValue)
      )
    },
    test("#FFFFFF") {
      fromRight(Color.parseHex("#FFFFFF")) assert isEqual(Color.White)
    },
    test("#000000") {
      fromRight(Color.parseHex("#000000")) assert isEqual(Color.Black)
    },
    test("#ff0000") {
      fromRight(Color.parseHex("#ff0000")) assert
        isEqual(Color(MaxValue, MinValue, MinValue, None))
    },
    test("#FFFFFFFF") {
      fromRight(Color.parseHex("#FFFFFFFF")) assert isEqual(
        Color.White.alpha(MaxValue)
      )
    },
    test("#000000FF") {
      fromRight(Color.parseHex("#000000FF")) assert isEqual(
        Color.Black.alpha(MaxValue)
      )
    },
    test("#FFFFFF00") {
      fromRight(Color.parseHex("#FFFFFF00")) assert isEqual(
        Color.White.copy(alpha = Some(MinValue))
      )
    },
    test("#00000000") {
      fromRight(Color.parseHex("#00000000")) assert isEqual(
        Color.Black.copy(alpha = Some(MinValue))
      )
    }
  )

  test("toHex")(
    test("#FFFFFF") {
      isEqual("#ffffff")(Color.White.toHex)
    },
    test("#FFFFFFFF") {
      isEqual("#ffffffff")(Color.White.alpha(MaxValue).toHex)
    },
    test("#000000") {
      isEqual("#000000")(Color.Black.toHex)
    },
    test("#000000FF") {
      isEqual("#000000ff")(Color.Black.alpha(MaxValue).toHex)
    }
  )

  test("toRgb")(
    test("#FFFFFF") {
      isEqual("rgb(255, 255, 255)")(Color.White.toRgb)
    },
    test("#FFFFFFFF") {
      isEqual("rgba(255, 255, 255, 255)")(Color.White.alpha(MaxValue).toRgb)
    },
    test("#000000") {
      isEqual("rgb(0, 0, 0)")(Color.Black.toRgb)
    },
    test("#000000FF") {
      isEqual("rgba(0, 0, 0, 255)")(Color.Black.alpha(MaxValue).toRgb)
    }
  )
}

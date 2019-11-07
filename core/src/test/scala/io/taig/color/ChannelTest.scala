package io.taig.color

import io.taig.testf.{AutoTest, IOAutoTestApp}
import io.taig.testf._
import io.taig.color.dsl._

@AutoTest
object ChannelTest extends IOAutoTestApp {
  test("fromScale")(
    test("-1") {
      isEqual(Channel.MinValue)(Channel.fromScale(-1f))
    },
    test("0") {
      isEqual(Channel.MinValue)(Channel.fromScale(0f))
    },
    test("0.5") {
      isEqual(Channel.unsafeFromUnsignedShort(127))(Channel.fromScale(.5f))
    },
    test("1") {
      isEqual(Channel.MaxValue)(Channel.fromScale(1f))
    },
    test("2") {
      isEqual(Channel.MaxValue)(Channel.fromScale(2f))
    }
  )

  test("fromHex")(
    test("-1") {
      fromLeft(Channel.fromHex(-1))
    },
    test("0x00") {
      fromRight(Channel.fromHex(0x00))
    },
    test("0xF0") {
      fromRight(Channel.fromHex(0xF0))
    },
    test("0xFF") {
      fromRight(Channel.fromHex(0xFF))
    },
    test("0xFFF") {
      fromLeft(Channel.fromHex(0xFFF))
    }
  )
}

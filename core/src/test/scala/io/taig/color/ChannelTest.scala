package io.taig.color

import munit.FunSuite

final class ChannelTest extends FunSuite {
  test("fromScale") {
    assertEquals(obtained = Channel.fromScale(-1f), expected = Channel.MinValue)
    assertEquals(obtained = Channel.fromScale(0f), expected = Channel.MinValue)
    assertEquals(obtained = Channel.fromScale(0.5f), expected = Channel.unsafeFromUnsignedShort(127))
    assertEquals(obtained = Channel.fromScale(1f), expected = Channel.MaxValue)
    assertEquals(obtained = Channel.fromScale(2f), expected = Channel.MaxValue)
  }

  test("fromHex") {
    assert(Channel.fromHex(0x00 - 1).isLeft)
    assert(Channel.fromHex(0x00).isRight)
    assert(Channel.fromHex(0xF0).isRight)
    assert(Channel.fromHex(0xFF).isRight)
    assert(Channel.fromHex(0xFF + 1).isLeft)
  }
}

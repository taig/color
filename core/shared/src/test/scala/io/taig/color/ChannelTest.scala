package io.taig.color

import munit.FunSuite

final class ChannelTest extends FunSuite {
  test("fromScale") {
    assertEquals(obtained = Channel.fromScale(-1f), expected = Channel.MinValue)
    assertEquals(obtained = Channel.fromScale(0f), expected = Channel.MinValue)
    assertEquals(obtained = Channel.fromScale(0.5f), expected = Channel.unsafeFromInt(127))
    assertEquals(obtained = Channel.fromScale(1f), expected = Channel.MaxValue)
    assertEquals(obtained = Channel.fromScale(2f), expected = Channel.MaxValue)
  }

  test("fromInt") {
    assert(Channel.fromInt(0x00 - 1).isLeft)
    assert(Channel.fromInt(0x00).isRight)
    assert(Channel.fromInt(0xf0).isRight)
    assert(Channel.fromInt(0xff).isRight)
    assert(Channel.fromInt(0xff + 1).isLeft)
  }
}

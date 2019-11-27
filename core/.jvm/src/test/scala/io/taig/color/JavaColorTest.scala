package io.taig.color

import io.taig.color.dsl._
import io.taig.testf._
import io.taig.testf.{AutoTest, IOAutoTestApp}

@AutoTest
object JavaColorTest extends IOAutoTestApp {
  test("toAwt / fromAwt") {
    test("#FFAACC33") {
      fromRight(Color.parseHex("#FFAACC33")).flatMap { color =>
        isEqual(color)(Color.fromAwt(color.toAwt))
      }
    }
  }
}

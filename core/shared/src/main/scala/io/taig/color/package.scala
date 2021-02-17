package io.taig

import java.lang.{Short => JShort}
import java.lang.{Long => JLong}

package object color {
  private[color] val parseShort16: String => Option[Short] = { value =>
    try Some(JShort.parseShort(value, 16))
    catch {
      case _: Throwable => None
    }
  }

  private[color] val parseLong16: String => Option[Long] = { value =>
    try Some(JLong.parseLong(value, 16))
    catch {
      case _: Throwable => None
    }
  }
}

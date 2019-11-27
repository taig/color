package io.taig.color

final case class Channel private (raw: Byte) extends AnyVal {
  def value: Int = raw - Byte.MinValue

  /** Convert the value to a Float between 0.0 and 1.0 */
  def scaled: Float = (1f / 0xFF) * value

  /** Convert the value to a `String` with a numeric value between 0 and 100
    * and a `%`-sign
    */
  def % : String = s"${scaled * 100}%"

  override def toString: String = String.valueOf(value)
}

object Channel {
  val MaxValue: Channel = Channel(Byte.MaxValue)

  val MinValue: Channel = Channel(Byte.MinValue)

  /**
    * Convert a `Short` value between `0` and `255` to a `Channel`
    *
    * This method is unsafe in the sense that the underlying `Short` to `Byte`
    * conversion may overflow. [[fromHex]] is a safe alternative.
    */
  def unsafeFromUnsignedShort(value: Short): Channel =
    Channel((value + Byte.MinValue).toByte)

  /**
    * Convert an `Int` value between `0` and `255` to a `Channel`
    *
    * This method is unsafe in the sense that the underlying `Int` to `Byte`
    * conversion may overflow. [[fromHex]] is a safe alternative.
    */
  def unsafeFromUnsignedInt(value: Int): Channel =
    Channel((value + Byte.MinValue).toByte)

  /**
    * Convert a `Float` value between `0.0` and `1.0` to a `Channel`
    *
    * Values less than `0` are be treated as `0`, values bigger than `1` are
    * treated as `1`.
    */
  def fromScale(value: Float): Channel = {
    val adjusted = math.min(math.max(0, value), 1)
    Channel.unsafeFromUnsignedShort((0xFF * adjusted).toShort)
  }

  /**
    * Convert a (hexadecimal) number to a `Channel`
    *
    * Returns a `Left` if the input value is not within the `0x00 - 0xFF` range.
    */
  def fromHex(value: Short): Either[String, Channel] =
    if (value < 0x00) Left("Channel value must be >= 0")
    else if (value > 0xFF) Left("Channel value must be <= 255")
    else Right(Channel.unsafeFromUnsignedShort(value))
}

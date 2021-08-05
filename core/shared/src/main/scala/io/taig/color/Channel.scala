package io.taig.color

final case class Channel private (raw: Byte) extends AnyVal {
  def value: Int = raw - Byte.MinValue

  /** Convert the value to a Double between 0.0 and 1.0 */
  def scaled: Double = (1d / 0xff) * value

  def linear: Double = {
    val scaled = this.scaled
    if (scaled <= 0.03928) scaled / 12.92 else math.pow((scaled + 0.055) / 1.055, 2.4)
  }

  /** Convert the value to a `String` with a numeric value between 0 and 100 and a `%`-sign */
  def % : String = s"${scaled * 100}%"

  override def toString: String = String.valueOf(value)
}

object Channel {
  val MaxValue: Channel = Channel(Byte.MaxValue)

  val MinValue: Channel = Channel(Byte.MinValue)

  /** Create a `Channel` from an `Int` value between `0` and `255`
    *
    * This method is unsafe in the sense that the underlying `Int` to `Byte` conversion may overflow. [[fromInt]] is a
    * safe alternative.
    */
  def unsafeFromInt(value: Int): Channel = Channel((value + Byte.MinValue).toByte)

  /** Create a `Channel` from an `Int` value between `0` and `255`
    *
    * Returns a `Left` if the input value is not within the range.
    */
  def fromInt(value: Int): Either[String, Channel] =
    if (value < 0x00) Left("Channel value must be >= 0")
    else if (value > 0xff) Left("Channel value must be <= 255")
    else Right(Channel.unsafeFromInt(value))

  /** Convert a `Float` value between `0.0` and `1.0` to a `Channel`
    *
    * Values less than `0` are be treated as `0`, values bigger than `1` are treated as `1`.
    */
  def fromScale(value: Float): Channel = {
    val adjusted = math.min(math.max(0, value), 1)
    Channel.unsafeFromInt((0xff * adjusted).toInt)
  }
}

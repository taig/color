package io.taig.color

final case class Channel private (value: Short) extends AnyVal {

  /** Convert the value to a Float between 0.0 and 1.0 */
  def scaled: Float = (1f / 255) * value

  def % : String = s"${scaled * 100}%"

  override def toString: String = String.valueOf(value)
}

object Channel {
  val MaxValue: Channel = Channel(255)

  val MinValue: Channel = Channel(0)

  def unsafeFromLong(value: Long): Channel = Channel(value.toShort)

  def fromScale(value: Float): Channel = {
    val adjusted = math.min(math.max(0, value), 1)
    Channel((255 * adjusted).toShort)
  }

  def fromHex(value: Short): Either[String, Channel] =
    if (value < 0) Left("Channel value must be >= 0")
    else if (value > 255) Left("Channel value must be <= 255")
    else Right(Channel(value))

  def parseHex(value: String): Either[String, Channel] =
    parseShort16(value).toRight("Invalid number format").flatMap(fromHex)
}

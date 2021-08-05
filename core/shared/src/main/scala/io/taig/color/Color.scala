package io.taig.color

import java.awt.{Color => JColor}

final case class Color(
    red: Channel,
    green: Channel,
    blue: Channel,
    alpha: Channel
) {

  /** Set the alpha channel to its min value */
  def transparent: Color = alpha(Channel.MinValue)

  /** Set the alpha channel to its min value */
  def opaque: Color = alpha(Channel.MaxValue)

  def alpha(value: Channel): Color = copy(alpha = value)

  def isOpaque: Boolean = alpha == Channel.MaxValue
  def isTransparent: Boolean = alpha == Channel.MinValue

  def darker(factor: Double = 0.7d): Color = Color(
    red = Channel.unsafeFromInt((red.value * factor).toInt),
    green = Channel.unsafeFromInt((green.value * factor).toInt),
    blue = Channel.unsafeFromInt((blue.value * factor).toInt),
    alpha
  )

  def brighter(factor: Double = 0.7d): Color = {
    var red = this.red.value
    var green = this.green.value
    var blue = this.blue.value

    val i = (1.0 / 1.0 - factor).toInt

    if (red == 0 && green == 0 && blue == 0) {
      val channel = Channel.unsafeFromInt(i)
      Color(channel, channel, channel, alpha)
    } else {
      if (red > 0 && red < i) red = i
      if (green > 0 && green < i) green = i
      if (blue > 0 && blue < i) blue = i

      Color(
        Channel.unsafeFromInt(math.min((red / factor).toInt, 255)),
        Channel.unsafeFromInt(math.min((green / factor).toInt, 255)),
        Channel.unsafeFromInt(math.min((blue / factor).toInt, 255)),
        alpha
      )
    }
  }

  /** Calculate a luminance value between 0.0 and 1.0
    *
    * @see
    *   https://developer.mozilla.org/en-US/docs/Web/Accessibility/Understanding_Colors_and_Luminance#measuring_relative_luminance
    */
  def luminance: Double = 0.2126 * red.linear + 0.7152 * green.linear + 0.0722 * blue.linear

  /** Calculate a contrast value between 0.0 (minimum contrast) and 21.0 (maximum contrast) */
  def contrast(color: Color): Double = {
    val left = luminance + 0.05d
    val right = color.luminance + 0.05d
    if (left > right) left / right else right / left
  }

  /** Lay this `Color` on top of the given `Color` and calculate a combination simple blending is this `Color` is not
    * opaque
    *
    * @see
    *   https://gist.github.com/JordanDelcros/518396da1c13f75ee057
    */
  def over(color: Color): Color = {
    if (alpha == Channel.MaxValue) this
    else if (alpha == Channel.MinValue) color
    else {
      val factor = 1 - (1 - alpha.scaled) * (1 - color.alpha.scaled)
      val foreground = alpha.scaled / factor
      val background = color.alpha.scaled * (1 - alpha.scaled) / factor

      Color(
        red = Channel.unsafeFromInt((red.value * foreground + color.red.value * background).toInt),
        green = Channel.unsafeFromInt((green.value * foreground + color.green.value * background).toInt),
        blue = Channel.unsafeFromInt((blue.value * foreground + color.blue.value * background).toInt),
        alpha = Channel.unsafeFromInt((255 * factor).toInt)
      )
    }
  }

  /** Print the color as a hex string */
  def toHex: String =
    if (isOpaque) f"#${red.value}%02x${green.value}%02x${blue.value}%02x"
    else f"#${red.value}%02x${green.value}%02x${blue.value}%02x${alpha.value}%02x"

  /** Print the color a rgb or rgba string */
  def toRgb: String = toRgbX(color => String.valueOf(color.value))
  def toRgbScaled: String = toRgbX(color => String.valueOf(color.scaled))
  def toRgb_% : String = toRgbX(color => String.valueOf(color.%))

  private def toRgbX(render: Channel => String): String =
    if (isOpaque) "rgb(" + render(red) + ", " + render(green) + ", " + render(blue) + ")"
    else "rgba(" + render(red) + ", " + render(green) + ", " + render(blue) + ", " + render(alpha) + ")"

  def toAwt: JColor = new JColor(red.value, green.value, blue.value, alpha.value)
}

object Color {
  def opaque(red: Channel, green: Channel, blue: Channel): Color =
    Color(red, green, blue, Channel.MaxValue)

  def fromAwt(color: JColor): Color = Color(
    Channel.unsafeFromInt(color.getRed),
    Channel.unsafeFromInt(color.getGreen),
    Channel.unsafeFromInt(color.getBlue),
    Channel.unsafeFromInt(color.getAlpha)
  )

  /** Convert a (hexadecimal) number to a `Color`
    *
    * Input values that are not within the `0x00000000 - 0xFFFFFFFF` range will be rejected.
    *
    * This method should be used with hexadecimal number representations, e.g.:
    *
    * {{{
    * fromHex(0xFFF, digits = 3)
    * fromHex(0x000000, digits = 6)
    * fromHex(0xFF00, digits = 4)
    * fromHex(0xFF0000FF, digits = 8)
    * }}}
    *
    * This method requires the amount of digits because hexadecimal color values should generally be represented as
    * Strings, not numbers. This method will, for instance, accept `0xF` as input value but might misinterpret it
    * without the explicit digits hint as it could mean `0x00F`, `0x000F`, `0x00000F` or even `0x0000000F`.
    */
  def fromHex(value: Long, digits: Int): Either[String, Color] = {
    val channel: Long => Channel = value => Channel.unsafeFromInt(value.toInt)

    if (value < 0x00 || value > 0xffffffffL)
      Left("Color value must be between 0x00000000 and 0xFFFFFFFF")
    else if (digits == 3) {
      val red = channel(((value & 0xfff) >> 8) * 17)
      val green = channel(((value & 0xff) >> 4) * 17)
      val blue = channel((value & 0xf) * 17)
      Right(opaque(red, green, blue))
    } else if (digits == 4) {
      val red = channel(((value & 0xffff) >> 12) * 17)
      val green = channel(((value & 0xfff) >> 8) * 17)
      val blue = channel(((value & 0xff) >> 4) * 17)
      val alpha = channel((value & 0xf) * 17)
      Right(Color(red, green, blue, alpha))
    } else if (digits == 6) {
      val red = channel((value & 0xffffff) >> 16)
      val green = channel((value & 0xffff) >> 8)
      val blue = channel(value & 0xff)
      Right(opaque(red, green, blue))
    } else if (digits == 8) {
      val red = channel((value & 0xffffffff) >> 24)
      val green = channel((value & 0xffffff) >> 16)
      val blue = channel((value & 0xffff) >> 8)
      val alpha = channel(value & 0xff)
      Right(Color(red, green, blue, alpha))
    } else {
      val message = "Color value can only have 6 (rgba), 8 (rgba), 3 (rgb " +
        "shorthand) or 4 (rgba shorthand) digits"
      Left(message)
    }
  }

  /** Parse a hexadecimal `String` to a `Color`
    *
    * The input `String` may start with a `#`.
    */
  def parseHex(value: String): Either[String, Color] = {
    val hex = if (value.startsWith("#")) value.substring(1) else value
    val digits = hex.length
    parseLong16(hex)
      .toRight("Invalid number format")
      .flatMap(fromHex(_, digits))
  }

  def unsafeParseHex(value: String): Color = parseHex(value).getOrElse(throw new IllegalArgumentException)

  val Black: Color = opaque(Channel.MinValue, Channel.MinValue, Channel.MinValue)

  val White: Color = opaque(Channel.MaxValue, Channel.MaxValue, Channel.MaxValue)
}

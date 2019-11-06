# Color

> A simple approach to colors with String interpolators

## Dependency

_Color_ is available for Scala(.js) 2.13 and 2.12.

```scala
libraryDependencies ++=
  "io.taig" %%% "color-core" % "x.y.z" ::
  Nil
```

## Usage

```scala
import io.taig.color.Color
import io.taig.color.implicits._

val red: Color = rgb"FF0000"
// red: io.taig.color.Color = Color(255,0,0,None)

red.toHex
// res0: String = #ff0000

red.toRgb
// res1: String = rgb(255, 0, 0)

red.toRgbScaled
// res2: String = rgb(1.0, 0.0, 0.0)

red.toRgb_%
// res3: String = rgb(100.0%, 0.0%, 0.0%)

val reddish = red.alpha(Channel.fromScale(.5f))
// reddish: io.taig.color.Color = Color(255,0,0,Some(127))

reddish.toHex
// res4: String = #ff00007f

reddish.toRgb
// res5: String = rgba(255, 0, 0, 127)
```
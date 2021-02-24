# Changelog

## 0.4.0

_2021-02-24_

 * Add `Color.luminance` and `Color.contrast` methods
 * Improve `Channel` builder method names
 * Remove deprecated `rgb` interpolator
 * Upgrade to scala 2.12.13 and 2.13.5

## 0.3.1

_2021-02-17_

 * Cross-build with scala 3 (RC1) support (#1)
 * Upgrade to sbt-houserules 0.3.4
 * Upgrade to sbt-scalajs 1.5.0
 * Upgrade to munit 0.7.21
 * Upgrade to sbt 1.4.7

## 0.3.0

_2020-12-21_

 * Rename `rgb` interpolator to `hex`
 * Upgrade to scala 2.13.4
 * Upgrade to munit 0.7.20
 * Upgrade to sbt-houserules 0.3.2
 * Upgrade to sbt-scalajs 1.3.1
 * Upgrade to sbt 1.4.5

## 0.2.3

_2020-10-19_

 * Add `Color.brighter` and `Color.darker` methods
 * Upgrade to sbt-scalajs 1.3.0

## 0.2.2

_2020-10-14_

 * Migrate test suite to munit
 * Upgrade to sbt-houserules 0.3.0
 * Upgrade to sbt 1.4.0
 * Upgrade to scala 2.12.12
 * Upgrade to scala 2.13.3

## 0.2.1

_2020-03-28_

 * Upgrade to sbt-scalajs 1.0.1
 * Upgrade to scala 2.12.11
 * Upgrade to testf 0.1.4
 * Upgrade to sbt-houserules 0.1.10
 * Upgrade to sbt 1.3.8
 * Disable test coverage while it'S incompatible to scalajs 1.0

## 0.2.0

_2019-11-27_

 * Add `jwt.Color` conversion methods
 * Make alpha channel non optional
 * Upgrade to scala 2.13.1
 * Upgrade to scalajs 0.6.31
 * Upgrade to sbt-houserule 0.1.4
 * Upgrade to sbt 1.3.4

## 0.1.1

_2019-11-07_

 * Change `Color.opaque` to set alpha channel to max, and instead add `Color.dropAlpha` method as an alternative
 * Use `Byte` instead of `Short` for the `Channel` representation

## 0.1.0

_2019-11-06_

 * Initial release
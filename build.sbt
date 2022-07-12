import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val Version = new {
  val Munit = "0.7.29"
  val Scala212 = "2.12.16"
  val Scala213 = "2.13.8"
  val Scala3 = "3.1.0"
}

noPublishSettings

ThisBuild / crossScalaVersions := List(Version.Scala212, Version.Scala213, Version.Scala3)
ThisBuild / scalaVersion := Version.Scala213
ThisBuild / testFrameworks += new TestFramework("munit.Framework")

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Full)
  .settings(sonatypePublishSettings)
  .settings(
    console / initialCommands +=
      """import io.taig.color._
        |import io.taig.color.implicits._""".stripMargin,
    libraryDependencies += "org.scalameta" %%% "munit" % Version.Munit % "test",
    libraryDependencies ++= {
      if (scalaVersion.value == Version.Scala3) Nil
      else "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided" :: Nil
    },
    name := "color-core"
  )
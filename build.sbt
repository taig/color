import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val Version = new {
  val Munit = "0.7.27"
  val Scala212 = "2.12.13"
  val Scala213 = "2.13.5"
  val Scala3 = "3.0.1"
}

noPublishSettings

ThisBuild / crossScalaVersions := List(Version.Scala212, Version.Scala213, Version.Scala3)
ThisBuild / scalafmtRules += """project.excludeFilters = [ "/scala-3/" ]""".stripMargin
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
  .jsSettings(scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)))

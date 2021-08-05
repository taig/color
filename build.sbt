import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val Version = new {
  val Munit = "0.7.22"
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
      if (isDotty.value) Nil else "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided" :: Nil
    },
    name := "color-core",
    Compile / unmanagedSourceDirectories ++= {
      def extraDirs(suffix: String) =
        CrossType.Full.sharedSrcDir(baseDirectory.value, "main").toList.map(f => file(f.getPath + suffix))

      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, y)) => extraDirs("-2") ++ (if (y >= 13) extraDirs("-2.13+") else Nil)
        case Some((3, _)) => extraDirs("-3") ++ extraDirs("-2.13+")
        case _            => Nil
      }
    }
  )
  .jsSettings(scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule)))

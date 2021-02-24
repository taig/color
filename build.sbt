import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val SilencerVersion = "1.7.1"
val MunitVersion = "0.7.22"

noPublishSettings

ThisBuild / crossScalaVersions := List("2.12.13", "2.13.5", scalaVersion.value)
ThisBuild / scalafmtRules += """project.excludeFilters = [ "/scala-3/" ]""".stripMargin
ThisBuild / scalaVersion := "3.0.0-RC1"
ThisBuild / testFrameworks += new TestFramework("munit.Framework")

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Full)
  .settings(sonatypePublishSettings)
  .settings(
    initialCommands in console +=
      """import io.taig.color._
        |import io.taig.color.implicits._""".stripMargin,
    libraryDependencies ++=
      "org.scalameta" %%% "munit" % MunitVersion % "test" ::
        Nil,
    libraryDependencies ++= {
      if (isDotty.value) Nil
      else "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided" :: Nil
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

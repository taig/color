import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val SilencerVersion = "1.7.1"
val MunitVersion = "0.7.21"

lazy val color = project
  .settings(noPublishSettings)
  .in(file("."))
  .settings(
    console := { (core.jvm / Compile / console).value }
  )
  .aggregate(core.jvm, core.js)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .settings(sonatypePublishSettings)
  .settings(
    initialCommands in console +=
      """import io.taig.color._
        |import io.taig.color.implicits._""".stripMargin,
    libraryDependencies ++=
      compilerPlugin("com.github.ghik" % "silencer-plugin" % SilencerVersion cross CrossVersion.full) ::
        ("com.github.ghik" % "silencer-lib" % SilencerVersion % "provided" cross CrossVersion.full) ::
        "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided" ::
        "org.scalameta" %%% "munit" % MunitVersion % "test" ::
        Nil,
    name := "color-core",
    testFrameworks += new TestFramework("munit.Framework")
  )
  .jsSettings(
    scalaJSLinkerConfig ~= (_.withModuleKind(ModuleKind.CommonJSModule))
  )

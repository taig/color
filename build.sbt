import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val SilencerVersion = "1.6.0"
val TestfVersion = "0.1.2"

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
    addCompilerPlugin(
      "com.github.ghik" % "silencer-plugin" % SilencerVersion cross CrossVersion.full
    ),
    initialCommands in console +=
      """import io.taig.color._
        |import io.taig.color.implicits._""".stripMargin,
    libraryDependencies ++=
      ("com.github.ghik" % "silencer-lib" % SilencerVersion % "provided" cross CrossVersion.full) ::
        "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided" ::
        "io.taig" %%% "testf-auto" % TestfVersion % "test" ::
        "io.taig" %%% "testf-runner-sbt" % TestfVersion % "test" ::
        Nil,
    testFrameworks += new TestFramework("io.taig.testf.runner.TestF")
  )

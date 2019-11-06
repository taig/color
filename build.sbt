import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

lazy val color = project
  .settings(noPublishSettings)
  .in(file("."))
  .aggregate(core.jvm, core.js)

lazy val core = crossProject(JVMPlatform, JSPlatform)
  .crossType(CrossType.Pure)
  .settings(sonatypePublishSettings)
  .settings(
    initialCommands in console +=
      """import io.taig.color._
        |import io.taig.color.implicits._""".stripMargin,
    libraryDependencies ++=
      "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided" ::
        "io.taig" %%% "testf-auto" % "0.1.2" % "test" ::
        "io.taig" %%% "testf-runner-sbt" % "0.1.2" % "test" ::
        Nil,
    testFrameworks += new TestFramework("io.taig.testf.runner.TestF")
  )

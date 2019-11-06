initialCommands in console += "import io.taig.color._"

libraryDependencies ++=
  "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided" ::
    "io.taig" %% "testf-auto" % "0.1.2-SNAPSHOT" % "test" ::
    "io.taig" %% "testf-runner-sbt" % "0.1.2-SNAPSHOT" % "test" ::
    Nil

resolvers += Resolver.sonatypeRepo("snapshots")

testFrameworks += new TestFramework("io.taig.testf.runner.TestF")

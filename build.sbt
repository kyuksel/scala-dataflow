import sbt.project

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq("-feature"),
  resolvers ++= Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  ),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-library" % scalaVersion.value,
    "org.scala-lang" % "scala-compiler" % scalaVersion.value,
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "com.google.cloud.dataflow" % "google-cloud-dataflow-java-sdk-all" % "1.4.0",
    "ch.qos.logback" % "logback-classic" % "1.1.3")
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .settings(
    name := "Scala Dataflow",
    mainClass in Compile := Some("apps.SimpleApp")
  )

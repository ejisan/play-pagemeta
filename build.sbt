name := """play-pagemeta"""

version := "2.0.0"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.8")

organization := "com.ejisan"

publishTo := Some(Resolver.file("ejisan", file(Path.userHome.absolutePath+"/Development/repo.ejisan"))(Patterns(true, Resolver.mavenStyleBasePattern)))

lazy val `play-pagemeta` = (project in file("."))

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.5.4" % Provided,
  "com.typesafe.play" %% "play-specs2" % "2.5.4" % Test
)

name := """play-pagemeta"""

version := "1.0.0"

scalaVersion := "2.11.6"

organization := "com.ejisan"

publishTo := Some(Resolver.file("ejisan", file(Path.userHome.absolutePath+"/Development/repo.ejisan"))(Patterns(true, Resolver.mavenStyleBasePattern)))

lazy val `play-pagemeta` = (project in file("."))

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play" % "2.4.0" % Provided,
  "com.typesafe.play" %% "play-specs2" % "2.4.0" % Test
)

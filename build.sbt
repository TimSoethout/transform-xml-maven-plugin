name := "transform-xml-maven-plugin"
organization := "nl.timmybankers.maven"

publishMavenStyle := true
//packageOptions +=
publishTo := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))
//pomExtra := <packaging>maven-plugin</packaging>
artifact := Artifact("", "maven-plugin", "")

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.6"


libraryDependencies ++= Seq(
//  "org.apache.maven" % "maven-plugin-plugin" % "3.4",
//  "org.apache.maven" % "maven-plugin-api" % "3.3.3",
  "org.apache.maven.plugin-tools" % "maven-plugin-tools-api" % "3.4",
  "org.apache.maven.plugin-tools" % "maven-plugin-annotations" % "3.4"
)

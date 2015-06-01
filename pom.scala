import org.sonatype.maven.polyglot.scala.model._

import scala.collection.immutable.Seq

implicit val scalaVersion = ScalaVersion("2.11.6")

ScalaModel(
  "nl.timmybankers.maven" % "transform-xml-maven-plugin" % "1.0-SNAPSHOT",
  name = "Transform XML Maven Plugin",
  packaging = "maven-plugin",
  contributors = Seq(
    Contributor(
      name = "Tim Soethout"
      //      organization = "",
      //      organizationUrl = ""
    )
  ),
  description = "Maven plugin which can transform xml during the build. Currently supports deleting of nodes by xpath.",
  scm = Scm(url = "https://github.com/TimSoethout/transform-xml-maven-plugin"),
  dependencies = Seq(
    "org.apache.maven.plugin-tools" % "maven-plugin-tools-api" % "3.4",
    "org.apache.maven.plugin-tools" % "maven-plugin-annotations" % "3.4",
    "org.scala-lang.modules" %% "scala-xml" % "1.0.3",
    "org.scalesxml" %% "scales-jaxen" % "0.6.0-M3",
    "org.scalatest" %% "scalatest" % "2.2.4" % "test"
  ),
  build = Build(
    plugins = Seq(
      Plugin(
        "io.takari.polyglot" % "polyglot-translate-plugin" % "0.1.10",
        configuration = Config(input = "pom.scala", output = "pom.xml"),
        executions = Seq(Execution(phase = "compile", goals = Seq("translate")))

      )
    )
  ),
  modelVersion = "4.0.0"
)
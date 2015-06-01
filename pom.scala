import org.sonatype.maven.polyglot.scala.model._

import scala.collection.immutable.Seq

implicit val scalaVersion = ScalaVersion("2.11.6")

ScalaModel(
  "nl.timmybankers.maven" % "transform-xml-maven-plugin" % "1.0.1-SNAPSHOT",
  name = "Transform XML Maven Plugin",
  packaging = "maven-plugin",
  developers = Seq(
    Developer(
      name = "Tim Soethout"
      //      organization = "",
      //      organizationUrl = ""
    )
  ),
  description = "Maven plugin which can transform xml during the build. Currently supports deleting of nodes by xpath.",
  url = "https://github.com/TimSoethout/transform-xml-maven-plugin",
  scm = Scm(
    url = "https://github.com/TimSoethout/transform-xml-maven-plugin",
    connection = "scm:git:git://github.com/TimSoethout/transform-xml-maven-plugin"),
  licenses = Seq(License("MIT", url = "http://opensource.org/licenses/MIT")),
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

      ),
      Plugin(
        "org.sonatype.plugins" % "nexus-staging-maven-plugin" % "1.6.5",
        extensions = true, configuration = Config(
          serverId = "ossrh",
          nexusUrl = "https://oss.sonatype.org/",
          autoReleaseAfterClose = true)
      )
    )
  ),
  distributionManagement = DistributionManagement(
    snapshotRepository =
      DeploymentRepository(id = "ossrh",
        url = "https://oss.sonatype.org/content/repositories/snapshots"),
    repository = DeploymentRepository(id = "ossrh",
      url = "https://oss.sonatype.org/service/local/staging/deploy/maven2/")
  ),
  profiles = Seq(
    Profile(id = "release",
      build = Build(plugins = Seq(
        Plugin(
          "org.apache.maven.plugins" % "maven-source-plugin" % "2.4",
          executions = Seq(Execution(
            id = "attach-sources",
            goals = Seq("jar-no-fork")
          ))),
        Plugin(
          "org.apache.maven.plugins" % "maven-javadoc-plugin" % "2.10.3",
          executions = Seq(Execution(
            id = "attach-javadocs",
            goals = Seq("jar")))),
        Plugin(
          "org.apache.maven.plugins" % "maven-gpg-plugin" % "1.6",
          executions = Seq(Execution(
            id = "sign-artifacts",
            phase = "verify",
            goals = Seq("sign")
          )))
      )))
  ),
  modelVersion = "4.0.0"
)
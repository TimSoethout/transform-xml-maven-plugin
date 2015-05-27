import org.sonatype.maven.polyglot.scala.model._
import scala.collection.immutable.Seq

implicit val scalaVersion = ScalaVersion("2.11.6")

ScalaModel(
  "nl.timmybankers.maven" % "transform-xml-maven-plugin" % "1.0-SNAPSHOT",
  name = "Transform XML Maven Plugin",

  contributors = Seq(
    Contributor(
      name = "Tim Soethout"
      //      organization = "",
      //      organizationUrl = ""
    )
  ),
  //  repositories = Seq(
  //    Repository(
  //      snapshots = RepositoryPolicy(enabled = false),
  //      id = "sonatype-public-grid",
  //      url = "http://repository.sonatype.org/content/groups/sonatype-public-grid/"
  //    )
  //  ),
  dependencies = Seq(
    //    "io.tesla.polyglot" % "tesla-polyglot-common" % "0.0.1-SNAPSHOT",
    //    "com.twitter" %% "util-eval" % "6.3.8",
    //    "com.googlecode.kiama" %% "kiama" % "1.5.1",
    //    "org.specs2" %% "specs2" % "2.1.1" % "test",
    //    "junit" % "junit" % "4.12" % "test"
    "org.apache.maven.plugin-tools" % "maven-plugin-tools-api" % "3.4",
    "org.apache.maven.plugin-tools" % "maven-plugin-annotations" % "3.4"
  ),
  build = Build(
    plugins = Seq(
      Plugin(
        "io.takari.polyglot" % "polyglot-translate-plugin" % "0.1.10",
        configuration = Config(input = "pom.scala", output = "pom.xml"),
        executions = Seq(Execution(phase = "compile", goals = Seq("translate")))

      )
    ),
    tasks = Seq(Task("someTaskId", "verify") {
      ec => println("I'm Scala running during the verify phase. The ec passed in allows me to access the project")
    })
  ),
  modelVersion = "4.0.0"
)
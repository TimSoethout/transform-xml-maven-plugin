package nl.timmybankers.maven

import java.io.{StringWriter, Writer, FileReader, FileWriter}

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.{Mojo, Parameter}
import scales.utils.top
import scales.xml.ScalesXml._
import scales.xml._
import scales.xml.jaxen.ScalesXPath

@Mojo(name = "transform-xml")
class TransformXmlMojo extends AbstractMojo {

  @Parameter
  var inputXmlPath: String = null
  @Parameter
  val outputXmlPath: String = null
  @Parameter
  val xpath: String = null
  @Parameter
  val action: String = "DELETE"

  override def execute(): Unit = {
    getLog.info("Starting Transform XML Maven plugin")

    val xml: Doc = loadXml(new FileReader(inputXmlPath))

    val path: ScalesXPath = ScalesXPath("//class[contains(@filename,'.scala')]")

//    val filter: Iterable[Either[AttributePath, XmlPath]] = path.evaluate(top(xml))

//    val output: String = asString(filter)
//    getLog.debug(output)
//    val writer: Writer = new FileWriter(outputXmlPath)
//    output.writeTo(new StringWriter())


    //    inputFile.
  }
}

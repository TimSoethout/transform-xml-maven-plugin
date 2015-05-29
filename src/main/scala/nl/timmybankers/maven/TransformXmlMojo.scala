package nl.timmybankers.maven

import java.io.FileReader
import javax.xml.parsers.{DocumentBuilderFactory, DocumentBuilder}
import javax.xml.xpath._

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.{Mojo, Parameter}
import org.w3c.dom.{Document, Node, NodeList}
import org.xml.sax.InputSource
import scales.xml.ScalesXml._

//import scales.xml._

@Mojo(name = "transform-xml")
class TransformXmlMojo extends AbstractMojo {

  @Parameter(property = "inputXmlPath")
  var inputXmlPath: String = null
  @Parameter(property = "outputXmlPath")
  val outputXmlPath: String = null
//  @Parameter
//  val xpath: String = null
  @Parameter
  val action: String = "DELETE"

  override def execute(): Unit = {
    getLog.debug("Starting Transform XML Maven plugin")

//    val xmlDoc: InputSource = new FileReader(inputXmlPath)

    val xpath: XPath = XPathFactory.newInstance().newXPath()

    val factory = DocumentBuilderFactory.newInstance()
    factory.setNamespaceAware( true );
    val builder = factory.newDocumentBuilder();
    builder.setEntityResolver( new BlankingResolver() );

    val xmlDoc: Document = builder.parse(inputXmlPath)
    val pathExpr: XPathExpression = xpath.compile("//class[contains(@filename,'.scala')]");
    //    try {
    //      pathExpr =
    //    } catch {
    //      case (e: XPathExpressionException) => e.printStackTrace();
    //    }
    val list: NodeList = pathExpr.evaluate(xmlDoc, XPathConstants.NODESET).asInstanceOf[NodeList]
//    try {
//      list =
//    } catch {
//      case (e: XPathExpressionException) => e.printStackTrace();
//    }

    for ( i <- 0 to list.getLength) {
      val node: Node = list.item(i)
      getLog.debug(s"Removing node: ${node}")
      node.getParentNode.removeChild(node)
    }

    getLog.info(xmlDoc.toString)


    //    val xml: Doc = loadXml(new FileReader(inputXmlPath))

    //    val path: ScalesXPath = ScalesXPath("//class[contains(@filename,'.scala')]")
    //
    //    val filter: Iterable[Either[AttributePath, XmlPath]] = path.evaluate(top(xml))
    //
    //    val output: String = asString(filter)
    //    getLog.debug(output)
    //    val writer: Writer = new FileWriter(outputXmlPath)
    //    output.writeTo(new StringWriter())


    //    inputFile.
    getLog.debug("Finished Transform XML Maven plugin")
  }
}

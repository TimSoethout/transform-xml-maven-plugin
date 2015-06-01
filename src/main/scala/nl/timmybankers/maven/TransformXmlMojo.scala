package nl.timmybankers.maven

import java.io.{FileWriter, StringWriter}
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.{Transformer, TransformerFactory}
import javax.xml.xpath._

import org.apache.maven.plugin.AbstractMojo
import org.apache.maven.plugins.annotations.{Mojo, Parameter}
import org.w3c.dom.{Document, Node, NodeList}

@Mojo(name = "transform-xml")
class TransformXmlMojo extends AbstractMojo {

  @Parameter(property = "inputXmlPath")
  var inputXmlPath: String = null
  @Parameter(property = "outputXmlPath")
  var outputXmlPath: String = null
  @Parameter(property = "xpath")
  var xpath: String = null
  @Parameter(property = "action")
  var action: String = "DELETE"
  @Parameter(property = "skipOnFileErrors")
  var skipOnFileErrors = false

  override def execute(): Unit = {
    getLog.debug("Starting Transform XML Maven plugin")

    val xmlDocOption = loadFile(inputXmlPath)

    xmlDocOption match {
      case None =>
        if (skipOnFileErrors)
          getLog.info(s"inputXmlPath `$inputXmlPath` not found. Skipping execution.")
        else throw new Exception(s"inputXmlPath `$inputXmlPath` not found. Breaking.")
      case Some(xmlDoc) =>
        val xpathHandler: XPath = XPathFactory.newInstance().newXPath()

        val pathExpr: XPathExpression = xpathHandler.compile(xpath)
        val list: NodeList = pathExpr.evaluate(xmlDoc, XPathConstants.NODESET).asInstanceOf[NodeList]

        for (i <- 0 until list.getLength) {
          val node: Node = list.item(i)
          getLog.debug(s"Removing node: ${node.getNodeName}")
          node.getParentNode.removeChild(node)
        }

        writeToFile(xmlDoc)
    }
    getLog.debug("Finished Transform XML Maven plugin")
  }

  def writeToFile(xmlDoc: Document): Unit = {
    val source: DOMSource = new DOMSource(xmlDoc)
    val transFactory: TransformerFactory = TransformerFactory.newInstance()
    val transformer: Transformer = transFactory.newTransformer()
    if (getLog.isDebugEnabled) {

      val writer: StringWriter = new StringWriter()

      transformer.transform(source, new StreamResult(writer))
      writer.flush()
      getLog.debug(writer.toString)
    }

    getLog.info("writing to file " + outputXmlPath)
    transformer.transform(source, new StreamResult(new FileWriter(outputXmlPath)))
  }

  def loadFile(inputXmlPath: String): Option[Document] = {

    try {
      val factory = DocumentBuilderFactory.newInstance()
      factory.setNamespaceAware(true)
      val builder = factory.newDocumentBuilder()
      builder.setEntityResolver(new BlankingResolver())

      val xmlDoc: Document = builder.parse(inputXmlPath)
      Some(xmlDoc)
    }
    catch {
      case e: Throwable =>
        getLog.warn(s"Something went wrong with loading the xml file `$inputXmlPath`: ${e.toString}")
        None
    }
  }
}

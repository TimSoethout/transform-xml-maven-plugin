package nl.timmybankers.maven

import java.io.{File, FileWriter, StringWriter}
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.{Transformer, TransformerFactory}
import javax.xml.xpath._

import org.apache.maven.plugin.{AbstractMojo, MojoFailureException}
import org.apache.maven.plugins.annotations.{Mojo, Parameter}
import org.w3c.dom.{Document, Node, NodeList}

import scala.util.{Failure, Success, Try}

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

      case Failure(exception) =>
        if (skipOnFileErrors)
          getLog.info(s"inputXmlPath `$inputXmlPath` not found. Skipping plugin execution.", exception)
        else throw new MojoFailureException(s"inputXmlPath `$inputXmlPath` not found. Breaking.", exception)
      case Success(xmlDoc) =>
        val pathExpr: XPathExpression = XPathFactory.newInstance().newXPath().compile(xpath)
        val evaluate: AnyRef = pathExpr.evaluate(xmlDoc, XPathConstants.NODESET)
        evaluate match {
          case list: NodeList =>
            for (i <- 0 until list.getLength) {
              val node: Node = list.item(i)
              getLog.debug(s"Removing node: ${node.getNodeName}")
              node.getParentNode.removeChild(node)
            }
          case otherwise => getLog.warn(s"Warning, something else than NodeList found: $otherwise")
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
    val outputFile: File = new File(outputXmlPath)
    outputFile.createNewFile()
    transformer.transform(source, new StreamResult(new FileWriter(outputFile)))
  }

  def loadFile(inputXmlPath: String): Try[Document] = {
    Try {
      val factory = DocumentBuilderFactory.newInstance()
      factory.setNamespaceAware(true)
      val builder = factory.newDocumentBuilder()
      builder.setEntityResolver(new BlankingResolver())

      builder.parse(inputXmlPath)
    }
  }
}

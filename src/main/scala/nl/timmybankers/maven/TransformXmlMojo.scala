package nl.timmybankers.maven

import org.apache.maven.plugin.{ AbstractMojo}
import org.apache.maven.plugins.annotations.Mojo

@Mojo(name="transform-xml")
class TransformXmlMojo extends AbstractMojo{

  val inputXmlPath : String = null
  val outputXmlPath : String = null

  override def execute(): Unit = {
    getLog.info("Starting Transform XML Maven plugin")


  }
}

package nl.timmybankers.maven

import java.io.File
import java.nio.file.{Files, Paths}

import org.scalatest._

import scala.xml.{NodeSeq, XML}

class TransformXmlMojoTest extends FlatSpec with ShouldMatchers {

  "execte" should "work with some sample xml" in {

    val mojo: TransformXmlMojo = new TransformXmlMojo
    mojo.inputXmlPath = Paths.get(getClass.getResource("/coverage.xml").toURI).toString

    val outputFile = File.createTempFile("transform-xml-output", ".xml").toString
    mojo.outputXmlPath = outputFile

    mojo.xpath = "//class[contains(@filename,'.scala')]"
    mojo.action = "DELETE"

    mojo.execute()

    println(Files.readAllLines(Paths.get(outputFile)))

    val xml = XML.loadFile(outputFile)
    val scalaClasses: NodeSeq = xml \\ "class" filter (_.attribute("filename").exists(_.text contains ".scala"))

    scalaClasses should be(empty)
  }

}

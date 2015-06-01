package nl.timmybankers.maven

import java.io.File
import java.nio.file.{Files, Paths}

import org.scalatest._

import scala.xml.{NodeSeq, XML}

class TransformXmlMojoTest extends FlatSpec with ShouldMatchers {

  behavior of "execute"
  it should "work with some sample xml" in {

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

    val javaClasses: NodeSeq = xml \\ "class" filter (_.attribute("filename").exists(_.text contains ".java"))
    javaClasses should have size 1

  }

  it should "work skip with non-existing file when skipping is enabled" in {

    val mojo: TransformXmlMojo = new TransformXmlMojo
    mojo.inputXmlPath = "/non-existing.xml"

    val outputFile = File.createTempFile("transform-xml-output", ".xml").toString
    mojo.outputXmlPath = outputFile

    mojo.xpath = "//class[contains(@filename,'.scala')]"
    mojo.action = "DELETE"
    mojo.skipIfNonExisting = true

    mojo.execute()

    Files.readAllLines(Paths.get(outputFile)) should be(empty)
  }

  it should "not work with non-existing file when skipping is disabled" in {

    val mojo: TransformXmlMojo = new TransformXmlMojo
    mojo.inputXmlPath = "/non-existing.xml"

    val outputFile = File.createTempFile("transform-xml-output", ".xml").toString
    mojo.outputXmlPath = outputFile

    mojo.xpath = "//class[contains(@filename,'.scala')]"
    mojo.action = "DELETE"
    mojo.skipIfNonExisting = false

    intercept[Exception] {
      mojo.execute()
    }
  }

}

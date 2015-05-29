package nl.timmybankers.maven

import org.scalatest._

class TransformXmlMojoTest extends FlatSpec {

  "execte" should "work with some sample xml" in {

    val mojo: TransformXmlMojo = new TransformXmlMojo
    mojo.inputXmlPath = ""
//    mojo.outputXmlPath = ""

    mojo.execute()


  }

}

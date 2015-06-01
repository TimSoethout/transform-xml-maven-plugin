package nl.timmybankers.maven

import java.io.ByteArrayInputStream

import org.xml.sax.{EntityResolver, InputSource};

class BlankingResolver extends EntityResolver {
  override def resolveEntity(publicId: String, systemId: String): InputSource =
    new InputSource(new ByteArrayInputStream("".getBytes))
}
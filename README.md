# transform-xml-maven-plugin

[![Codacy Badge](https://www.codacy.com/project/badge/c911189b93a8487f9dae16bd3013da60)](https://www.codacy.com/app/github_10/transform-xml-maven-plugin)

Maven-plugin which can be used to transform xml as part of your build process.

For now only supports deleting of xml element which conform to an xpath query.

usage:
```
mvn nl.timmybankers.maven:transform-xml-maven-plugin:1.0-SNAPSHOT:transform-xml -DinputXmlPath="/Users/tim/workspace/api-toolkit/toolkit-http/target/site/cobertura/coverage.xml" -Dxpath="//class[contains(@filename,'.scala')]" -DoutputXmlPath=/tmp/test.xml
```

maven plugin usage:
```
    <build>
        <plugins>
            <plugin>
                <groupId>nl.timmybankers.maven</groupId>
                <artifactId>transform-xml-maven-plugin</artifactId>
                <version>1.0-SNAPSHOT</version>
                <executions>
                  <execution>
                      <phase>verify</phase>
                      <goals>
                          <goal>transform-xml</goal>
                      </goals>
                  </execution>
                </executions>
                <configuration>
                    <inputXmlPath>${project.build.directory}/site/cobertura/coverage.xml</inputXmlPath>
                    <outputXmlPath>${sonar.build.directore}/coverage-without-scala.xml</outputXmlPath>
                    <xpath>//class[contains(@filename,'.scala')]</xpath>
                    <action>DELETE</action>
                    <skipOnFileErrors>true</skipOnFileErrors>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
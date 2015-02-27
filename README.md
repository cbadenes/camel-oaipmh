
For more details about OAI-PMH see the documentation: [http://www.openarchives.org/pmh/](http://www.openarchives.org/pmh/)

## OAI-PMH Component

The **oaipmh** component is used for polling OAI-PMH data providers. Camel will default poll the provider every 60th seconds.

Maven users will need to add the following dependency to their pom.xml for this component:
```xml
<dependency>
    <groupId>es.upm.oeg.camel</groupId>
    <artifactId>camel-oaipmh</artifactId>
    <version>x.x.x</version>
</dependency>
```

**Note**: The component currently only supports polling (consuming) feeds.

**Note**: You must include this repository in your *pom.xml*:
 ```xml
 <repositories>
     <!-- GitHub Repository -->
     <repository>
         <id>camel-oaipmh-mvn-repo</id>
         <url>https://raw.github.com/cabadol/camel-oaipmh/mvn-repo/</url>
         <snapshots>
             <enabled>true</enabled>
             <updatePolicy>always</updatePolicy>
         </snapshots>
     </repository>
 </repositories>
 ```

## URI format

```txt
oaipmh:oaipmhURI
```

Where `oaipmhURI` is the URI to the OAI-PMH feed to poll.

You can append query options to the URI in the following format, `?option=value&option=value&`...

## Options

| Property | Default  | Description |
| :------- |:--------:| :---------- |
| delay    | 60000    | Delay in milliseconds between each poll |
| initialDelay    | 1000    | Milliseconds before polling starts |
| userFixedDelay    | false    | Set to true to use fixed delay between pools, otherwise fixed rate is used. See [ScheduledExecutorService](http://docs.oracle.com/javase/1.5.0/docs/api/java/util/concurrent/ScheduledExecutorService.html) in JDK for details. |
| verb    | ListRecords    | Retrieve records from a repository |
| metadataPrefix    | oai_dc    | Specifies the [metadataPrefix](http://www.openarchives.org/OAI/openarchivesprotocol.html#metadataPrefix) of the format that should be included in the [metadata part of the returned records](http://www.openarchives.org/OAI/openarchivesprotocol.html#Record). |
| from    | current    | Specifies a lower bound for datestamp-based [selective harvesting](http://www.openarchives.org/OAI/openarchivesprotocol.html#Datestamp). [UTC DateTime](http://www.openarchives.org/OAI/openarchivesprotocol.html#Dates) value|

## Exchange data types

Camel initializes the **In** body on the Exchange with a *ListRecords* response message in XML format. Camel returns a message for each *Record* received.

## Message Headers

| Header | Description |
| :------- |:--------:| :---------- |
| `OAIPMH.Message`    | The `OAIPMHtype`message object |

## OAI-PMH Data Format

The **oaipmh** component ships with an OAIPMH dataformat that can be used to convert between `String` (XML) and `OAIPMHType` model object (JaxB).
- `marshal` = from `OAIPMHType` to XML `String`
- `unmarshal` = from XML `String` to `OAIPMHType`
More details about these xsd [here](http://www.openarchives.org/OAI/openarchivesprotocol.html#OAIPMHschema).

A route using this would look something like this:
```txt
from("oaipmh://aprendeenlinea.udea.edu.co/revistas/index.php/ingenieria/oai?delay=60000").unmarshal().jaxb("es.upm.oeg.camel.oaipmh.model").to("mock:result");
```


The purpose of this feature is to make it possible to use Camel's lovely built-in expressions for manipulating OAI-PMH messages. As show below, an XPath expression can be used to filter the OAI-PMH message:
```txt
from("oaipmh://aprendeenlinea.udea.edu.co/revistas/index.php/ingenieria/oai?delay=60000").unmarshal().jaxb("es.upm.oeg.camel.oaipmh.model").filter().xpath("//item/request/set[contains(.,'physics')]").to("mock:result");
```

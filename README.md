
This project is a [Camel](http://camel.apache.org) component for communicate to Data Stores using the Open Archives Initiative Protocol for Metadata Harvesting (OAI-PMH).

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
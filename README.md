# spring-mtxml-api
This is a web service / api built with Spring. The functionality mirrors the [mtxml repository](https://github.com/pederhaag/mtxml). The mtxml-repository on the other hand provides deployment into AWS.

On the backend a MySQL database is initialized with content description of single tags, including constructed regular expressions used in parsing. This initialization is relatively heavy, but is only performed at startup.

**Note:** A lot of features in terms of api-funcitonality is missing in this repository like for example caching.

## Supported methods
* GET /mtxml/mttoxml translates a full MT message to XML. Contents of the MT message is expected in parameter `content` of the request
* GET /mtxml/tagtoxml translates a single tag to XML. Content of the is expected in parameter `content` of the request. The tag type (i.e. 22F, 19A, etc.) is expected in parameter `tag`


### Example
#### Input: A fictional MT103 message
```
{1:F01SOGEFRPPAXXX0070970817}{2:O1031734150713DEUTDEFFBXXX00739698421607131634N}{3:{103:TGT}{108:OPTUSERREF16CHAR}}{4:
:20:UNIQUEREFOFTRX16
:23B:CRED
:32A:180724EUR735927,75
:33B:EUR735927,75
:50A:/DE37500700100950596700
DEUTDEFF
:59:/FR7630003034950005005419318
CHARLES DUPONT COMPANY
RUE GENERAL DE GAULLE, 21
75013 PARIS
:71A:SHA
-}{5:{CHK:D628FE0165A7}}
```
### Output:
```xml
<SwiftMessaage>
    <BasicHeader>
        <ApplicationIdentifier>F</ApplicationIdentifier>
        <ServiceIdentifier>01</ServiceIdentifier>
        <LTAddress>SOGEFRPPAXXX</LTAddress>
        <LTAddress_Details>
            <BIC>SOGEFRPP</BIC>
            <LogicalTerminal>A</LogicalTerminal>
            <BIC8>SOGEFRPP</BIC8>
        </LTAddress_Details>
        <SessionNumber>0070</SessionNumber>
        <SequenceNumber>970817</SequenceNumber>
    </BasicHeader>
    <ApplicationHeader>
        <InputOutputIdentifier>O</InputOutputIdentifier>
        <MessageType>103</MessageType>
        <InputTime>1734</InputTime>
        <MIR>150713DEUTDEFFBXXX0073969842</MIR>
        <MIR_Details>
            <SendersDate>150713</SendersDate>
            <LogicalTerminal>DEUTDEFFBXXX</LogicalTerminal>
            <SessionNumber>0073</SessionNumber>
            <SequenceNumber>969842</SequenceNumber>
        </MIR_Details>
        <OutputDate>160713</OutputDate>
        <OutputTime>1634</OutputTime>
        <Priority>N</Priority>
    </ApplicationHeader>
    <UserHeader>
        <UserTag>
            <Tag>103</Tag>
            <Contents>TGT</Contents>
        </UserTag>
        <UserTag>
            <Tag>108</Tag>
            <Contents>OPTUSERREF16CHAR</Contents>
        </UserTag>
    </UserHeader>
    <TextBlock>
        <Tag20>
            <Contents>UNIQUEREFOFTRX16</Contents>
        </Tag20>
        <Tag23B>
            <Type>CRED</Type>
        </Tag23B>
        <Tag32A>
            <Date>180724</Date>
            <Currency>EUR</Currency>
            <Amount>735927.75</Amount>
        </Tag32A>
        <Tag33B>
            <Currency>EUR</Currency>
            <Amount>735927.75</Amount>
        </Tag33B>
        <Tag50A>
            <Account>DE37500700100950596700</Account>
            <BIC>DEUTDEFF</BIC>
        </Tag50A>
        <Tag59>
            <Account>FR7630003034950005005419318</Account>
            <NameAndAddress>
                <Line>CHARLES DUPONT COMPANY</Line>
                <Line>RUE GENERAL DE GAULLE, 21</Line>
                <Line>75013 PARIS</Line>
            </NameAndAddress>
        </Tag59>
        <Tag71A>
            <Code>SHA</Code>
        </Tag71A>
    </TextBlock>
    <TrailerBlock>
        <Trailer>
            <Code>CHK</Code>
            <TrailerInformation>D628FE0165A7</TrailerInformation>
        </Trailer>
    </TrailerBlock>
</SwiftMessaage>
```

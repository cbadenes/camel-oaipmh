package es.upm.oeg.camel;


import es.upm.oeg.camel.oaipmh.dataformat.OAIPMHConverter;
import es.upm.oeg.camel.oaipmh.model.ElementType;
import es.upm.oeg.camel.oaipmh.model.OAIPMHtype;
import es.upm.oeg.camel.oaipmh.model.RecordType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.util.List;

public class MetadataTest {

    private static final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<OAI-PMH xmlns=\"http://www.openarchives.org/OAI/2.0/\" xmlns:provenance=\"http://www.openarchives.org/OAI/2.0/provenance\" xmlns:oai_dc=\"http://www.openarchives.org/OAI/2.0/oai_dc/\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">\n" +
            "    <responseDate>2015-02-27T14:53:10Z</responseDate>\n" +
            "    <request verb=\"ListRecords\" metadataPrefix=\"oai_dc\" from=\"2015-02-27T13:00:00Z\">http://eprints.ucm.es/cgi/oai2</request>\n" +
            "    <ListRecords>\n" +
            "        <record>\n" +
            "            <header>\n" +
            "                <identifier>oai:www.ucm.es:12217</identifier>\n" +
            "                <datestamp>2015-02-27T13:22:55Z</datestamp>\n" +
            "                <setSpec>7374617475733D707562</setSpec>\n" +
            "                <setSpec>7375626A656374733D41:415F3133:415F31335F333135</setSpec>\n" +
            "                <setSpec>74797065733D61727469636C65</setSpec>\n" +
            "            </header>\n" +
            "            <metadata>\n" +
            "                <dc>\n" +
            "                    <dc:relation>http://eprints.ucm.es/12217/</dc:relation>\n" +
            "                    <dc:title>Modelos estructurales de elementos finitos sobre la nucleación de las deformaciones compresivas en la Sierra de Altomira (España Central)</dc:title>\n" +
            "                    <dc:creator>Muñoz Martín, Alfonso</dc:creator>\n" +
            "                    <dc:creator>De Vicente Muñoz, Gerardo</dc:creator>\n" +
            "                    <dc:subject>Geodinámica</dc:subject>\n" +
            "                    <dc:description>En este trabajo se realizan una serie de modelos estructurales de elementos finitos sobre la geometría en profundidad del cinturón de pliegues y cabalgamientos alpinos de la Sierra de Altomira. El objetivo de estos modelos es comprobar numéricamente la hipótesis de que una geometría en escalón del basamento, asociado a una falla normal, es capaz de nuclear y concentrar las deformaciones de la cobertera situada por encima. La geometría utilizada ha sido un segmento simplificado de un corte geológico equilibrado basado en datos estructurales y geofísicos (gravimetría y perfiles sísmicos de reflexión). Los modelos incluyen tres tipos de materiales: un basamento elástico y resistente indeformado, una cobertera elástica y menos resistente que se desplaza de E a O, y un nivel de despegue poco resistente y al cual se le han supuesto dos tipos de comportamiento mecánico diferentes: a) elástico y b) elástico-plástico. Los dos modelos indican que la presencia del escalón nuclea las deformaciones en la cobertera, concentrando los desplazamientos verticales y los máximos valores de esfuerzo de cizalla. El modelo con nivel de despegue elástico predice una máxima deformación inicial en la cobertera al E de la falla en el basamento. Por el contrario, en el modelo con nivel de despegue elástico-plástico las deformaciones se concentran justo encima del escalón en el basamento, tal y como sugieren los datos geofísicos. Estos resultados amplían y completan el modelo previo (Van Wees, 1994) que asociaba la formación de la Sierra de Altomira con la desaparición de las facies Keuper (Triásico Superior). Lo más probable es que la presencia de la falla en el basamento no sólo controle el espesor de los materiales triásicos y jurásicos, sino también las facies, por lo que ambos factores pueden haber actuado conjuntamente en la nucleación de las deformaciones que dieron lugar a la formación de la Sierra de Altomira.</dc:description>\n" +
            "                    <dc:publisher>Sociedad Geológica de España</dc:publisher>\n" +
            "                    <dc:date>1999</dc:date>\n" +
            "                    <dc:type>info:eu-repo/semantics/article</dc:type>\n" +
            "                    <dc:type>PeerReviewed</dc:type>\n" +
            "                    <dc:identifier>http://eprints.ucm.es/12217/1/Mu%C3%B1oz_martin%26DeVicente2009.pdf</dc:identifier>\n" +
            "                    <dc:format>application/pdf</dc:format>\n" +
            "                    <dc:language>es</dc:language>\n" +
            "                    <dc:rights>info:eu-repo/semantics/openAccess</dc:rights>\n" +
            "                    <dc:relation>http://www.sociedadgeologica.es/publicaciones_revista.html</dc:relation>\n" +
            "                </dc>\n" +
            "            </metadata>\n" +
            "        </record>\n" +
            "    </ListRecords>\n" +
            "</OAI-PMH>\n";

    private OAIPMHtype message;


    @Before
    public void setup() throws JAXBException {
        this.message = OAIPMHConverter.xmlToOaipmh(xml);
    }

    @Test
    public void identifierTest(){

        RecordType record = message.getListRecords().getRecord().get(0);

        List<JAXBElement<ElementType>> metadata = record.getMetadata().getDc().getTitleOrCreatorOrSubject();

        String identifier = "";
        for (JAXBElement<ElementType> element: metadata){
            String parameter = element.getName().getLocalPart();
            if (parameter.equals("identifier")) {
                identifier = element.getValue().getValue();
            }
        }


        Assert.assertEquals("http://eprints.ucm.es/12217/1/Mu%C3%B1oz_martin%26DeVicente2009.pdf",identifier);
    }

}

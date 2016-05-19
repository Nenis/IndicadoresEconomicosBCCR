package indicadoreseconomicosbccr;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.Date;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;

public class WebServiceBCCR {

    private final String indicador = "317"; //Compra venta 318
    private final String fechaInicio;
    private final String fechaFinal;
    private final String nombre = "User";
    private final String subniveles = "N";
    private BufferedWriter writer = null;

    public WebServiceBCCR() {
        crearArchivoXML();
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.fechaInicio = dateFormat.format(date);
        this.fechaFinal = dateFormat.format(date);
    }

    public String obtenerTipoCambio() {
        String valor = "";
        try {
            File xml = new File("RespuestaBCCR.xml"); //Lee archivo xml
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = documentFactory.newDocumentBuilder();
            Document document = (Document) db.parse(xml);
           
            NodeList list = document.getElementsByTagName("INGC011_CAT_INDICADORECONOMIC");
            
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    valor =  element.getElementsByTagName("NUM_VALOR").item(0).getTextContent();
                }
            }
            
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println("Error");
        }
        return valor;
    }

    public void obtenerRespuesta() throws MalformedURLException, IOException {
        String urlWS = "http://indicadoreseconomicos.bccr.fi.cr/indicadoreseconomicos/WebServices/wsIndicadoresEconomicos.asmx";
        String methodWS = "/ObtenerIndicadoresEconomicos";
        URL url = new URL(urlWS + methodWS
                + "?tcIndicador=" + this.indicador
                + "&tcFechaInicio=" + this.fechaInicio
                + "&tcFechaFinal=" + this.fechaFinal
                + "&tcNombre=" + this.nombre
                + "&tnSubNiveles=" + this.subniveles);

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        String strTemp = "";
        while (null != (strTemp = br.readLine())) {
            guardarRespuesta(strTemp);

        }
        cerrarArchivoXML();
    }

    private void guardarRespuesta(String respuestaTemporal) {
        try {
            writer.write(respuestaTemporal);
        } catch (IOException ex) {
            System.out.println("No se pudo guardar el archivo");
        }
    }

    private void crearArchivoXML() {
        try {
            writer = new BufferedWriter(new FileWriter("RespuestaBCCR.xml"));
        } catch (IOException ex) {
            System.out.println("No se pudo Crear El archivo");
        }
    }

    private void cerrarArchivoXML() {
        try {
            writer.close();
        } catch (IOException ex) {
            System.out.println("No pudo cerrarse el archivo");
        }
    }
}

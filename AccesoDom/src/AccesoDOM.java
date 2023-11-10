
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;//for Document
import org.w3c.dom.Document;
import java.util.*;
import java.io.*;//clase File

public class AccesoDOM {

    
    Document doc;

    public int abriXMLaDOM(File f) {
        try {
            System.out.println("Abriendo archivo XML file y generando DOM");

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
//DocumentBuilder tiene el método parse que es el que genera DOM

            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(f);
// ahora doc apunta al arbol DOM y podemos recorrerlo
            System.out.println("DOM creado con éxito");
            return 0;//si el método funciona
        } catch (Exception e) {
            System.out.println(e);
            return -1;//if the method aborta en algún punto
        }
    }
}//fin clase


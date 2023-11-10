
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;//for Document
import org.w3c.dom.Document;
import java.util.*;
import java.io.*;//clase File
import static javax.management.Query.lt;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class AccessXMLDOM {
    public static void main(String args[]) throws Exception{
       File file = new File("Libros.xml");
       String s="hola";
       abriXMLaDOM(file);
        guardarDOMcomoArchivo(s);
    }
    Document doc;
    
    public static int abriXMLaDOM(File f) {
        try {
            Document doc;
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
    
    public int insertarLibroEnDOM(String titulo, String autor,String fecha) {
    try{
        System.out.println("Añadir libro al árbol DOM:"+titulo+";"+autor+";"+fecha);

        Node ntitulo=doc.createElement("Titulo");
        Node ntitulo_text=doc.createTextNode(titulo);
        ntitulo.appendChild(ntitulo_text);

        Node nautor=doc.createElement("Autor");
        Node nautor_text=doc.createTextNode(autor);
        nautor.appendChild(nautor_text);
        //CREA LIBRO, con atributo y nodos Título y Autor
        Node nLibro=doc.createElement("Libro");
        ((Element)nLibro).setAttribute("publicado",fecha);
        nLibro.appendChild(ntitulo);
        nLibro.appendChild(nautor);
        //APPEND LIBRO TO THE ROOT
        nLibro.appendChild(doc.createTextNode("\n"));
        Node raiz=doc.getFirstChild();//tb. doc.getChildNodes().item(0)
        raiz.appendChild(nLibro);
        System.out.println("Libro insertado en DOM.");
        return 0;
        }catch(Exception e){
        System.out.println(e);
        return -1;}
    }
    
    // Crea un nuevo archivo xml del DOM en memoria
public static void guardarDOMcomoArchivo(String nuevoArchivo) {
    try {
        Document doc=null;
        Source src = new DOMSource(doc); // Definimos el origen
        StreamResult rst = new StreamResult(new File(nuevoArchivo)); // Definimos el resultado

        // Declaramos el Transformer que tiene el método .transform() que necesitamos.
        Transformer transformer = TransformerFactory.newInstance().newTransformer();

        // Opción para indentar el archivo
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(src, (javax.xml.transform.Result) rst);
        System.out.println("Archivo creado del DOM con éxito");
    }
    catch (Exception e) {
        e.printStackTrace();
    }
}
    
    public int deleteNode(String tit) {
        System.out.println("Buscando el Libro "+tit+" para borrarlo");
        try{
        //Node root=doc.getFirstChild();
        Node raiz= doc.getDocumentElement();
        NodeList nl1=doc.getElementsByTagName("Titulo");
        Node n1;
        for(int i=0;i<nl1.getLength();i++){
        n1=nl1.item(i);

        if(n1.getNodeType()==Node.ELEMENT_NODE){//redundante por getElementsByTagName, no lo es si buscamos getChildNodes()

        if

        (n1.getChildNodes().item(0).getNodeValue().equals(tit)){

        System.out.println("Borrando el nodo <Libro> con título "+tit);

        //n1.getParentNode().removeChild(n1);

        //borra &lt;Titulo&gt; tit &lt;/Titulo&gt;, pero deja Libro y Autor
        n1.getParentNode().getParentNode().removeChild(n1.getParentNode());

        }
        }
        }
        System.out.println("Nodo borrado");
        //Guardar el arbol DOM en un nuevo archivo para mantener nuestro archivo original

        //guardarDOMcomoArchivo(&quot;LibrosBorrados.xml&quot;);
        return 0;
        }catch(Exception e){
        System.out.println(e);
        e.printStackTrace();
        return -1;
        }
}

    public static void recorreDOMyMuestra(File f) {
        Document doc = null;
        String[] datos = new String[3];//lo usamos para la información de cada libro
        Node nodo = null;
        Node root = doc.getFirstChild();
        NodeList nodelist = root.getChildNodes(); //(1)Ver dibujo del árbol
//recorrer el árbol DOM. El 1er nivel de nodos hijos del raíz
        for (int i = 0; i < nodelist.getLength(); i++) {
            nodo = nodelist.item(i);//node toma el valor de los hijos de raíz
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {//miramos nodos de tipo Element
                Node ntemp = null;
                int contador = 1;
//sacamos el valor del atributo publicado
                datos[0] = nodo.getAttributes().item(0).getNodeValue();
//sacamos los valores de los hijos de nodo, Titulo y Autor
                NodeList nl2 = nodo.getChildNodes();//obtenemos lista de hijos (2)
                for (int j = 0; j < nl2.getLength(); j++) {//iteramos en esa lista
                    ntemp = nl2.item(j);
                    if (ntemp.getNodeType() == Node.ELEMENT_NODE) {

                        if (ntemp.getNodeType() == Node.ELEMENT_NODE) {

                            datos[contador] = ntemp.getChildNodes().item(0).getNodeValue();

                            contador++;

                        }
                    }
//el array de String datos[] tiene los valores que necesitamos
                    System.out.println(datos[0] + "--" + datos[2] + "--" + datos[1]);
                }//
            }
        }
    }
}

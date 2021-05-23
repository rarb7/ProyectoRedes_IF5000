package Utility;

import java.io.IOException;
import java.io.StringReader;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class XMLConvert {
    public static String xmlToString(Element element) {
        XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat().getCompactFormat());
        String xmlStringElement = outputter.outputString(element);
        xmlStringElement = xmlStringElement.replace("\n", "");
        return xmlStringElement;
    }

    public static Element stringToXML(String stringElement) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        StringReader stringReader = new StringReader(stringElement);
        Document doc = saxBuilder.build(stringReader);
        return doc.getRootElement();
    } // stringToXML 
    
    public Element accion1(Element element, String accion) {
        Element eAccion = new Element("Accion");
        eAccion.addContent(accion);
        element.addContent(eAccion);
        return element;
    }//accion
}

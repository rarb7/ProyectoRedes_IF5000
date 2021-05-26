package Utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


import Domain.subImages;

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
    
    public static String imagetoString(BufferedImage image) throws IOException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        String encodedImage = Base64.getEncoder().encodeToString(baos.toByteArray());
        baos.close();
        return encodedImage;
    }//imagetoString
    
    public static BufferedImage xmltoBufferedImage (String imagenRuta) throws IOException {
    	byte[] bytes = Base64.getDecoder().decode(imagenRuta);
    	BufferedImage imagen = ImageIO.read(new ByteArrayInputStream(bytes));
    	return imagen;
    }//xmltoBufferedImage
    
    public static ArrayList<subImages> ImagenPartidaxmltoArray(Element element) throws JDOMException, IOException {
		ArrayList<subImages> fichas = new ArrayList<subImages>();
		List elementList = element.getContent();
		for (Object object : elementList) {
			Element elementoActual = (Element) object;
//			Element ImagenPartidaxml = stringToXML(elementoActual.getChild("subImagen").getValue());
			subImages subImage = new subImages(elementoActual.getAttributeValue("rutaCod"),
					Integer.parseInt(elementoActual.getChild("numOrden").getValue()));
					

			fichas.add(subImage);
		}
		return fichas;
	}
    
}

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
		XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
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
	
	public static String imagetoString(BufferedImage image) throws IOException {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
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
    
    public static Element generarSubImagenesXML(ArrayList<subImages> subImagenes) {
        Element eCasillas = new Element("subImagenes");
        for (int i = 0; i < subImagenes.size(); i++) {
            eCasillas.addContent(generarSubimagenXML(subImagenes.get(i)));
        }
        return eCasillas;
    }//generaSubImagenes
    
    public static Element generarSubimagenXML(subImages subImagen) {
        Element eSubImagen = new Element("subImagen");
        eSubImagen.setAttribute("rutaCod", subImagen.getImagen());

        Element enumOrden = new Element("numOrden");
        enumOrden.addContent(String.valueOf(subImagen.getImagenId()));


        eSubImagen.addContent(enumOrden);

        return eSubImagen;
    } // generarSubImagen 
    
    public static Element generarLogIn(String nombre, String password) {
		Element eInicio = new Element("Usuario");
		eInicio.setAttribute("nombre", nombre);

		Element ePassword = new Element("password");
		ePassword.addContent(password);

		eInicio.addContent(ePassword);

		return eInicio;
	}
    
    public static Element generarLogIn1(String nombre, String password) {
		Element eInicio = new Element("Usuario1");
		eInicio.setAttribute("nombre1", nombre);

		Element ePassword = new Element("password1");
		ePassword.addContent(password);

		eInicio.addContent(ePassword);

		return eInicio;
	}

    public static ArrayList<String> archivosxmltoArray(Element element) throws JDOMException, IOException {
		ArrayList<String> fichas = new ArrayList<String>();
		List elementList = element.getContent();
		for (Object object : elementList) {
			Element elementoActual = (Element) object;
			String nombreArch = elementoActual.getAttributeValue("nombre");			
			fichas.add(nombreArch);
		}
		return fichas;
	}

	public static Element verificando(String nombre) {
		
		Element eInicio = new Element("UsuarioVerificar");
		eInicio.setAttribute("nombreUser", nombre);


		return eInicio;
	}
}

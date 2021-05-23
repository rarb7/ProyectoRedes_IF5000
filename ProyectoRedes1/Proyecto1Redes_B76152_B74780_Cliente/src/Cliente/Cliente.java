package Cliente;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.imageio.ImageIO;

import org.jdom.Element;
import org.jdom.JDOMException;

import Utility.XMLConvert;

public class Cliente extends Thread {
	private static Cliente instance;
	private int socketPortNumber;
	private PrintStream send;
	private BufferedReader receive;
	private Socket socket;
	private InetAddress address;
	private Element entrada;
	private Element menuprin;
	private boolean host;
	private BufferedImage image1;
	
	private Cliente(int socketPortNumber) throws IOException {
		this.socketPortNumber = socketPortNumber;
		this.address = InetAddress.getLocalHost();
//		this.address = InetAddress.getByName("192.168.1.40");
		this.socket = new Socket(this.address, this.socketPortNumber);
		this.send = new PrintStream(this.socket.getOutputStream());
		this.receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.host = false;
	} // constructor

	public static Cliente getClient() throws IOException {
		if (instance == null) {
			instance = new Cliente(69);
			instance.start();
		}
		return instance;
	}

	public void run() {
		do {
			try {
				entrada = listen();
				switch (entrada.getChild("Accion").getValue()) {
				case "agregado":
					System.out.println("conectado al servidor");
					sendImage();
			default:
				break;
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (true);
	}// run
	
private void sendImage() {
//		// TODO Auto-generated method stub
	String imagePath = null;
	try {
		this.image1 = ImageIO.read(getClass().getResourceAsStream("/assets/crash.jpg"));
		 imagePath = XMLConvert.imagetoString(image1);
		 System.out.println(imagePath);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Element enviarImagen = new Element("Imagen");
	enviarImagen.setAttribute("ruta", imagePath);
    Element envio = acciones(enviarImagen, "image");
	this.send.println(XMLConvert.xmlToString(envio));
		
}

	public Element listen() throws IOException, JDOMException {
		return XMLConvert.stringToXML(this.receive.readLine());
	}// listen
	
	public Element acciones(Element element, String accion) {
		Element eAccion = new Element("Accion");
		eAccion.addContent(accion);
		element.addContent(eAccion);
		return element;
	}// acciones

}

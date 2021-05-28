package Cliente;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.jdom.Element;
import org.jdom.JDOMException;

import Domain.Usuario;
import Domain.subImages;
import Utility.ImagesConvert;
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
	private boolean verificado;
	private Usuario usuario;
	private String nombre;
	private String password;
	
	
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
//					sendImage();
					//EnviarImagenPartida("inosuke.jpg");
					
				case "verificado":
					String verificacion=entrada.getAttributeValue("boolean1");
					if (verificacion.equals("false")) {
						verificado=false;
					}else {
						verificado=true;
					}
					
					
					if(verificado) {
						JOptionPane.showMessageDialog(null, "Inicio de Seccion Correcto");
						
						usuario=new Usuario(this.nombre,this.password);

					}else {
						JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrecta");
						this.nombre="";
						this.password="";
					}
					System.out.println("Nombre "+this.nombre+" pass "+this.password);
					
					System.out.println("Usuario verificado desde cliente "+verificacion);
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
	
	public void EnviarImagenPartida(String rutaImagen) {
		Element envioImagen = new Element("EnvioImagen");
		ArrayList<subImages> subImagenes = ImagesConvert.partirImagenes(rutaImagen);
		
		Collections.shuffle(subImagenes);
		
		Element imagenesPartidas = XMLConvert.generarSubImagenesXML(subImagenes);
		envioImagen.addContent(imagenesPartidas);
		Element envio = acciones(envioImagen, "imagenPartida");
		
		this.send.println(XMLConvert.xmlToString(envio));
	}
	public boolean registarCliente(String nombre, String password) throws IOException {

		Element element = XMLConvert.generarLogIn(nombre, password);
		Element verificar = acciones(element, "registar");
		this.send.println(XMLConvert.xmlToString(verificar));
		return true;

	}
	public boolean logIn(String nombre, String password) throws IOException {
		Element element = XMLConvert.generarLogIn1(nombre, password);
		Element verificar = acciones(element, "login");
		this.send.println(XMLConvert.xmlToString(verificar));
		this.nombre=nombre;
		this.password=password;
		return true;
	}
	public boolean getVerificado() {
		return verificado;
	}
}

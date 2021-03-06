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
import javax.swing.ImageIcon;
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
	public Element entrada=new Element("hola");
	private Element menuprin;
	private boolean host;
	private BufferedImage image1;
	public boolean verificado;
	public Usuario usuario=new Usuario();
	public String nombre;
	public String password;
	private String imagenServidor;
	private ArrayList<String> archivos;
	public boolean verificadoBD;

	private Cliente(int socketPortNumber) throws IOException {
		this.socketPortNumber = socketPortNumber;
		this.address = InetAddress.getLocalHost();
//		this.address = InetAddress.getByName("192.168.1.40");
		this.socket = new Socket(this.address, this.socketPortNumber);
		this.send = new PrintStream(this.socket.getOutputStream());
		this.receive = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		this.host = false;
		this.archivos = null;
	} // constructor

	public static Cliente getClient() throws IOException {
		if (instance == null) {
			instance = new Cliente(5025);
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

					break;

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

	public void EnviarImagenPartida(String rutaImagen, String nombreImagen) {
		Element envioImagen = new Element("EnvioImagen");
		ArrayList<subImages> subImagenes = ImagesConvert.partirImagenes(rutaImagen);

		Collections.shuffle(subImagenes);

		Element imagenesPartidas = XMLConvert.generarSubImagenesXML(subImagenes);
		envioImagen.addContent(imagenesPartidas);
		Element envioNombreImg = new Element("nombreImg");
		envioNombreImg.addContent(nombreImagen);

		envioImagen.addContent(envioNombreImg);
		Element envio = acciones(envioImagen, "imagenPartida");

		this.send.println(XMLConvert.xmlToString(envio));
	}

	public boolean verificiandoNuevoCliente(String nombre) throws IOException {

		Element element = XMLConvert.verificando(nombre);
		Element verificar = acciones(element, "duplicado");
		this.send.println(XMLConvert.xmlToString(verificar));
		return true;

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
		this.nombre = nombre;
		this.password = password;
		return true;
	}

	public boolean getVerificado() {
		return verificado;
	}

	public void pedirImagen(String selectCb) {
		Element pedirImagen = new Element("nameImage");
		pedirImagen.setAttribute("nombreImagen", selectCb);

		Element envio = acciones(pedirImagen, "pedirArchivoUsuario");
		this.send.println(XMLConvert.xmlToString(envio));
	}//

	public ArrayList<String> getArchivos() {
		return archivos;
	}

	public void setArchivos(ArrayList<String> archivos) {
		this.archivos = archivos;
	}

	public Element getEntrada() {
		return entrada;
	}

	public void setEntrada(Element entrada) {
		this.entrada = entrada;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setVerificado(boolean verificado) {
		this.verificado = verificado;
	}

	public boolean isVerificadoBD() {
		return verificadoBD;
	}

	public void setVerificadoBD(boolean verificadoBD) {
		this.verificadoBD = verificadoBD;
	}
	

}

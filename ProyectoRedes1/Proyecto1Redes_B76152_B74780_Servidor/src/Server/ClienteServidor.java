package Server;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.jdom.Element;
import org.jdom.JDOMException;

import Data.DataUsuario;
import Domain.AdministracionCliente;
import Domain.Usuario;
import Domain.comparadorImagen;
import Domain.imagenDecodificada;
import Domain.subImages;
import Utility.ImagesConvert;
import Utility.XMLConvert;

public class ClienteServidor extends Thread {
	public PrintStream send;
	private BufferedReader receive;
	private int socketPortNumber;
	private Socket socketR;
	private AdministracionCliente adminClientes;
	private boolean activo;
	private Element escucha;
	private Element element;
	DataUsuario usuarioBD;

	public ClienteServidor(Socket socket) {
		this.socketR = socket;
		this.activo = false;
		this.adminClientes = AdministracionCliente.getInstance();
		usuarioBD=new DataUsuario();
		try {
			this.send = new PrintStream(this.socketR.getOutputStream());
			this.receive = new BufferedReader(new InputStreamReader(this.socketR.getInputStream()));
		} catch (IOException ex) {
			Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// cliente_Servidor?constructor

	public void run() {
		Element inicioSesion = new Element("Existe");
		inicioSesion.setAttribute("boolean", "true");
		Element verificar = accion(inicioSesion, "agregado");
		this.send.println(XMLConvert.xmlToString(verificar));
		do {
			try {
				escucha = listen();
				switch (escucha.getChild("Accion").getValue()) {
				case "conectado":
					System.out.println("se conecto");
					break;
				case "image":
					System.out.println("envio imagen");
					readImage(escucha.getAttributeValue("ruta"));
					System.out.println("se guardo imagen");
					break;
				case "imagenPartida":
					System.out.println("Entro la imagen Partida");
					try {
						ArrayList<subImages> subImages = XMLConvert
								.ImagenPartidaxmltoArray(escucha.getChild("subImagenes"));
						ArrayList<imagenDecodificada> imagenesDeco = imgDecodificadas(subImages);
						unirImagenes(imagenesDeco,"DesordenInosuke");
						System.out.println("imagenes en desorden");
						for (int i = 0; i < imagenesDeco.size(); i++) {
							System.out.println(imagenesDeco.get(i).getImagenId());
						}
						ArrayList<imagenDecodificada> imgsOrdenadas=ordenarxNumero(imagenesDeco);
						System.out.println("imagenes en orden");
						for (int i = 0; i < imgsOrdenadas.size(); i++) {
							System.out.println(imgsOrdenadas.get(i).getImagenId());
						}
						
						unirImagenes(imgsOrdenadas,"EnsambladoimgInosuke");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				case "registar":
					
					
					String nombre=escucha.getAttributeValue("nombre");
					String pass=escucha.getChild("password").getValue();
					System.out.println(nombre+" "+pass);
					
					usuarioBD.insertarUsuario(new Usuario(nombre,pass));
					
				case "login":
					String nombreUser=escucha.getAttributeValue("nombre");
					String passUser=escucha.getChild("password").getValue();
					Boolean band=usuarioBD.verificarUsuario(nombreUser, passUser);
					
					Element verificado = new Element("ExisteUser");
					if (band) {
						verificado.setAttribute("boolean1", "true");
					}else {
						verificado.setAttribute("boolean1", "false");
					}
					
					Element verificarUser = accion(verificado, "verificado");
					this.send.println(XMLConvert.xmlToString(verificarUser));
					
				default:
					break;
				}
			} catch (JDOMException ex) {
				Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} while (true);

	}

	private void readImage(String element) {
		BufferedImage image1;
		try {

//		image1 = XMLConvert.xmltoBufferedImage(element);
			byte[] imageByteArray = Base64.getDecoder().decode(element);
			FileOutputStream imageOutFile = new FileOutputStream("src/imagenesEnviadas/saved.jpg");
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
//		File outputfile = new File("/assets/saved.png");
//		ImageIO.write(image1, "jpg",outputfile );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// metodos
	public Element listen() throws JDOMException {
		Element escuchar = null;
		try {
			escuchar = XMLConvert.stringToXML(this.receive.readLine());
		} catch (IOException ex) {
			Logger.getLogger(XMLConvert.class.getName()).log(Level.SEVERE, null, ex);
		}
		return escuchar;
	}// listen

	public Element accion(Element element, String accion) {
		Element eAccion = new Element("Accion");
		eAccion.addContent(accion);
		element.addContent(eAccion);
		return element;
	}// accion

	public ArrayList<imagenDecodificada> imgDecodificadas(ArrayList<subImages> imgPartes) {
		ArrayList<imagenDecodificada> imgDecod = new ArrayList<imagenDecodificada>();
		BufferedImage img = null;
		for (int i = 0; i < imgPartes.size(); i++) {
			img = ImagesConvert.readImage(imgPartes.get(i).getImagen(), i);
			imgDecod.add(new imagenDecodificada(img, imgPartes.get(i).getImagenId()));
		} // recorre todo el array para decodificar la imagen
		return imgDecod;
	}// imgDecodificadas

	private void unirImagenes(ArrayList<imagenDecodificada> imagenesDeco, String ruta) {
		ArrayList<BufferedImage> imagenes = new ArrayList<BufferedImage>();
		int iter=0;
		int goal=4;
		for (int i = 0; i < imagenesDeco.size(); i++) {
			while(iter+3<16) {
				BufferedImage img1 = ImagesConvert.joinImages(imagenesDeco.get(iter).getImagen(), imagenesDeco.get(iter+1).getImagen());
				BufferedImage img2 = ImagesConvert.joinImages(imagenesDeco.get(iter+2).getImagen(), imagenesDeco.get(iter+3).getImagen());
				BufferedImage img = ImagesConvert.joinImages(img1, img2);
				imagenes.add(img);
				try {
					ImageIO.write(img, "jpg", new File("src/imagenesRecibidas/joinImageDos"+goal+".jpg"));
					goal+=1;
					//img = ImageIO.read(new File("src/imagenesRecibidas/savedRect"+1+".jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				iter+=4;
			}
			
			
		}
		ImagesConvert.mergeFiles(imagenes,ruta);

	}// unirImagenes

	
	public  ArrayList<imagenDecodificada> ordenarxNumero(ArrayList<imagenDecodificada> imgDeco){
		for (int i = 0; i < imgDeco.size(); i++) {
			for (int j = 0; j < imgDeco.size(); j++) {
				if (imgDeco.get(i).getImagenId() < imgDeco.get(j).getImagenId()) {
					
					BufferedImage temp1 = imgDeco.get(i).getImagen();
					int temp = imgDeco.get(i).getImagenId();
					
					imgDeco.get(i).setImagenId(imgDeco.get(j).getImagenId());
					imgDeco.get(i).setImagen(imgDeco.get(j).getImagen());
					
					imgDeco.get(j).setImagenId(temp);
					imgDeco.get(j).setImagen(temp1);
				}
			}
		}
		

		return imgDeco;
		
	}
}


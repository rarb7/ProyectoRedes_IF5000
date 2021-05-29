package Server;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import org.jdom.Element;
import org.jdom.JDOMException;

import Data.DataUsuario;
import Domain.AdministracionCliente;
import Domain.Usuario;
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
	private String nombre;
	private String password;

	public ClienteServidor(Socket socket) {
		this.socketR = socket;
		this.activo = false;
		this.adminClientes = AdministracionCliente.getInstance();
		usuarioBD = new DataUsuario();
		try {
			this.send = new PrintStream(this.socketR.getOutputStream());
			this.receive = new BufferedReader(new InputStreamReader(this.socketR.getInputStream()));
		} catch (IOException ex) {
			Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
		}
	}// cliente_Servidor?constructor

	public void run() {

		do {
			try {
				escucha = listen();
				System.out.println("Servidor ---->" + escucha.getChild("Accion").getValue());
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
					String nombreImg = escucha.getChild("nombreImg").getValue();
					try {
						ArrayList<subImages> subImages = XMLConvert
								.ImagenPartidaxmltoArray(escucha.getChild("subImagenes"));
						ArrayList<imagenDecodificada> imagenesDeco = imgDecodificadas(subImages);
						System.out.println("/////////////" + subImages.get(0).getName());
						unirImagenes(imagenesDeco, "/" + nombre + "/Desordenada" + nombreImg);
						System.out.println("imagenes en desorden");
						for (int i = 0; i < imagenesDeco.size(); i++) {
							System.out.println(imagenesDeco.get(i).getImagenId());
						}
						ArrayList<imagenDecodificada> imgsOrdenadas = ordenarxNumero(imagenesDeco);
						System.out.println("imagenes en orden");
						for (int i = 0; i < imgsOrdenadas.size(); i++) {
							System.out.println(imgsOrdenadas.get(i).getImagenId());
						}

						unirImagenes(imgsOrdenadas, "/" + nombre + "/" + nombreImg);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case "registar":

					String nombre = escucha.getAttributeValue("nombre");
					System.out.print("resgitar " + nombre);
					String pass = escucha.getChild("password").getValue();
					System.out.println(nombre + " " + pass);

					boolean band = usuarioBD.insertarUsuario(new Usuario(nombre, pass));
					if (band) {
						File directorioUser = new File("src/imagenesEnviadas/" + nombre);
						File directorioUser1 = new File("src/imagenesRecibidas/" + nombre);
						if (!directorioUser.exists()) {
							if (directorioUser.mkdirs()) {
								System.out.println("Directorio creado");
							} else {
								System.out.println("Error al crear directorio");
							}
						}
						if (!directorioUser1.exists()) {
							if (directorioUser1.mkdirs()) {
								System.out.println("Directorio creado");
							} else {
								System.out.println("Error al crear directorio");
							}
						}

					}
					break;
				case "login":
					String nombreUser = escucha.getAttributeValue("nombre1");
					String passUser = escucha.getChild("password1").getValue();
					Boolean bandLogin = usuarioBD.verificarUsuario(nombreUser, passUser);

					Element verificado = new Element("ExisteUser");
					if (bandLogin) {
						verificado.setAttribute("boolean1", "true");
						this.nombre = nombreUser;
						this.password = passUser;
						ArrayList<String> archivos = imagenesCarpetaUsuario(nombreUser);
						Element archivosUsuario = XMLConvert.generarArchivoXml(archivos);
						verificado.addContent(archivosUsuario);

					} else {
						verificado.setAttribute("boolean1", "false");
					}

					Element verificarUser = accion(verificado, "verificado");
					this.send.println(XMLConvert.xmlToString(verificarUser));
					break;
				case "pedirArchivoUsuario":
					String nameImage = escucha.getChild("nameImage").getValue();
					break;
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
			FileOutputStream imageOutFile = new FileOutputStream("src/imagenesEnviadas/" + nombre + "/saved.jpg");
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
		int iter = 0;
		int goal = 4;
		for (int i = 0; i < imagenesDeco.size(); i++) {
			while (iter + 3 < 16) {
				BufferedImage img1 = ImagesConvert.joinImages(imagenesDeco.get(iter).getImagen(),
						imagenesDeco.get(iter + 1).getImagen());
				BufferedImage img2 = ImagesConvert.joinImages(imagenesDeco.get(iter + 2).getImagen(),
						imagenesDeco.get(iter + 3).getImagen());
				BufferedImage img = ImagesConvert.joinImages(img1, img2);
				imagenes.add(img);
				try {
					ImageIO.write(img, "jpg",
							new File("src/imagenesRecibidas/" + nombre + "/joinImageDos" + goal + ".jpg"));
					goal += 1;
					// img = ImageIO.read(new File("src/imagenesRecibidas/savedRect"+1+".jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				iter += 4;
			}

		}
		ImagesConvert.mergeFiles(imagenes, ruta);

	}// unirImagenes

	public ArrayList<imagenDecodificada> ordenarxNumero(ArrayList<imagenDecodificada> imgDeco) {
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

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public ArrayList<String> imagenesCarpetaUsuario(String nombreUsuario) {
		ArrayList<String> nombresArchivos = new ArrayList<String>();
		File carpeta = new File("D:/UCR/UCR 2021/l Semestre/Redes/Proyecto/ProyectoRedes_IF5000/ProyectoRedes1/Proyecto1Redes_B76152_B74780_Servidor/src/imagenesEnviadas/" + nombreUsuario);
		String[] listado = carpeta.list();
		if (listado == null || listado.length == 0) {
			System.out.println("No hay elementos dentro de la carpeta actual");
			return null;
		} else {
			for (int i = 0; i < listado.length; i++) {
				System.out.println(listado[i]);
				nombresArchivos.add(listado[i]);

			}
		}
		return nombresArchivos;
	}// obtiene los archivos asociados al usuario

	private void sendImage(String nombre, String usuario) {
//		// TODO Auto-generated method stub
		String imagePath = null;
		try {
			BufferedImage image1 = ImageIO
					.read(getClass().getResourceAsStream("src/imagenesEnviadas/" + usuario + "/" + nombre));
			imagePath = XMLConvert.imagetoString(image1);
			System.out.println(imagePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element enviarImagen = new Element("Imagen");
		enviarImagen.setAttribute("ruta", imagePath);
		Element envio = accion(enviarImagen, "image");
		this.send.println(XMLConvert.xmlToString(envio));

	}
}

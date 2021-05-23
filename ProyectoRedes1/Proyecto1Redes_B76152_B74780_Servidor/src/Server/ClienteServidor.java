package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Element;
import org.jdom.JDOMException;

import Domain.AdministracionCliente;
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

	public ClienteServidor(Socket socket) {
		this.socketR = socket;
		this.activo = false;
		this.adminClientes = AdministracionCliente.getInstance();
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
				default:
					break;
				}
			} catch (JDOMException ex) {
				Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
			}
		} while (true);

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
}
package Server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Element;

import Domain.AdministracionCliente;
import Utility.XMLConvert;

public class Server extends Thread{
	private ArrayList<ClienteServidor> clientes;
    private AdministracionCliente adminClientes;
    private Element enviar;
    public PrintStream send;
    private int socketPortNumber;
    
    public Server(int socketPortNumber) {
        super("Hilo Servidor");
        this.socketPortNumber = socketPortNumber;
        this.adminClientes= AdministracionCliente.getInstance();
    } // constructor
    
    
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.socketPortNumber);

            do {
                System.out.println("Servidor ejecutando");
                Socket socket = serverSocket.accept(); // el servidor se queda esperando conexion
                System.out.println("Cliente acceptado");
                
                ClienteServidor client = new ClienteServidor(socket);
                client.start();
                this.adminClientes.getClientes().add(client);

//                socket.close();
            } while (true);

        } catch (IOException ex) {
            Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
        }

    } // run
}

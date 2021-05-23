package Domain;

import java.util.ArrayList;

import Server.ClienteServidor;




public class AdministracionCliente {
	private ArrayList<ClienteServidor> clientes;
	private ClienteServidor clienteHost = null;
    private static AdministracionCliente sc = null;
    
    private AdministracionCliente() {
        this.clientes = new ArrayList<>();

    }//constructor
    
    public static AdministracionCliente getInstance() {
        if (sc == null) {
            sc = new AdministracionCliente();
        }
        return sc;
    }
    
  //setter&getter
    public ArrayList<ClienteServidor> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<ClienteServidor> clientes) {
        this.clientes = clientes;
    }
}

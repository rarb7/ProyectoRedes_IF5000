package Proyecto1Redes_B76152_B74780;

import Data.DataUsuario;
import Domain.Usuario;
import Server.Server;

public class Proyecto1Redes {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataUsuario dataUser=new DataUsuario();
		try {
			dataUser.insertarUsuario(new Usuario("Prueba1","Prueba1.2"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Server server;
		server = new Server(69);
		server.start();
		
	}

}

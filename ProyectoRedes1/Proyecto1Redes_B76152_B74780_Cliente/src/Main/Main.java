package Main;

import java.io.IOException;

import Cliente.Cliente;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cliente cliente;
		try {
			cliente = Cliente.getClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Hola");
	}

}

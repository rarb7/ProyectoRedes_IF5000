package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Cliente.Cliente;

public class VentanaLogin extends JInternalFrame implements ActionListener {
	 JLabel labelTitulo;
	    JLabel labelUsuario;
	    JLabel labelPassword;
	    JTextField textUsuario;
	    JTextField textPassword;
	    JButton buttonInicio;
	    Cliente cliente;	
	    public VentanaLogin() {
	        super();
	        this.setLayout(null);
	        this.setSize(600, 400);
	        init();
	    }

	    public void init() {
	        this.labelTitulo = new JLabel("Inicio Seccion");
	        this.labelUsuario = new JLabel("Usuario: ");
	        this.labelPassword = new JLabel("Contraseña: ");
	        this.textPassword = new JPasswordField();
	        this.textUsuario = new JTextField();
	        this.buttonInicio = new JButton("Inicio Seccion");
	        this.labelTitulo.setBounds(100, 10, 100, 30);
	        this.labelUsuario.setBounds(10, 50, 100, 30);
	        this.labelPassword.setBounds(10, 100, 100, 30);
	        this.textUsuario.setBounds(150, 50, 100, 30);
	        this.textPassword.setBounds(150, 100, 100, 30);
	        this.buttonInicio.setBounds(135, 150, 120, 30);
	        this.buttonInicio.addActionListener(this);
	        this.add(labelPassword);
	        this.add(labelTitulo);
	        this.add(labelUsuario);
	        this.add(textPassword);
	        this.add(textUsuario);
	        this.add(buttonInicio);
	        try {
				cliente = Cliente.getClient();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    }

	    @Override
	    public void actionPerformed(ActionEvent e) {
	    	if (e.getSource() == this.buttonInicio) {
	    		
	    		try {
	    			
					cliente.registarCliente(this.textUsuario.getText(), this.textPassword.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    	}
	    }

}

package GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Cliente.Cliente;
public class VentanaRegistar extends JInternalFrame implements ActionListener {
	JLabel labelTitulo;
    JLabel labelUsuario;
    JLabel labelPassword;
    JTextField textUsuario;
    JPasswordField textPassword;
    JButton buttonInicio;
    
    JPanel panel;
    Cliente cliente;	

    public VentanaRegistar() {
        
        this.setLayout(null);
        this.setSize(300, 300);
        init();
        try {
			cliente = Cliente.getClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void init() {
        this.labelTitulo = new JLabel("Registrar Usuario");
        this.labelUsuario = new JLabel("Usuario: ");
        this.labelPassword = new JLabel("Contraseña: ");
        this.panel = new JPanel(null);
        this.panel.setBounds(0, 40, 100, 100);

        this.textPassword = new JPasswordField();
        this.textUsuario = new JTextField();

        this.buttonInicio = new JButton("Registar");

        this.labelTitulo.setBounds(100, 10, 100, 30);
        this.labelUsuario.setBounds(10, 50, 100, 30);
        this.labelPassword.setBounds(10, 100, 100, 30);

        this.textUsuario.setBounds(150, 50, 100, 30);
        this.textPassword.setBounds(150, 100, 100, 30);

        this.buttonInicio.setBounds(50, 200, 120, 30);

        this.buttonInicio.addActionListener(this);

        this.add(labelPassword);
        this.add(labelTitulo);
        this.add(labelUsuario);
        this.add(textPassword);
        this.add(textUsuario);
        this.add(buttonInicio);
        
        this.add(panel);
        this.panel.setVisible(true);

    }

    
  

    @Override
    public void actionPerformed(ActionEvent e) {
    	if (e.getSource() == this.buttonInicio) {
    		
    		try {
    			char[] password = this.textPassword.getPassword();
                String pass = new String(password);
				cliente.registarCliente(this.textUsuario.getText(), pass);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	}

    }
}

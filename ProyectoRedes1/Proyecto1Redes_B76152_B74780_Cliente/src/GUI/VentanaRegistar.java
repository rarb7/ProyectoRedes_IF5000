package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdom.Element;

import Cliente.Cliente;

public class VentanaRegistar extends JInternalFrame implements ActionListener, Runnable {
	JLabel labelTitulo;
	JLabel labelUsuario;
	JLabel labelPassword;
	JTextField textUsuario;
	JPasswordField textPassword;
	JButton buttonInicio;
	JButton buttonSalir;

	JPanel panel;
	Cliente cliente;
	private Thread hilo;
	private boolean verificarBD;
	private Element entrada;

	public VentanaRegistar() {

		this.setLayout(null);
		this.setSize(300, 300);
		this.entrada = null;
		init();

		try {
			cliente = Cliente.getClient();
			this.start();
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
		this.buttonSalir = new JButton("Salir");

		this.labelTitulo.setBounds(100, 10, 100, 30);
		this.labelUsuario.setBounds(10, 50, 100, 30);
		this.labelPassword.setBounds(10, 100, 100, 30);

		this.textUsuario.setBounds(150, 50, 100, 30);
		this.textPassword.setBounds(150, 100, 100, 30);

		this.buttonInicio.setBounds(150, 160, 120, 30);
		this.buttonInicio.addActionListener(this);

		this.buttonSalir.setBounds(10, 220, 80, 30);
		this.buttonSalir.addActionListener(this);

		this.add(labelPassword);
		this.add(labelTitulo);
		this.add(labelUsuario);
		this.add(textPassword);
		this.add(textUsuario);
		this.add(buttonInicio);
		this.add(buttonSalir);

		this.add(panel);
		this.panel.setVisible(true);

	}

	public void start() {
		if (hilo == null) {
			hilo = new Thread(this);
			hilo.start();
		}
	}// start

	public void stop() {
		if (hilo != null) {

			hilo.stop();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.buttonInicio) {

			try {
				cliente.verificiandoNuevoCliente(this.textUsuario.getText());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource() == this.buttonSalir) {
			this.dispose();
		}

	}

	@Override
	public void run() {
		do {
			entrada = this.cliente.getEntrada();

			switch (entrada.getValue()) {
			case "verificadoBD":
				String verificadoBD = entrada.getAttributeValue("booleanBD");
				System.out.println("Llega a cliente booleano " + verificadoBD);

				if (verificadoBD.equals("true")) {
					JOptionPane.showMessageDialog(null, "Usuario ya existe");
					cliente.setVerificadoBD(true);
					this.textUsuario.setText("");
					this.textPassword.setText("");
					this.cliente.setVerificadoBD(true);

				} else {
					char[] password = this.textPassword.getPassword();
					String pass = new String(password);
					JOptionPane.showMessageDialog(null, "Registro de exitoso");
					try {
						cliente.registarCliente(this.textUsuario.getText(), pass);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.textUsuario.setText("");
					this.textPassword.setText("");
					this.cliente.setVerificadoBD(false);

				}

				entrada.getChild("Accion").setText(" ");
				break;
			default:
				break;
			}

		} while (true);
	}
}

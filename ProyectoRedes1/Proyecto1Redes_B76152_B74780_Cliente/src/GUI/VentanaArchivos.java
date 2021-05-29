package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import Cliente.Cliente;
import Domain.Usuario;
import Utility.ImagesConvert;

public class VentanaArchivos extends JInternalFrame implements ActionListener {
	private JLabel jlblNombre;
	private JTextField jtfNombre;
	private JLabel jlblPassword;
	private JPasswordField jtfPassword;
	private JTextField jtfRuta;
	// boton
	private JButton entrar;
	private JButton enviar;
	private JButton FileChooser;
	// JFileChooser
	private JFileChooser jfcSelector;
	private String ruta;
	private Cliente cliente;
	private Usuario usuario;
	private String nombreImagen;

	private JLabel archivosServer;
	private JComboBox<String> nombreArchivos;
	private JButton comboBoxbutton;
	private JLabel imagen;
	// constructor
	public VentanaArchivos() {
		super("Envio de Archivos");
		usuario = new Usuario();
		this.setSize(600, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);

		this.init();

		this.setVisible(true);
		try {
			cliente = Cliente.getClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// init
	public void init() {
		this.jlblNombre = new JLabel("Nombre");
		this.jlblNombre.setBounds(20, 20, 60, 20);
		this.add(this.jlblNombre);
		this.jtfNombre = new JTextField(60);
		this.jtfNombre.setBounds(100, 20, 80, 20);
		this.add(this.jtfNombre);

		this.jlblPassword = new JLabel("Password");
		this.jlblPassword.setBounds(200, 20, 80, 20);
		this.add(this.jlblPassword);
		this.jtfPassword = new JPasswordField(60);
		this.jtfPassword.setBounds(300, 20, 80, 20);
		this.add(this.jtfPassword);

		this.entrar = new JButton("Log In");
		this.entrar.setBounds(400, 20, 100, 30);
		this.entrar.addActionListener(this);
		this.add(this.entrar);

		// botones del fileChooser y la imagen seleccionada
		this.FileChooser = new JButton("SelectFile");
		this.FileChooser.setBounds(20, 60, 100, 30);
		this.FileChooser.addActionListener(this);
		this.add(FileChooser);

		this.jtfRuta = new JTextField(60);
		this.jtfRuta.setBounds(200, 60, 100, 30);
		this.jtfRuta.setEnabled(false);
		this.add(this.jtfRuta);

		this.imagen = new JLabel("foto");
		this.imagen.setBounds(50, 100, 200, 200);
		this.imagen.setOpaque(true);
		this.imagen.setBackground(Color.white);
		this.add(this.imagen);
		
		this.enviar = new JButton("Enviar");
		this.enviar.setBounds(300, 60, 100, 30);
		this.enviar.addActionListener(this);
		this.add(this.enviar);

		this.archivosServer = new JLabel("Seleccione los Archivos del Cliente en el Servidor");
		this.archivosServer.setBounds(300, 100, 300, 20);
		this.add(this.archivosServer);
		
		this.comboBoxbutton = new JButton("ver");
		this.comboBoxbutton.setBounds(400, 160, 60, 30);
		this.comboBoxbutton.addActionListener(this);
		this.add(this.comboBoxbutton);
		
		this.nombreArchivos = new JComboBox<String>();
		this.nombreArchivos.setBounds(350, 130, 80, 20);
		this.add(this.nombreArchivos);
		
	}

	public String initJFileChooser() { // se iniciliza JFileChooser.
		this.jfcSelector = new JFileChooser();
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("JPG", "jpg");
		this.jfcSelector.setFileFilter(filtro);
		this.jfcSelector.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		int resultado = this.jfcSelector.showOpenDialog(this);
		if (resultado == JFileChooser.CANCEL_OPTION) {
			this.jfcSelector.cancelSelection();
		}
		File archivo = this.jfcSelector.getSelectedFile();
		if (archivo == null || archivo.getName().equals("")) {
			this.jfcSelector.cancelSelection();
			JOptionPane.showMessageDialog(null, "Error. Archivo inválido.");
		} else {
			this.ruta = archivo.getAbsolutePath();
			this.nombreImagen=archivo.getName();
		}
		

		return archivo.getAbsolutePath();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == this.FileChooser) {
			if(this.cliente.getVerificado()) {
				this.ruta = initJFileChooser();
				this.jtfRuta.setText(this.ruta);
				ImageIcon imgIcon = ImagesConvert.imageIcon(this.ruta);
				this.imagen.setIcon(imgIcon);
				System.out.println(this.ruta);
			}else {
				JOptionPane.showMessageDialog(rootPane, "Por Favor Inicie Sesion");
			}
			
		} // select_file
		if (arg0.getSource() == this.entrar) {

			try {
				char[] password = this.jtfPassword.getPassword();
				String pass = new String(password);
				cliente.logIn(this.jtfNombre.getText(), pass);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // fin entrar

		if (arg0.getSource() == this.enviar) {// boton enviar imagene
			if (!this.jtfRuta.getText().isEmpty()) {
				this.ruta = this.jtfRuta.getText();
				
				this.cliente.EnviarImagenPartida(this.ruta,this.nombreImagen);
			} else {
				JOptionPane.showMessageDialog(rootPane, "Por Favor seleccione un archivo en el FileChooser");
			} // se fiija si la ruta tiene informacion

		}//fin enviar
		
		if (arg0.getSource() == this.comboBoxbutton) {
			String selectCb = this.nombreArchivos.getSelectedItem().toString();
			this.cliente.pedirImagen(selectCb);
			

		}//ver
	}

}// VentanaArchivos

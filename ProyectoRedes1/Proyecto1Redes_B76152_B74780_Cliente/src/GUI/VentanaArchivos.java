package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jdom.Element;
import org.jdom.JDOMException;

import Cliente.Cliente;
import Domain.Usuario;
import Utility.ImagesConvert;
import Utility.XMLConvert;

public class VentanaArchivos extends JInternalFrame implements ActionListener, Runnable {
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
	private Thread hilo;
	private JButton btnCerrar;
	private Element entrada;

	// constructor
	public VentanaArchivos() {
		super("Envio de Archivos");
		usuario = new Usuario();
		this.entrada=null;
		this.setSize(635, 482);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(null);

		this.init();

		
		try {
			cliente = Cliente.getClient();
			this.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setVisible(true);
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

	// init
	public void init() {
		this.jlblNombre = new JLabel("Nombre:");
		this.jlblNombre.setBounds(20, 20, 60, 20);
		this.add(this.jlblNombre);
		this.jtfNombre = new JTextField(60);
		this.jtfNombre.setBounds(100, 20, 80, 20);
		this.add(this.jtfNombre);

		this.jlblPassword = new JLabel("Password:");
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
		this.FileChooser.setBounds(20, 70, 100, 25);
		this.FileChooser.addActionListener(this);
		this.add(FileChooser);

		this.jtfRuta = new JTextField(60);
		this.jtfRuta.setBounds(165, 70, 130, 25);
		this.jtfRuta.setEnabled(false);
		this.add(this.jtfRuta);

		this.imagen = new JLabel("foto");
		this.imagen.setBounds(50, 150, 200, 200);
		this.imagen.setOpaque(true);
		this.imagen.setBackground(Color.white);
		this.add(this.imagen);

		this.enviar = new JButton("Enviar");
		this.enviar.setBounds(300, 70, 80, 25);
		this.enviar.addActionListener(this);
		this.add(this.enviar);

		this.archivosServer = new JLabel("Seleccione los Archivos del Cliente en el Servidor");
		this.archivosServer.setBounds(300, 150, 300, 20);
		this.add(this.archivosServer);

		this.comboBoxbutton = new JButton("ver");
		this.comboBoxbutton.setBounds(490, 175, 70, 25);
		this.comboBoxbutton.addActionListener(this);
		this.add(this.comboBoxbutton);

		this.btnCerrar = new JButton("Salir");
		this.btnCerrar.setBounds(10, 400, 80, 30);
		this.btnCerrar.addActionListener(this);
		this.add(this.btnCerrar);

		this.nombreArchivos = new JComboBox<String>();
		this.nombreArchivos.setBounds(300, 175, 160, 20);
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
			JOptionPane.showMessageDialog(null, "Error. Archivo inv?lido.");
		} else {
			this.ruta = archivo.getAbsolutePath();
			this.nombreImagen = archivo.getName();
		}

		return archivo.getAbsolutePath();
	}

	public void llenarCombo(ArrayList<String> archivos) {
        this.nombreArchivos.removeAllItems();
        this.nombreArchivos.addItem("Cargados");

        if (archivos == null || archivos.isEmpty()) {

        } else {

            for (String archivo : archivos) {
            	if (!archivo.equalsIgnoreCase(".gitignore")) {
            		 this.nombreArchivos.addItem(archivo);
				}
               
            } // fin foreach
        } // fin else-if
    }// fin llenarCombo

	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == this.FileChooser) {
			if (this.cliente.getVerificado()) {
				this.ruta = initJFileChooser();
				this.jtfRuta.setText(this.ruta);
				ImageIcon imgIcon = ImagesConvert.imageIcon(this.ruta);
				this.imagen.setIcon(imgIcon);
				System.out.println(this.ruta);
			} else {
				JOptionPane.showMessageDialog(rootPane, "Por Favor Inicie Sesion");
			}

		} // select_file
		if (arg0.getSource() == this.entrar) {

			char[] password = this.jtfPassword.getPassword();
			String pass = new String(password);
			try {
				cliente.logIn(this.jtfNombre.getText(), pass);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} // fin entrar

		if (arg0.getSource() == this.enviar) {// boton enviar imagene
			if (!this.jtfRuta.getText().isEmpty()) {
				this.ruta = this.jtfRuta.getText();

				this.cliente.EnviarImagenPartida(this.ruta, this.nombreImagen);
			} else {
				JOptionPane.showMessageDialog(rootPane, "Por Favor seleccione un archivo en el FileChooser");
			} // se fiija si la ruta tiene informacion

		} // fin enviar

		if (arg0.getSource() == this.comboBoxbutton) {
			String selectCb = this.nombreArchivos.getSelectedItem().toString();
			if (!selectCb.equalsIgnoreCase("cargados")) {
				this.cliente.pedirImagen(selectCb);
			}
			

		} // ver

		if (arg0.getSource() == this.btnCerrar) {
			this.dispose();
			
			

		}
	}

	private ArrayList<String> archivos = new ArrayList<String>();

	@Override
	public void run() {
		do {
			try {
				System.out.println("ventana archivos");
				entrada = this.cliente.getEntrada();
				
				switch (entrada.getValue()) {
				case "image":
					
					ImagesConvert.readImage(entrada.getAttributeValue("ruta"));
					ImageIcon imgIcon = ImagesConvert.imageIcon(
							"D:/UCR/UCR 2021/l Semestre/Redes/Proyecto/ProyectoRedes_IF5000/ProyectoRedes1/Proyecto1Redes_B76152_B74780_Cliente/src/imagenesServidor/imagenDesdeServidor.jpg");
					JOptionPane.showMessageDialog(null, null, "Image desde el Servidor",
							JOptionPane.INFORMATION_MESSAGE, imgIcon);
					entrada.getChild("Accion").setText(" ");
					break;
				case "verificado":

					char[] password = this.jtfPassword.getPassword();
					String pass = new String(password);

					String verificacion = entrada.getAttributeValue("boolean1");
					if (verificacion.equals("false")) {
						JOptionPane.showMessageDialog(null, "Usuario o contrase?a incorrecta");
						this.cliente.getUsuario().setNombreUsuario(" ");
						this.cliente.getUsuario().setPassword("");
						this.cliente.setVerificado(false);
						
					} else {
						this.cliente.setUsuario(new Usuario(this.jtfNombre.getText(), pass));

						archivos = XMLConvert.archivosxmltoArray(entrada.getChild("archivos"));
						
						JOptionPane.showMessageDialog(null, "Inicio de Seccion Correcto");
						this.cliente.setVerificado(true);
						llenarCombo(archivos);

					}
					entrada.getChild("Accion").setText(" ");
					break;
				case "cargarCombo":
					archivos = XMLConvert.archivosxmltoArray(entrada.getChild("archivos"));
					llenarCombo(archivos);
					entrada.getChild("Accion").setText(" ");
					archivos=null;
					break;
				default:
					break;
				}
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (true);

	}//run

}
// VentanaArchivos

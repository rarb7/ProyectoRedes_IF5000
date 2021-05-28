package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class VentanaArchivos extends JInternalFrame implements ActionListener  {
	private JLabel jlblNombre;
	private JTextField jtfNombre;
	private JLabel jlblPassword;
	private JPasswordField jtfPassword;
	private JTextField jtfRuta;
	//boton
	private JButton entrar;
	private JButton enviar;
	private JButton FileChooser;
	//JFileChooser
	private JFileChooser jfcSelector;
	private String ruta;
	
	
	// constructor
		public VentanaArchivos() {
			super("Envio de Archivos");
			this.setSize(600, 500);
			this.setResizable(false);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setLayout(null);

			this.init();

			this.setVisible(true);
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
			
			this.enviar = new JButton("Enviar");
			this.enviar.setBounds(300, 60, 100, 30);
			this.enviar.addActionListener(this);
			this.add(this.enviar);
			
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
				this.ruta=archivo.getName();
			}
			
			return archivo.getName();
		}
		
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (arg0.getSource() == this.FileChooser) {
			this.ruta = initJFileChooser();
			this.jtfRuta.setText(this.ruta);
			
			System.out.println(this.ruta);
		} // select_file
	}
	
}//VentanaArchivos

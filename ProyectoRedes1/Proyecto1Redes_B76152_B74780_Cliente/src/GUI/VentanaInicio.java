package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import Cliente.Cliente;

public class VentanaInicio extends JFrame implements ActionListener {
	int contElementos;
	JMenuBar barraMenu;
	JMenu menu;
	JMenu subMenu;
	JMenuItem itemMenuRegistar;
	JMenuItem itemMenuIniciar;
	JMenuItem itemMenuBuscar;

	JMenuItem itemMenuSalir;
	JDesktopPane deskPanel;
	JSeparator separador;
	Cliente cliente;

	public VentanaInicio() {
		super();
		this.setLayout(null);
		this.setSize(650, 550);
		init();

		try {
			cliente = Cliente.getClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Prueba");

	}

	public void init() {
		this.barraMenu = new JMenuBar();
		this.deskPanel = new JDesktopPane();

		this.barraMenu.setBounds(0, 0, 60, 30);

		this.menu = new JMenu("MENU");

		this.menu.setBounds(0, 0, 100, 30);
		this.itemMenuRegistar = new JMenuItem("Registar Usuario");
		this.itemMenuIniciar = new JMenuItem("Iniciar Sesion");

		this.itemMenuSalir = new JMenuItem("Salir");

		this.separador = new JSeparator();

		this.menu.add(itemMenuRegistar);
		this.menu.add(itemMenuIniciar);

		this.itemMenuRegistar.addActionListener(this);
		this.itemMenuIniciar.addActionListener(this);

		this.menu.add(separador);
		this.menu.add(itemMenuSalir);

		this.barraMenu.add(menu);
		this.deskPanel.setBounds(0, 30, 650, 550);

		this.add(this.barraMenu);
		this.add(this.deskPanel);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.itemMenuRegistar) {
			VentanaRegistar registrar = new VentanaRegistar();
			this.deskPanel.removeAll();
			this.repaint();
			this.deskPanel.add(registrar);
			registrar.setLocation(0, 0);
			registrar.show();
			registrar.setVisible(true);

		}
		if (e.getSource() == this.itemMenuIniciar) {
			this.deskPanel.removeAll();
			this.repaint();
			VentanaArchivos ventanaArchivo = new VentanaArchivos();
			this.deskPanel.add(ventanaArchivo);

		}

		if (e.getSource() == this.itemMenuSalir) {
			this.dispose();
		}

	}
}

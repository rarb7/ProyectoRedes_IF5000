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
    //JMenuItem itemTablero;

    JMenuItem itemMenuSalir;
    JDesktopPane deskPanel;
    JSeparator separador;
    Cliente cliente;

    public VentanaInicio() {
        super();
        this.setLayout(null);
        this.setSize(600, 400);
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
        this.itemMenuIniciar = new JMenuItem("Iniciar Seccion");
        
        this.itemMenuSalir = new JMenuItem("Salir");

        this.separador = new JSeparator();
        
        this.menu.add(itemMenuRegistar);
        this.menu.add(itemMenuIniciar);
        
        this.itemMenuRegistar.addActionListener(this);
        this.itemMenuIniciar.addActionListener(this);
        
        
        this.menu.add(separador);
        this.menu.add(itemMenuSalir);

        this.barraMenu.add(menu);
        this.deskPanel.setBounds(0, 30, 600, 400);

        this.add(this.barraMenu);
        this.add(this.deskPanel);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);

    }

    

	@Override
	public void actionPerformed(ActionEvent e) {
		 if (e.getSource() == this.itemMenuRegistar) {
	            VentanaRegistrar registrar = new VentanaRegistrar();
	            this.deskPanel.add(registrar);
	            registrar.setLocation(0, 130);
	            registrar.show();
	            registrar.setVisible(true);

	        }
	        if (e.getSource() == this.itemMenuIniciar) {
	            VentanaLogin login = new VentanaLogin();
	            this.deskPanel.add(login);
	            login.show();

	        }

	        if (e.getSource() == this.itemMenuSalir) {
	            this.dispose();
	        }
		
	}

}

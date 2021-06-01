package Proyecto1Redes_B76152_B74780;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.jdom.Element;

import Data.DataUsuario;
import Domain.Usuario;
import Server.Server;
import Utility.XMLConvert;

public class Proyecto1Redes {

	public static void main(String[] args) {
// TODO Auto-generated method stub
		Server server;
		server = new Server(5025);
		server.start();

//		System.out.println("Nombre imagen "+nombre);
//		System.out.println("Nombre usuario "+usuario);
//		// TODO Auto-generated method stub
		//prueba();

	}

	public static void prueba() {
		File file=new File("D:/UCR/UCR 2021/l Semestre/Redes/Proyecto/ProyectoRedes_IF5000/ProyectoRedes1/Proyecto1Redes_B76152_B74780_Cliente/src/imagenesServidor/EnsambladoimgInosuke.jpg" );
		try {
			BufferedImage image1 = ImageIO.read(file);
			ImageIcon imgIcon = null;
			imgIcon = new ImageIcon(file.getAbsolutePath());
			Image mod = imgIcon.getImage();
			Image currently = mod.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			imgIcon = new ImageIcon(currently);
		
			JOptionPane.showMessageDialog(null, null, "Image desde el Servidor",
					JOptionPane.INFORMATION_MESSAGE, imgIcon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

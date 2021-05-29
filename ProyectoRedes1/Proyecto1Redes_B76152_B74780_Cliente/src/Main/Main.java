package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import Cliente.Cliente;
import GUI.VentanaInicio;

public class Main {
//	
//	public static BufferedImage icono(ImageIcon imgIcon) {
//		Image temp  = imgIcon.getScaledInstance(200,200,Image.SCALE_SMOOTH);
//		BufferedImage bi=new BufferedImage(200,200,BufferedImage.TYPE_INT_RGB);
//
//		Graphics2D g = bi.createGraphics();
//
//
//		g.drawImage(temp, 0, 0, null);
//
//		g.dispose();
//
//		
//		return bi;
//	}
	public static void main(String[] args) {

		VentanaInicio inicioGUI = new VentanaInicio();
//		ImageIcon imgIcon = null;
//		imgIcon = new ImageIcon("src/assets/crash.jpg");
//		Image mod = imgIcon.getImage();
//		Image currently = mod.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
//		imgIcon = new ImageIcon(currently);
//		JOptionPane.showMessageDialog(null, "Error. Archivo inválido.","hi",JOptionPane.INFORMATION_MESSAGE,imgIcon);
		
//		ArrayList<String> nombresArchivos = new ArrayList<String>();
//		File carpeta = new File("D:/UCR/UCR 2021/l Semestre/Redes/Proyecto/ProyectoRedes_IF5000/ProyectoRedes1/Proyecto1Redes_B76152_B74780_Servidor/src/imagenesEnviadas/raku");
//		String[] listado = carpeta.list();
//		if (listado == null || listado.length == 0) {
//			System.out.println("No hay elementos dentro de la carpeta actual");
//			
//		} else {
//			for (int i = 0; i < listado.length; i++) {
//				System.out.println(listado[i]);
//				nombresArchivos.add(listado[i]);
//
//			}
//		}
	}

}// main

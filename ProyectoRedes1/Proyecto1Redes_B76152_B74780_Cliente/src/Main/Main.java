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

	public static void main(String[] args) {

		VentanaInicio inicioGUI = new VentanaInicio();	
		inicioGUI.setLayout(null);
		inicioGUI.setDefaultCloseOperation(3);
		inicioGUI.setLocationRelativeTo(null);
	}

}// main

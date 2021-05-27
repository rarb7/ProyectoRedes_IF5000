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

import Cliente.Cliente;
import GUI.VentanaInicio;

public class Main {

	

	public static void main(String[] args) {
		
		VentanaInicio ventana=new VentanaInicio();
		
		try {
			Cliente cliente;
			cliente = Cliente.getClient();
			cliente.logIn("1","1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	}// main


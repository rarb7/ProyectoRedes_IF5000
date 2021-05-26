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

public class Main {

	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Cliente cliente;
		try {
			cliente = Cliente.getClient();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		ArrayList<BufferedImage> imagenPartes = partirImagenes();
//		int iter;
//		iter = 0;

//		unirPartes(imagenPartes);
//		ArrayList<BufferedImage> emsamblaje = ensamblaje();
//		mergeFiles(emsamblaje);

//		System.out.println("Cortes hechos");
	}// main
}

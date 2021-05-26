package Utility;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;

import Domain.imagenDecodificada;
import Domain.subImages;

public class ImagesConvert {

	public static BufferedImage readImage(String element,int numero) {
		BufferedImage image1 = null;;
		try {

//			image1 = XMLConvert.xmltoBufferedImage(element);
			byte[] imageByteArray = Base64.getDecoder().decode(element);
			FileOutputStream imageOutFile = new FileOutputStream("src/imagenesRecibidas/saved"+numero+".jpg");
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
			image1 = ImageIO.read(new File("src/imagenesRecibidas/saved"+numero+".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image1;
	}//fin readImage decodifica la imagen y la guarda
	
	public static void guardarImagenesRect(ArrayList<imagenDecodificada> imgsDeco) {
		
	}//guarda las imagenes en grupos de cuatro para luego ensamblarlas
	
	
	public static ArrayList<BufferedImage> ensamblaje() {
		ArrayList<BufferedImage> emsamblaje = new ArrayList<BufferedImage>();
		int cont = 1;
		int goal = 1;
		BufferedImage b1 = null;
		while (goal < 8) {
			if (cont == goal) {

				try {
					b1 = ImageIO.read(new File("src/assets/joinImageo" + cont + ".jpg"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				emsamblaje.add(b1);
				goal += 2;
			}
			cont++;
		}
		return emsamblaje;

	}//guarda las partes de las imagenes en un arrayList para luego unirlas
	
	public static void mergeFiles(ArrayList<BufferedImage> files,String ruta) {

		BufferedImage concatImage;

		ArrayList<BufferedImage> images = files;
		int heightTotal = 4 * files.get(0).getHeight();
		int maxWidth = files.get(0).getWidth();
		for (BufferedImage bufferedImage : images) {

			if (bufferedImage.getWidth() > maxWidth) {
				maxWidth = bufferedImage.getWidth();
			}
		}

		int heightCurr = 0;
		concatImage = new BufferedImage(maxWidth, heightTotal, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = concatImage.createGraphics();
		for (BufferedImage bufferedImage : images) {
			g2d.drawImage(bufferedImage, 0, heightCurr, null);
			heightCurr += bufferedImage.getHeight();
		}
		try {
			ImageIO.write(concatImage, "jpg", new File("src/imagenesEnviadas/Imagen"+ruta+".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//	return concatImage;

	}//une las partes de las imagenes verticalmente y da el resultado final de la imagen
	
	public static BufferedImage joinImages(BufferedImage img1, BufferedImage img2) {
		//int offset = 2;
		BufferedImage b = img1;

		int width = img1.getWidth() + img2.getWidth();

		int height = Math.max(img1.getHeight(), img2.getHeight());
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = newImage.createGraphics();
		g2.drawImage(img1, null, 0, 0);
		g2.drawImage(img2, null, img1.getWidth(), 0);
		g2.dispose();
		return newImage;
	}//une imagenes

}// clase encargada de procedimiento de envio de las imagenes

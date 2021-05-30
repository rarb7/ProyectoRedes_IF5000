package Utility;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import Domain.subImages;

public class ImagesConvert {

	public static ArrayList<subImages> partirImagenes(String ruta) {
		BufferedImage imagenPrincipal = null;
		int width, height;
		BufferedImage imagenPart1, imagenPart2;
		ArrayList<subImages> imagenPartes = new ArrayList<>();
		String nombreImagen = "";
		// primer corte//

		try {
			imagenPrincipal = ImageIO.read(new File(ruta));
			nombreImagen = new File(ruta).getName();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		width = imagenPrincipal.getWidth(null);
		height = imagenPrincipal.getHeight(null);
		int iter = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				BufferedImage subImgage = imagenPrincipal.getSubimage(j * (width / 4), i * (height / 4), width / 4,
						height / 4);
				try {
					String subImagenString = XMLConvert.imagetoString(subImgage);
					subImages subImagen = new subImages(subImagenString, iter);
					subImagen.setName(nombreImagen);
//					ImageIO.write(subImgage, "jpg", new File("src/assets/corte" + iter+ ".png"));
					imagenPartes.add(subImagen);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				iter++;
//				try {
//					ImageIO.write(subImgage, "jpg", new File("src/assets/corte" + iter+ ".png"));
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			} // for2

		} // for1
		return imagenPartes;

	}

//	public static String readImage(String element) {
//		BufferedImage image1 = null;
//		String ruta = " ";
//		try {
//
////			image1 = XMLConvert.xmltoBufferedImage(element);
//			byte[] imageByteArray = Base64.getDecoder().decode(element);
//			FileOutputStream imageOutFile = new FileOutputStream("src/imagenesServidor/" + element);
//			imageOutFile.write(imageByteArray);
//			imageOutFile.close();
//			image1 = ImageIO.read(new File("src/imagenesServidor/" + element));
//			ruta = "src/imagenesServidor/" + element;
//			System.out.println("Ruta---- "+ruta);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return ruta;
//	}
	public static void readImage(String element) {
        BufferedImage image1;
        try {

        image1 = XMLConvert.xmltoBufferedImage(element);
            byte[] imageByteArray = Base64.getDecoder().decode(element);
            FileOutputStream imageOutFile = new FileOutputStream("src/imagenesServidor/imagenDesdeServidor.jpg");
            imageOutFile.write(imageByteArray);
            imageOutFile.close();
        File outputfile = new File("src/imagenesServidor/imagenDesdeServidor.jpg");
        ImageIO.write(image1, "jpg",outputfile );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	// fin readImage decodifica la imagen y la guarda

	public static ImageIcon imageIcon(String ruta) {
		ImageIcon imgIcon = null;
		imgIcon = new ImageIcon(ruta);
		Image mod = imgIcon.getImage();
		Image currently = mod.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		imgIcon = new ImageIcon(currently);
		return imgIcon;
	}// convierte las imagenes en un icono para visualizarlas en un label

}// clase encargada de procedimiento de envio de las imagenes

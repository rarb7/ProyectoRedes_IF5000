package Utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Domain.subImages;

public class ImagesConvert {

	public static ArrayList<subImages> partirImagenes(String ruta) {
		BufferedImage imagenPrincipal = null;
		int width, height;
		BufferedImage imagenPart1, imagenPart2;
		ArrayList<subImages> imagenPartes = new ArrayList<>();
		String nombreImagen="";
		// primer corte//

		try {
			imagenPrincipal = ImageIO.read(new File(ruta));
			nombreImagen=new File(ruta).getName();
			
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

}// clase encargada de procedimiento de envio de las imagenes

package Domain;

import java.awt.image.BufferedImage;

public class imagenDecodificada {

	private BufferedImage imagen;
	private int imagenId;
	
	public imagenDecodificada() {
		imagen = null;
		imagenId=0;
	}//contructo default
	
	public imagenDecodificada(BufferedImage imagen, int imagenId) {
		this.imagen = imagen;
		this.imagenId = imagenId;
	}//contructor con Parametros

	
	//set & get
	public BufferedImage getImagen() {
		return imagen;
	}

	public void setImagen(BufferedImage imagen) {
		this.imagen = imagen;
	}

	public int getImagenId() {
		return imagenId;
	}

	public void setImagenId(int imagenId) {
		this.imagenId = imagenId;
	}
	
	
}//imagenDecodificada

package Domain;

import java.awt.image.BufferedImage;

public class subImages {
	private String imagen;
	private int imagenId;
	private String name;
	
	public subImages() {
		this.imagen = "";
		this.imagenId = 0;
	}//contructor
	
	public subImages(String imagen, int imagenid) {
		this.imagen= imagen;
		this.imagenId= imagenid;
	}//constructor
	
	
	//toString
	@Override
	public String toString() {
		return "subImages [imagen=" + imagen + ", imagenId=" + imagenId + "]";
	}

	//set && get
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getImagenId() {
		return imagenId;
	}

	public void setImagenId(int imagenId) {
		this.imagenId = imagenId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
	
	
}//fin clase 

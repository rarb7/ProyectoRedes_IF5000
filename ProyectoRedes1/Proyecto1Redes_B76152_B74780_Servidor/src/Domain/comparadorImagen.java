package Domain;

import java.util.Comparator;


public class comparadorImagen implements Comparator<imagenDecodificada>{
	@Override
    public int compare(imagenDecodificada emp1, imagenDecodificada emp2) {
        return emp1.getImagenId() - emp2.getImagenId();
    }
}

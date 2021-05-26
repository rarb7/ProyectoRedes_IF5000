package Domain;

public class Usuario {
 private String nombreUsuario;
 private String password;
 public Usuario() {
	// TODO Auto-generated constructor stub
	 this.nombreUsuario = "";
		this.password = "";
}
public Usuario(String nombreUsuario, String password) {
	super();
	this.nombreUsuario = nombreUsuario;
	this.password = password;
}
public String getNombreUsuario() {
	return nombreUsuario;
}
public void setNombreUsuario(String nombreUsuario) {
	this.nombreUsuario = nombreUsuario;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}

@Override
public String toString() {
	return "Usuario [nombreUsuario=" + nombreUsuario + ", password=" + password + "]";
}

 
}

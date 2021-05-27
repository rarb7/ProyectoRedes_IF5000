package Data;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Domain.Usuario;

public class DataUsuario {
	public static final String TABLA = "usuario";
	public static final String NOMBRE = "nombre";
    public static final String PASSWORD = "contrasena";
    
    public boolean insertarUsuario(Usuario usuario) throws ClassNotFoundException {
        boolean inserto = false;
        String query = "INSERT INTO " + TABLA + "(" + NOMBRE + "," + PASSWORD + ") VALUES(?,?); ";
        Connection conexion = null;
        
        try {
            conexion = DataBase.getConexion();
            PreparedStatement statement = conexion.prepareStatement(query);

            statement.setString(1, usuario.getNombreUsuario());
            statement.setString(2, usuario.getPassword());
            statement.executeUpdate();
            statement.close();
            conexion.close();
            inserto = true;
        } catch (SQLException ex) {
            inserto = false;
            Logger.getLogger(DataUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return inserto;
    }
    public Usuario getUsuario(String nombre) {


      String query = "select * from "+TABLA+" where nombre=?;";
      Usuario usuario = new Usuario();
      try {
          Connection conexion = DataBase.getConexion();
          PreparedStatement statement = conexion.prepareStatement(query);

          statement.setString(1, nombre);
         
          ResultSet result = statement.executeQuery();
          while (result.next()) {

        	  usuario.setNombreUsuario(result.getString(NOMBRE));
        	  usuario.setPassword(result.getString(PASSWORD));
             

          }
          statement.close();
          conexion.close();
      } catch (ClassNotFoundException ex) {
          Logger.getLogger(DataUsuario.class.getName()).log(Level.SEVERE, null, ex);
      } catch (SQLException ex) {
          Logger.getLogger(DataUsuario.class.getName()).log(Level.SEVERE, null, ex);
      }

      return usuario;
  }
    public boolean verificarUsuario(String nombre,String pass) {
    	Usuario usuarioVerificar=getUsuario(nombre);
    	System.out.println("----->BD ");

    	System.out.println("usuario NOmbre  "+usuarioVerificar.getNombreUsuario());
    	System.out.println("pass  "+pass);
    	System.out.println("usuario pass  "+usuarioVerificar.getPassword());
    	
    	if (usuarioVerificar.getPassword().equals(pass)) {
    		return true;
		}else {
    	return false;
		}
    }
    
}

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
}

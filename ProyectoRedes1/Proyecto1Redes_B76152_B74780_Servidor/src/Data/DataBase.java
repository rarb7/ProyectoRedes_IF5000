package Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {
    private static final String BASEDATOS="proyectoredes";
    private static final String HOSTNAME="localhost";//ip
    private static final String USER="root";//USUARIO QUE SE USA EN MYSQL
    private static final String PASSWORD="mezafernandez123";//QUE SE USA EN MYSQL
    
    public static Connection getConexion() throws ClassNotFoundException, SQLException{
        Connection conexion=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion=DriverManager.getConnection("jdbc:mysql://"+HOSTNAME+":3306/"+BASEDATOS, USER, PASSWORD);
        } catch (SQLException ex) {
            conexion=DriverManager.getConnection("jdbc:mysql://"+HOSTNAME+":3306/"+BASEDATOS, USER, PASSWORD);
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("conecto a la base de datos");
        return conexion;
    }
            
    
    
}

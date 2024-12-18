package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import excepciones.ConnectionException;

public class ConexionBD {

    private static final String JDBC_URL = "jdbc:mariadb://192.168.56.103/empresa";
//    private static final String JDBC_URL = "jdbc:postgresql://192.168.56.103:5432/batoi?currentSchema=empresa";
    private static Connection con = null;    

    public static Connection getConexion() throws ConnectionException {
        if (con == null) {            
            try {
            	Properties pc = new Properties();
            pc.put("user", "batoi");
            pc.put("password", "1234");
				con = DriverManager.getConnection(JDBC_URL, pc);
			} catch (SQLException e) {
				throw new ConnectionException("Error al intentar conectar a la base de datos: " + e.getMessage());
			}
        }
        return con;
    }

    public static void cerrar() throws ConnectionException {
        if (con != null) {
            try {
				con.close();
				con = null;
			} catch (SQLException e) {
				throw new ConnectionException("Error al intentar cerrar conexión a la base de datos: " + e.getMessage());
			}
            
        }
    }

}

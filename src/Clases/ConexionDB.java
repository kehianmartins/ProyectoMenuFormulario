/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author 57184203
 */
public class ConexionDB {
    /**
     * Contante del nombre de la Base de Datos.
     */
    private static final String BD = "agendabd";
    
    /**
     * Contante de la URL de la Base de Datos.
     */
    private static final String URL = "jdbc:mysql://localhost:3306/" + BD;
    
    /**
     * Contante del nombre del usuario administrador (root) de la Base de Datos.
     */
    private static final String USER = "root";
    
    /**
     * Contante de la contraseña del usuario root de de la Base de Datos.
     */
    private static final String PASS = "";
    
    /**
     * Instancia del objeto Connection, objeto utilizado para invocar la conexion con la Base de Datos.
     */
    private static Connection conexionBD;
    
    /**
     * Obtención de la conexión. Este método es utilizado para crear la conexión
     * con la Base de Datos. Utiliza un driver JDBC (Java Database Connectivity).
     * 
     * 
     * @return 
     */
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexionBD = DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException | SQLException e){
            return null;
        }
        return conexionBD;
    }
    
    public static void closeConnection() {
        try {
            if (!conexionBD.isClosed()) {
                conexionBD.close();
                System.out.println("Base de Datos cerrada con éxito.");
            }
        } catch (SQLException e) {
            System.out.println("Error");
        }
    }
    
    public static boolean verificarConexión(Connection conexion) {
        return conexion != null;
    }
}

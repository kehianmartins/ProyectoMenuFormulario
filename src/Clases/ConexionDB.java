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
     * Retorna null si ocurre un error al intentar establecer la conexión. Por ejemplo,
     * si el servidor de Base de Datos se encuentre apagado.
     * 
     * @return Un objeto de tipo Connection con la conexión si se establece exitosamente, 
     * caso contrario, devuelve null.
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
    
    /**
     * Cierre de la Base de Datos. Siempre, luego de terminar de utilizar una conexión,
     * se debe cerrarla para evitar una brecha de datos.
     * 
     * Este método cumple esa función, solo si ya no lo está.
     */
    public static void closeConnection() {
        try {
            if (!conexionBD.isClosed()) {
                conexionBD.close();
            }
        } catch (SQLException e) {
        }
    }
    
    /**
     * Verificación de conexión. Este método verifica si la conexión con la Base de Datos funciona 
     * correctamente.
     * 
     * @param conexion La conexión a verificar.
     * @return True o False en función de si la conexión es nula o no. Por eso en el método "getConnection()", si hay un error, devuelve null.
     */
    public static boolean verificarConexión(Connection conexion) {
        return conexion != null;
    }
}

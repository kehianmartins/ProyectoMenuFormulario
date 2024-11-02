/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.Arrays;

/**
 * @author Kehian
 * 
 * Clase con los métodos necesarios para la validación
 * de la contraseña en los formularios principales. Se
 * decidió separar los métodos para evitar repeticiones de
 * código y que el código fuente de los formularios sea
 * más limpio.
 */
public class Contrasena {
    
    private final char[] CONTRASENA_DEF = {'*','*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*', '*'};
    
    public Contrasena () {}
    
    /** 
     * Verifica Si el Largo de la Contraseña es Valido.
     * 
     * @param pass Array de caracteres que representa a la contraseña a verificar
     * @return true si el largo es valido (entre 8 y 18), false si no lo es.
     */
    public boolean verificarLargo(char[] pass) {
        int largo = pass.length;
        
        return largo >= 8 && largo <= 18;
    }
    
    /**
     * Verificar Contraseña por Defecto
     * 
     * @param pass Array de caracteres que representa a la contraseña a verificar
     * @return True si la contraseña es igual a la por defecto, Falso en el caso contrario
     */
    public boolean verificarContDef (char[] pass) {
        return Arrays.equals(pass, CONTRASENA_DEF);
    }
    
    
    /** 
     * Verifica Si la Contraseña Tiene un Espacio en Blanco.
     * 
     * @param pass Array de caracteres que representa a la contraseña a verificar
     * @return true si la contraseña contiene al menos un espacio en blanco, false en caso contrario.
     */
    public boolean contieneVacio (char[] pass) {
        for (char c : pass) {
            if(c == ' ') {
                return true;
            }
        }
        return false;
    }
}

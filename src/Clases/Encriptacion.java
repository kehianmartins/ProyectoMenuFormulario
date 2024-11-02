/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * <b>Clase Encriptacion</b>

 * <p>
 * Esta clase proporciona métodos para encriptar y desencriptar contraseñas
 * utilizando el algoritmo de encriptación simétrica <b>AES</b>. La clase incluye métodos
 * para gestionar la conversión entre arrays de char y bytes.
 * </p>
 * 
 * <p>
 * La encriptación y desencriptación se realizan con el estándar de codificación
 * AES, que es fácil de utilizar, versátil y muy seguro, ya que utiliza 16 bytes. La
 * clave se genera mediante un hash SHA-256 de una ya definida en la clase.
 * </p>
 * 
 * <br><u>Métodos públicos:</u>
 *
 * <ul>
 *  <li><code>ejecutarEncript(char[] pass)</code>: Encripta la contraseña
 *  recibida y la devuelve como un String codificado en Base64.</li>
 *  <li><code>ejecutarDesencript(String passEncript)</code>: Desencripta la
 *  contraseña encriptada recibida en formato Base64, devolviendo un array de
 *  caracteres.</li>
 * </ul>
 *
 *
 *
 * <p>
 * Nota: La clave de encriptación es definida en la clase como un array de
 * caracteres y es transformada a un formato de 128 bits requerido por AES.
 * </p>
 *
 * @author kehian
 *
 */
public class Encriptacion {
    
    /**
     * Constante que define la llave a utilizar en un arreglo de caracteres.
     */
    private final char[] llave = {'l', 'l', 'a', 'v', 'e', '_', 's', 'e', 'c', 'r', 'e', 't', 'a', '1', '2', '3', '@'};

    /**
     * Constructor de la clase Encriptacion.
     */
    public Encriptacion() {
    }
    
    /**
     *
     * Convierte un array de caracteres a un array de bytes utilizando la
     * codificación UTF-8.
     *
     * @param charArray Arreglo de caracteres a convertir.
     * @return Un arreglo de bytes obtenido de los caracteres del array de char
     * ingresado.
     */
    private byte[] charToByteUTF8(char[] charArray) {
        CharBuffer charBuffer;
        ByteBuffer byteBuffer;
        
        // Crea un buffer char(pequeño espacio en memoria) con el largo del array char
        charBuffer = CharBuffer.allocate(charArray.length);
        
        // Ingresa el array al buffer
        charBuffer.put(charArray);
        
        // Cambia el estado del buffer de escritura a lectura
        charBuffer.flip();

        // Crea otro buffer, pero esta vez de byte, que es instanciado con la decodificación del char utilizando UTF-8 (admite @, _, -, etc)
        byteBuffer = StandardCharsets.UTF_8.encode(charBuffer);
        
        // Se crea un array byte con el largo de los datos restantes por leer en el buffer byte
        byte[] byteArray = new byte[byteBuffer.remaining()];
        
        // se ingresa los datos del buffer al array byte
        byteBuffer.get(byteArray);
        return byteArray;
    }
    
    /**
     *
     * Convierte un array de bytes a un array de caracteres.
     * La operación "(caracteres[i] & 0xFF)" se utiliza para que solo los
     * valores de 8 bits más bajos se consideren en la conversión a "char", 
     * eliminando cualquie bit de signo por si el byte tiene un valor negativo.
     * 
     * @param caracteres El array de bytes que se va a convertir.
     * @return Un array de caracteres que representa los valores de cada byte en
     * el array que se ingresó.
     */
    private char[] byteToChar(byte[] caracteres) {
        
        char[] caracteresChar = new char[caracteres.length];
        
        for (int i = 0; i < caracteres.length; i++) {
            // Se usa un operador de bit a bit &, 0xFF representeación hexadecimal de 255
            caracteresChar[i] = (char) (caracteres[i] & 0xFF);
        }
        
        return caracteresChar;
    }
    
    /**
     * Generación de la llave secreta. Con este método se genera la llave secreta para la encriptación
     * utilizando un array de char ingresado como llave. La clave generada tiene un tamaño de 128 bits (16 bytes).
     * Luego se aplica SHA-256 a llaveBytes (se encripta), generando un hash de 256 bits (32 bytes). Lo que asegura que la clave 
     * tenga una longitud optima y más segura para usarla en cifrado.
     * 
     * Luego, se reduce el tamaño de la llave a 128 bits (16 bytes) porque AES funciona con esa longitud, para que así
     * se cree una llave secreta especificando que es para AES.
     * 
     * @param llave Array de caracteres que contiene la llave inicial de la que se obtiene la llave resultado.
     * @return Un SecretKeySpec que contiene la llave secreta generada.
     */
    private SecretKeySpec generarLlave(char[] llave) {
        try {
            // Un array de la llave en bytes
            byte[] llaveBytes = charToByteUTF8(llave);
            
            // Un objeto que define que encriptación utilizar, en este caso SHA-256 para la clave
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            // Encriptar la llave
            llaveBytes = md.digest(llaveBytes);
            // Se ajusta el array llaveBytes a una clave de 16bytes, 128bits, porque AES requiere 16 bytes
            llaveBytes = Arrays.copyOf(llaveBytes, 16);
            
            // Se crea la clave secreta utilizando AES, la encriptación utilizada para la contraseña
            SecretKeySpec llaveSecreta = new SecretKeySpec(llaveBytes, "AES");
            return llaveSecreta;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
    
    /**
     * Encripta una contraseña dada con AES.
     *
     * @param pass La contraseña en array de caracteres a encriptar.
     * @param llave La clave de encriptación AES en formato SecretKeySpec.
     * @return La contraseña encriptada como un String codificado en Base64, o null si ocurre una excepción.
     */
    private String encriptar(char[] pass, SecretKeySpec llave) {
        try {
            /*
             * Objeto que cifra la contraseña con AES.
             * AES (Advanced Encryption Standard) es un algoritmo de cifrado simétrico, es decir, utiliza la misma llave para cifrar y descifrar
             */
            Cipher cifrador = Cipher.getInstance("AES");
            // Inicia el cifrador en modo de encriptación con la llave
            cifrador.init(Cipher.ENCRYPT_MODE, llave);
            
            // Contraseña en bytes
            byte[] passByte = charToByteUTF8(pass);
            // Encriptación de la contraseña en bytes
            byte[] passByteEncriptada = cifrador.doFinal(passByte);
            
            // Se convierte la contraseña encriptada de byte[] a String con Base64
            String cadenaPassEncriptada = Base64.getEncoder().encodeToString(passByteEncriptada);
            
            return cadenaPassEncriptada;
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            return null;
            // Retorna null si hay un error
        }
    }
    
    /**
     * Desencripta una contraseña previamente encriptada con AES.
     *
     * @param encriptPass La contraseña encriptada en formato Base64.
     * @param llave La clave de desencriptación AES en, un SecretKeySpec.
     * @return La contraseña desencriptada como un array de caracteres, o null si ocurre una excepción.
     */
    private char[] desencriptar(String encriptPass, SecretKeySpec llave) {
        try {
            Cipher descifrador = Cipher.getInstance("AES");
            // Se inicia el descifrador, en modo de descrifrar (decrypt), con la llave
            descifrador.init(Cipher.DECRYPT_MODE, llave);
            
            // Se pasa del String a byte[], igualmente con Base64
            byte[] passByte = Base64.getDecoder().decode(encriptPass);
            // Se descriptra la contraseña
            byte[] passDesencriptByte = descifrador.doFinal(passByte);
            
            // Se convierte de byte a char, ya que los PasswordField manejas arrays de char
            char[] charPassDesencript = byteToChar(passDesencriptByte);
            return charPassDesencript;
        } catch (InvalidKeyException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            return null;
        }
    }
    
    /**
     * Método para ejecutar la encriptación. Se realizó de esta manera para que 
     * no se tenga acceso directamente al método de encriptación ni a la llave.
     * 
     * @param pass Array char con la contraseña que se desea encriptar.
     * @return Un String con la contraseña encriptada con AES.
     */
    public String ejecutarEncript(char[] pass) {
        SecretKeySpec llave = generarLlave(this.llave);
        return encriptar(pass, llave);
    }
    
    /**
     * Método para ejecutar la desencriptación. Igualmente, se realizó de esta manera para que 
     * no se tenga acceso directamente al método de desencriptación ni a la llave.
     * 
     * @param passEncript String con la contraseña ecriptada que se desea desencriptar.
     * @return Un array de char con la contraseña desencriptada con AES.
     */
    public char[] ejecutarDesencript(String passEncript) {
        SecretKeySpec llave = generarLlave(this.llave);
        return desencriptar(passEncript, llave);
    }
}

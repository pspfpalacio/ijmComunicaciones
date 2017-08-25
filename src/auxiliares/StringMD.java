package auxiliares;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class StringMD {
	
    //algoritmos
    public static String MD2 = "MD2";
    public static String MD5 = "MD5";
    public static String SHA1 = "SHA-1";
    public static String SHA256 = "SHA-256";
    public static String SHA384 = "SHA-384";
    public static String SHA512 = "SHA-512";

    /***
     * Convierte un arreglo de bytes a String usando valores hexadecimales
     * @param digest arreglo de bytes a convertir
     * @return String creado a partir de <code>digest</code>
     */
    private String toHexadecimal(byte[] digest){
        String hash = "";
        for(byte aux : digest) {
            int b = aux & 0xff;
            if (Integer.toHexString(b).length() == 1) hash += "0";
            hash += Integer.toHexString(b);
        }
        return hash;
    }

    /***
     * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.
     * @param message texto a encriptar
     * @param algorithm algoritmo de encriptacion, puede ser: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
     * @return mensaje encriptado
     */
    public String getStringMessageDigest(String message, String algorithm){
        byte[] digest = null;
        byte[] buffer = null;
//        try {
        try {
				
        	buffer = message.getBytes("UTF-16LE");			
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(buffer);
            digest = messageDigest.digest();
    	} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
    		System.out.println("Error creando Digest");
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.out.println("Error creando Digest");
			e.printStackTrace();
		}
//        return toHexadecimal(digest);
        BASE64Encoder endecoder = new BASE64Encoder();
        String s = endecoder.encode(digest);
        s = s.replaceAll("[\n\r]", "");
//        String s = new sun.misc.BASE64Encoder().encode(digest);
        return s;
    } 

}

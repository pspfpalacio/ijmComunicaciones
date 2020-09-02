package auxiliares;

public class Helper {
	
	public String EncodePassword(String originalPassword){
		//Clave que se utilizará para encriptar el usuario y la contraseña
		String clave = "7f9facc418f74439c5e9709832;0ab8a5:OCOdN5Wl,q8SLIQz8i|8agmu¬s13Q7ZXyno/";
		//Se instancia el objeto sha512 para posteriormente usarlo para calcular la matriz de bytes especificada
		StringMD stringMD512 = new StringMD();
		//String que va a poseer el pass mas la clave
		String pass = originalPassword + clave;
//		System.out.println(pass);
		//Tipo de encriptacion, en este caso SHA512
		String algorithm = "SHA-512";
		//Obtencion de la contrasenia encriptada
		return stringMD512.getStringMessageDigest(pass, algorithm);
		
	}

}

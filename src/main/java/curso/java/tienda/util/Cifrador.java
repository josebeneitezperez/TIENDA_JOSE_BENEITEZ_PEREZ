package curso.java.tienda.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Cifrador {

	public static String patron = "noCambiarEstaLinea";
	private static StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
	
	public static String cifrar(String contrasenaOriginal) {

		return encryptor.encrypt(contrasenaOriginal);
	}

	public static String descifrar(String contrasenaCifrada) {

		return encryptor.decrypt(contrasenaCifrada);
	}
	
	//al iniciar SpringBoot
	public static void fijarPatronCifrado() {
		encryptor.setPassword(patron);
	}
}

package processor;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import controllersAndProcessor.LoginController;

public class GenerateHash {
	
	private static String bytesToHex(byte[] hash) {
	    StringBuilder hexString = new StringBuilder(2 * hash.length);
	    for (int i = 0; i < hash.length; i++) {
	        String hex = Integer.toHexString(0xff & hash[i]);
	        if(hex.length() == 1) {
	            hexString.append('0');
	        }
	        hexString.append(hex);
	    }
	    return hexString.toString();
	}
    
    public static String generate(String password, String salted) throws NoSuchAlgorithmException  {
		String originalString = password + salted;
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hashbytes = digest.digest(originalString.getBytes(StandardCharsets.UTF_8));
		String sha256Text = bytesToHex(hashbytes);
		return sha256Text;
	}
    
    

}

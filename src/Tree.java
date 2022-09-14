import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Tree {
	String content;
	
	public Tree (ArrayList<String> keyVal) throws FileNotFoundException {
		for (String obj: keyVal) {
			content=content+obj+"\n";
		}
		content = content.substring(0, content.length()-1);
		String sha1 = encrypt(content);
		PrintWriter writer = new PrintWriter (new File ("objects/" + sha1));
		writer.print(content);
	}

	private String encrypt(String fileContent) {
		String sha1 = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(fileContent.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}
		return sha1;
	}
}

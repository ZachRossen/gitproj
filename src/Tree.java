import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Scanner;

public class Tree {
	String content;
	String sha1;

	public Tree() throws FileNotFoundException {
		content = "";
		Scanner scan = new Scanner("index.txt");
		while (scan.hasNextLine()) {
			String fileName = scan.next();
			scan.next();
			String sha = scan.next();
			content += "blob : " + sha + " " + fileName;
		}
		scan.close();
		content = content.substring(0, content.length() - 1);
		sha1 = encrypt(content);
		PrintWriter writer = new PrintWriter(new File("objects/" + sha1));
		writer.print(content);
		writer.close();
	}

	public String getSha() {
		return sha1;
	}

	private String encrypt(String fileContent) {
		String sha1 = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(fileContent.getBytes("utf8"));
			sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sha1;
	}

//	public static void main (String [] args) throws FileNotFoundException {
//		ArrayList<String> test = new ArrayList<String>();
//		test.add("blob : c09f382894b42abb22deaef2b26ca5b008334cf7");
//		test.add("tree : 60eaad68490578f099fc5f29fbab9029561198e5");
//		test.add("blob : db2c0fa24afb6334ce69488262c5ba671312207a");
//		Tree testTree = new Tree(test);
//	}
}

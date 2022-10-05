import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Scanner;

public class Tree {
	ArrayList<String> content;
	String sha1;
	File index = new File("index.txt");

	public Tree(Commit parent, boolean tf) throws Exception {
		content = new ArrayList<String>();
		if (tf) {
			content.add("tree : " + parent.getTree().getSha());
		}
		Scanner scan = new Scanner(index);
		ArrayList<String> fileNames = new ArrayList<String>();
		int count = 0;
		while (scan.hasNextLine()) {
			String pretext = scan.next();
			String fileName = pretext;
			if (pretext.contains("*")) {
				if (pretext.charAt(1) == 'd') {
					fileName = scan.next();
					fileNames.add(fileName);
					content.add(pretext + " " + fileName);
					count++;
				} else {
					fileName = scan.next();
					fileNames.add(fileName);
					scan.next();
					String sha = scan.next();
					content.add(pretext + sha + " " + fileName);
					count++;
				}
			} else {
				scan.next();
				String sha = scan.next();
				content.add("blob : " + sha + " " + fileName);
			}
		}
		scan.close();
		if (count > 0) {
			getContents(fileNames, parent, parent.getTree() != null);
		}
		clearIndex();
		String words = "";
		for (String str : content) {
			words += str + "\n";
		}
		words = words.substring(0, words.length() - 1);
		sha1 = encrypt(words);
		PrintWriter writer = new PrintWriter(new File("objects/" + sha1));
		writer.print(words);
		writer.close();
	}

	public void getContents(ArrayList<String> fileNames, Commit p, boolean tf) {
		if (tf) {
			for (String s : p.getTree().getContent()) {
				int count = 0;
				for (String str : fileNames) {
					if (s.contains(str)) {
						count++;
					}
				}
				if (count == 0) {
					content.add(s);
				}
			}
			getContents(fileNames, p.getParent(), p.getParent().getTree() != null);
		}
	}

	public ArrayList<String> getContent() {
		return content;
	}

	public String getSha() {
		return sha1;
	}

	public void clearIndex() throws Exception {
		FileWriter fw = new FileWriter(index);
		PrintWriter pw = new PrintWriter(fw);
		pw.write("");
		pw.close();
		fw.close();
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

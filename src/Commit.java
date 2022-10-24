import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Commit {
	private Commit parent = null;
	private Commit child = null;
	public String sha = null;
	private Tree pTree;
	private String author;
	private String date;
	private String summary;

	public Commit(String summ, String auth, Commit prent) throws Exception {
		if (prent != null) {
			parent = prent;
			writeParent();
			pTree = new Tree(prent, true);
		} else
			pTree = new Tree(null, false);
		summary = summ;
		author = auth;
		sha = encryptThisString(getContent());
	}

	public Tree getTree() {
		return pTree;
	}

	public Commit getParent() {
		return parent;
	}

	public Tree getParentTree() {
		return parent.getTree();
	}

	private void writeParent() throws Exception {
		parent.setChild(this);
		parent.writeNew();
	}

	// set next
	public void setChild(Commit c) {
		child = c;
	}

	// SHA1 method
	private String encryptThisString(String input) {
		try {
			// getInstance() method is called with algorithm SHA-1
			MessageDigest md = MessageDigest.getInstance("SHA-1");

			// digest() method is called
			// to calculate message digest of the input string
			// returned as array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);

			// Add preceding 0s to make it 32 bit
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}

			// return the HashText
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		date = (dtf.format(now));
		return date;
	}

	public String getContent() {
		String s = pTree.getSha();
		s += "\n";
		s += author;
		s += "\n";
		s += date;
		s += "\n";
		s += summary;
		return s;
	}

	public String getSha() {
		return sha;
	}

	public void writeNew() throws NoSuchAlgorithmException, IOException {
		String s = pTree.getSha();
		s += "\n";
		if (parent != null) {
			parent.getSha();
		}
		s += "\n";
		if (child != null) {
			child.getSha();
		}
		s += "\n";
		s += author;
		s += "\n";
		s += date;
		s += "\n";
		s += summary;

		File f = new File("objects\\" + sha);
		BufferedWriter wr = new BufferedWriter(new FileWriter(f));
		wr.write(s);
		wr.close();
		f.createNewFile();
	}

//	public static void main(String[] args) throws Exception {
//		Commit hi = new Commit("sum", "auth", null);
//		hi.getDate();
//		hi.writeNew();
//	}
}

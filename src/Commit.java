import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Commit {
	private Commit parent = null;
	private Commit child = null;
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

	public String genSha(File f) throws IOException, NoSuchAlgorithmException {
		BufferedReader r = new BufferedReader(new FileReader(f));
		ArrayList<String> strs = new ArrayList<String>();
		while (r.ready()) {
			strs.add(r.readLine());

		}
		r.close();
		String s = "";
		for (int i = 0; i < 4; i++) {
			if (strs.size() > i) {
				s += strs.get(strs.size() - i);
			}

		}
		date = strs.get(strs.size() - 1);
		MessageDigest md = MessageDigest.getInstance("SHA-1");

		// digest() method is called
		// to calculate message digest of the input string
		// returned as array of byte
		byte[] messageDigest = md.digest(s.getBytes());

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, messageDigest);

		// Convert message digest into hex value
		String hashtext = no.toString(16);
		return hashtext;
	}

	public String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		date = (dtf.format(now));
		return date;
	}

	public void writeNew() throws NoSuchAlgorithmException, IOException {
		String s = pTree.getSha();
		s += "\n";
		if (parent != null) {
			s += parent.getTree().getSha();
		}
		s += "\n";
		if (child != null) {
			s += child.getTree().getSha();
		}
		s += "\n";
		s += author;
		s += "\n";
		s += date;
		s += "\n";
		s += summary;
		MessageDigest md = MessageDigest.getInstance("SHA-1");

		// digest() method is called
		// to calculate message digest of the input string
		// returned as array of byte
		byte[] messageDigest = md.digest(s.getBytes());

		// Convert byte array into signum representation
		BigInteger no = new BigInteger(1, messageDigest);

		// Convert message digest into hex value
		String hashtext = no.toString(16);
		File f = new File("objects\\" + hashtext);
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

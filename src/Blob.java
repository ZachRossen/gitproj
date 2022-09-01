import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Blob {

	String shacode;
	public Blob (File f) throws IOException {
		Scanner myReader = new Scanner(f);
		String g ="";
        while (myReader.hasNextLine()) {
        	g = g + myReader.nextLine();
        }
        myReader.close();
			 try {
		            // getInstance() method is called with algorithm SHA-1
		            MessageDigest md = MessageDigest.getInstance("SHA-1");
		 
		            // digest() method is called
		            // to calculate message digest of the input string
		            // returned as array of byte
		            byte[] messageDigest = md.digest(g.getBytes());
		 
		            // Convert byte array into signum representation
		            BigInteger no = new BigInteger(1, messageDigest);
		 
		            // Convert message digest into hex value
		            String hashtext = no.toString(16);
		 
		            // Add preceding 0s to make it 32 bit
		            while (hashtext.length() < 32) {
		                hashtext = "0" + hashtext;
		            }
		 
		            // return the HashText
		            shacode = hashtext;
		            BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\swacz\\Documents\\Objects\\" + shacode));
		            Scanner myReader1 = new Scanner(f);
		            int c = 0;
		            while (myReader1.hasNextLine()) {
		            	if (c > 0) {
		            		writer.write("\n"+myReader1.nextLine());
		            	}
		            	else {
		            		writer.write(myReader1.nextLine());
		            		c++;
		            	}
		            	
		            }
		            
		            myReader1.close();
		            writer.close();
		        }
		 
		        // For specifying wrong message digest algorithms
		        catch (NoSuchAlgorithmException e) {
		            throw new RuntimeException(e);
		        }
	            
	}
	public String getSha() {
		return shacode;
	}
	public static void main (String [] args) throws IOException {
		File test = new File("\\C:\\Users\\swacz\\Documents\\SUPP DIFF.txt\\");
		Blob b = new Blob (test);
		System.out.print(b.getSha());
	}
}

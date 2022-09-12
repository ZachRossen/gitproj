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
		File serializedDir = new File("Objects");
		if (!serializedDir.exists()) {
		    serializedDir.mkdir();
		}
		Scanner myReader = new Scanner(f);
		String fileStr ="";
		int count =0;
		//makes string from file contents with enters
        while (myReader.hasNextLine()) {
        	if (count > 0) {
        		fileStr = fileStr + "\n" +myReader.nextLine();
        	}
        	else {
        		fileStr = fileStr + myReader.nextLine();
        		count++;
        	}
        	
        }
        myReader.close();
			 try {
		            // getInstance() method is called with algorithm SHA-1
		            MessageDigest md = MessageDigest.getInstance("SHA-1");
		 
		            // digest() method is called
		            // to calculate message digest of the input string
		            // returned as array of byte
		            byte[] messageDigest = md.digest(fileStr.getBytes());
		 
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
		            
		            BufferedWriter writer = new BufferedWriter(new FileWriter(serializedDir + "\\" +shacode));
		            writer.write(fileStr);
		        
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
//	public static void main (String [] args) throws IOException {
//		File test = new File("\\C:\\Users\\swacz\\Documents\\SUPP DIFF.txt\\");
//		Blob b = new Blob (test);
//		System.out.print(b.getSha());
//	}
}

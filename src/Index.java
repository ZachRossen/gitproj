import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class Index {
	HashMap<String,String> inds;
	File index;
	public Index () throws IOException {
		
		File serializedDir = new File("objects");
		if (!serializedDir.exists()) {
		    serializedDir.mkdir();
		}
		index = new File("index.txt");
		index.createNewFile();
		
		inds = new HashMap<String,String>();
		
	}
	public void addBlob(String fileName) throws IOException {
		File fileNameButAsAFile = new File(fileName);
		Blob b = new Blob (fileNameButAsAFile);
		inds.put(fileName.toString(), b.getSha());
		BufferedWriter writer = new BufferedWriter(new FileWriter(index));
        writer.write(inds.toString());
    
        writer.close();
	}
	public void removeBlob(String fileName) throws IOException {
		if (inds.containsKey(fileName)) {
			Path shadPath = Paths.get("objects/"+inds.get(fileName));
			inds.remove(fileName);
			Files.deleteIfExists(shadPath);
			BufferedWriter writer = new BufferedWriter(new FileWriter(index));
	        writer.write(inds.toString());
	        writer.close();
		}
		else {
			System.out.print("File not in current instance");
		}
		
	}
//	public static void main (String [] args) throws IOException {
//		Index h = new Index();
//
//		h.addBlob("foo.txt");
//		h.removeBlob("foo.txt");
//	}
}

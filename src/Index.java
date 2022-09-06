import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Index {
	File Index;
	public Index () throws IOException {
		Files.createDirectories(Paths.get("/path/to/directory"));
		
	}

}

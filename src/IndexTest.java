import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.PrintWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IndexTest {
	private File f;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		f = new File ("something.txt");
		PrintWriter writer = new PrintWriter(f);
		writer.println("some content\nwhat's up");
		writer.close();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}

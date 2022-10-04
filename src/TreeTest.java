import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TreeTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

		File directory = new File("objects\\");
		directory.mkdir();

	}

	@Test
	void makeTree() throws FileNotFoundException {
		ArrayList<String> test = new ArrayList<String>();
		test.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		test.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		test.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		test.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
		test.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
//		Tree t = new Tree(test);
		File f = new File("objects\\dd4840f48a74c1f97437b515101c66834b59b1be");
		assertTrue(f.exists());
	}

	@Test
	void checkCont() throws IOException {
		File f = new File("objects\\dd4840f48a74c1f97437b515101c66834b59b1be");
		BufferedReader r = new BufferedReader(new FileReader("objects\\dd4840f48a74c1f97437b515101c66834b59b1be"));
		String s = "";
		while (r.ready()) {
			s += (r.readLine());
			if (r.ready()) {
				s += "\n";
			}
		}
		assertTrue(s.equals("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f\n"
				+ "blob : 01d82591292494afd1602d175e165f94992f6f5f\n"
				+ "blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83\n"
				+ "tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b\n"
				+ "tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976"));
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {

		File directory = new File("objects\\");
		if (directory.isDirectory()) {
			for (File sub : directory.listFiles()) {
				sub.delete();
			}
		}
		directory.delete();
	}

}

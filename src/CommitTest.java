import java.io.File;
import java.io.FileWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CommitTest {

	private static File[] files;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		files = new File[6];
		for (int i = 20; i < 26; i++) {
			File f = new File((char) i + "txt.txt");
			FileWriter fw = new FileWriter(f);
			fw.write("content" + (char) i);
			fw.close();
			files[i - 20] = f;
		}
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		for (File f : files) {
			f.delete();
		}
	}

	@Test
	void test() throws Exception {
		Git g = new Git();
		g.init();
		g.add(files[0].getName());
		Commit first = new Commit("first", "idk", null);
		g.add(files[1].getName());
		g.add(files[2].getName());
		Commit second = new Commit("second", "idk", first);
		g.add(files[3].getName());
		Commit third = new Commit("third", "idk", second);
		g.add(files[4].getName());
		g.add(files[5].getName());
		Commit fourth = new Commit("fourth", "idk", third);
	}

}

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class IndexTest {
	private static File f;
	private static Index indy;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		indy = new Index();
		f = new File ("something.txt");
		PrintWriter writer = new PrintWriter(f);
		writer.println("some content\nwhat's up");
		writer.close();
		File foo = new File ("foo.txt");
		PrintWriter writer2 = new PrintWriter(foo);
		writer2.println("this is foo");
		writer2.close();
		File foobar = new File ("foobar.txt");
		PrintWriter writer3 = new PrintWriter(foobar);
		writer3.println("this is foobar");
		writer3.close();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		f.delete();
		new File("foo.txt").delete();
		new File("foobar.txt").delete();
		File sha1File = new File ("Objects/c09f382894b42abb22deaef2b26ca5b008334cf7");
		sha1File.delete();
		File sha1File2 = new File ("Objects/60eaad68490578f099fc5f29fbab9029561198e5");
		sha1File2.delete();
		File sha1File3 = new File ("Objects/db2c0fa24afb6334ce69488262c5ba671312207a");
		sha1File3.delete();
		File objects = new File ("Objects");
		objects.delete();
		File index = new File ("index.txt");
		index.delete();
	}

	@Test
	void testInit() throws IOException {
		Index indy = new Index();
		File check = new File ("Objects");
		File check2 = new File ("index.txt");
		assertTrue("Index initializer doesn't work", check.exists()&&check2.exists());
	}
	
	@Test
	void testAdd() throws IOException {
		File check = new File ("Objects/c09f382894b42abb22deaef2b26ca5b008334cf7");
		File check2 = new File ("Objects/60eaad68490578f099fc5f29fbab9029561198e5");
		File check3 = new File ("Objects/db2c0fa24afb6334ce69488262c5ba671312207a");
		indy.addBlob("something.txt");
		indy.addBlob("foo.txt");
		indy.addBlob("foobar.txt");
		boolean exists = check.exists()&&check2.exists()&&check3.exists();
		Path filePath = Path.of("index.txt");
		String content = Files.readString(filePath);
		assertTrue("Index add method doesn't work", exists&&content.contains((CharSequence)"{foo.txt=60eaad68490578f099fc5f29fbab9029561198e5, something.txt=c09f382894b42abb22deaef2b26ca5b008334cf7, foobar.txt=db2c0fa24afb6334ce69488262c5ba671312207a}"));
	}
	
	@Test
	void testRemove() throws IOException {
		indy.removeBlob("something.txt");
		File check = new File ("Objects/c09f382894b42abb22deaef2b26ca5b008334cf7");
		Path filePath = Path.of("index.txt");
		String content = Files.readString(filePath);
		assertTrue("Index remove method doesn't work", !check.exists()&&content.contains((CharSequence)"{foo.txt=60eaad68490578f099fc5f29fbab9029561198e5, foobar.txt=db2c0fa24afb6334ce69488262c5ba671312207a}"));
	}
}

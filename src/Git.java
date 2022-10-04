
public class Git {

	// index
	private Index index;

	// constructor
	public Git() {

	}

	// initializes index and objects folder
	public void init() throws Exception {
		index = new Index();
	}

	// creates blob and adds to index
	public void add(String fileName) throws Exception {
		index.addBlob(fileName);
	}

	// deletes blob
	public void delete(String fileName) {

	}
}

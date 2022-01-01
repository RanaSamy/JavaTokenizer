package JavaTokenizer;

public class ClassIdentifier {
	String path;
	Integer tokenUid;

	public ClassIdentifier(String path, Integer tokenUid) {
		this.path = path;
		this.tokenUid = tokenUid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getTokenUid() {
		return tokenUid;
	}

	public void setTokenUid(Integer tokenUid) {
		this.tokenUid = tokenUid;
	}

}

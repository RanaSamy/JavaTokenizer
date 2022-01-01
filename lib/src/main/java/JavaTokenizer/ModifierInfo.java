package JavaTokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.Position;
import com.github.javaparser.TokenRange;

public class ModifierInfo {
	private String name;
	private Position methodBodyBegin;
	private Position methodBodyEnd;
	private Optional<TokenRange> tokens;
	private List<String> parameters;
	
	public ModifierInfo() {
		parameters = new ArrayList<String>();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Position getMethodBodyBegin() {
		return methodBodyBegin;
	}

	public void setMethodBodyBegin(Position methodBodyBegin) {
		this.methodBodyBegin = methodBodyBegin;
	}

	public Position getMethodBodyEnd() {
		return methodBodyEnd;
	}

	public void setMethodBodyEnd(Position methodBodyEnd) {
		this.methodBodyEnd = methodBodyEnd;
	}

	public Optional<TokenRange> getTokens() {
		return tokens;
	}

	public void setTokens(Optional<TokenRange> tokens) {
		this.tokens = tokens;
	}

	public List<String> getParameters() {
		return parameters;
	}

	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}

}

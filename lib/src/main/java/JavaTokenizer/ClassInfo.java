package JavaTokenizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;
import com.github.javaparser.TokenRange;

public class ClassInfo {

	private String name;
	private String path;
	private List<ModifierInfo> methods;
	private Hashtable<String, Integer> methodsDict = new Hashtable<String, Integer>();
	private Hashtable<String, Integer> fieldsDict = new Hashtable<String, Integer>();
	private Optional<TokenRange> tokens;
	private String parentClass = null;
	private List<ClassInfo> innerClasses;

	public ClassInfo() {
		methods = new ArrayList<ModifierInfo>();
	}

	public List<ModifierInfo> getModifiers() {
		return methods;
	}

	public void setModifiers(List<ModifierInfo> methods) {
		this.methods = methods;
	}

	public Optional<TokenRange> getTokens() {
		return tokens;
	}

	public void setTokens(Optional<TokenRange> tokens) {
		this.tokens = tokens;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Hashtable<String, Integer> getMethodsDict() {
		return methodsDict;
	}

	public void setMethodsDict(Hashtable<String, Integer> methodsDict) {
		this.methodsDict = methodsDict;
	}

	public Hashtable<String, Integer> getFieldsDict() {
		return fieldsDict;
	}

	public void setFieldsDict(Hashtable<String, Integer> fieldsDict) {
		this.fieldsDict = fieldsDict;
	}

	public List<ClassInfo> getInnerClasses() {
		return innerClasses;
	}

	public String getParentClass() {
		return parentClass;
	}

	public void setParentClass(String parentClass) {
		this.parentClass = parentClass;
	}

	public void setInnerClasses(List<ClassInfo> innerClasses) {
		this.innerClasses = innerClasses;
	}

}

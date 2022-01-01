package JavaTokenizer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

public class ClassInfo {

	private String name;
	private String path;
	// private ClassOrInterfaceDeclaration javaParserClass;
	private List<ModifierInfo> methods;
	private Hashtable<String, Integer> methodsDict = new Hashtable<String, Integer>();
	private Hashtable<String, Integer> fieldsDict = new Hashtable<String, Integer>();
	private Optional<TokenRange> tokens;
	private String parentClass = null;
	// private Boolean isInnerClass=false;
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

	/*
	 * public ClassOrInterfaceDeclaration getJavaParserClass() { return
	 * javaParserClass; }
	 * 
	 * public void setJavaParserClass(ClassOrInterfaceDeclaration javaParserClass) {
	 * this.javaParserClass = javaParserClass; }
	 */
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

	/*
	 * public Boolean getIsInnerClass() { return isInnerClass; }
	 * 
	 * 
	 * public void setIsInnerClass(Boolean isInnerClass) { this.isInnerClass =
	 * isInnerClass; }
	 */

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

	/*
	 * public List<String> getMethodsNames() { return methodsNames; }
	 * 
	 * public void setMethodsNames(List<String> methodsNames) { this.methodsNames =
	 * methodsNames; }
	 * 
	 * public List<String> getFieldsNames() { return fieldsNames; }
	 * 
	 * public void setFieldsNames(List<String> fieldsNames) { this.fieldsNames =
	 * fieldsNames; }
	 */
}

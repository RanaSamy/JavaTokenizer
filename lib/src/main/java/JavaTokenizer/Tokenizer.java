package JavaTokenizer;

import java.util.Hashtable;
import java.util.List;

public class Tokenizer {

	private static Hashtable<String, Integer> classesDict = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> methodsDict = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> fieldsDict = new Hashtable<String, Integer>();
	private static Hashtable<String, List<String>> classMethodsLink = new Hashtable<String, List<String>>();
	private static Hashtable<String, List<String>> classFieldsLink = new Hashtable<String, List<String>>();

	

	public void AddClassInfo(String className, List<String> methods, List<String> fields) {

		classesDict.put(className, Constants.Class_Token + (++Constants.classIterator));

		for (String method : methods)
			methodsDict.put(method, Constants.Method_Token + (++Constants.methodIterator));

		for (String field : fields)
			fieldsDict.put(field, Constants.Field_Token + (++Constants.fieldIterator));

		classMethodsLink.put(className, methods);
		classFieldsLink.put(className, fields);
	}

	public static Hashtable<String, Integer> getClassesDict() {
		return classesDict;
	}

	public static Hashtable<String, Integer> getMethodsDict() {
		return methodsDict;
	}

	public static Hashtable<String, Integer> getFieldsDict() {
		return fieldsDict;
	}

	public static Hashtable<String, List<String>> getClassMethodsLink() {
		return classMethodsLink;
	}

	public static Hashtable<String, List<String>> getClassFieldsLink() {
		return classFieldsLink;
	}

	/*
	public int ProcessNumbers(double number) {
		return Constants.Number_Token;
	}

	public int ProcessWord(String word) {
		// is null?
		if (word.equals("null"))
			return Constants.Null_Token;
		return 0;
		// is a java keyword?

		// is a method name?
		// is a class name?
		// is an identifier?

	}
	*/

}

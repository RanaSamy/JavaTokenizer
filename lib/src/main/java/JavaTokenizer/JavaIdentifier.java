package JavaTokenizer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.JavaToken;
import com.github.javaparser.Position;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;

public class JavaIdentifier {

	private static Hashtable<String, Integer> predefinedClasses = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> predefinedMethods = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> classesDict = new Hashtable<String, Integer>();
	// private static Hashtable<String, List<ClassIdentifier>> classesDict = new
	// Hashtable<String, List<ClassIdentifier>>();
	// private static Hashtable<String,ClassIdentifier> sameClassesNames = new
	// Hashtable<String,ClassIdentifier>();

	private String latestCalledClass;
	private String latestCalledMethod;
	private Hashtable<String, Integer> methodVariables = new Hashtable<String, Integer>();

	public static void SetPredefinedClasses() {
		predefinedClasses.put("String", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Character", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Byte", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Short", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Integer", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Long", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Float", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Double", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Boolean", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("System", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("out", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Color", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Math", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Object", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Random", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Scanner", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Exception", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("IOException", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Runnable", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("Serializable", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("StringBuffer", Constants.Class_Token + (++Constants.classIterator));
		predefinedClasses.put("HashSet", Constants.Class_Token + (++Constants.classIterator));
		
		
	}

	public static void SetPredefinedMethods() {
		// String Methods
		predefinedMethods.put("toString", Constants.Method_Token + (++Constants.methodIterator));
		// Runnable Methods
		predefinedMethods.put("run", Constants.Method_Token + (++Constants.methodIterator));
		// List Methods
		predefinedMethods.put("length", Constants.Method_Token + (++Constants.methodIterator));
		predefinedMethods.put("append", Constants.Method_Token + (++Constants.methodIterator));
		predefinedMethods.put("add", Constants.Method_Token + (++Constants.methodIterator));
	}

	public static void AddClassTokens(ClassOrInterfaceDeclaration n, ClassInfo classInfo) {
		if (classesDict.containsKey(classInfo.getName())) {
			System.out.println(
					"Class: " + classInfo.getName() + " for path: " + classInfo.getPath() + " is already found ");
			// sameClassesNames.add(classInfo.getName());

		} else {
			classesDict.put(classInfo.getName(), Constants.Class_Token + (++Constants.classIterator));
			AddFieldsTokens(n.getFields(), classInfo);
			AddMethodsTokens(n.getMethods(), classInfo);
			AddModifierParameters(n, classInfo);
		}
	}

	private static void AddModifierParameters(ClassOrInterfaceDeclaration n, ClassInfo classInfo) {
		for (MethodDeclaration m : n.getMethods()) {

			MethodInfo methodInfo = new MethodInfo();
			methodInfo.setName(m.getName().toString());
			methodInfo.setTokens(m.getTokenRange());
			methodInfo.setMethodBodyBegin(m.getBody().get().getTokenRange().get().getBegin().getRange().get().begin);
			methodInfo.setMethodBodyEnd(m.getBody().get().getTokenRange().get().getEnd().getRange().get().end);

			NodeList<Parameter> parameters = m.getParameters();

			for (Parameter p : parameters)
				methodInfo.getParameters().add(p.getName().toString());

			classInfo.getModifiers().add(methodInfo);
		}

		for (ConstructorDeclaration c : n.getConstructors()) {

			ConstructorInfo constructorInfo = new ConstructorInfo();
			constructorInfo.setName(c.getName().toString());
			constructorInfo.setTokens(c.getTokenRange());
			constructorInfo.setMethodBodyBegin(c.getBody().getTokenRange().get().getBegin().getRange().get().begin);
			constructorInfo.setMethodBodyEnd(c.getBody().getTokenRange().get().getEnd().getRange().get().end);

			NodeList<Parameter> parameters = c.getParameters();

			for (Parameter p : parameters)
				constructorInfo.getParameters().add(p.getName().toString());

			classInfo.getModifiers().add(constructorInfo);
		}

	}

	private static void AddMethodsTokens(List<MethodDeclaration> methods, ClassInfo classInfo) {
		for (MethodDeclaration m : methods) {

			if (classInfo.getMethodsDict().containsKey(m.getName().toString())) {
				System.out.println(m.getName().toString() + " method is already found");
			} else {
				classInfo.getMethodsDict().put(m.getName().toString(),
						Constants.Method_Token + (++Constants.methodIterator));
			}
		}
	}

	private static void AddFieldsTokens(List<FieldDeclaration> fields, ClassInfo classInfo) {
		for (FieldDeclaration f : fields) {
			if (classInfo.getFieldsDict().containsKey(f.getVariable(0).getName().toString())) {
				System.out.println(f.getVariable(0).getName().toString() + " field is already found");
			} else {
				classInfo.getFieldsDict().put(f.getVariable(0).getName().toString(),
						Constants.Field_Token + (++Constants.fieldIterator));
			}
		}
	}

	public int GetIdentifierToken(JavaToken token, ClassInfo classInfo, TokenFileWriter tokensFile) {
		// Class Name
		if (classesDict.containsKey(token.getText())) {
			if (IsConstructor(token, classInfo.getName())) {
				methodVariables = new Hashtable<String, Integer>();
				latestCalledMethod = token.getText();
				tokensFile.WriteLineToFile("Constructor");
				return classesDict.get(token.getText());
			}
			latestCalledClass = token.getText();
			tokensFile.WriteLineToFile("Class");
			return classesDict.get(token.getText());
		}
		// Method Name
		else if (isMethod(token, classInfo)) {
			// latestCalledMethod =
			// classInfo.getJavaParserClass().getMethodsByName(token.getText()).get(0);
			methodVariables = new Hashtable<String, Integer>();
			latestCalledMethod = token.getText();
			tokensFile.WriteLineToFile("Method");
			return classInfo.getMethodsDict().get(token.getText());
		}
		// Method Parameter
		else if (IsMethodParameter(token, classInfo)) {
			tokensFile.WriteLineToFile("Add a Parameter Variable");
			methodVariables.put(token.getText(), ++Constants.Identifier_Token);
			return Constants.Identifier_Token;
		}
		// Field Name
		else if (IsClassField(token, classInfo)) {
			tokensFile.WriteLineToFile("Field");
			return classInfo.getFieldsDict().get(token.getText());
		}
		// Predefined Class
		else if (predefinedClasses.containsKey(token.getText())) {
			tokensFile.WriteLineToFile("Predefined Class");
			return predefinedClasses.get(token.getText());
		}
		// method call for a predefined class
		else if (predefinedMethods.containsKey(token.getText())) {
			tokensFile.WriteLineToFile("Predefined Method");
			return predefinedMethods.get(token.getText());
		}
		// Predefined method Variable
		else if (isMethodVariable(token, classInfo)) {
			tokensFile.WriteLineToFile("Predefined Method Variable/Prameter");
			return methodVariables.get(token.getText());

		} else if (IsMethodCalledFromAnotherClass(token)) {
			tokensFile.WriteLineToFile("Another Class Called " + latestCalledClass);
			return GetClassMembersToken(token.getText());
		}
		// Add a Method Variable
		else {

			tokensFile.WriteLineToFile("Add a Method Variable");
			methodVariables.put(token.getText(), ++Constants.Identifier_Token);
			return Constants.Identifier_Token;
		}

	}

	private boolean isMethod(JavaToken token, ClassInfo classInfo) {

		if (!classInfo.getMethodsDict().containsKey(token.getText()))
			return false;

		for (ModifierInfo method : classInfo.getModifiers()) {
			if (withinHeaderTokenRange(method, token)) {
				return true;
			}
		}
		return false;
	}

	private boolean IsClassField(JavaToken token, ClassInfo classInfo) {

		if (!classInfo.getFieldsDict().containsKey(token.getText()))
			return false;
		// "this.*" can be a field
		Optional<JavaToken> previousToken = token.getPreviousToken();
		if (".".equals(previousToken.get().getText())) {
			Optional<JavaToken> previousPreviousToken = previousToken.get().getPreviousToken();
			if ("this".equals(previousPreviousToken.get().getText())) {
				return true;
			}
		}

		// Already a method variable
		if (isMethodVariable(token, classInfo))
			return false;

		return true;
	}

	private boolean IsMethodParameter(JavaToken token, ClassInfo classInfo) {
		// "this.*" can't be a method parameter
		Optional<JavaToken> previousToken = token.getPreviousToken();
		if (".".equals(previousToken.get().getText())) {
			Optional<JavaToken> previousPreviousToken = previousToken.get().getPreviousToken();
			if ("this".equals(previousPreviousToken.get().getText())) {
				return false;
			}
		}

		// Already a method variable
		if (isMethodVariable(token, classInfo))
			return false;

		// check the method parameters
		for (ModifierInfo method : classInfo.getModifiers()) {
			if (method.getName().equals(latestCalledMethod) && withinTokenRange(method, token)) {
				for (String parameter : method.getParameters()) {
					if (parameter.equals(token.getText()))
						return true;
				}
			}
		}
		return false;
	}

	private boolean isMethodVariable(JavaToken token, ClassInfo classInfo) {

		if (!methodVariables.containsKey(token.getText()))
			return false;

		for (ModifierInfo method : classInfo.getModifiers()) {
			if (method.getName().equals(latestCalledMethod) && withinTokenRange(method, token)) {
				return true;
			}
		}
		return false;
	}

	private boolean withinTokenRange(ModifierInfo modifier, JavaToken token) {
		if (token.getRange().get().begin.isAfterOrEqual(modifier.getTokens().get().getBegin().getRange().get().begin)
				&& token.getRange().get().end.isBeforeOrEqual(modifier.getTokens().get().getEnd().getRange().get().end))
			return true;
		return false;
	}

	// if this token belongs to the method/constructor header
	private boolean withinHeaderTokenRange(ModifierInfo modifier, JavaToken token) {
		if (withinTokenRange(modifier, token) && !withinMethodBodyTokenRange(modifier, token))
			return true;
		return false;
	}

	private boolean withinMethodBodyTokenRange(ModifierInfo modifier, JavaToken token) {
		if (token.getRange().get().begin.isAfterOrEqual(modifier.getMethodBodyBegin())
				&& token.getRange().get().end.isBeforeOrEqual(modifier.getMethodBodyEnd()))
			return true;
		return false;
	}

	private boolean IsConstructor(JavaToken token, String className) {
		JavaToken previousToken = GetPreviousToken(token);
		if (!("class".equals(previousToken.getText())) && !("interface".equals(previousToken.getText()))
				&& (className.equals(token.getText())))
			return true;
		return false;
	}

	private boolean IsMethodCalledFromAnotherClass(JavaToken token) {
		JavaToken previousToken = GetPreviousToken(token);
		if (".".equals(previousToken.getText())) {
			JavaToken previousPreviousToken = GetPreviousToken(previousToken);
			if (latestCalledClass.equals(previousPreviousToken.getText()))
				return true;
		}
		return false;
	}

	private int GetClassMembersToken(String token) {
		for (ClassInfo classInfo : ClassParser.classes) {
			if (classInfo.getName().equals(latestCalledClass)) {
				if (classInfo.getMethodsDict().containsKey(token)) {
					return classInfo.getMethodsDict().get(token);
				} else if (classInfo.getFieldsDict().containsKey(token)) {
					return classInfo.getFieldsDict().get(token);
				}
			}
		}
		return Constants.Unidentified_Token;
	}

	private JavaToken GetPreviousToken(JavaToken token) {
		Optional<JavaToken> previousToken = token.getPreviousToken();
		while (previousToken.get().getText().equals(" ")) {
			previousToken = previousToken.get().getPreviousToken();
		}
		return previousToken.get();
	}
}

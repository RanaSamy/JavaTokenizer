package JavaTokenizer;

import com.github.javaparser.JavaToken;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassParser {

	private File projectDirectoryPath;
	private String dirPath;

	private JavaIdentifier javaIdentifier = new JavaIdentifier();
	private JavaKeyword javaKeyword = new JavaKeyword();
	private JavaLiteral javaLiteral = new JavaLiteral();
	private JavaOperator javaOperator = new JavaOperator();
	private JavaSeparator javaSeparator = new JavaSeparator();

	public static List<ClassInfo> classes = new ArrayList<ClassInfo>();

	public ClassParser(File projectDir, String parsingPath) {
		projectDirectoryPath = projectDir;
		dirPath = parsingPath;
	}

	public void ParseJavaCode() {
		JavaIdentifier.SetPredefinedClasses();
		JavaIdentifier.SetPredefinedMethods();
		IterateOnProjectDirectory(projectDirectoryPath);
		ProcessAllClasses();
	}


	public void IterateOnProjectDirectory(File projectDir) {
		new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {

			try {
				new VoidVisitorAdapter<Object>() {
					@Override
					public void visit(ClassOrInterfaceDeclaration n, Object arg) {
						super.visit(n, arg);
						FillClassInfoDict(n, file);
					}
				}.visit(StaticJavaParser.parse(file), null);
			} catch (IOException e) {
				System.out.println(e);
			}
		}).explore(projectDir);

	}

	protected void FillClassInfoDict(ClassOrInterfaceDeclaration n, File file) {

		ClassInfo classInfo = new ClassInfo();
		classInfo.setName(n.getName().toString());
		classInfo.setPath(file.getAbsolutePath());
		classInfo.setParentClass(SetParentClassName(n));
		classInfo.setInnerClasses(GetInnerClassesForClassOrInterfaceDeclaration(n));
		classInfo.setTokens(n.getTokenRange());
		JavaIdentifier.AddClassTokens(n, classInfo);

		classes.add(classInfo);
	}

	private String SetParentClassName(ClassOrInterfaceDeclaration n) {
		if (n.hasParentNode()) {
			Node m = n.getParentNode().get();
			if (m instanceof ClassOrInterfaceDeclaration)
				return ((ClassOrInterfaceDeclaration) m).getName().toString();
		}
		return null;
	}
	    
	   
	
	private List<ClassInfo> GetInnerClassesForClassOrInterfaceDeclaration(ClassOrInterfaceDeclaration n) {
		List<ClassOrInterfaceDeclaration> internalClasses = unmodifiableList(
				n.getMembers().stream().filter(m -> m instanceof ClassOrInterfaceDeclaration)
						.map(m -> (ClassOrInterfaceDeclaration) m).collect(toList()));

		List<ClassInfo> innerClasses = new ArrayList<ClassInfo>();

		for (ClassOrInterfaceDeclaration c : internalClasses) {
			ClassInfo innerClass = GetInnerClassForClassOrInterfaceDeclaration(n.getName().toString(),
					c.getName().toString());
			innerClasses.add(innerClass);
		}

		return innerClasses;
	}

	private ClassInfo GetInnerClassForClassOrInterfaceDeclaration(String parentClassName, String innerClassName) {
		ClassInfo innerClassInfo = null;

		for (ClassInfo classInfo : classes) {
			if (parentClassName.equals(classInfo.getParentClass()) && classInfo.getName().equals(innerClassName)) {
				innerClassInfo = classInfo;
				break;
			}
		}
		classes.remove(innerClassInfo);
		return innerClassInfo;
	}
	
	
	private String GetFilePath(String string, String path) {
		String parent = Paths.get(path).getParent().getFileName().toString();
		String filePath = dirPath + (parent + "_" + string + ".txt");
		return filePath;
	}

	protected void ProcessAllClasses() {
		for (ClassInfo classInfo : classes) {
			System.out.println("Processing  " + classInfo.getName());
			javaIdentifier = new JavaIdentifier();
			TokenFileWriter tokensFile = new TokenFileWriter(GetFilePath(classInfo.getName(), classInfo.getPath()));
			Optional<TokenRange> tokens = classInfo.getTokens();

			tokens.get().forEach(t -> TokenizeClass(classInfo, t, tokensFile));
			tokensFile.CloseFile();
		}
	}

	private void TokenizeClass(ClassInfo classInfo, JavaToken t, TokenFileWriter tokensFile) {

		ClassInfo innerClass = isInnerClassToken(classInfo.getInnerClasses(), t);
		
		if (innerClass != null) {
			TokenizeCode(innerClass, t, tokensFile);
		}
		else {
			TokenizeCode(classInfo, t, tokensFile);
		}
	}

	// returns null if it's not an inner class token
	// else returns the inner class
	private ClassInfo isInnerClassToken(List<ClassInfo> innerClassesList, JavaToken t) {

		for (ClassInfo innerClass : innerClassesList) {
			if (t.getRange().get().begin.isAfterOrEqual(innerClass.getTokens().get().getBegin().getRange().get().begin)
					&& t.getRange().get().end
							.isBeforeOrEqual(innerClass.getTokens().get().getEnd().getRange().get().end)) {

				return innerClass;
			}
		}

		return null;
	}

	protected void TokenizeCode(ClassInfo classInfo, JavaToken token, TokenFileWriter tokensFile) {

		if (token.getCategory().isWhitespaceOrComment()) {

		} else if (token.getCategory().isIdentifier()) {
			tokensFile.WriteLineToFile("Identifier");
			tokensFile.WriteLineToFile(
					token.getText() + "	->	" + javaIdentifier.GetIdentifierToken(token, classInfo, tokensFile));
			tokensFile.WriteLineToFile("---------------------------------------------------");

		} else if (token.getCategory().isKeyword()) {
			tokensFile.WriteLineToFile("Keyword");
			tokensFile.WriteLineToFile(token.getText() + "	->	" + javaKeyword.javaKeywordsDict.get(token.getText()));
			tokensFile.WriteLineToFile("---------------------------------------------------");

		} else if (token.getCategory().isLiteral()) {
			tokensFile.WriteLineToFile("Literal");
			tokensFile.WriteLineToFile(token.getText() + "	->	" + javaLiteral.GetLiteral(token));
			tokensFile.WriteLineToFile("---------------------------------------------------");

		} else if (token.getCategory().isSeparator()) {
			tokensFile.WriteLineToFile("Separator");
			tokensFile.WriteLineToFile(
					token.getText() + "	->	" + javaSeparator.javaSeparatorsDict.get(token.getText()));
			tokensFile.WriteLineToFile("---------------------------------------------------");

		} else if (token.getCategory().isOperator()) {
			tokensFile.WriteLineToFile("Operator");
			tokensFile.WriteLineToFile(
					token.getText() + "	->	" + javaOperator.javaOperatorsDict.get(token.getText()));
			tokensFile.WriteLineToFile("---------------------------------------------------");
		
		}

	}
}

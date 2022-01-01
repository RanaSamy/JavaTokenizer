package JavaTokenizer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.JavaToken;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BinaryExpr.Operator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.nodeTypes.NodeWithIdentifier;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.SourceRoot;
import com.google.common.base.Strings;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
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

	// to be removed
	// TokenFileWriter literals = new TokenFileWriter("literals.txt");
	/*
	 * HashSet<String> keywords= new HashSet<String>(); HashSet<String> identifiers=
	 * new HashSet<String>(); HashSet<String> literals= new HashSet<String>();
	 * HashSet<String> separators= new HashSet<String>(); HashSet<String> operators=
	 * new HashSet<String>(); HashSet<String> others= new HashSet<String>();
	 */

	public ClassParser(File projectDir, String parsingPath) {
		projectDirectoryPath = projectDir;
		dirPath = parsingPath;
	}

	public void ParseJavaCode() {

		// FillClassInfoDict();
		JavaIdentifier.SetPredefinedClasses();
		JavaIdentifier.SetPredefinedMethods();
		IterateOnProjectDirectory(projectDirectoryPath);
		ProcessAllClasses();
	}

	/*
	 * private void WriteTokens() { TokenFileWriter tokensFile = new
	 * TokenFileWriter("literals.txt"); tokensFile.WriteLineToFile("literals");
	 * for(String w: literals) tokensFile.WriteLineToFile(w);
	 * 
	 * tokensFile.WriteLineToFile("Keywords"); for(String w: keywords)
	 * tokensFile.WriteLineToFile(w); tokensFile.WriteLineToFile(
	 * "============================================================");
	 * 
	 * tokensFile.WriteLineToFile("identifiers"); for(String w: identifiers)
	 * tokensFile.WriteLineToFile(w); tokensFile.WriteLineToFile(
	 * "============================================================");
	 * 
	 * tokensFile.WriteLineToFile("literals"); for(String w: literals)
	 * tokensFile.WriteLineToFile(w); tokensFile.WriteLineToFile(
	 * "============================================================");
	 * 
	 * tokensFile.WriteLineToFile("separators"); for(String w: separators)
	 * tokensFile.WriteLineToFile(w); tokensFile.WriteLineToFile(
	 * "============================================================");
	 * 
	 * tokensFile.WriteLineToFile("operators"); for(String w: operators)
	 * tokensFile.WriteLineToFile(w); tokensFile.WriteLineToFile(
	 * "============================================================");
	 * 
	 * tokensFile.WriteLineToFile("others"); for(String w: others)
	 * tokensFile.WriteLineToFile(w); tokensFile.WriteLineToFile(
	 * "============================================================");
	 * 
	 * tokensFile.CloseFile(); }
	 */
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
		// RemoveOtherClassesFromSameFile(n);
		// ((CompilationUnit) n).recalculatePositions();
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
	/*
	 * protected void FillClassInfoDict() {
	 * 
	 * String filePath="C:\\Users\\Rana\\Desktop\\Attribute_Code.java"; SourceRoot
	 * sourceRoot = new SourceRoot(Paths.get(filePath)); CompilationUnit
	 * cu=sourceRoot.parse("", filePath); cu.accept( new ModifierVisitor<Void>() {
	 * 
	 * @Override public Visitable visit(ClassOrInterfaceDeclaration n, Void arg) {
	 * RemoveOtherClassesFromSameFile(n); return super.visit(n, arg); } }, null);
	 * sourceRoot.saveAll(Paths.get(filePath)); //RemoveOtherClassesFromSameFile(n);
	 * sourceRoot.saveAll(); //n.getTokenRange();
	 * 
	 * 
	 * }
	 */
	/*
	 * protected void FillClassInfoDict(ClassOrInterfaceDeclaration n, File file) {
	 * CompilationUnit compilationUnit; ClassInfo classInfo = new ClassInfo();
	 * classInfo.setName(n.getName().toString());
	 * classInfo.setPath(file.getAbsolutePath()); RemoveOtherClassesFromSameFile(n);
	 * if (n.getParentNode().isPresent() && n.getParentNode().get() instanceof
	 * CompilationUnit) { compilationUnit=(CompilationUnit) n.getParentNode().get();
	 * compilationUnit.getTokenRange(); compilationUnit.recalculatePositions();
	 * compilationUnit.getTokenRange();
	 * 
	 * } //classInfo.setTokens(n.getTokenRange());
	 * classInfo.setTokens(n.getTokenRange()); List<Optional<TokenRange>> tokens=
	 * unmodifiableList( n.getChildNodes().stream().filter(m -> ! (m instanceof
	 * ClassOrInterfaceDeclaration)) .map(m ->
	 * m.getTokenRange()).collect(toList())); JavaIdentifier.AddClassTokens(n,
	 * classInfo);
	 * 
	 * classes.add(classInfo); }
	 */

	private void RemoveOtherClassesFromSameFile(ClassOrInterfaceDeclaration n) {
		List<ClassOrInterfaceDeclaration> internalClasses = unmodifiableList(
				n.getMembers().stream().filter(m -> m instanceof ClassOrInterfaceDeclaration)
						.map(m -> (ClassOrInterfaceDeclaration) m).collect(toList()));
		for (ClassOrInterfaceDeclaration c : internalClasses)
			n.remove(c);
	}

	/*
	 * private void hamada(ClassOrInterfaceDeclaration n) { CompilationUnit
	 * compilationUnit = JavaParser.parse(rFile); TypeDeclaration resourceClass =
	 * compilationUnit.getTypes().get(0);
	 * 
	 * for (Node node : resourceClass.getChildNodes()) { if (node instanceof
	 * ClassOrInterfaceDeclaration) {
	 * addResourceType(Arrays.asList(SUPPORTED_TYPES), result,
	 * (ClassOrInterfaceDeclaration) node, useLegacyTypes); } } }
	 */
	/*
	 * public void IterateOnProjectDirectory(File projectDir) { new
	 * DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path,
	 * file) -> { System.out.println(path); System.out.println(Strings.repeat("=",
	 * path.length())); try { new VoidVisitorAdapter<Object>() {
	 * 
	 * @Override public void visit(ClassOrInterfaceDeclaration n, Object arg) {
	 * super.visit(n, arg); System.out.println(" * " + n.getName());
	 * SetClassOrInterfaceInfo(n, path); } }.visit(StaticJavaParser.parse(file),
	 * null); System.out.println(); // empty line } catch (IOException e) {
	 * System.out.println(e); } }).explore(projectDir);
	 * 
	 * }
	 */
	private String GetFilePath(String string, String path) {
		String parent = Paths.get(path).getParent().getFileName().toString();
		String filePath = dirPath + (parent + "_" + string + ".txt");
		return filePath;
		// return path;
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
			// tokensFile.WriteLineToFile(token.getText() + " isWhitespaceOrComment");
			// System.out.println(token.getText()+" isWhitespaceOrComment");

		} else if (token.getCategory().isIdentifier()) {
			tokensFile.WriteLineToFile("Identifier");
			tokensFile.WriteLineToFile(
					token.getText() + "	->	" + javaIdentifier.GetIdentifierToken(token, classInfo, tokensFile));
			tokensFile.WriteLineToFile("---------------------------------------------------");
			// System.out.println(token.get);
			// identifiers.add(token.getText());
			// System.out.println(token.getText()+" isIdentifier");

		} else if (token.getCategory().isKeyword()) {
			tokensFile.WriteLineToFile("Keyword");
			tokensFile.WriteLineToFile(token.getText() + "	->	" + javaKeyword.javaKeywordsDict.get(token.getText()));
			tokensFile.WriteLineToFile("---------------------------------------------------");
			// keywords.add(token.getText());
			// System.out.println(token.getText()+" isKeyword");

		} else if (token.getCategory().isLiteral()) {
			tokensFile.WriteLineToFile("Literal");
			tokensFile.WriteLineToFile(token.getText() + "	->	" + javaLiteral.GetLiteral(token));
			tokensFile.WriteLineToFile("---------------------------------------------------");
			// literals.WriteLineToFile(token.getText()+" "+token.getKind());
			// literals.add(token.getText());
			// System.out.println(token.getText()+" isLiteral");

		} else if (token.getCategory().isSeparator()) {
			tokensFile.WriteLineToFile("Separator");
			tokensFile.WriteLineToFile(
					token.getText() + "	->	" + javaSeparator.javaSeparatorsDict.get(token.getText()));
			tokensFile.WriteLineToFile("---------------------------------------------------");
			// separators.add(token.getText());
			// System.out.println(token.getText()+" isSeparator");

		} else if (token.getCategory().isOperator()) {
			tokensFile.WriteLineToFile("Operator");
			tokensFile.WriteLineToFile(
					token.getText() + "	->	" + javaOperator.javaOperatorsDict.get(token.getText()));
			tokensFile.WriteLineToFile("---------------------------------------------------");
			// operators.add(token.getText());
			// System.out.println(token.getText()+" isOperator");
		}
		// Java keyword ?
		// Java char?
		// number ?
		// word: method name/ class name/ Identifier

	}
}

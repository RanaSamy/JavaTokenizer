package JavaTokenizer;

import com.github.javaparser.JavaToken;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.TokenRange;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ClassParser {

	private File projectDirectoryPath;
	private String dirPath;
	private JavaIdentifier javaIdentifier = new JavaIdentifier();
	private JavaKeyword javaKeyword = new JavaKeyword();
	private JavaLiteral javaLiteral = new JavaLiteral();
	private JavaOperator javaOperator = new JavaOperator();
	private JavaSeparator javaSeparator = new JavaSeparator();
	public static List<ClassInfo> classes = new ArrayList<ClassInfo>();

	private XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet spreadsheet = workbook.createSheet("sheet");
	List<Object[]> excelData = new ArrayList<Object[]>();

	public ClassParser(File projectDir, String parsingPath) {
		projectDirectoryPath = projectDir;
		dirPath = parsingPath;
	}

	public void ParseJavaCode() {

		JavaIdentifier.SetPredefinedClasses();
		JavaIdentifier.SetPredefinedMethods();
		IterateOnProjectDirectory(projectDirectoryPath);
		ProcessAllClasses();
		try {
			CreateExcelMappingSheet();
		} catch (IOException e) {
			System.out.print(e);
		}
		System.out.println("Done");
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
			} catch (Exception e) {
				System.out.println(file + " : " + e);
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

	protected void ProcessAllClasses() {
		for (ClassInfo classInfo : classes) {
			//System.out.println("Processing  " + classInfo.getName());
			javaIdentifier = new JavaIdentifier();
			try {
				TokenFileWriter tokensFile = new TokenFileWriter(GetFilePath(classInfo.getName(), classInfo.getPath()));
				Optional<TokenRange> tokens = classInfo.getTokens();
				tokens.get().forEach(t -> TokenizeClass(classInfo, t, tokensFile));
				tokensFile.CloseFile();
			} catch (Exception e) {
				System.out.println(classInfo.getPath() + " : " + e);
			}
		}
	}

	private void TokenizeClass(ClassInfo classInfo, JavaToken t, TokenFileWriter tokensFile) {

		ClassInfo innerClass = isInnerClassToken(classInfo.getInnerClasses(), t);

		if (innerClass != null) {
			TokenizeCode(innerClass, t, tokensFile);
		} else {
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
			tokensFile.WriteToFile(javaIdentifier.GetIdentifierToken(token, classInfo));

		} else if (token.getCategory().isKeyword()) {
			tokensFile.WriteToFile(javaKeyword.javaKeywordsDict.get(token.getText()));
		} else if (token.getCategory().isLiteral()) {
			tokensFile.WriteToFile(javaLiteral.GetLiteral(token));

		} else if (token.getCategory().isSeparator()) {
			tokensFile.WriteToFile(javaSeparator.javaSeparatorsDict.get(token.getText()));

		} else if (token.getCategory().isOperator()) {
			tokensFile.WriteToFile(javaOperator.javaOperatorsDict.get(token.getText()));
		}
	}

	private String GetFilePath(String string, String absPath) {
		String parent = Paths.get(absPath).getParent().getFileName().toString();
		String filePath = dirPath + (parent + "_" + string + ".txt");
		excelData.add(new Object[] { absPath, filePath });
		return filePath;
	}

	private void CreateExcelMappingSheet() throws IOException {
		XSSFRow row;
		int rowid = 0;
		for (Object[] rowItem : excelData) {
			row = spreadsheet.createRow(rowid++);
			int cellId = 0;
			for (Object obj : rowItem) {
				Cell cell = row.createCell(cellId++);
				cell.setCellValue((String) obj);
			}
		}

		FileOutputStream out;
		out = new FileOutputStream(new File(dirPath + "Mapping.xlsx"));
		workbook.write(out);
		out.close();
	}
}

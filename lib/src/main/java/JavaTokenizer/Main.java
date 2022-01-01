package JavaTokenizer;

import java.io.File;
import java.io.FileReader;
import java.io.StreamTokenizer;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.type.Type;

public class Main {

	public static void main(String[] args) {
		
		try {
			
			File projectDir = new File("D:\\Master\\Thesis\\RDT\\C_Data\\Projects\\jasml-0.1");
			ClassParser parser = new ClassParser(projectDir,"D:\\Master\\Thesis\\RDT\\path\\");
			parser.ParseJavaCode();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}

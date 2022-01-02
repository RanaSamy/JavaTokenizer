package JavaTokenizer;

import java.io.File;

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

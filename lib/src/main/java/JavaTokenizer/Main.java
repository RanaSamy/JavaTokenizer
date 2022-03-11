package JavaTokenizer;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		
		try {
			
			File projectDir = new File("D:\\Master\\Thesis\\RDT\\C_Data\\Projects\\xmojo-5.0.0");
			ClassParser parser = new ClassParser(projectDir,"D:\\Master\\Thesis\\CodeSmellsDetector\\DL_Approach\\Data\\xmojo-5.0.0\\");
			parser.ParseJavaCode();
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
}

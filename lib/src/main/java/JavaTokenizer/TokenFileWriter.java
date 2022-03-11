package JavaTokenizer;

import java.io.FileWriter;
import java.io.IOException;

public class TokenFileWriter {

	private FileWriter fileWriter;

	public TokenFileWriter(String fileName) {
		try {
			fileWriter = new FileWriter(fileName);

		} catch (Exception e) {
			System.out.println("An error occurred for file: "+fileName);
			e.printStackTrace();
		}
	}

	public void WriteToFile(Integer integer) {
		try {
			fileWriter.write(integer+" ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void WriteLineToFile(String line) {
		try {
			fileWriter.write(line+"\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void CloseFile() {
		try {
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

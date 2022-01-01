package JavaTokenizer;

import java.util.Hashtable;

public class JavaSeparator {
	
	public static Hashtable<String, Integer> javaSeparatorsDict = new Hashtable<String, Integer>();
	
	public JavaSeparator() {
		javaSeparatorsDict.put("@", Constants.Separator_Token + 1);
		javaSeparatorsDict.put("(", Constants.Separator_Token + 2);
		javaSeparatorsDict.put(")", Constants.Separator_Token + 3);
		javaSeparatorsDict.put("{", Constants.Separator_Token + 4);
		javaSeparatorsDict.put("}", Constants.Separator_Token + 5);
		javaSeparatorsDict.put(";", Constants.Separator_Token + 6);
		javaSeparatorsDict.put("[", Constants.Separator_Token + 7);
		javaSeparatorsDict.put("]", Constants.Separator_Token + 8);
		javaSeparatorsDict.put(",", Constants.Separator_Token + 9);
		javaSeparatorsDict.put(".", Constants.Separator_Token + 10);
		javaSeparatorsDict.put(":", Constants.Separator_Token + 11);
	}

}

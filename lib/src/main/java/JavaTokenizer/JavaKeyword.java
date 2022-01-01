package JavaTokenizer;

import java.util.Hashtable;

public class JavaKeyword {

	public static Hashtable<String, Integer> javaKeywordsDict = new Hashtable<String, Integer>();

	public JavaKeyword() {
		javaKeywordsDict.put("abstract", Constants.Keyword_Token + 1);
		javaKeywordsDict.put("assert", Constants.Keyword_Token + 2);
		javaKeywordsDict.put("boolean", Constants.Keyword_Token + 3);
		javaKeywordsDict.put("break", Constants.Keyword_Token + 4);
		javaKeywordsDict.put("byte", Constants.Keyword_Token + 5);
		javaKeywordsDict.put("case", Constants.Keyword_Token + 6);
		javaKeywordsDict.put("catch", Constants.Keyword_Token + 7);
		javaKeywordsDict.put("char", Constants.Keyword_Token + 8);
		javaKeywordsDict.put("class", Constants.Keyword_Token + 9);
		javaKeywordsDict.put("const", Constants.Keyword_Token + 10);
		javaKeywordsDict.put("continue", Constants.Keyword_Token + 11);
		javaKeywordsDict.put("default", Constants.Keyword_Token + 12);
		javaKeywordsDict.put("do", Constants.Keyword_Token + 13);
		javaKeywordsDict.put("double", Constants.Keyword_Token + 14);
		javaKeywordsDict.put("else", Constants.Keyword_Token + 15);
		javaKeywordsDict.put("enum", Constants.Keyword_Token + 16);
		javaKeywordsDict.put("extends", Constants.Keyword_Token + 17);
		javaKeywordsDict.put("final", Constants.Keyword_Token + 18);
		javaKeywordsDict.put("finally", Constants.Keyword_Token + 19);
		javaKeywordsDict.put("float", Constants.Keyword_Token + 20);
		javaKeywordsDict.put("for", Constants.Keyword_Token + 21);
		javaKeywordsDict.put("goto", Constants.Keyword_Token + 22);
		javaKeywordsDict.put("if", Constants.Keyword_Token + 23);
		javaKeywordsDict.put("implements", Constants.Keyword_Token + 24);
		javaKeywordsDict.put("import", Constants.Keyword_Token + 25);
		javaKeywordsDict.put("instanceof", Constants.Keyword_Token + 26);
		javaKeywordsDict.put("int", Constants.Keyword_Token + 27);
		javaKeywordsDict.put("interface", Constants.Keyword_Token + 28);
		javaKeywordsDict.put("long", Constants.Keyword_Token + 29);
		javaKeywordsDict.put("native", Constants.Keyword_Token + 30);
		javaKeywordsDict.put("new", Constants.Keyword_Token + 31);
		javaKeywordsDict.put("package", Constants.Keyword_Token + 32);
		javaKeywordsDict.put("private", Constants.Keyword_Token + 33);
		javaKeywordsDict.put("protected", Constants.Keyword_Token + 34);
		javaKeywordsDict.put("public", Constants.Keyword_Token + 35);
		javaKeywordsDict.put("return", Constants.Keyword_Token + 36);
		javaKeywordsDict.put("short", Constants.Keyword_Token + 37);
		javaKeywordsDict.put("static", Constants.Keyword_Token + 38);
		javaKeywordsDict.put("strictfp", Constants.Keyword_Token + 39);
		javaKeywordsDict.put("super", Constants.Keyword_Token + 40);
		javaKeywordsDict.put("switch", Constants.Keyword_Token + 41);
		javaKeywordsDict.put("synchronized", Constants.Keyword_Token + 42);
		javaKeywordsDict.put("this", Constants.Keyword_Token + 43);
		javaKeywordsDict.put("throw", Constants.Keyword_Token + 44);
		javaKeywordsDict.put("throws", Constants.Keyword_Token + 45);
		javaKeywordsDict.put("transient", Constants.Keyword_Token + 46);
		javaKeywordsDict.put("try", Constants.Keyword_Token + 47);
		javaKeywordsDict.put("void", Constants.Keyword_Token + 48);
		javaKeywordsDict.put("volatile", Constants.Keyword_Token + 49);
		javaKeywordsDict.put("while", Constants.Keyword_Token + 50);

	}
}

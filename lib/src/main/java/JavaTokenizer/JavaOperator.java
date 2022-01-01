package JavaTokenizer;

import java.util.Hashtable;

public class JavaOperator {

	public static Hashtable<String, Integer> javaOperatorsDict = new Hashtable<String, Integer>();

	public JavaOperator() {
		javaOperatorsDict.put("!", Constants.Operator_Token + 1);
		javaOperatorsDict.put("!=", Constants.Operator_Token + 2);
		javaOperatorsDict.put("%", Constants.Operator_Token + 3);
		javaOperatorsDict.put("%=", Constants.Operator_Token + 4);
		javaOperatorsDict.put("&", Constants.Operator_Token + 5);
		javaOperatorsDict.put("&&", Constants.Operator_Token + 6);
		javaOperatorsDict.put("&=", Constants.Operator_Token + 7);
		javaOperatorsDict.put("*", Constants.Operator_Token + 8);
		javaOperatorsDict.put("*=", Constants.Operator_Token + 9);
		javaOperatorsDict.put("+", Constants.Operator_Token + 10);
		javaOperatorsDict.put("+=", Constants.Operator_Token + 11);
		javaOperatorsDict.put("++", Constants.Operator_Token + 12);
		javaOperatorsDict.put("-", Constants.Operator_Token + 13);
		javaOperatorsDict.put("-=", Constants.Operator_Token + 14);
		javaOperatorsDict.put("--", Constants.Operator_Token + 15);
		javaOperatorsDict.put("|", Constants.Operator_Token + 16);
		javaOperatorsDict.put("|=", Constants.Operator_Token + 17);
		javaOperatorsDict.put("||", Constants.Operator_Token + 18);
		javaOperatorsDict.put("<", Constants.Operator_Token + 19);
		javaOperatorsDict.put("<<", Constants.Operator_Token + 20);
		javaOperatorsDict.put("<<=", Constants.Operator_Token + 21);
		javaOperatorsDict.put("<=", Constants.Operator_Token + 22);
		javaOperatorsDict.put(">", Constants.Operator_Token + 23);
		javaOperatorsDict.put(">>", Constants.Operator_Token + 24);
		javaOperatorsDict.put(">>=", Constants.Operator_Token + 25);
		javaOperatorsDict.put(">=", Constants.Operator_Token + 26);
		javaOperatorsDict.put("^", Constants.Operator_Token + 27);
		javaOperatorsDict.put("^=", Constants.Operator_Token + 28);
		javaOperatorsDict.put("/", Constants.Operator_Token + 29);
		javaOperatorsDict.put("/=", Constants.Operator_Token + 30);
		javaOperatorsDict.put("=", Constants.Operator_Token + 31);
		javaOperatorsDict.put("==", Constants.Operator_Token + 32);
		javaOperatorsDict.put("~", Constants.Operator_Token + 33);
		javaOperatorsDict.put("?", Constants.Operator_Token + 34);

	}
}

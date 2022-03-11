package JavaTokenizer;

public class Constants {

	
	public static int Separator_Token= 0;	// from 0 to 100
	public static int Operator_Token= 100; // from 100 to 300
	public static int Keyword_Token = 300; // from 300 to 1000
	public static int Class_Token=1200; // from 1200 to 3000
	public static int Method_Token=3000; // from 3000 to 5000
	public static int Field_Token=5000; // from 5000 to 7000
	public static int Identifier_Token=7000; // from 4000 and Upwards
	public static int Unidentified_Token=0;
	
	public static int classIterator = 0;
	public static int methodIterator = 0;
	public static int fieldIterator = 0;
	
	public static class LiteralsConstants{
		// from 1000 to 1100
		public static int Integer_Token=1000;
		public static int Floating_Token=1010;
		public static int Char_Token=1020;
		public static int String_Token=1030;
		public static int Boolean_Token=1040;
		public static int Null_Token=1050;
		
	}
}

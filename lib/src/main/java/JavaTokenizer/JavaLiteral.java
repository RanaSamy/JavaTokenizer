package JavaTokenizer;

import com.github.javaparser.JavaToken;
import com.github.javaparser.JavaToken.Kind;

public class JavaLiteral {
	
	public static int GetLiteral(JavaToken token) {
		
		if(token.getKind()== Kind.INTEGER_LITERAL.getKind() ||
				token.getKind()== Kind.LONG_LITERAL.getKind() ||
				token.getKind() == Kind.HEX_LITERAL.getKind() || 
				token.getKind() == Kind.OCTAL_LITERAL.getKind() ||
				token.getKind() == Kind.BINARY_LITERAL.getKind()) {
			return Constants.LiteralsConstants.Integer_Token;
		}
		else if(token.getKind() == Kind.DECIMAL_LITERAL.getKind() ||
				token.getKind() == Kind.FLOATING_POINT_LITERAL.getKind() ||
				token.getKind() == Kind.DECIMAL_FLOATING_POINT_LITERAL.getKind() ||
				token.getKind() == Kind.DECIMAL_EXPONENT.getKind() ||
				token.getKind() == Kind.HEXADECIMAL_FLOATING_POINT_LITERAL.getKind() ||
				token.getKind() == Kind.HEXADECIMAL_EXPONENT.getKind()) {
			return Constants.LiteralsConstants.Floating_Token;			
		}
		else if(token.getKind() == Kind.CHARACTER_LITERAL.getKind()) {
			return Constants.LiteralsConstants.Char_Token;			
		}
		else if(token.getKind() == Kind.STRING_LITERAL.getKind() ||
				token.getKind() == Kind.TEXT_BLOCK_LITERAL.getKind()) {
			return Constants.LiteralsConstants.String_Token;			
		}
		else if(token.getKind() == Kind.TRUE.getKind() ||
				token.getKind() == Kind.FALSE.getKind()) {
			return Constants.LiteralsConstants.Boolean_Token;			
		}
		else if(token.getKind() == Kind.NULL.getKind()) {
			return Constants.LiteralsConstants.Null_Token;			
		}
		
		return Constants.Unidentified_Token;
	}

}

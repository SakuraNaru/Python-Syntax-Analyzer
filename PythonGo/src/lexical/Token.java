package lexical;
import utils.Enum.*;
public class Token {
	public int linenum;
	public Lex lex;
	public String context;
	public Token(int a,Lex l,String c){
		linenum=a;
		lex=l;
		context=c;
	}
}

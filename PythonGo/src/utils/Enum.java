package utils;

public class Enum {
	public enum Lex{VAR,DATA,ADD,SUB,MUL,DIV,EQL,BIG,LIT,   //commaΪ������  
		LSPAREN,LMPAREN,LLPAREN,RSPAREN,RMPAREN,RLPAREN,       //colonΪ��ð�� 
		COMMA,COLON,SQUOTE,DQUOTE,POUND,BLANK,ERROR,EOL,           //poundΪ#ע��
		IF,ELSE,ELIF,FOR,WHILE,IMPORT,INPUT,DEF,PRINT,RETURN,CONTINUE,BREAK,
		Value,String,Tuple,List,Dict,CommonSentence,JudgeSentence,STRING}  
	public enum VN{CommonSentence}
	public enum State{S0,S1,S2,S3,S4,S5,S6,S7}
	public enum CharGroup{LETTER,DATA,BOARDER,POUND,OTHER}
}
 
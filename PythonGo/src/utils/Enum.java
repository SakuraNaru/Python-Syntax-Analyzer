package utils;

public class Enum {
	public enum Lex{VAR,DATA,ADD,SUB,MUL,DIV,EQL,BIG,LIT,   //commaÎª£¬¶ººÅ  
		LSPAREN,LMPAREN,LLPAREN,RSPAREN,RMPAREN,RLPAREN,       //colonÎª£ºÃ°ºÅ 
		COMMA,COLON,SQUOTE,DQUOTE,POUND,BLANK,ERROR,EOL,           //poundÎª#×¢ÊÍ
		IF,ELSE,ELIF,FOR,WHILE,IMPORT,INPUT,DEF,PRINT,RETURN,CONTINUE,BREAK,
		Value,String,Tuple,List,Dict,CommonSentence,JudgeSentence,STRING}  
	public enum VN{CommonSentence}
	public enum State{S0,S1,S2,S3,S4,S5,S6,S7}
	public enum CharGroup{LETTER,DATA,BOARDER,POUND,OTHER}
}
 
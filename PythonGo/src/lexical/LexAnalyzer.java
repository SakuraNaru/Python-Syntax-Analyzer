package lexical;
import java.util.ArrayList;
import java.util.List;
import readfile.*;
import syntax.SynAnalyzer;
import utils.Enum.CharGroup;
import utils.Enum.Lex;
import utils.Enum.State;
public class LexAnalyzer {
	private List<String> line;
	private List<Token> token;
	private State state;
	private int begin,end,i;
	public LexAnalyzer()
	{
		line=new Read().getLine();
		token=new ArrayList <Token>();
		for (int i = 0; i < line.size(); i++) {
			analyzer(i+1, line.get(i));
		}
		for (int i = 0; i < token.size(); i++) {
			System.out.println(token.get(i).linenum+"  "+token.get(i).lex+"  "+token.get(i).context);
		}
		new SynAnalyzer(token);
	}
	public void analyzer(int line,String s)
	{
		state=State.S0;
		begin=0;
		end=0;
		i=0;
		while(i<=s.length())
		{						
			if(i!=s.length())
			{
				stateMachine(state, judgeChar(s.charAt(i)));
			}
			else
			{
				if(state==State.S1)
					state=State.S2;
				else if(state==State.S3)
					state=State.S4;
			}
			//System.out.println(s.charAt(i));
			if(state==State.S2)
			{
				i--;
				end=i;
				//System.out.println(s.substring(begin,end+1));		
				token.add(new Token(line,judgeRetain(s.substring(begin,end+1)),s.substring(begin,end+1)));
				//存储
				begin=i+1;
				state=State.S0;
			}
			else if(state==State.S4)
			{
				i--;
				end=i;
				token.add(new Token(line,Lex.DATA,s.substring(begin,end+1)));
				//存储
				begin=i+1;
				state=State.S0;
			}
			else if(state==State.S5)
			{
				end=i;
				token.add(new Token(line,judgeBoarder(s.charAt(i)),s.substring(begin,end+1)));
				//存储
				begin=i+1;
				state=State.S0;
			}
			else if(state==State.S6)
			{
				end=s.length();
				token.add(new Token(line,Lex.POUND,s.substring(begin,end)));
				//存储
				i=s.length()-1;
				state=State.S0;
			}
			else if(state==State.S7)
			{
				end=i;
				token.add(new Token(line,Lex.ERROR,s.substring(begin,end+1)));
				begin=i+1;
				state=State.S0;
			}
			i++;
		}
		token.add(new Token(line,Lex.EOL," "));
	}
	public void stateMachine(State s,CharGroup cg)
	{
		//System.out.println(s+""+cg);
		switch(s)
		{
		case S0:
			switch (cg) {
			case LETTER:
				state=State.S1;
				break;
			case DATA:
				state=State.S3;
				break;
			case BOARDER:
				state=State.S5;
				break;
			case POUND:
				state=State.S6;
				break;
			case OTHER:
				state=State.S7;
				break;
			}
			break;
		case S1:
			switch (cg) {
			case LETTER:
				state=State.S1;
				break;
			case DATA:
				state=State.S1;
				break;
			default:
				state=State.S2;
				break;
			}
			break; 
		case S2:
			break;//回退一个char
		case S3:		
			switch(cg)
			{
			case DATA:
				state=State.S3;
				break;
			default:
				state=State.S4;	
				break;
			}
			break;
		case S4:
			break;//回退一个char
		case S5:
			break;//回退一个char
		case S6:			
			break;//回退一个char
		case S7:
			break;//出错
		}
	}
	public CharGroup judgeChar(char a){
		if((a>=65&&a<=90)||(a>=97&&a<=122))
			return CharGroup.LETTER;
		else if(a>=48&&a<=57)
			return CharGroup.DATA;
		else if(a==35)
			return CharGroup.POUND;
		else if(a==32||a==34||(a>=39&&a<=47)||(a>=58&&a<=62)||a==91||a==93||a==123||a==125)
			return CharGroup.BOARDER;
		else 
			return CharGroup.OTHER;
	}
	public Lex judgeBoarder(char a)
	{
		switch(a)
		{
			case 32:
				return Lex.BLANK;
			case 43:
				return Lex.ADD;
			case 45:
				return Lex.SUB;
			case 42:
				return Lex.MUL;
			case 47:
				return Lex.DIV;
			case 61:
				return Lex.EQL;
			case 62:
				return Lex.BIG;
			case 60:
				return Lex.LIT;
			case 40:
				return Lex.LSPAREN;
			case 41:
				return Lex.RSPAREN;
			case 91:
				return Lex.LMPAREN;
			case 93:
				return Lex.RMPAREN;
			case 123:
				return Lex.LLPAREN;
			case 125:
				return Lex.RLPAREN;
			case 44:
				return Lex.COMMA;
			case 58:
				return Lex.COLON;
			case 39:
				return Lex.SQUOTE;
			case 34:
				return Lex.DQUOTE;
			case 35:
				return Lex.POUND;
			default:
				return Lex.ERROR;
					
		}
	}
	public Lex judgeRetain(String s)
	{
		if(s.equals("break"))
			return Lex.BREAK;
		else if(s.equals("for"))
			return Lex.FOR;
		else if(s.equals("print"))
			return Lex.PRINT;
		else if(s.equals("continue"))
			return Lex.CONTINUE;
		else if(s.equals("if"))
			return Lex.IF;
		else if(s.equals("return"))
			return Lex.RETURN;
		else if(s.equals("import"))
			return Lex.IMPORT;
		else if(s.equals("input"))
			return Lex.INPUT;
		else if(s.equals("elif"))
			return Lex.ELIF;
		else if(s.equals("while"))
			return Lex.WHILE;
		else if(s.equals("else"))
			return Lex.ELSE;
		else 
			return Lex.VAR;
	}
	
}

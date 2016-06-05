package syntax;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import lexical.Token;
import utils.Enum.Lex;

public class SynMachine {
	public int state;
	public int route ;
	public DefaultMutableTreeNode dmt;
	public List<Token> list;
	public List<Token> CommonList;
	public List<DefaultMutableTreeNode> NodeList;
	public int CommonNum;
	public int CommonType;
	public boolean mElse;
	public SynMachine()
	{
		state=0;
		route=0;
		CommonNum=0;
		CommonType=0;
		mElse=false;
		list=new ArrayList<Token>();
		CommonList=new ArrayList<Token>();
		NodeList=new ArrayList<DefaultMutableTreeNode>();
	}
	public void analyzer(Token a)
	{
		list.add(a);
		switch(state)
		{
		case 0:
			switch(a.lex)
			{
			case VAR:
				state=1;
				route=1;
				break;
			case SQUOTE:
				state=1;
				route=2;
				break;
			case DQUOTE:
				state=1;
				route=3;
				break;
			case DEF:
				state=1;
				route=4;
				break;
			case LSPAREN:
				state=6;
				route=5;
				break;
			case LMPAREN:
				state=6;
				route=6;
				break;
			case LLPAREN:
				state=6;
				route=7;
				break;
			case WHILE:
				state=1;
				route=8;
				break;
			case IF:
				state=1;
				route=9;
				break;
			case ELIF:
				state=1;
				route=10;
				break;
				
			case PRINT:
				state=1;
				route=12;
				break;
			case ELSE:
				state=1;
				route=13;
				break;
			default:
				state=5;
				break;
			}
			break;
		case 1:
			switch(route)
			{
			case 1:
				switch(a.lex)
				{
				case EQL:
					state=2;
					break;
				case BIG:
					route=11;
					state=2;
					break;
				case LIT:
					route=11;
					state=2;
					break;
				case ADD:
					route=11;
					state=2;
					break;
				case SUB:
					route=11;
					state=2;
					break;
				case MUL:
					route=11;
					state=2;
					break;
				case DIV:
					route=11;
					state=2;
					break;
				case Tuple:
					route=14;
					state=3;
					break;
				case LSPAREN:
					route=14;
					state=4;
					break;
				default:
					state=5;
					break;
				}
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 4:
				if(a.lex==Lex.VAR)
				{
					state=2;
				}
				else
					state=5;//�����������Ǹ�����
				break;
			case 5:
				switch(a.lex)
				{
				case VAR:
					state=6;
					break;
				case CommonSentence:
					state=4;
					break;
				default:
					state=5;
					break;
				}
			case 6:
				switch(a.lex)
				{
				case VAR:
					state=2;
					break;
				case CommonSentence:
					state=4;
					break;
				default:
					state=5;
					break;
				}
			case 7:
				switch(a.lex)
				{
				case VAR:
					state=2;
					break;
				case CommonSentence:
					state=4;
					break;
				default:
					state=5;
					break;
				}
			case 8:
				switch(a.lex)
				{
				case JudgeSentence:
					state=2;
					break;
				case VAR:
					state=4;
					break;
				case DATA:
					state=4;
					break;
				default:
					state=5;//�ж����
					break;
				}
				break;
			case 9:
				switch(a.lex)
				{
				case JudgeSentence:
					state=2;
					break;
				case VAR:
					state=4;
					break;
				case DATA:
					state=4;
					break;
				default:
					state=5;//�ж����
					break;
				}
				break;
			case 10:
				switch(a.lex)
				{
				case JudgeSentence:
					state=2;
					break;
				case VAR:
					state=4;
					break;
				case DATA:
					state=4;
					break;
				default:
					state=5;//�ж����
					break;
				}
				break;
			case 12:
				switch(a.lex)
				{
				case VAR:
					state=3;
					break;
				case DATA:
					state=3;
					break;
				case LSPAREN:
					state=4;//���еڶ��ι�Լ
					break;
				case LMPAREN:
					state=4;//���еڶ��ι�Լ
					break;
				case LLPAREN:
					state=4;//���еڶ��ι�Լ
					break;
				case SQUOTE:
					state=4;//���еڶ��ι�Լ
					break;
				case DQUOTE:
					state=4;//���еڶ��ι�Լ
					break;
				default:
					state=5;//����״̬�Ⱥ�����ֵ����
				}
				break;
			case 13:
				if(a.lex==Lex.COLON)
					state=3;
				else if(a.lex==Lex.EOL)
				{
					state=5;
				}
				else
				{
					state=5;
					mElse=true;
				}
				break;
			}
			break;
		case 2:
			switch(route)
			{
			case 1:
				switch(a.lex)
				{
				case VAR:
					state=3;//���ɽڵ�
					break;
				case DATA:
					state=3;//���ɽڵ�
					break;
				case LSPAREN:
					state=4;//���еڶ��ι�Լ
					break;
				case LMPAREN:
					state=4;//���еڶ��ι�Լ
					break;
				case LLPAREN:
					state=4;//���еڶ��ι�Լ
					break;
				case SQUOTE:
					state=4;//���еڶ��ι�Լ
					break;
				case DQUOTE:
					state=4;//���еڶ��ι�Լ
					break;
				default:
					state=5;//����״̬�Ⱥ�����ֵ����
				}
				break;
			case 2:
				switch(a.lex)
				{
				case SQUOTE:
					state=3;//���ɽڵ�
					break;
				default:
					state=5;//��ʧ���� ����	
				}
				break;
			case 3:
				switch(a.lex)
				{
				case DQUOTE:
					state=3;//���ɽڵ�
					break;
				default:
					state=5;//��ʧ���� ����	
				}
				break;
			case 4:
				switch(a.lex)
				{
				case Tuple:
					state=3;//���ɽڵ�
					break;
				case LSPAREN:
					state=4;//���еڶ��ι�Լ
					break;
				default:
					state=5;//û���β�
				}
				break;
			case 5:
				if(a.lex==Lex.RSPAREN)
					state=3;
				else
					state=5;//��ʧ������
				break;
			case 6:
				if(a.lex==Lex.RMPAREN)
					state=3;
				else
					state=5;//��ʧ������
				break;
			case 7:
				if(a.lex==Lex.RLPAREN)
					state=3;
				else
					state=5;//��ʧ������
				break;
			case 8:
				if(a.lex==Lex.COLON)
					state=3;
				else
					state=5;//��ʧð��
				break;
			case 9:
				if(a.lex==Lex.COLON)
					state=3;
				else
					state=5;//��ʧð��
				break;
			case 10:
				if(a.lex==Lex.COLON)
					state=3;
				else
					state=5;//��ʧð��
				break;
			case 11:
				switch(a.lex)
				{
				case VAR:
					state=3;
					break;
				case DATA:
					state=3;
					break;
				default:
					state=5;
				}
				break;
			}
			break;
		case 3:
			if(a.lex==Lex.EOL)
			{
				state=9;
			}
			break;
		case 4:
			switch(route)
			{
			case 1:
				switch(a.lex)
				{
				case String:
					state=3;
					break;
				case List:
					state=3;
					break;
				case Tuple:
					state=3;
					break;
				case Dict:
					state=3;
					break;
				case EOL:
					state=3;
					break;
				case VAR:
					break;
				case DATA:
					break;
				default:
					state=5;//�Ⱥ��Ҷ˲���
					break;
				}
				break;
			case 4:
				if(a.lex==Lex.CommonSentence)
					state=3;
				else
					state=5;//�ββ���
				break;
			case 5:
				switch(a.lex)
				{
				case List:
					state=3;
					break;
				case Tuple:
					state=3;
					break;
				case Dict:
					state=3;
					break;
				default:
					state=5;//�����м�Ԫ�ش���	
				}
				break;
			case 6:
				switch(a.lex)
				{
				case List:
					state=3;
					break;
				case Tuple:
					state=3;
					break;
				case Dict:
					state=3;
					break;
				default:
					state=5;//�����м�Ԫ�ش���	
				}
				break;
			case 7:
				switch(a.lex)
				{
				case List:
					state=3;
					break;
				case Tuple:
					state=3;
					break;
				case Dict:
					state=3;
					break;
				default:
					state=5;//�����м�Ԫ�ش���	
				}
				break;
			case 8:
				if(a.lex==Lex.JudgeSentence)
					state=2;
				else
					state=5;//while�м�������ж����
				break;
			case 9:
				if(a.lex==Lex.JudgeSentence)
					state=2;
				else
					state=5;//while�м�������ж����
				break;
			case 10:
				if(a.lex==Lex.JudgeSentence)
					state=2;
				else
					state=5;//while�м�������ж����
				break;
			case 12:
				switch(a.lex)
				{
				case String:
					state=3;
					break;
				case LSPAREN:
					state=3;
					break;
				case LMPAREN:
					state=3;
					break;
				case LLPAREN:
					state=3;
					break;
				case EOL:
					state=3;
					break;
				default:
					state=5;//����ֵ����
					break;
				}
				break;
			case 14:
				if(a.lex==Lex.Tuple)
					state=3;
				else
					state=5;
			}
			break;
		case 5:
			break;
		case 6:
			recComSten(a);
			break;
		}
		
	}
	public void recComSten(Token a)
	{
		switch(CommonType)
		{
		case 0:
			
			switch(a.lex)
			{
			case VAR:
				CommonType=1;
				CommonNum++;
				NodeList.add(new DefaultMutableTreeNode("ExpK VarK "+a.context));
				CommonList.add(a);
				break;
			case DATA:
				CommonNum++;
				NodeList.add(new DefaultMutableTreeNode("ExpK DataK "+a.context));
				CommonType=1;
				CommonList.add(a);
				break;
			default:
				state=5;
			}
			break;
		case 1:
			switch(a.lex)
			{
			case COMMA:
				CommonType=0;
				break;
			case RSPAREN:
				state=3;
				break;
			case RMPAREN:
				state=3;
				break;
			case RLPAREN:
				state=3;
				break;
			default:
				state=5;	
			}
		}
	}
	public void createTree()
	{
			switch(route)
			{
			case 1:
				dmt=new DefaultMutableTreeNode("StmtK AssignK");
				if(list.get(0).lex==Lex.VAR)
					dmt.add(new DefaultMutableTreeNode("ExpK VarK "+list.get(0).context));
				else
					dmt.add(new DefaultMutableTreeNode("ExpK DataK "+list.get(0).context));

				//�������
				break;
			case 4:
				dmt=new DefaultMutableTreeNode("DecK "+list.get(1).lex);
				//����β��б�
				break;
			case 5:
				dmt=new DefaultMutableTreeNode("ExpK TupleK");
				//��Ӳ����б�
				break;
			case 6:
				dmt=new DefaultMutableTreeNode("ExpK ListK");
				//��Ӳ����б�
				break;
			case 7:
				dmt=new DefaultMutableTreeNode("ExpK DictK");
				//��Ӳ����б�
				break;
			case 8:
				dmt=new DefaultMutableTreeNode("StmtK WhileK");
				//dmt.add(new DefaultMutableTreeNode("ExpK Opk"+list.get(1).lex));
				//��Ӳ����������ߵ�Ԫ��
				//System.out.println("haha");
				break;
			case 9:
				dmt=new DefaultMutableTreeNode("StmtK IfK");
				//dmt.add(new DefaultMutableTreeNode("ExpK Opk"+list.get(1).lex));
				//��Ӳ����������ߵ�Ԫ��
				break;
			case 10:
				dmt=new DefaultMutableTreeNode("StmtK ElifK");
				//��Ӳ����������ߵ�Ԫ��
				break;
			case 11:
				dmt=new DefaultMutableTreeNode("ExpK OpK "+list.get(1).context);
				if(list.get(0).lex==Lex.VAR)
					dmt.add(new DefaultMutableTreeNode("ExpK VarK "+list.get(0).context));
				else
					dmt.add(new DefaultMutableTreeNode("ExpK DataK "+list.get(0).context));
				if(list.get(2).lex==Lex.VAR)
					dmt.add(new DefaultMutableTreeNode("ExpK VarK "+list.get(2).context));
				else
					dmt.add(new DefaultMutableTreeNode("ExpK DataK "+list.get(2).context));

				//dmt.add(new DefaultMutableTreeNode("ExpK "+list.get(0).lex));
				//dmt.add(new DefaultMutableTreeNode("ExpK "+list.get(2).lex));
				//��Ӳ����������ߵ�Ԫ��
				break;
			case 12:
				dmt=new DefaultMutableTreeNode("StmtK PrintK");
				//��ӷ���ֵ
				break;
			case 13:
				dmt=new DefaultMutableTreeNode("StmtK ElseK");
				//�������
				break;
			case 14:
				dmt=new DefaultMutableTreeNode("StmtK CallK"+list.get(0).lex);
				//�������
				break;
			}
	}
	
}

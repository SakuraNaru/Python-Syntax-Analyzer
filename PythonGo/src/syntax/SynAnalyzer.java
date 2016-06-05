package syntax;

import java.util.List;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import lexical.Token;
import utils.Enum.Lex;

public class SynAnalyzer {
	private List<Token> token;
	private SynMachine syn;
	private JTree tree;

	private Stack<SynMachine> machineStack;
	private SynMachine machineTmp;
	private int line_now=0;
	private DefaultMutableTreeNode mDmt;
	private String sumString;
	private boolean reduction;
	private boolean ifIndent;
	private Stack<Integer> indent;
	private Stack<DefaultMutableTreeNode> tmpNode;
	
	//private DefaultMutableTreeNode mDmtTmp;
	public SynAnalyzer(List<Token> token)
	{
		this.token=token;
		reduction=false;
		ifIndent=false;
		indent=new Stack<Integer>();
		mDmt=new DefaultMutableTreeNode("Program");
		machineStack=new Stack<SynMachine>();
		tmpNode=new Stack<DefaultMutableTreeNode>();
		//mDmtTmp=mDmt;
		tree=new JTree(mDmt);
		analyze();
		JFrame frame =new JFrame();
		frame.add(tree);
		frame.setSize(500, 500);
		frame.setVisible(true);
	}
	public int addString1(int start)
	{
		int length=0;
		sumString="";
		while(token.get(start).lex!=Lex.SQUOTE)
		{
			if(token.get(start).lex==Lex.EOL)
			{
				return -1;
			}
			sumString+=token.get(start).context;
			start++;
			length++;
		}
		return length;
				
	}
	public int addString2(int start)
	{
		int length=0;
		sumString="";
		while(token.get(start).lex!=Lex.DQUOTE)
		{
			if(token.get(start).lex==Lex.EOL)
			{
				return -1;
			}
			sumString+=token.get(start).context;
			start++;
			length++;
		}
		return length;
				
	}
	public int sumBlank(int i)
	{
		int sum=0;	
		while(token.get(i).lex==Lex.BLANK)
		{
			sum++;
			i++;
		}
		return sum;
	}
	public void analyze()
	{
		for (int i = 0; i < token.size(); i++) {
			if(line_now!=token.get(i).linenum)
			{
				line_now=token.get(i).linenum;
				syn=new SynMachine();
				if(ifIndent)
				{
					int tmp=sumBlank(i);
					i+=tmp;
					if(tmp>0)
					{
						if(indent.isEmpty())
						{
							indent.push(tmp);
							syn=new SynMachine();
						}
						else
						{
							if(indent.pop()!=tmp)
							{
								syn.state=5;
								syn.route=16;
							}
							else
							{
								indent.push(tmp);
								syn=new SynMachine();
							}
						}
					}
					else
					{
						if(indent.isEmpty())
						{
							syn.state=5;
							syn.route=16;
						}
						else
						{
							if(indent.pop()==sumBlank(i))
							{
								indent.clear();
								ifIndent=false;
								syn=new SynMachine();
							}
						}
						
					}
					
				}
				
			}	
			if(token.get(i).lex==Lex.BLANK)
				continue;
			if(token.get(i).lex==Lex.COLON)
				ifIndent=true;
			syn.analyzer(token.get(i));
			//System.out.println(""+syn.state);
			//System.out.println("state="+syn.state+" route="+syn.route+" lex="+token.get(i).lex);
			if(syn.state==3)
			{
				//生成语法树
				switch(syn.route)
				{
				case 1:
					boolean tmp;
					if(reduction)
					{
						tmp=token.get(i).lex==Lex.EOL;
					}
					else
					{
						tmp=token.get(i+1).lex==Lex.EOL;
					}
					if(tmp)
					{
						syn.createTree();
						if(ifIndent)
						{
							tmpNode.lastElement().add(syn.dmt);
						}
						else
						{
							tmpNode.clear();
							mDmt.add(syn.dmt);
						}
						
						if(reduction)
						{
							
							syn.dmt.add(machineTmp.dmt);
							i--;
						}
						else
						{
							
							if(token.get(i).lex==Lex.VAR)
								syn.dmt.add(new DefaultMutableTreeNode("ExpK VarK "+token.get(i).context));
							else if(token.get(i).lex==Lex.DATA)
								syn.dmt.add(new DefaultMutableTreeNode("ExpK DataK "+token.get(i).context));
						}
						i++;
						reduction=false;
					}
					else
					{
						reduction=true;
						syn.state=4;
						i--;
					}
					
					//添加第二次递归产生的子树
					break;
				case 2:
					syn.dmt=new DefaultMutableTreeNode("ExpK StringK "+sumString);
					machineTmp=syn;
					syn=machineStack.pop();
					break;
				case 3:
					syn.dmt=new DefaultMutableTreeNode("ExpK StringK "+sumString);
					machineTmp=syn;
					syn=machineStack.pop();
					break;
				case 4:
					mDmt.add(syn.dmt);
					break;
				case 5:
					syn.dmt=new DefaultMutableTreeNode("ExpK TupleK");
					for(int j=0;j<syn.CommonNum;j++)
					{
						syn.dmt.add(syn.NodeList.get(j));
					}
					machineTmp=syn;
					syn=machineStack.pop();
					break;
				case 6:
					syn.dmt=new DefaultMutableTreeNode("ExpK ListK");
					for(int j=0;j<syn.CommonNum;j++)
					{
						syn.dmt.add(syn.NodeList.get(j));
					}
					machineTmp=syn;
					syn=machineStack.pop();
					break;
				case 7:
					syn.dmt=new DefaultMutableTreeNode("ExpK DictK");
					for(int j=0;j<syn.CommonNum;j++)
					{
						syn.dmt.add(syn.NodeList.get(j));
					}
					machineTmp=syn;
					syn=machineStack.pop();
					break;
				case 8:
					syn.createTree();
					mDmt.add(syn.dmt);
					tmpNode.push(syn.dmt);
					syn.dmt.add(machineTmp.dmt);
					//添加第二次递归产生的子树
					break;
				case 9:
					syn.createTree();
					mDmt.add(syn.dmt);
					tmpNode.push(syn.dmt);
					syn.dmt.add(machineTmp.dmt);
					break;
				case 10:
					syn.createTree();
					mDmt.add(syn.dmt);
					tmpNode.push(syn.dmt);
					syn.dmt.add(machineTmp.dmt);
					break;
				case 11:
					syn.createTree();
					machineTmp=syn;
					syn=machineStack.pop();
					if(syn.route==8)
					{
						syn.state=2;
					}
					else if(syn.route==9)
					{
						syn.state=2;
					}
					else if(syn.route==10)
					{
						syn.state=2;
					}
					break;
				case 12:
					syn.createTree();
					if(ifIndent)
					{
						tmpNode.lastElement().add(syn.dmt);
					}
					else
					{
						tmpNode.clear();
						mDmt.add(syn.dmt);
					}
					if(reduction)
					{
						syn.dmt.add(machineTmp.dmt);
						reduction=false;
					}
					else
					{
						if(token.get(i).lex==Lex.VAR)
						{
							syn.dmt.add(new DefaultMutableTreeNode("ExpK VarK "+token.get(i).context));
						}
						else if(token.get(i).lex==Lex.DATA)
						{
							syn.dmt.add(new DefaultMutableTreeNode("ExpK DataK "+token.get(i).context));

						}
						else 
							syn.state=5;
						
					}
					break;
				case 13:
					syn.createTree();
					mDmt.add(syn.dmt);
					tmpNode.push(syn.dmt);
					
					break;
				}
			}
			else if(syn.state==4)
			{
				if(syn.route==12)
				{
					reduction=true;
					
				}
				machineStack.push(syn);
				syn=new SynMachine();
				i--;
			}
			else if(syn.state==5)
			{
				switch(syn.route)
				{
				case 1:
					break;
				case 2:
					System.out.println("丢失     '  在第    "+token.get(i).linenum+"  行");
					break;
				case 3:
					System.out.println("丢失     \"  在第    "+token.get(i).linenum+"  行");
					break;
				case 5:
					System.out.println("丢失     )  在第    "+token.get(i).linenum+"  行");
					break;
				case 6:
					System.out.println("丢失     ]  在第    "+token.get(i).linenum+"  行");
					break;
				case 7:
					System.out.println("丢失     }  在第    "+token.get(i).linenum+"  行");
					break;
				case 8:
					System.out.println("丢失     :  在第    "+token.get(i).linenum+"  行");
					break;	
				case 9:
					System.out.println("丢失     :  在第    "+token.get(i).linenum+"  行");
					break;	
				case 10:
					System.out.println("丢失     :  在第    "+token.get(i).linenum+"  行");
					break;	
				case 12:
					System.out.println("返回值错误  在第    "+token.get(i).linenum+"  行");
					break;
				case 13:
					if(syn.mElse)
					{
					System.out.println("Else错误   在第    "+token.get(i).linenum+"  行");
					}
					else
					{
					System.out.println("丢失     :  在第    "+token.get(i).linenum+"  行");
					}
					
					break;
				case 16:
					System.out.println("缩进错误     在第    "+token.get(i).linenum+"  行");
					break;
				}
				i=token.size();
			}
			else if(syn.state==1)
			{
				switch(syn.route)
				{
				case 2:
					int a=addString1(i+1);
					if(a==-1)
					{
						i=token.size()-2;
						syn.state=5;
					}
					else
					{
						i=i+a;
						syn.state=3;
					}
					
					break;
				case 3:
					int b=addString2(i+1);
					if(b==-1)
					{
						i=token.size()-2;
						syn.state=5;
					}
					else
					{
						i=i+b;
						syn.state=3;
					}
					break;
				
				}
			}
			
			
			
			
		}
	}
	
	
}

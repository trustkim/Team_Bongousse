import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Problem41
{
	private final int NUM = 0;
	private final int SUM = 1;
	private final int DIF = 2;
	private final int DIFEE = 2;	// �ǰ��� 3-1 = 2 ���� 3
	private final int DIFER = 3;	// ���� 3-1 = 2���� 1
	private final int AVG = 3;
	private class Node
	{
		int name;		// ���� �̸�(���ڷ� ǥ��)
		Node next;
		Node(int id) { this.name = id; next=null; }
	}
	private HashMap<Integer, Integer> hsm;	// ���� ���� �ĺ� ���� �ؽ���
	private Node[] adjList;					// �׷����� ��������Ʈ�� ǥ��
	private int[] indegree;					// ���� ���� ���̺�
	private int[] outdegree;				// �� ����� ���� ���� ���̺�
	private int[][] regfile;				// �� ������ �Ӽ�, ����� ������ ���̺�
	private int[] visited;					// ���� ���� �˰��� DFS�� �湮 ���� üũ �迭
	private Node R;							// ���� ���� ��� ����Ʈ
	private boolean isVar(String token)	// ������ ���� ��ȯ
	{
		return (token.charAt(0) == '[');
	}
	private boolean isNum(String token)	// token[1]�� ���� ���ڰ� �ƴϸ� ����̹Ƿ� ���� ��ȯ
	{
		return (!(token.charAt(0)>='a' && token.charAt(0)<='z'));
	}
	private int trimVar(String token)	// �������� '[', ']'�� ����
	{
		return (Integer.parseInt(token.substring(1, token.length()-1)));
	}
	private int opCode(String token)
	{
		char op = token.charAt(0);
		switch(op)
		{
			case 's': return SUM;
			case 'd': return DIF;
			case 'a': return AVG;
			default: return NUM;
		}
	}
	private void add(int out, int in) {
		Node r = adjList[hsm.get(out)];
		Node cur = new Node(in);		// �߰��� ���.
		Node p = r;						// �߰��� ��ġ.
		while(p.next!=null) {
			p = p.next;
		}
		p.next = cur;
		// ��������Ʈ�� ��� ���鿡 outdegree�� ���� �� ��. �������� ��� 0�̴�.
		indegree[hsm.get(in)]++;
		outdegree[hsm.get(out)]++;
	}
	/* �������� �˰��� 2 */
	private boolean DFS_TS(int v) {
		boolean isDAG = true;
		visited[hsm.get(v)]++;
		Node x = adjList[hsm.get(v)].next;
		while(x!=null && isDAG) {
			if(visited[hsm.get(x.name)]==0)
				isDAG = DFS_TS(x.name);
			else if(visited[hsm.get(x.name)]==1) {
				return false;	// ���� ���Ľ� �������� �;� �ϴ� ����� outdegree�� 0�� �ƴѰ�� DAG�� �ƴ�.
			}
			x = x.next;
		}
		
		// R�� �տ� �߰�.
		x = new Node(adjList[hsm.get(v)].name);
		x.next = R;
		R = x;
		visited[hsm.get(v)]++;	// DFS�� �Ž��� �ö󰡸鼭 �ι�° üũ.
		return isDAG;
	}
	private boolean topologicalSort2() {
		visited = new int[adjList.length];	// [0]�� ������� ����. DFS ���� �� �ִ� �� ������ üũ��.
		R = null;
		for(int i=1;i<adjList.length;i++) {
			if(visited[i]==0) {
				if(!DFS_TS(adjList[i].name)) return false;
			}
		}
		return true;
	}
	private int calc() {
		Node p = R;
		while(p!=null)				// ���� ���ĵ� �迭���� ������� �����͸� ����.
		{
			int loadIndex = hsm.get(p.name);
			Node ld = adjList[loadIndex];
			Node st = ld.next;			// ���� ����� ������ �ּ� ���.
			while(st!=null) {	// ld�� ������ ��� ��� st�� ld�� ���� ����.
				int storeIndex = hsm.get(st.name);
				int opcode = regfile[storeIndex][0];
				if(opcode!=DIF)		// ������ �ƴϸ� ���� ���̺��� ���� �ҷ� �� ����.
				{
					regfile[storeIndex][1] += regfile[loadIndex][1];
					if(opcode==AVG)	// ��� ���ϱ�
					{
						if(regfile[storeIndex][2]<indegree[storeIndex])
							regfile[storeIndex][2]++;
						if(regfile[storeIndex][2]==indegree[storeIndex])
							regfile[storeIndex][1] /= regfile[storeIndex][2];
					}
				}
				else			// �����̸�
				{
					if(loadIndex==regfile[storeIndex][2])	// �ǰ����� ���� ���
						regfile[storeIndex][1] += regfile[loadIndex][1];
					if(loadIndex==regfile[storeIndex][3])	// ������ ���� ���
						regfile[storeIndex][1] -= regfile[loadIndex][1];
				}
				st = st.next;
			}
			p=p.next;
		}
		int result = 0;
		for(int i=1;i<regfile.length;i++)
		{
			result+=regfile[i][1];
		}
		return result;
	}
	private void readFile(Scanner sc)	// ������ �о� ���� ����Ʈ �� �ʿ��� �ڷ� ������ ����
	{
		hsm = new HashMap<Integer, Integer>(); int id=1;
		int N = sc.nextInt();		// ������ ����
		sc.nextLine();				// �б� ������ �������� �Ѿ.
		adjList = new Node[N+1];	// [0]�� ������� �ʴ´�.
		indegree = new int[N+1];
		outdegree = new int[N+1];
		regfile = new int[N+1][4];	// [0] �Ӽ�, [1] ��, diff������ �ǰ���, avg������ ����, [2] diff���� ����, avg������ cnt(indegree), [3] DIF�� �������� ��� á���� üũ�ϴ� �÷���
		String[] tokens;
		for(int i=1;i<=N;i++) {		// N ���� �о� ó��
			tokens = sc.nextLine().split("[ \\=]+");	// ���� ����, '='���� ��ũ����¡
			int leftValue = trimVar(tokens[0]);			// �̹��� ���� ����. leftValue 
			if(!hsm.containsKey(leftValue))				// hsm�� ������ �߰�. Ȥ�� �𸣴� ��. ��� �� �������� �ʿ� ����.
				hsm.put(leftValue, id++);				// �� ���� ��� �ĺ��ڸ� �ο���
			int leftIndex = hsm.get(leftValue);			// incoming
			if(adjList[leftIndex]==null)
				adjList[leftIndex] = new Node(leftValue);		// �� ���� ����Ʈ�� ����� �ش� �ε����� ��带 ��. ��¥ ���� ������ �ش� ����� next���� ���� ����.
			if(isNum(tokens[1]))	// ����̸�
			{
				regfile[leftIndex][0] = NUM;
				regfile[leftIndex][1] = Integer.parseInt(tokens[1]);
			}
			else					// ����� �ƴϸ�
			{
				regfile[leftIndex][0] = opCode(tokens[1]);
				for(int j=2;j<tokens.length;j++)	// ������ ������ �κ��� �о� ó��
				{
					if(isVar(tokens[j]))			// ������
					{
						int rightValue = trimVar(tokens[j]);
						if(!hsm.containsKey(rightValue)) {
							hsm.put(rightValue, id++);
						}
						int rightIndex = hsm.get(rightValue);	// outcoming
						if(adjList[rightIndex]==null)
							adjList[rightIndex] = new Node(rightValue);
						add(rightValue,leftValue);	// outcoming�� ��忡 incoming�� ��带 �߰���.
						if(regfile[leftIndex][0]==DIF)	// ���� �ΰ� ó��
						{
							if(j==DIFEE)
								regfile[leftIndex][DIFEE] = rightIndex;	// �ǰ����� �ּҸ� ����
							if(j==DIFER)
								regfile[leftIndex][DIFER] = rightIndex;	// ������ �ּҸ� ����
						}
					}
					else							// �����
					{
						int num = Integer.parseInt(tokens[j]);
						if(regfile[leftIndex][0]==DIF)	// ������ ���� ó��
						{
							if(j==DIFEE)
								regfile[leftIndex][1] = num;
							else regfile[leftIndex][1] -= num;
						}else
							regfile[leftIndex][1] += num;
					}
				}
			}
		}

	}
	public static void main(String [] args)
	{
		Problem41 test = new Problem41();
		try
		{
			Scanner sc = new Scanner(new File("input41.txt"));
			for(int T=sc.nextInt();T>0;T--)
			{
				test.readFile(sc);	// file read complete
				if(test.topologicalSort2())				// ���� ����
				{
					System.out.println(test.calc());	// ���� ���� ������ ������ �����Ͽ� ����� �����.
				}else
					System.out.println("Impossible");	// DAG�� �ƴ�
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("file not found...");
		}		
	}

}

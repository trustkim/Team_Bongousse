package test.bjay;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem41 {
	private static int N;			// ���� ����
	private static Node[] adjList;	// �׷����� ��������Ʈ�� ǥ��
	private static int[] indegree;	// �׷����� ��� ��忡 ���� ���� ���� ���̺�
	private static int[] outdegree;	// �׷����� ��� ��忡 ���� ���� ���� ���̺�
	private static int[] regtable;	// ������ ���ص� �� �� �����鿡 ����Ǵ� ���� ���� ���̺�. ������ �������� �����̶�� �����ϸ� �ȴ�.
	
	private static String [] func;	// ���� �뵵?
	
	private static class Node {
		int data;	// ���ĺ����� ǥ���� ������ �ƽ�Ű �ڵ� ������ ������ � ���� ������.
		Node next;	// ���� ����Ʈ�� ���� ����
		Node(int data) {this.data=data;next=null;}
	}
	
	// ��������Ʈ�� �߰��ϴ� �Լ�
	private static void add(int i, int entry){
		Node p = new Node(entry);
		p.next = adjList[i];
		adjList[i] = p;
	}
	
	// ���� ���� ����� ���� ����Ʈ�� �߰��ϴ� �Լ�
	public static void add(int entry){
		Node p = new Node(entry);
		p.next = Rlist;
		Rlist = p;
	}
	private static Node Rlist;		// ���� ���� ����� ������ ���� ����Ʈ
	private static int[] visited;	// ���� ���� DFS�� �湮 ���� üũ�� ���� �迭
	private static Node[] table2;	// ���� �뵵��?
	private final static int NO = 0;
	
	private static boolean topologicalSort2(int [] graph){
		Rlist = null;
		visited = new int[27];
		for(int i=1; i<=N;i++){
			visited[i] = NO;
		}
		table2 = adjList;
		for(int i=1;i<27;i++){
			if(visited[i] == NO)
				if(!DFS_TS(i))
					return false;
		}
		return true;
	}

	private static boolean DFS_TS(int i) {
		boolean isDAG = true;
		visited[i]++;
		Node p= table2[i];
		while(p!=null && isDAG){
			if(visited[p.data] == NO)
				isDAG = DFS_TS(p.data);
			else if(visited[p.data] == 1)
				return false;
			p=p.next;
		}
		visited[i]++;
		add(i);
		return isDAG;
	}
	
	private static String[][] tmptokens;
	private static void result2(){
		Node p = Rlist;
		//DAGprint();
		
		while(p!=null){
			//System.out.print(p.data + " " + table[p.data]);

			if(tmptokens[p.data] != null && tmptokens[p.data][0]!=null && func[p.data]!=null && func[p.data].equals("sum")){
				for(int j=2;j<tmptokens[p.data].length;j++){
					if(tmptokens[p.data][j].charAt(0)=='['){
						//System.out.print(Integer.parseInt(tmptokens[p.data][j].substring(1, tmptokens[p.data][j].length()-1)));
						regtable[p.data] += regtable[Integer.parseInt(tmptokens[p.data][j].substring(1, tmptokens[p.data][j].length()-1))];
					}
					else
						regtable[p.data] += regtable[Integer.parseInt(tmptokens[p.data][j])];
				}
			}
//			else if(tmptokens[p.data] != null && tmptokens[p.data][0]!=null && func[p.data]!=null && func[p.data].equals("diff")){
//				if(tmptokens[p.data][2].charAt(0)=='['){
//					regtable[p.data] = regtable[Integer.parseInt(tmptokens[p.data][2].substring(1, tmptokens[p.data][2].length()-1))];
//				}
//				else
//					regtable[p.data] += regtable[Integer.parseInt(tmptokens[p.data][j])];
//
//			}
//			else{
//
//			}
			p=p.next;

		}
		for(int i=1;i<27;i++){
			System.out.print(regtable[i]+ " ");
		}
	}
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();	// �ð� ���� �ڵ�
		try {
			Scanner input = new Scanner(new File("input41.txt"));
			for(int T = input.nextInt(); T > 0; T--){
				N = input.nextInt();	// �׷����� ���� ������ ����
				input.nextLine();		// ���� ��������
				adjList = new Node[26+1];	// ��� ���ĺ��� ���� ��������Ʈ. [0]�� ������� �ʴ´�. ����, �ٵ� ������ [����] �̷��� ǥ���ϸ� �� 26 ������ ������ ����Ѵٴ� ������ ���� �ʳ�?
				outdegree = new int[26+1];	// [0]�� ���� ����
				indegree = new int[26+1];	// [0]�� ���� ����
				//			for(int i=0;i<27;i++){
				//				outdegree[i] = indegree[i] = -1;
				//			}	// -1�� �ʱ�ȭ�� ������ ����.
				regtable = new int[26+1];	// [0]�� ���� ����
				func = new String[27];
				tmptokens = new String[27][];
				for(int i=0;i<N;i++){
					String [] tokens = input.nextLine().split("[=\\ ]+");	// []ǥ�õ� �Բ� ��ũ����¡ �ϸ� ��� �������� ��� ���� ����?
					tmptokens[i+1] = tokens;

					for(int j=0;j<tokens.length;j++){
						System.out.print(tokens[j] + " ");
					}
					System.out.println();

					int j=1;

					while(j<tokens.length){
						if(tokens[j].length() > 1 && tokens[j].charAt(0) != '['){		//sum, avg,diff �� ���
							func[tokens[0].charAt(1)-48] = tokens[j];
							j++;
						}
						else if(tokens[j].length() == 1){		//�޶� ���� �ϳ��̸�, �ٷ� regtable
							//System.out.println("regtest");
							regtable[tokens[0].charAt(1)-48] = Integer.parseInt(tokens[j]);
							j++;
						}
						else{
							int outcoming = tokens[j].charAt(1)-48;			// start
							int incoming = tokens[0].charAt(1)-48;			//start�κ��� end���� ����
							indegree[incoming]++;
							outdegree[outcoming]++;
							add(outcoming, incoming);
							j++;
						}

					}

				}
				//mylist.print();
				for(int i=1;i<tmptokens.length;i++){
					if(tmptokens[i] != null){
						for(int j=0;j<tmptokens[i].length;j++){
							System.out.print(tmptokens[i][j] + " ");
						}
						System.out.println();
					}
				}	// file read complete...
				
				if(topologicalSort2(indegree)){
					result2();
				}
				else
					System.out.println("Not isDAG");
			}
			input.close();
		}catch(FileNotFoundException e) {e.printStackTrace();}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);	// �ð� ���� �ڵ�
	}
}

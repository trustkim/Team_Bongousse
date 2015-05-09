package test.bjay;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem41 {
	private static int N;			// 정점 개수
	private static Node[] adjList;	// 그래프는 인접리스트로 표현
	private static int[] indegree;	// 그래프의 모든 노드에 대한 진입 차수 테이블
	private static int[] outdegree;	// 그래프의 모든 노드에 대한 진출 차수 테이블
	private static int[] regtable;	// 연산이 수해될 때 각 변수들에 저장되는 값을 위한 테이블. 일종의 레지스터 파일이라고 생각하면 된다.
	
	private static String [] func;	// 무슨 용도?
	
	private static class Node {
		int data;	// 알파벳으로 표현된 변수의 아스키 코드 값에서 추출한 어떤 값을 저장함.
		Node next;	// 연결 리스트를 위한 변수
		Node(int data) {this.data=data;next=null;}
	}
	
	// 인접리스트에 추가하는 함수
	private static void add(int i, int entry){
		Node p = new Node(entry);
		p.next = adjList[i];
		adjList[i] = p;
	}
	
	// 위상 정렬 결과인 연결 리스트에 추가하는 함수
	public static void add(int entry){
		Node p = new Node(entry);
		p.next = Rlist;
		Rlist = p;
	}
	private static Node Rlist;		// 위상 정렬 결과를 저장할 연결 리스트
	private static int[] visited;	// 위상 정렬 DFS시 방문 여부 체크를 위한 배열
	private static Node[] table2;	// 무슨 용도지?
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
		long start = System.currentTimeMillis();	// 시간 측정 코드
		try {
			Scanner input = new Scanner(new File("input41.txt"));
			for(int T = input.nextInt(); T > 0; T--){
				N = input.nextInt();	// 그래프의 정점 개수를 읽음
				input.nextLine();		// 한줄 내려가기
				adjList = new Node[26+1];	// 모든 알파벳에 대한 인접리스트. [0]은 사용하지 않는다. 가만, 근데 변수를 [숫자] 이렇게 표시하면 꼭 26 종류의 변수만 사용한다는 보장이 없지 않나?
				outdegree = new int[26+1];	// [0]은 쓰지 않음
				indegree = new int[26+1];	// [0]은 쓰지 않음
				//			for(int i=0;i<27;i++){
				//				outdegree[i] = indegree[i] = -1;
				//			}	// -1로 초기화할 이유가 없다.
				regtable = new int[26+1];	// [0]은 쓰지 않음
				func = new String[27];
				tmptokens = new String[27][];
				for(int i=0;i<N;i++){
					String [] tokens = input.nextLine().split("[=\\ ]+");	// []표시도 함께 토크나이징 하면 어떻게 변수인지 상수 인지 구분?
					tmptokens[i+1] = tokens;

					for(int j=0;j<tokens.length;j++){
						System.out.print(tokens[j] + " ");
					}
					System.out.println();

					int j=1;

					while(j<tokens.length){
						if(tokens[j].length() > 1 && tokens[j].charAt(0) != '['){		//sum, avg,diff 일 경우
							func[tokens[0].charAt(1)-48] = tokens[j];
							j++;
						}
						else if(tokens[j].length() == 1){		//달랑 숫자 하나이면, 바로 regtable
							//System.out.println("regtest");
							regtable[tokens[0].charAt(1)-48] = Integer.parseInt(tokens[j]);
							j++;
						}
						else{
							int outcoming = tokens[j].charAt(1)-48;			// start
							int incoming = tokens[0].charAt(1)-48;			//start로부터 end로의 에지
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
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);	// 시간 측정 코드
	}
}

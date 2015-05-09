package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Problem41
{
	private final int NUM = 0;
	private final int SUM = 1;
	private final int DIF = 2;
	private final int AVG = 3;
	private class Node
	{
		int name;		// 변수 이름(숫자로 표시)
		Node next;
		Node(int id) { this.name = id; next=null; }
	}
	private HashMap<Integer, Integer> hsm;	// 변수 종류 식별 위한 해쉬맵
	private Node[] adjList;					// 그래프는 인접리스트로 표현
	private int[] indegree;					// 진입 차수 테이블
	private int[] outdegree;				// 각 노드의 진출 차수 테이블
	private int[][] regfile;				// 각 변수의 속성, 결과를 저장할 테이블
	private int[] visited;					// 위상 정렬 알고리즘 DFS시 방문 여부 체크 배열
	private Node R;							// 위상 정렬 결과 리스트
	private boolean isVar(String token)	// 변수면 참을 반환
	{
		return (token.charAt(0) == '[');
	}
	private boolean isNum(String token)	// token[1]이 연산 문자가 아니면 상수이므로 참을 반환
	{
		return (!(token.charAt(0)>='a' && token.charAt(0)<='z'));
	}
	private int trimVar(String token)	// 변수에서 '[', ']'을 제거
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
		Node cur = new Node(in);		// 추가할 노드.
		Node p = r;						// 추가할 위치.
		while(p.next!=null) {
			p = p.next;
		}
		p.next = cur;
		// 인접리스트의 헤드 노드들에 outdegree를 저장 해 둠. 나머지는 모두 0이다.
		indegree[hsm.get(in)]++;
		outdegree[hsm.get(out)]++;
	}
	/* 위상정렬 알고리즘 2 */
	private boolean DFS_TS(int v) {
		boolean isDAG = true;
		visited[hsm.get(v)]++;
		Node x = adjList[hsm.get(v)].next;
		while(x!=null && isDAG) {
			if(visited[hsm.get(x.name)]==0)
				isDAG = DFS_TS(x.name);
			else if(visited[hsm.get(x.name)]==1) {
				return false;	// 위상 정렬시 마지막에 와야 하는 노드의 outdegree가 0이 아닌경우 DAG가 아님.
			}
			x = x.next;
		}
		
		// R의 앞에 추가.
		x = new Node(adjList[hsm.get(v)].name);
		x.next = R;
		R = x;
		visited[hsm.get(v)]++;	// DFS를 거슬러 올라가면서 두번째 체크.
		return isDAG;
	}
	private boolean topologicalSort2() {
		visited = new int[adjList.length];	// [0]은 사용하지 않음. DFS 수행 시 최대 두 번까지 체크됨.
		R = null;
		for(int i=1;i<adjList.length;i++) {
			if(visited[i]==0) {
				if(!DFS_TS(adjList[i].name)) return false;
			}
		}
		return true;
	}
//	private int calc(String[] A) {
//		Node p = R;
//		while(p!=null)				// 위상 정렬된 배열에서 순서대로 데이터를 읽음.
//		{
//			Node ld = adjList[hsm.get(p.name)];	
//			Node st = ld.next;			// 더한 결과를 저장할 주소 노드.
//			while(st!=null) {	// ld의 인접한 모든 노드 st에 ld의 값을 더함.
//				if(isVar(A[i]))	// 변수이면 변수 테이블에서 값을 불러 옮.
//					varTable[hsm.get(st.name)] += varTable[hsm.get(ld.name)];
//				else			// 상수이면 위상정렬 배열의 문자열을 int형 값으로 읽음.
//					varTable[hsm.get(st.name)] += Integer.parseInt(A[i]);
//				st = st.next;
//			}
//		}
//		for(int i=1;i<A.length;i++) {	
//			
//		}
//		return varTable[hsm.get(A[A.length-1])];
//	}
	private void readFile(Scanner sc)	// 파일을 읽어 인접 리스트 등 필요한 자료 구조를 생성
	{
		hsm = new HashMap<Integer, Integer>(); int id=1;
		int N = sc.nextInt();		// 수식의 개수
		sc.nextLine();				// 읽기 시작할 라인으로 넘어감.
		adjList = new Node[N+1];	// [0]은 사용하지 않는다.
		indegree = new int[N+1];
		outdegree = new int[N+1];
		regfile = new int[N+1][2];	// [0] 속성, [1] 값
		String[] tokens;
		for(int i=1;i<=N;i++) {		// N 줄을 읽어 처리
			tokens = sc.nextLine().split("[ \\=]+");	// 공백 문자, '='으로 토크나이징
//			for(int j=0;j<tokens.length;j++)
//				System.out.print(tokens[j]+" ");		// 수식 토크나이징 테스트 출력
//			System.out.println();
			int leftValue = trimVar(tokens[0]);			// 이번에 읽은 변수. leftValue 
			//System.out.println(var);					// 변수 테스트 출력
			if(!hsm.containsKey(leftValue))				// hsm에 없으면 추가. 혹시 모르니 둠. 사실 이 문제에선 필요 없음.
				hsm.put(leftValue, id++);				// 식 순서 대로 식별자를 부여함
			int leftIndex = hsm.get(leftValue);			// incoming
			if(adjList[leftIndex]==null)
				adjList[leftIndex] = new Node(leftValue);		// 각 인접 리스트의 헤더는 해당 인덱스의 노드를 둠. 진짜 인접 노드들은 해당 노드의 next부터 참조 가능.
			if(isNum(tokens[1]))	// 상수이면
			{
				regfile[leftIndex][0] = NUM;
				regfile[leftIndex][1] = Integer.parseInt(tokens[1]);
			}
			else					// 상수가 아니면
			{
				regfile[leftIndex][0] = opCode(tokens[1]);
				for(int j=2;j<tokens.length;j++)	// 수식의 오른쪽 부분을 읽어 처리
				{
					if(isVar(tokens[j]))
					{
						int rightValue = trimVar(tokens[j]);
						if(!hsm.containsKey(rightValue)) {
							hsm.put(rightValue, id++);
						}
						int rightIndex = hsm.get(rightValue);	// outcoming
						if(adjList[rightIndex]==null)
							adjList[rightIndex] = new Node(rightValue);
						add(rightValue,leftValue);	// outcoming쪽 노드에 incoming쪽 노드를 추가함.
					}
					else
					{
						int num = Integer.parseInt(tokens[j]);
						if(opCode(tokens[1])==DIF)
						{
							if(j==2)
								regfile[leftIndex][1] = num;
							else regfile[leftIndex][1] = -1 * num;
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
				test.Print();
				if(test.topologicalSort2())
				{
					test.PrintTopol();
//					test.calc();
				}else
					System.out.println("Impossible");
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("file not found...");
		}		
	}
	
	private void Print()
	{
		System.out.println("id\tvar(indeg,outdeg)\tatt");
		for(int i=1;i<adjList.length;i++)
		{
			Node p = adjList[i];
			System.out.print(hsm.get(p.name)+"\t"+p.name+"("+indegree[hsm.get(p.name)]+", "+outdegree[hsm.get(p.name)]+")\t\t\t"+regfile[hsm.get(p.name)][0]+"\t");
			if(p!=null) p=p.next;
			while(p!=null)
			{
				System.out.print(p.name+"\t");
				p=p.next;
			}
			System.out.println();
		}
		System.out.println("regfile: ");
		for(int i=1;i<regfile.length;i++)
		{
			System.out.println(adjList[i].name+": "+regfile[i][1]);
		}
	}
	private void PrintTopol()
	{
		Node p = R;
		while(p!=null)
		{
			System.out.print(p.name+" ");
			p=p.next;
		}
		System.out.println();
	}
}

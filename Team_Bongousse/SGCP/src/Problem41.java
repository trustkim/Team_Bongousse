import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Problem41
{
	private final int NUM = 0;
	private final int SUM = 1;
	private final int DIF = 2;
	private final int DIFEE = 2;	// 피감수 3-1 = 2 에서 3
	private final int DIFER = 3;	// 감수 3-1 = 2에서 1
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
	private int calc() {
		Node p = R;
		while(p!=null)				// 위상 정렬된 배열에서 순서대로 데이터를 읽음.
		{
			int loadIndex = hsm.get(p.name);
			Node ld = adjList[loadIndex];
			Node st = ld.next;			// 더한 결과를 저장할 주소 노드.
			while(st!=null) {	// ld의 인접한 모든 노드 st에 ld의 값을 더함.
				int storeIndex = hsm.get(st.name);
				int opcode = regfile[storeIndex][0];
				if(opcode!=DIF)		// 뺄셈이 아니면 변수 테이블에서 값을 불러 와 더함.
				{
					regfile[storeIndex][1] += regfile[loadIndex][1];
					if(opcode==AVG)	// 평균 구하기
					{
						if(regfile[storeIndex][2]<indegree[storeIndex])
							regfile[storeIndex][2]++;
						if(regfile[storeIndex][2]==indegree[storeIndex])
							regfile[storeIndex][1] /= regfile[storeIndex][2];
					}
				}
				else			// 뺄셈이면
				{
					if(loadIndex==regfile[storeIndex][2])	// 피감수를 읽은 경우
						regfile[storeIndex][1] += regfile[loadIndex][1];
					if(loadIndex==regfile[storeIndex][3])	// 감수를 읽은 경우
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
	private void readFile(Scanner sc)	// 파일을 읽어 인접 리스트 등 필요한 자료 구조를 생성
	{
		hsm = new HashMap<Integer, Integer>(); int id=1;
		int N = sc.nextInt();		// 수식의 개수
		sc.nextLine();				// 읽기 시작할 라인으로 넘어감.
		adjList = new Node[N+1];	// [0]은 사용하지 않는다.
		indegree = new int[N+1];
		outdegree = new int[N+1];
		regfile = new int[N+1][4];	// [0] 속성, [1] 값, diff에서는 피감수, avg에서는 누승, [2] diff에서 감수, avg에서는 cnt(indegree), [3] DIF의 변수들이 모두 찼는지 체크하는 플래그
		String[] tokens;
		for(int i=1;i<=N;i++) {		// N 줄을 읽어 처리
			tokens = sc.nextLine().split("[ \\=]+");	// 공백 문자, '='으로 토크나이징
			int leftValue = trimVar(tokens[0]);			// 이번에 읽은 변수. leftValue 
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
					if(isVar(tokens[j]))			// 변수면
					{
						int rightValue = trimVar(tokens[j]);
						if(!hsm.containsKey(rightValue)) {
							hsm.put(rightValue, id++);
						}
						int rightIndex = hsm.get(rightValue);	// outcoming
						if(adjList[rightIndex]==null)
							adjList[rightIndex] = new Node(rightValue);
						add(rightValue,leftValue);	// outcoming쪽 노드에 incoming쪽 노드를 추가함.
						if(regfile[leftIndex][0]==DIF)	// 뺄셈 부가 처리
						{
							if(j==DIFEE)
								regfile[leftIndex][DIFEE] = rightIndex;	// 피감수의 주소를 저장
							if(j==DIFER)
								regfile[leftIndex][DIFER] = rightIndex;	// 감수의 주소를 저장
						}
					}
					else							// 상수면
					{
						int num = Integer.parseInt(tokens[j]);
						if(regfile[leftIndex][0]==DIF)	// 뺄셈은 따로 처리
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
				if(test.topologicalSort2())				// 위상 정렬
				{
					System.out.println(test.calc());	// 위상 정렬 순으로 연산을 수행하여 결과를 출력함.
				}else
					System.out.println("Impossible");	// DAG가 아님
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			System.out.println("file not found...");
		}		
	}

}

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem45 {
	private class Edge{
		int u, v;
		int weight;
		Edge next;
		Edge(int u, int v, int w){
			this.u = u;
			this.v = v;
			this.weight = w;
			next = null;
		}
	}

	private int N;			// 정점 개수	
	private int M;			// 에지 개수
	private int[][] edges;	// 단순한 에지 테이블
	private Edge[] adjList;	// 인접리스트
	private int start;		// 출발점
	private int dest;		// 도착점

	/* Dijkstra를 위한 멤버 */
	private int[] d;
	private int[] c;
	private boolean[] include;
	private void initDijkstra()
	{
		d = new int[N];	// 전체 정점들의 key값을 매번 갱신시켜 나감. 따라서 N개
		c = new int[N];		// 얘도 N개
		include = new boolean[N];
		for(int i=0;i<N;i++)
		{
			d[i] = -1;		// key를 무한으로
		}
	}
	private int findMinKey()
	{
		// S에 넣지 않은 인접 노드 중 거리가 가장 가까운 노드의 인덱스를 반환
		double minKey=-1;		// 현재 가장 가까운 정점과의 거리가 저장
		int minIndex=-1;		// 현재 가중치가 가장 낮은 정점 인덱스를 반환
		for(int i=0;i<N;i++)
		{
			if(!include[i]){
				int temp = d[i];
				if(minKey == -1 || (temp!=-1&&minKey > temp))
				{
					minKey = temp;
					minIndex=i;
				}
			}
		}
		return minIndex;
	}
	private void dijkstra()
	{	// Prim의 알고리즘과 거의 동일하다!
		initDijkstra();
		d[start] = 0;		// 시작 정점을 정함.
		c[start] = 1;		// 최단 경로의 개수도 초기 값을 정해 줘야...
		int cnt = 0;
		while(cnt<N)		// n-1번 반복. 따로 dest까지 결정되면 멈추도록 하지 않음.
		{
			int u = findMinKey();			// S에 속하지 않고 key가 가장 낮은 정점을 찾음
			include[u] = true; cnt++;		// 그 u를 S에 포함 시킴
			Edge p = adjList[u];
			while(p!=null)					// u의 모든 인접한 정점 중 S에 포함 되지 않은 v의 key[v], pi[v]를 갱신
			{
				int v = p.v;
				if(include[v])
					;// S에 포함된 key[v]는 갱신하지 않음
				else
				{
					int weight = p.weight;
					if(d[v]==-1 || d[v] > d[u]+weight)
					{
						d[v] = d[u]+weight;
						c[v] = c[u];					// pi는 필요 없었음.
					}
					else if(d[v] == d[u]+weight)		// 최단 경로의 개수를 세기 위해서는 이 부분만 추가해주면 된다.
					{
						c[v] += c[u];
					}
				}
				p = p.next;
			}
		}
		//System.out.println("dijkstra compelete");
		System.out.println(c[dest]);
	}
	private void add(int u, int v, int weight)
	{
		if(adjList[u] == null)
			adjList[u] = new Edge(u,v,weight);
		else
		{
			Edge p = new Edge(u,v,weight);
			p.next = adjList[u];
			adjList[u] = p;
		}
	}
	private void readfile(Scanner sc){
		N = sc.nextInt();
		M = sc.nextInt();
		edges=new int[M][3];	// 모든 에지에 대한 테이블. 나중에 인접리스트 뒤집을 때 쓰려교...
		adjList = new Edge[N];
		for(int i=0;i<M;i++)
		{
			int u=sc.nextInt(),v=sc.nextInt(),w=sc.nextInt();
			add(u,v,w);
			edges[i][0] = u; edges[i][1] = v; edges[i][2] = w;
		}
		start = sc.nextInt(); dest = sc.nextInt();
	}
	public static void main(String[] args){
		Problem45 test = new Problem45();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("input45.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int T=sc.nextInt();T>0;T--)
		{
			test.readfile(sc);
			test.initDijkstra();	// 읽은 파일로 다른 필요한 자료구조를 세팅
			test.dijkstra();// 다익스트라 수행
		}
		sc.close();
	}
}

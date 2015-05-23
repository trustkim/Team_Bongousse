package test.trustkim;

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
	
	private int N;
	private int M;
	private int[][] edges;
	private Edge[] adjList;
	private int start;
	private int dest;

	/* 본격적인 풀이 solve */
	private void inverseEdge()
	{
		adjList = new Edge[N];
		for(int[] i:edges)
		{
			int u = i[1], v=i[0], w=i[2];
			add(u,v,w);
		}
	}
	private int countPath(int v)
	{	// start부터 v까지의 최단경로의 개수 총 합을 구하여 반환하는 함수 
		int sum=0;
		if(v==start)
			return 1;
		Edge p = adjList[v];
		while(p!=null)
		{
			if(key[p.u]-p.weight==key[p.v])
				sum += countPath(p.v);
			p = p.next;
		}
		return sum;	// 여기 도달하나?
	}
	
	/* Dijkstra를 위한 멤버 */
	private int[] key;
	private int[] pi;
	private boolean[] include;
	private void initDijkstra()
	{
		key = new int[N];	// 전체 정점들의 key값을 매번 갱신시켜 나감. 따라서 N개
		pi = new int[N];		// 얘도 N개
		include = new boolean[N];
		for(int i=0;i<N;i++)
		{
			key[i] = -1;		// key를 무한으로
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
				int temp = key[i];
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
		key[start] = 0;		// 시작 정점을 정함.
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
					if(key[v]==-1 || key[v] > key[u]+weight)
					{
						key[v] = key[u]+weight;
						pi[v] = u;
					}	
				}
				p = p.next;
			}
		}
		//System.out.println("dijkstra compelete");
		System.out.println("dijkstra test: ["+start+"] -> ["+dest+"]: "+key[dest]);
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
			//test.edgesprint();
			test.initDijkstra();	// 읽은 파일로 다른 필요한 자료구조를 세팅
			test.dijkstra();// 다익스트라 수행
			//test.nodesprint();
			test.inverseEdge();// 모든 에지를 뒤집은 인접리스트를 얻음
			//test.edgesprint();
			
			System.out.println(test.countPath(test.dest));// 최단경로의 개수를 구함
		}
		sc.close();
	}
	
	/* 제출용에는 빠질 테스트용 함수들 */
	private void edgesprint(){
		for(int i=0;i<N;i++){
			Edge p = adjList[i];
			System.out.print("["+i+"]: ");
			while(p!=null){
				System.out.print("("+p.u + "->" + p.v + "," + p.weight+"), ");
				p=p.next;
			}
			System.out.println();
		}
	}
	private void nodesprint(){
		for(int i=0;i<N;i++){
			System.out.println("[" + i + "]: " + key[i] + " " + pi[i]);
		}
	}
}

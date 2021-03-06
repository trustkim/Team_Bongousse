package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
//import java.util.HashMap;	// 좋은 아이디어는 아닌듯.
import java.util.Scanner;

public class Problem38 {
	private class Node // 인접 리스트를 위한 노드 정점을 표현하는 Point와 다르게 가장 간단히 구성
	{
		protected int index;
		protected Node next;
		Node() {index=0;next=null;}
		Node(int index) 
		{
			this.index=index;
			next=null;
		}
	}
	private class Point extends Node
	{
		protected int x,y;
		Point(){x=0;y=0;}
		Point(int x,int y)
		{
			//this.index=index;
			this.x=x; this.y=y;
			//next=null;
		}
	}
	protected class Vertex extends Point
	{
		//int index;
		int outdegree;	// 각정렬 후 새로운 인접 리스트를 만들 때 필요
		Vertex(int index,int x,int y)
		{
			this.index=index;this.x=x;this.y=y;
			outdegree = 0;
			//next=null;
		}
	}
	private class Edge implements Comparable<Edge>
	{
		//int index;
		int[] v;
		Edge(int v1, int v2)
		{
			//this.index=index;
			v=new int[2];
			v[0]=v1; v[1]=v2;
		}
//		Edge(Integer[] v)
//		{
//			this.v = new int[2];
//			this.v[0] = v[0];
//			this.v[1] = v[1];
//		}
		
		private int[] findOPoint(Edge other)
		{
			int[] res = new int[3];
			if(v[0]==other.v[0]) {
				res[0] = v[0];
				res[1] = v[1];
				res[2] = other.v[1];
				return res;
			}
			if(v[0]==other.v[1]) {
				res[0] = v[0];
				res[1] = v[1];
				res[2] = other.v[0];
				return res;
			}
			if(v[1]==other.v[0]) {
				res[0] = v[1];
				res[1] = v[0];
				res[2] = other.v[1];
				return res;
			}
			if(v[1]==other.v[1]) {
				res[0] = v[1];
				res[1] = v[0];
				res[2] = other.v[0];
				return res;
			}
			return null;
		}
		private int crossProduct(Point pi, Point pj)
		{
			return (pi.x*pj.y-pi.y*pj.x);
		}
		/* return negative if q<r, 0 if q=r, positive if q>r */
		public int compareTo(Edge other)
		{
			int[] oqr = findOPoint(other);
			Point o = vertices[oqr[0]];
			Point q = vertices[oqr[1]];
			Point r = vertices[oqr[2]];
			Point vecter_oq = new Point(q.x-o.x,q.y-o.y);
			Point vecter_or = new Point(r.x-o.x,r.y-o.y);

			if(vecter_oq.x >= 0 && vecter_or.x < 0) return -1;
			if(vecter_oq.x <= 0 && vecter_or.x > 0) return 1;
			if(vecter_oq.x == 0 && vecter_or.x == 0) ;
				
			return crossProduct(vecter_oq,vecter_or);
		}
	}
	
	private int N;				// 전체 그래프의 정점 개수
	private int K;				// 찾는 face를 이루는 정점 개수
	
	private Node[] adjList;		// 그래프를 표현한 인접 리스트. 정점의 id로만 표현
	private Vertex[] vertices;	// 모든 정점의 좌표 테이블
	//private HashMap<Edge, Integer[]> edges;		// 모든 에지의 맵. 생각대로 잘 안되었다.
	private boolean[][] visited;	// 지나온 에지를 체크하는 배열
	
	// find all inner face
	private void findAllFace()
	{	// 아우터 페이스를 제외한 모든 내부 페이스를 찾아 차수가 K인 페이스를 카운트 한다.
		//System.out.println("inner face detection!");
		int cnt=0;
		for(int i=0;i<N;i++)
		{
			Node p = adjList[i];
			while(p!=null)
			{
				int start = i; int end = p.index;
				if(K==findFace(start,end))
				{
					cnt++;
				}
				p = p.next;
			}
			
		}
		System.out.println(cnt);
	}
	// find a new face
	private int findFace(int start, int end)
	{	// 한 페이스를 찾을 때까지 반복하는 함수. start에서 출발해 end로 끝나는 에지를 시작으로 페이스를 순회한다?
		int cnt=0;
		int u = start;			// 최초 방물할 에지의 시작점
		int v = end;			// 최초 방문할 에지의 끝점
		Node p = adjList[v];	// 최초 방문할 에지.
		while(p!=null)
		{		
			if(!visited[u][v])	// 이 방문할 에지가 방문하지 않았을 때
			{
				visited[u][v] = true;	// 방문함을 체크
				//System.out.println(u+" -> "+v);	// 확인차 출력
				cnt++;
				if(v==start) {		// 현재 방문할 에지의 끝점이 face를 찾기 시작한 에지의 시작점이 되면 한 face를 찾은 것임. 이 때가 bace case
					//System.out.println("found a new face");
					return cnt;
				}
				Node pre = null; // p의 바로 앞 노드를 따라가는 프리디세서
				while(pre==null || (pre!=null && pre.index!=u))
				{
					pre = p;
					p = p.next;
					if(p==null)	// circular하게 진행해야 하므로 다시 리스트의 맨 처음으로 돌아간다
					{
						p=adjList[v];
					}
				}
				u = v;
				v = p.index;
				p = adjList[v];
			}
			else
				p=p.next;
		}
		return cnt;
	}
	// detect the outer face
	private int findMinX()
	{
		int minX=-1;
		int minIndex=-1;
		for(int i=0;i<N;i++)
		{
			if(minX==-1 || minX>vertices[i].x)
			{
				minX=vertices[i].x;
				minIndex=i;
			}
		}
		return minIndex;
	}
	private void detectOuterFace()
	{		
		// 가장 왼쪽 정점(x좌표가 최소인 정점)을 찾는다.
		int start = findMinX();
		// 기울기가 최대인 에지를 찾는다. 즉 각정렬 했을 때 가장 먼저 오는 에지.
		int end = adjList[start].index;
		// 그 에지로 시작하는 face를 찾는다.
		findFace(start,end);
		//System.out.println("found the Outer face");
	}
	
//	private void makeCircularList()	// 인접 리스트를 영구적으로 circular하게 하면 이후 출력 등의 용도로 만들어 둔 다른 함수를 다 고쳐야 한다...
//	{
//		for(int i=0;i<N;i++)
//		{
//			Node p = adjList[i];
//			Node pre = null;
//			while(p!=null)
//			{
//				pre = p;
//				p=p.next;
//			}
//			pre.next = adjList[i];
//			
//		}
//	}
	
	// Clockwise Angular Sort
	private int getAddIndex(int index, int[] keys)
	{
		if(index==keys[0]) return keys[1];
		else return keys[0];
	}
	private void angularSort(int index)
	{	// 인접 리스트에서 해당 정점 인덱스의 인접한 에지를 시계방향 순으로 각정렬 함
		//System.out.println(index+":");
		Edge[] tempEdges = new Edge[vertices[index].outdegree];	// 정렬할 에지의 인덱스의 배열
		// 원래 인접리스트의 각 노드를 temp의 각 원소로  저장
		Node p = adjList[index];
		int edgeIndex = 0;
		while(p!=null)
		{
			//tempEdges[edgeIndex++] = new Edge(getKeys(index,p.index));
			tempEdges[edgeIndex++] = new Edge(index,p.index);
			p=p.next;	// 한 연결 리스트 순회
		}
//		System.out.println("\tBefore Sorting");
//		for(int i=0;i<tempEdges.length;i++)
//		{
//			System.out.println("\t"+tempEdges[i].v[0]+" <-> "+tempEdges[i].v[1]);
//		}
		Arrays.sort(tempEdges);
//		System.out.println("\tAfter Sorting");
//		for(int i=0;i<tempEdges.length;i++)
//		{
//			System.out.println("\t"+tempEdges[i].v[0]+" <-> "+tempEdges[i].v[1]);
//		}

		adjList[index] = null;
		for(int i=tempEdges.length-1;i>=0;i--)
		{
			add(index,getAddIndex(index,tempEdges[i].v));
		}
	}
	private void rebuildAdjList()
	{
		for(int i=0;i<N;i++)	// 모든 정점에 대하여 수행. N번 반복
		{
			angularSort(i);	// 해당 정점의 인접 에지들을 각정렬 함
		}
		//System.out.println("rebuildAdjList complete");
	}
	
	// 파일 읽고 자료구조 생성
	private void add(int r, int i)	// 정점 r에 인접한 정점 i를 추가함. 항상 맨 앞에 추가함.
	{
		//Vertex copyValue = vertices[i];
		Node p = new Node(i);
		p.next = adjList[r];
		adjList[r] = p;
	}
	private void initGraph()
	{
		vertices = new Vertex[N];	// 전체 정점 정보만 저장 하는 배열
		adjList = new Node[N];		// 그래프를 표현하는 인접리스트
		visited = new boolean[N][N];// 어쩔 수 없이 인접 행렬로...	N이 결정되고 가까운 곳에서 생성해 줬다.
		//edges = new HashMap<Edge, Integer[]>();
	}
//	private Integer[] getKeys(int u, int v)
//	{
//		int temp=u;
//		if(temp!=v)
//		{
//			u = Math.min(temp, v);
//			v = Math.max(temp, v);	
//		}
//		Integer[] key = new Integer[2];
//		key[0] = u;
//		key[1] = v;
//		
//		return key;
//	}
	private void readFile(Scanner sc)
	{
		N = sc.nextInt();
		K = sc.nextInt();
		initGraph();
		for(int i=0;i<N;i++)
		{
			int index = sc.nextInt()-1;	// 배열 인덱스는 항상 -1 해줘야 함
			int x=sc.nextInt();
			int y=sc.nextInt();
			vertices[index] = new Vertex(index,x,y);
			for(int j=sc.nextInt();j>0;j--)
			{
				int v = sc.nextInt()-1;
				add(index,v);
				vertices[index].outdegree++;
//				Integer[] keys = getKeys(index,v);
//				if(!edges.containsKey(keys))
//				{
//					edges.put(new Edge(keys), keys);
//				}
			}
			
		}
	}
	public static void main(String[] args) {
		Problem38 theApp = new Problem38();
		try {
			Scanner sc = new Scanner(new File("input38.txt"));
			for(int T=sc.nextInt();T>0;T--) {	// T번 반복
				theApp.readFile(sc);	// file read complete
				//theApp.pointsPrint();	// test print
				//theApp.adjPrint();	// test print
				theApp.rebuildAdjList();// 모든 정점의 인접한 에지에 대하여 각정렬 수행하여 새 인접 리스트를 만든다
				//theApp.adjPrint();
				//theApp.makeCircularList();
				theApp.detectOuterFace();	// 아우터 페이스를 얻는다.
				theApp.findAllFace();
				// 
			}
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
//	private void pointsPrint()
//	{
//		System.out.println("points table:");
//		for(int i=0;i<N;i++)
//		{
//			System.out.println("["+(i+1)+"]: ("+vertices[i].x+", "+vertices[i].y+")");
//		}
//	}
//	private void adjPrint()
//	{
//		System.out.println("adjList:");
//		for(int i=0;i<N;i++)
//		{
//			Node p = adjList[i];
//			System.out.print("["+(i+1)+"]: ");
//			//p = p.next;
//			while(p!=null) {
//				System.out.print("["+(p.index+1)+"], ");
//				p = p.next;
//			}
//			System.out.println();
//		}
//	}
}
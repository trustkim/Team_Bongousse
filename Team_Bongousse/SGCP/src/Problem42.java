import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

public class Problem42 {
	private class Point
	{
		protected int x,y;
		Point(int x,int y)
		{
			this.x=x; this.y=y;
		}
	}
	private class Edge implements Comparable<Edge>
	{
		int[] v;	// 방향 그래프로 가정 하는 것이 더 간단할 것이다.
		boolean visited;
		Edge next;
		Edge(int v1, int v2)
		{
			v=new int[2];
			v[0]=v1; v[1]=v2;
			next = null;
		}
		private int crossProduct(Point pi, Point pj)
		{
			return (pi.x*pj.y-pi.y*pj.x);
		}
		/* return negative if q<r, 0 if q=r, positive if q>r */
		public int compareTo(Edge other)
		{
			Point o = points[this.v[0]];
			Point q = points[this.v[1]];
			Point r = points[other.v[1]];
			Point vecter_oq = new Point(q.x-o.x,q.y-o.y);
			Point vecter_or = new Point(r.x-o.x,r.y-o.y);

			if(vecter_oq.x >= 0 && vecter_or.x < 0) return -1;
			if(vecter_oq.x <= 0 && vecter_or.x > 0) return 1;
			//if(vecter_oq.x == 0 && vecter_or.x == 0) ;

			return crossProduct(vecter_oq,vecter_or);
		}
		public boolean intersects(Point other_u, Point other_v)
		{
			Point o = points[v[0]];
			Point p = points[v[1]];
			Point line = new Point(p.x-o.x,p.y-o.y);
			Point other1 = new Point(other_u.x-o.x,other_u.y-o.y);
			Point other2 = new Point(other_v.x-o.x,other_v.y-o.y);
			int result1 = crossProduct(line,other1);
			int result2 = crossProduct(line,other2);
			if(result1!=0) result1 /= Math.abs(result1);
			if(result2!=0) result2 /= Math.abs(result2);
			if(result1*result2 >= 0)
				return false;
			
			line = new Point(other_v.x-other_u.x,other_v.y-other_u.y);
			other1 = new Point(o.x-other_u.x,o.y-other_u.y);
			other2 = new Point(p.x-other_u.x,p.y-other_u.y);
			result1 = crossProduct(line,other1);
			result2 = crossProduct(line,other2);
			if(result1!=0) result1 /= Math.abs(result1);
			if(result2!=0) result2 /= Math.abs(result2);
			if(result1*result2 >= 0)
				return false;
			
			return true;
		}
	}
	private class Face
	{
		int size;		// 페이스를 이루는 정점 개수
		Vector<Edge> elements;	// 구성하는 에지
		Face()
		{
			size=0; elements = new Vector<Edge>();
		}
	}
	
	private int N;				// 전체 그래프의 정점 개수
	private Point startP;;		// 탈출 시작 점
	
	private Edge[] adjList;		// 그래프를 표현한 인접 리스트. 정점의 id로만 표현
	private Point[] points;
	private int[] outdegree;	// 별도의 outdegree배열
	
	private Vector<Face> faces;	// 페이스 테이블. findAllFace() 하면서 하나씩 추가해 나갈 거라 벡터로 선언.
	private Edge[] adjFaceList;	// 페이스들의 인접리스트
	
	/* 이제 탈출이다 */
	private boolean isInnerPoint(int faceIndex)
	{
		Face fi = faces.get(faceIndex);	// 현재 검사하려는 face
		int interCnt = 0;
		for(int i=0;i<fi.size;i++)	// fi의 모든 정점을 방문하면서 검사
		{
			Point O = new Point(-1,-1);	// 임의의 점을 원점으로 해봄
			Edge p = fi.elements.get(i);// fi의 i번째 에지
			if(p.intersects(O, startP))
				interCnt++;
		}
		
		return interCnt%2!=0;
	}
	private int findStartFace()
	{
		for(int i=1;i<faces.size();i++)	// outer face를 제외한 모든 face에 대해 검사
		{
			// i번째 face의 모든 점에 대해 왼쪽 에지와 오른쪽 에지 사이에 주어진 점과 이루는 에지가 위치하는지 검사
			if(isInnerPoint(i))
				return i;
		}
		return 0;	// 어떤 내부 face 내에도 있지 않으면 페이즈 내부에 존재 하지 않음. 0을 반환.
	}
	private int solve()
	{
		int[] visited = new int[adjFaceList.length];
		for(int i=0;i<visited.length;i++)
		{
			visited[i] = -1;
		}
		Queue<Integer> queue = new LinkedList<Integer>();
		
		int start = findStartFace();	// 외부이면 0을 반환
		queue.offer(start);
		visited[start] = 0;
		
		HashSet<Integer> outCheck = new HashSet<Integer>();	// 아우터 페이스에 인접한 페이스를 저장할 해쉬 셋	
		Edge adjOuter = adjFaceList[0];
		while(adjOuter!=null)
		{
			outCheck.add(adjOuter.v[1]);
			adjOuter = adjOuter.next;
		} // 아우터 페이스로 BFS의 베이스케이스를 탐지

		while(start>0&&!queue.isEmpty())
		{
			int cur = queue.poll();
			Edge curEdge = adjFaceList[cur];
			while(curEdge!=null)
			{
				if(outCheck.contains(cur))	// 다음 페이스가 아우터 페이스와 인접했는지 검사
				{
					return visited[cur]+1;
				}
				int next = curEdge.v[1];
				visited[cur] = visited[cur];
				if(visited[next]<0)
				{
					queue.offer(next);
					visited[next] = visited[cur]+1;
				}
				curEdge = curEdge.next;
			}
		}
		return 0;	// 외부로부터 출발하는 점이 주어지면 0을 반환
	}
	
	/* 페이스로 또다른 그래프를 형성 */
	private boolean isSameEdge(Edge a, Edge b)
	{	// 두 에지가 동일 한지 검사
		if(a.v[0]==b.v[0]) return a.v[1]==b.v[1];
		else if(a.v[0]==b.v[1])
			return a.v[1]==b.v[0];
		else return false;
	}
	private boolean isAdjFace(int a, int b)
	{	// 두 페이스에 동일한 에지를 공유하는지 검사
		Face fi = faces.get(a);
		Face fj = faces.get(b);
		for(int i=0;i<fi.size;i++)
		{
			Edge p = fi.elements.get(i);
			for(int j=0;j<fj.size;j++)
			{
				Edge q = fj.elements.get(j);
				if(isSameEdge(p,q))
					return true;
			}
		}
		return false;
	}
	private void  makeAdjFace()
	{
		adjFaceList = new Edge[faces.size()];	// 페이스 테이블 개수 만큼 페이스의 인접리스트 생성.
		// 아우터 페이스를 포함하여 인접리스트를 만든다!
		for(int i=0;i<faces.size();i++)
		{
			for(int k=0;k<faces.size();k++)	// i번째를 제외한 나머지 모든 페이스들과 교차 검색
			{
				if(i!=k && isAdjFace(i,k))	// 페이스 i,k에서 동일한 에지가 있으면 인접
				{	// adjFaceList에 add(k,p)
					Edge faceEdge = new Edge(i,k);
					faceEdge.next = adjFaceList[i];
					adjFaceList[i] = faceEdge;
				}
			}
		}
	}
	
	/* 페이스 탐지 부분 */
	// find all inner face
	private void findAllFace()
	{	// 아우터 페이스를 제외한 모든 내부 페이스를 찾아 차수가 K인 페이스를 카운트 한다.
		for(int i=0;i<N;i++)
		{
			Edge p = adjList[i];
			while(p!=null)
			{
				int start = i; int end = p.v[1];
				findFace(start,end);
				p = p.next;
			}
		}
	}
	// find a new face
	private void findFace(int start, int end)
	{	// 한 페이스를 찾을 때까지 반복하는 함수. start에서 출발해 end로 끝나는 에지를 시작으로 페이스를 순회한다?
		Face temp = new Face();	// 이번에 찾은 페이스는 새로 형성하여 추가한다
		int cnt=0;
		int u = start;			// 최초 방물할 에지의 시작점
		int v = end;			// 최초 방문할 에지의 끝점
		Edge p = adjList[u];	// 최초 방문할 에지.
		Edge q = adjList[v];	// 다음에 방문할 에지.
		while(p!=null)
		{		
			if(!p.visited)	// 이 방문할 에지가 방문하지 않았을 때
			{
				p.visited=true;	// 방문함을 체크
				temp.elements.add(p);	// 방문한 에지를 이번에 탐지하는 페이스에 추가
				cnt++;
				if(v==start) {		// 현재 방문할 에지의 끝점이 face를 찾기 시작한 에지의 시작점이 되면 한 face를 찾은 것임. 이 때가 bace case
					temp.size=cnt;
					faces.add(temp);
					return ;	// 페이스 탐지 한 경우
				}
				Edge pre = null; // q의 바로 앞 노드를 따라가는 프리디세서
				while(pre==null || (pre!=null && pre.v[1]!=u))
				{
					pre = q;
					q = q.next;
					if(q==null)	// circular하게 진행해야 하므로 다시 리스트의 맨 처음으로 돌아간다
					{
						q=adjList[v];
					}
				}
				u = v;
				v = q.v[1];
				p = q;
				q = adjList[v];
			}
			else
			{
				p=p.next;
				if(p!=null) {
					q=adjList[p.v[1]];
					v=p.v[1];
				}
				
			}
		}
		return ;	// 페이스 없는 경우
	}
	// detect the outer face
	private int findMinX()
	{
		int minX=-1;
		int minIndex=-1;
		for(int i=0;i<N;i++)
		{
			if(minX==-1 || minX>points[i].x)
			{
				minX=points[i].x;
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
		int end = adjList[start].v[1];
		// 그 에지로 시작하는 face를 찾는다.
		findFace(start,end);
	}

	// Clockwise Angular Sort
	private void angularSort(int index)
	{	// 인접 리스트에서 해당 정점 인덱스의 인접한 에지를 시계방향 순으로 각정렬 함
		Edge[] tempEdges = new Edge[outdegree[index]];	// 정렬할 에지의 인덱스의 배열
		// 원래 인접리스트의 각 노드를 temp의 각 원소로  저장
		Edge p = adjList[index];
		int edgeIndex = 0;
		while(p!=null)
		{
			tempEdges[edgeIndex++] = new Edge(index,p.v[1]);
			p=p.next;	// 한 연결 리스트 순회
		}
		Arrays.sort(tempEdges);

		adjList[index] = null;
		for(int i=tempEdges.length-1;i>=0;i--)
		{
			add(index,tempEdges[i].v[1]);
		}
	}
	private void rebuildAdjList()
	{
		for(int i=0;i<N;i++)	// 모든 정점에 대하여 수행. N번 반복
		{
			angularSort(i);	// 해당 정점의 인접 에지들을 각정렬 함
		}
	}

	// 파일 읽고 자료구조 생성
	private void add(int r, int i)	// 정점 r에 인접한 정점 i를 추가함. 항상 맨 앞에 추가함.
	{
		Edge p = new Edge(r,i);
		p.next = adjList[r];
		adjList[r] = p;
	}
	private void initGraph()
	{
		points = new Point[N];		// 전체 정점 정보만 저장 하는 배열
		adjList = new Edge[N];		// 그래프를 표현하는 인접리스트
		outdegree = new int[N];		// 별도의 아웃디그리 배열
		faces = new Vector<Face>();	// 페이스들의 그래프
	}

	private void readFile(Scanner sc)
	{
		N = sc.nextInt();
		startP = new Point(sc.nextInt(),sc.nextInt()); 
		initGraph();
		for(int i=0;i<N;i++)
		{
			int index = sc.nextInt()-1;	// 배열 인덱스는 항상 -1 해줘야 함
			int x=sc.nextInt();
			int y=sc.nextInt();
			points[index] = new Point(x,y);
			for(int j=sc.nextInt();j>0;j--)
			{
				int v = sc.nextInt()-1;
				add(index,v);
				outdegree[index]++;
			}
		}
	}
	public static void main(String[] args) {
		Problem42 theApp = new Problem42();
		try {
			Scanner sc = new Scanner(new File("input42.txt"));
			for(int T=sc.nextInt();T>0;T--) {	// T번 반복
				theApp.readFile(sc);	// file read complete
				theApp.rebuildAdjList();// 모든 정점의 인접한 에지에 대하여 각정렬 수행하여 새 인접 리스트를 만든다
				theApp.detectOuterFace();	// 아우터 페이스를 얻는다.
				theApp.findAllFace();		// 모든 페이스를 찾아 페이스 테이블 생성
				theApp.makeAdjFace();		// 페이스 테이블을 서로 검사하여 페이스들 간의 인접관계 정의
				
				System.out.println(theApp.solve());
			}
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
}
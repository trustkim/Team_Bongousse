package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Problem44 {
	private class Point implements Comparable<Point>
	{
		int x,y;
		Point(int x,int y) {this.x=x;this.y=y;}
		public double getDist(Point p)
		{
			return (Math.sqrt(Math.pow(p.x-this.x,2)+Math.pow(p.y-this.y,2)));
		}
		
		@Override
		public int compareTo(Point q) {
			// TODO Auto-generated method stub
			double op = getDist(start);	// 출발점으로부터의 거리를 계산
			double dp = getDist(dest);	// 도착점으로부터의 거리를 계산
			double oq = q.getDist(start);
			double dq = q.getDist(dest);
			
			if(op<=oq) return -1;	// 시작점에 더 가까운 점이 더 작다
			if(dp<=dq) return 1;	// 도착점에 더 가까운 점이 더 크다
			
			return 0;
		}
	}
	private class Edge
	{
		//Point u,v;
		int u,v;
		Edge next;
		Edge(int u, int v)
		{
			this.u = u; this.v=v; next=null;
		}
		private int crossProduct(Point pi, Point pj)
		{
			return (pi.x*pj.y-pi.y*pj.x);
		}
		public boolean intersects(Edge other)
		{
			Point u = allPoints.get(this.u);
			Point v = allPoints.get(this.v);
			Point other_u = allPoints.get(other.u);
			Point other_v = allPoints.get(other.v);
			Point line = new Point(v.x-u.x,v.y-u.y);
			Point other1 = new Point(other_u.x-u.x,other_u.y-u.y);
			Point other2 = new Point(other_v.x-u.x,other_v.y-u.y);
			int result1 = crossProduct(line,other1);
			int result2 = crossProduct(line,other2);
			if(result1!=0) result1 /= Math.abs(result1);
			if(result2!=0) result2 /= Math.abs(result2);
			if(result1*result2 >= 0)
				return false;
			
			line = new Point(other_v.x-other_u.x,other_v.y-other_u.y);
			other1 = new Point(u.x-other_u.x,u.y-other_u.y);
			other2 = new Point(v.x-other_u.x,v.y-other_u.y);
			result1 = crossProduct(line,other1);
			result2 = crossProduct(line,other2);
			if(result1!=0) result1 /= Math.abs(result1);
			if(result2!=0) result2 /= Math.abs(result2);
			if(result1*result2 >= 0)
				return false;
			
			return true;
		}
	}
	private class Rectangle
	{
		int[] v;
		private int w,h;
		Edge[] edges;
		Rectangle(int x,int y,int w,int h)
		{
			v = new int[4];
			v[0] = allPoints.size();
			allPoints.addElement(new Point(x,y));
			v[1] = allPoints.size();
			allPoints.addElement(new Point(x,y+h));
			v[2] = allPoints.size();
			allPoints.addElement(new Point(x+w,y+h));
			v[3] = allPoints.size();
			allPoints.addElement(new Point(x+w,y));
			this.w=w; this.h=h;
			edges = new Edge[4];
			edges[0] = new Edge(v[0],v[1]);
			edges[1] = new Edge(v[1],v[2]);
			edges[2] = new Edge(v[2],v[3]);
			edges[3] = new Edge(v[3],v[0]);
		}
	}
	private int M;	// 직사각형의 개수
	private Point start;	// 출발점
	private Point dest;		// 도착점
	private Vector<Point> allPoints;	// 모든 점의 테이블
	private Rectangle[] rects;	// 직사각형 배열
	private Vector<Integer> samples;// 출발점 도착점 사이의 선분에 교점을 갖는 직사각형의 인덱스 배열
	private Vector<Point> points;	// 출발점 도착점 사이의 선분에 교점을 갖는 직사각형 위의 모든 점들의 배열
	private int N;	// 선별된 점의 개수
	private Edge[] edgeTable;	// 

	/* Dijkstra를 위한 멤버 */
	private double[] key;
	private int[] pi;
	private boolean[] include;
	private void initDijkstra()
	{
		key = new double[N];	// 전체 정점들의 key값을 매번 갱신시켜 나감. 따라서 N개
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
				double temp = key[i];
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
		key[0] = 0;		// 시작 정점을 정함.
		int cnt = 0;
		while(cnt<N)		// n-1번 반복. 따로 dest까지 결정되면 멈추도록 하지 않음.
		{
			int u = findMinKey();			// S에 속하지 않고 key가 가장 낮은 정점을 찾음
			include[u] = true; cnt++;		// 그 u를 S에 포함 시킴
			Edge p = edgeTable[u];
			while(p!=null)					// u의 모든 인접한 정점 중 S에 포함 되지 않은 v의 key[v], pi[v]를 갱신
			{
				int v = p.v;
				if(include[v])
					;// S에 포함된 key[v]는 갱신하지 않음
				// double weight = findWeight(p,type);
				double dist = allPoints.get(p.u).getDist(allPoints.get(v));
				if(key[v]==-1 || key[v] > key[u]+dist)
				{
					key[v] = key[u]+dist;
					pi[v] = u;
				}
				p = p.next;
			}
		}
		//System.out.println("dijkstra compelete");
		System.out.println((int)key[1]);
	}
	private boolean isAdjEdge(Edge p)
	{
		for(Rectangle ri:rects)
		{
			//Rectangle ri = rects[i];
			for(Edge q:ri.edges)
			{
				if(p.intersects(q))
				{
					return false;	
				}
			}
		}
		return true;
	}
	private void add(Edge p)
	{
		if(edgeTable[p.u]==null)
			edgeTable[p.u] = p;
		else
		{
			p.next = edgeTable[p.u];
			edgeTable[p.u] = p;	
		}
		
	}
	private void makeWeightTable()
	{
		for(int i=0;i<allPoints.size();i++)	// 모든 점에 대하여
		{
			for(int j=0;j<allPoints.size();j++)
			{
				if(i!=j)
				{
					int u,v;
					if(allPoints.get(i).compareTo(allPoints.get(j))==-1)
					{
						u = i;
						v = j;
					}else
					{
						u = j;
						v = i;
					}
					Edge p = new Edge(u,v);
					if(isAdjEdge(p))
						add(p);
				}
			}
		}
	}
	private void findRect()
	{	// 출발점 도착점을 잊는 선분에 교점을 갖는 직사각형을 추려냄.
//		Edge line = new Edge(0,1);
//		for(int i=0;i<M;i++)
//		{
//			Rectangle ri = rects[i];
//			for(Edge p:ri.edges)
//			{
//				//if(line.intersects(p))
//				//{
//					//System.out.println(i);
//					samples.add(i);
//					for(int j=0;j<4;j++)
//					{
//						points.add(allPoints.get(ri.v[j]));
//						//interRect.add(i);
//						
//					}
//					//break;	
//				//}
//			}
//		}
		N = allPoints.size();
		edgeTable = new Edge[N];
	}
	private void init()
	{	// 필요한 전역 자료구조를 한 곳에서 초기화 함. 알아보기 쉬우라고
		samples = new Vector<Integer>();
		points = new Vector<Point>();
		points.add(start); points.add(dest);
	}
	private void readFile(Scanner sc)
	{
		M = sc.nextInt();
		start = new Point(sc.nextInt(),sc.nextInt());
		dest = new Point(sc.nextInt(),sc.nextInt());
		rects = new Rectangle[M];
		allPoints = new Vector<Point>();
		allPoints.add(start); allPoints.add(dest);
		for(int i=0;i<M;i++)
		{
			rects[i] = new Rectangle(sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt());
		}
		//System.out.println("file read complete!");
	}
	public static void main(String[] args)
	{
		Problem44 theApp = new Problem44();
		Scanner sc = null;;
		try	{
			sc = new Scanner(new File("input44.txt"));
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
		for(int T=sc.nextInt();T>0;T--)
		{
			theApp.readFile(sc);
			theApp.init();
			theApp.findRect();
			//theApp.pointsPrint();
			theApp.makeWeightTable();
			//theApp.edgesPrint();
			theApp.dijkstra();
		}
		sc.close();
	}
	private void pointsPrint()
	{
		for(Point p:points)
		{
			System.out.println(p.x+", "+p.y);
		}
	}
	private void edgesPrint()
	{
		for(Edge p:edgeTable)
		{
			if(p!=null) System.out.println("("+allPoints.get(p.u).x+", "+allPoints.get(p.u).y+") -> ("+allPoints.get(p.v).x+", "+allPoints.get(p.v).y+")");
		}
	}
}

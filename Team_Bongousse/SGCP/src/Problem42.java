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
		int[] v;	// ���� �׷����� ���� �ϴ� ���� �� ������ ���̴�.
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
		int size;		// ���̽��� �̷�� ���� ����
		Vector<Edge> elements;	// �����ϴ� ����
		Face()
		{
			size=0; elements = new Vector<Edge>();
		}
	}
	
	private int N;				// ��ü �׷����� ���� ����
	private Point startP;;		// Ż�� ���� ��
	
	private Edge[] adjList;		// �׷����� ǥ���� ���� ����Ʈ. ������ id�θ� ǥ��
	private Point[] points;
	private int[] outdegree;	// ������ outdegree�迭
	
	private Vector<Face> faces;	// ���̽� ���̺�. findAllFace() �ϸ鼭 �ϳ��� �߰��� ���� �Ŷ� ���ͷ� ����.
	private Edge[] adjFaceList;	// ���̽����� ��������Ʈ
	
	/* ���� Ż���̴� */
	private boolean isInnerPoint(int faceIndex)
	{
		Face fi = faces.get(faceIndex);	// ���� �˻��Ϸ��� face
		int interCnt = 0;
		for(int i=0;i<fi.size;i++)	// fi�� ��� ������ �湮�ϸ鼭 �˻�
		{
			Point O = new Point(-1,-1);	// ������ ���� �������� �غ�
			Edge p = fi.elements.get(i);// fi�� i��° ����
			if(p.intersects(O, startP))
				interCnt++;
		}
		
		return interCnt%2!=0;
	}
	private int findStartFace()
	{
		for(int i=1;i<faces.size();i++)	// outer face�� ������ ��� face�� ���� �˻�
		{
			// i��° face�� ��� ���� ���� ���� ������ ������ ���� ���̿� �־��� ���� �̷�� ������ ��ġ�ϴ��� �˻�
			if(isInnerPoint(i))
				return i;
		}
		return 0;	// � ���� face ������ ���� ������ ������ ���ο� ���� ���� ����. 0�� ��ȯ.
	}
	private int solve()
	{
		int[] visited = new int[adjFaceList.length];
		for(int i=0;i<visited.length;i++)
		{
			visited[i] = -1;
		}
		Queue<Integer> queue = new LinkedList<Integer>();
		
		int start = findStartFace();	// �ܺ��̸� 0�� ��ȯ
		queue.offer(start);
		visited[start] = 0;
		
		HashSet<Integer> outCheck = new HashSet<Integer>();	// �ƿ��� ���̽��� ������ ���̽��� ������ �ؽ� ��	
		Edge adjOuter = adjFaceList[0];
		while(adjOuter!=null)
		{
			outCheck.add(adjOuter.v[1]);
			adjOuter = adjOuter.next;
		} // �ƿ��� ���̽��� BFS�� ���̽����̽��� Ž��

		while(start>0&&!queue.isEmpty())
		{
			int cur = queue.poll();
			Edge curEdge = adjFaceList[cur];
			while(curEdge!=null)
			{
				if(outCheck.contains(cur))	// ���� ���̽��� �ƿ��� ���̽��� �����ߴ��� �˻�
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
		return 0;	// �ܺηκ��� ����ϴ� ���� �־����� 0�� ��ȯ
	}
	
	/* ���̽��� �Ǵٸ� �׷����� ���� */
	private boolean isSameEdge(Edge a, Edge b)
	{	// �� ������ ���� ���� �˻�
		if(a.v[0]==b.v[0]) return a.v[1]==b.v[1];
		else if(a.v[0]==b.v[1])
			return a.v[1]==b.v[0];
		else return false;
	}
	private boolean isAdjFace(int a, int b)
	{	// �� ���̽��� ������ ������ �����ϴ��� �˻�
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
		adjFaceList = new Edge[faces.size()];	// ���̽� ���̺� ���� ��ŭ ���̽��� ��������Ʈ ����.
		// �ƿ��� ���̽��� �����Ͽ� ��������Ʈ�� �����!
		for(int i=0;i<faces.size();i++)
		{
			for(int k=0;k<faces.size();k++)	// i��°�� ������ ������ ��� ���̽���� ���� �˻�
			{
				if(i!=k && isAdjFace(i,k))	// ���̽� i,k���� ������ ������ ������ ����
				{	// adjFaceList�� add(k,p)
					Edge faceEdge = new Edge(i,k);
					faceEdge.next = adjFaceList[i];
					adjFaceList[i] = faceEdge;
				}
			}
		}
	}
	
	/* ���̽� Ž�� �κ� */
	// find all inner face
	private void findAllFace()
	{	// �ƿ��� ���̽��� ������ ��� ���� ���̽��� ã�� ������ K�� ���̽��� ī��Ʈ �Ѵ�.
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
	{	// �� ���̽��� ã�� ������ �ݺ��ϴ� �Լ�. start���� ����� end�� ������ ������ �������� ���̽��� ��ȸ�Ѵ�?
		Face temp = new Face();	// �̹��� ã�� ���̽��� ���� �����Ͽ� �߰��Ѵ�
		int cnt=0;
		int u = start;			// ���� �湰�� ������ ������
		int v = end;			// ���� �湮�� ������ ����
		Edge p = adjList[u];	// ���� �湮�� ����.
		Edge q = adjList[v];	// ������ �湮�� ����.
		while(p!=null)
		{		
			if(!p.visited)	// �� �湮�� ������ �湮���� �ʾ��� ��
			{
				p.visited=true;	// �湮���� üũ
				temp.elements.add(p);	// �湮�� ������ �̹��� Ž���ϴ� ���̽��� �߰�
				cnt++;
				if(v==start) {		// ���� �湮�� ������ ������ face�� ã�� ������ ������ �������� �Ǹ� �� face�� ã�� ����. �� ���� bace case
					temp.size=cnt;
					faces.add(temp);
					return ;	// ���̽� Ž�� �� ���
				}
				Edge pre = null; // q�� �ٷ� �� ��带 ���󰡴� �����𼼼�
				while(pre==null || (pre!=null && pre.v[1]!=u))
				{
					pre = q;
					q = q.next;
					if(q==null)	// circular�ϰ� �����ؾ� �ϹǷ� �ٽ� ����Ʈ�� �� ó������ ���ư���
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
		return ;	// ���̽� ���� ���
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
		// ���� ���� ����(x��ǥ�� �ּ��� ����)�� ã�´�.
		int start = findMinX();
		// ���Ⱑ �ִ��� ������ ã�´�. �� ������ ���� �� ���� ���� ���� ����.
		int end = adjList[start].v[1];
		// �� ������ �����ϴ� face�� ã�´�.
		findFace(start,end);
	}

	// Clockwise Angular Sort
	private void angularSort(int index)
	{	// ���� ����Ʈ���� �ش� ���� �ε����� ������ ������ �ð���� ������ ������ ��
		Edge[] tempEdges = new Edge[outdegree[index]];	// ������ ������ �ε����� �迭
		// ���� ��������Ʈ�� �� ��带 temp�� �� ���ҷ�  ����
		Edge p = adjList[index];
		int edgeIndex = 0;
		while(p!=null)
		{
			tempEdges[edgeIndex++] = new Edge(index,p.v[1]);
			p=p.next;	// �� ���� ����Ʈ ��ȸ
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
		for(int i=0;i<N;i++)	// ��� ������ ���Ͽ� ����. N�� �ݺ�
		{
			angularSort(i);	// �ش� ������ ���� �������� ������ ��
		}
	}

	// ���� �а� �ڷᱸ�� ����
	private void add(int r, int i)	// ���� r�� ������ ���� i�� �߰���. �׻� �� �տ� �߰���.
	{
		Edge p = new Edge(r,i);
		p.next = adjList[r];
		adjList[r] = p;
	}
	private void initGraph()
	{
		points = new Point[N];		// ��ü ���� ������ ���� �ϴ� �迭
		adjList = new Edge[N];		// �׷����� ǥ���ϴ� ��������Ʈ
		outdegree = new int[N];		// ������ �ƿ���׸� �迭
		faces = new Vector<Face>();	// ���̽����� �׷���
	}

	private void readFile(Scanner sc)
	{
		N = sc.nextInt();
		startP = new Point(sc.nextInt(),sc.nextInt()); 
		initGraph();
		for(int i=0;i<N;i++)
		{
			int index = sc.nextInt()-1;	// �迭 �ε����� �׻� -1 ����� ��
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
			for(int T=sc.nextInt();T>0;T--) {	// T�� �ݺ�
				theApp.readFile(sc);	// file read complete
				theApp.rebuildAdjList();// ��� ������ ������ ������ ���Ͽ� ������ �����Ͽ� �� ���� ����Ʈ�� �����
				theApp.detectOuterFace();	// �ƿ��� ���̽��� ��´�.
				theApp.findAllFace();		// ��� ���̽��� ã�� ���̽� ���̺� ����
				theApp.makeAdjFace();		// ���̽� ���̺��� ���� �˻��Ͽ� ���̽��� ���� �������� ����
				
				System.out.println(theApp.solve());
			}
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
}
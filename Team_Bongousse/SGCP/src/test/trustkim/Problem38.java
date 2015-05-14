package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
//import java.util.HashMap;
import java.util.Scanner;

public class Problem38 {
	private class Node // ���� ����Ʈ�� ���� ��� ������ ǥ���ϴ� Point�� �ٸ��� ���� ������ ����
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
		int outdegree;	// ������ �� ���ο� ���� ����Ʈ�� ���� �� �ʿ�
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
	
	private int N;				// ��ü �׷����� ���� ����
	private int K;				// ã�� face�� �̷�� ���� ����
	
	private Node[] adjList;		// �׷����� ǥ���� ���� ����Ʈ. ������ id�θ� ǥ��
	private Vertex[] vertices;	// ��� ������ ��ǥ ���̺�
	//private HashMap<Edge, Integer[]> edges;		// ��� ������ ��. ������� �� �ȵǾ���.
	
	// find the face
	private void findFace(int start, int end)
	{
		;
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
	private void outLine()
	{
		// ���� ���� ����(x��ǥ�� �ּ��� ����)�� ã�´�.
		int start = findMinX();
		// ���Ⱑ �ִ��� ������ ã�´�. �� ������ ���� �� ���� ���� ���� ����.
		int end = adjList[start].index;
		// �� ������ �����ϴ� face�� ã�´�.
		System.out.println(start+", "+end);
		findFace(start,end);
	}
	// Clockwise Angular Sort
	private int getAddIndex(int index, int[] keys)
	{
		if(index==keys[0]) return keys[1];
		else return keys[0];
	}
	private void angularSort(int index)
	{	// ���� ����Ʈ���� �ش� ���� �ε����� ������ ������ �ð���� ������ ������ ��
		//System.out.println(index+":");
		Edge[] tempEdges = new Edge[vertices[index].outdegree];	// ������ ������ �ε����� �迭
		// ���� ��������Ʈ�� �� ��带 temp�� �� ���ҷ�  ����
		Node p = adjList[index];
		int edgeIndex = 0;
		while(p!=null)
		{
			//tempEdges[edgeIndex++] = new Edge(getKeys(index,p.index));
			tempEdges[edgeIndex++] = new Edge(index,p.index);
			p=p.next;	// �� ���� ����Ʈ ��ȸ
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
		for(int i=0;i<N;i++)	// ��� ������ ���Ͽ� ����. N�� �ݺ�
		{
			angularSort(i);	// �ش� ������ ���� �������� ������ ��
		}
		//System.out.println("rebuildAdjList complete");
	}
	
	// ���� �а� �ڷᱸ�� ����
	private void add(int r, int i)	// ���� r�� ������ ���� i�� �߰���. �׻� �� �տ� �߰���.
	{
		//Vertex copyValue = vertices[i];
		Node p = new Node(i);
		p.next = adjList[r];
		adjList[r] = p;
	}
	private void initGraph()
	{
		vertices = new Vertex[N];	// ��ü ���� ������ ���� �ϴ� �迭
		adjList = new Node[N];		// �׷����� ǥ���ϴ� ��������Ʈ
		//edges = new HashMap<Edge, Integer[]>();
	}
	private Integer[] getKeys(int u, int v)
	{
		int temp=u;
		if(temp!=v)
		{
			u = Math.min(temp, v);
			v = Math.max(temp, v);	
		}
		Integer[] key = new Integer[2];
		key[0] = u;
		key[1] = v;
		
		return key;
	}
	private void readFile(Scanner sc)
	{
		N = sc.nextInt();
		K = sc.nextInt();
		initGraph();
		for(int i=0;i<N;i++)
		{
			int index = sc.nextInt()-1;	// �迭 �ε����� �׻� -1 ����� ��
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
			for(int T=sc.nextInt();T>0;T--) {	// T�� �ݺ�
				theApp.readFile(sc);	// file read complete
				theApp.pointsPrint();	// test print
				//theApp.adjPrint();	// test print
				theApp.rebuildAdjList();// ��� ������ ������ ������ ���Ͽ� ������ �����Ͽ� �� ���� ����Ʈ�� �����
				theApp.adjPrint();
				theApp.outLine();	// �ƿ��� ���̽��� ��´�.
				// 
			}
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
	private void pointsPrint()
	{
		System.out.println("points table:");
		for(int i=0;i<N;i++)
		{
			System.out.println("["+(i+1)+"]: ("+vertices[i].x+", "+vertices[i].y+")");
		}
	}
	private void adjPrint()
	{
		System.out.println("adjList:");
		for(int i=0;i<N;i++)
		{
			Node p = adjList[i];
			System.out.print("["+(i+1)+"]: ");
			//p = p.next;
			while(p!=null) {
				System.out.print("["+(p.index+1)+"], ");
				p = p.next;
			}
			System.out.println();
		}
	}
}

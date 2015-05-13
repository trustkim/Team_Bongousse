package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem38 {
	private class Point
	{
		private int x,y;
		Point next;
		Point(int x,int y){this.x=x; this.y=y; next=null;}
	}
	private class Node
	{
		int index;
		Node next;
		Node(int index) {this.index=index;next=null;}
	}
	private int N;				// ��ü �׷����� ���� ����
	private int K;				// ã�� face�� �̷�� ���� ����
	private Point[] points;	// ��� ������ ��ǥ ���̺�
	private Node[] adjList;	// �׷����� ǥ���� ���� ����Ʈ. ������ id�θ� ǥ��
	private void initAdjList()
	{
		points = new Point[N];
		adjList = new Node[N];
		for(int i=0;i<N;i++)
		{
			adjList[i] = new Node(i+1);
		}
	}
	private void add(int r, int i)	// ���� r�� ������ ���� p�� �߰���. �׻� ��� ���� �ٷ� �ڿ� �߰���.
	{
		Node p = new Node(adjList[i].index);
		p.next = adjList[r].next;
		adjList[r].next = p;
	}
	private void readFile(Scanner sc)
	{
		N = sc.nextInt();
		K = sc.nextInt();
		initAdjList();
		for(int i=0;i<N;i++)
		{
			int index = sc.nextInt()-1;	// �迭 �ε����� �׻� -1 ����� ��
			points[index] = new Point(sc.nextInt(),sc.nextInt());
			for(int j=sc.nextInt();j>0;j--)
			{
				add(index,sc.nextInt()-1);
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
				theApp.adjPrint();		// test print
			}
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
	private void pointsPrint()
	{
		System.out.println("points:");
		for(int i=0;i<N;i++)
		{
			
		}
	}
	private void adjPrint()
	{
		System.out.println("adjList:");
		for(int i=0;i<N;i++)
		{
			Node p = adjList[i];
			System.out.print("["+p.index+"] : ");
			p = p.next;
			while(p!=null) {
				System.out.print("["+p.index+"], ");
				p = p.next;
			}
			System.out.println();
		}
	}
}

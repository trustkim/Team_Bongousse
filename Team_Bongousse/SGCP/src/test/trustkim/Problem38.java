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
	private int N;				// 전체 그래프의 정점 개수
	private int K;				// 찾는 face를 이루는 정점 개수
	private Point[] points;	// 모든 정점의 좌표 테이블
	private Node[] adjList;	// 그래프를 표현한 인접 리스트. 정점의 id로만 표현
	private void initAdjList()
	{
		points = new Point[N];
		adjList = new Node[N];
		for(int i=0;i<N;i++)
		{
			adjList[i] = new Node(i+1);
		}
	}
	private void add(int r, int i)	// 정점 r에 인접한 정점 p를 추가함. 항상 헤드 정점 바로 뒤에 추가함.
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
			int index = sc.nextInt()-1;	// 배열 인덱스는 항상 -1 해줘야 함
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
			for(int T=sc.nextInt();T>0;T--) {	// T번 반복
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

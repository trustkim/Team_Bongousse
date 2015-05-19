package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Problem44 {
	private class Point
	{
		int x,y;
		Point(int x,int y) {this.x=x;this.y=y;}
		public double getDist(Point p)
		{
			return (Math.sqrt(Math.pow(p.x-this.x,2)+Math.pow(p.y-this.y,2)));
		}
	}
	private class Edge
	{
		Point u,v;
		Edge(Point u, Point v)
		{
			this.u = u; this.v=v;
		}
		private int crossProduct(Point pi, Point pj)
		{
			return (pi.x*pj.y-pi.y*pj.x);
		}
		public boolean intersects(Edge other)
		{
			Point line = new Point(v.x-u.x,v.y-u.y);
			Point other1 = new Point(other.u.x-u.x,other.u.y-u.y);
			Point other2 = new Point(other.v.x-u.x,other.v.y-u.y);
			int result1 = crossProduct(line,other1);
			int result2 = crossProduct(line,other2);
			if(result1!=0) result1 /= Math.abs(result1);
			if(result2!=0) result2 /= Math.abs(result2);
			if(result1*result2 >= 0)
				return false;
			
			line = new Point(other.v.x-other.u.x,other.v.y-other.u.y);
			other1 = new Point(u.x-other.u.x,u.y-other.u.y);
			other2 = new Point(v.x-other.u.x,v.y-other.u.y);
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
		Point[] vertices;
		int w,h;
		Edge[] edges;
		Rectangle(int x,int y,int w,int h)
		{
			vertices = new Point[4];
			vertices[0] = new Point(x,y);
			vertices[1] = new Point(x,y+h);
			vertices[2] = new Point(x+w,y+h);
			vertices[3] = new Point(x+w,y);
			this.w=w; this.h=h;
			edges = new Edge[4];
			edges[0] = new Edge(vertices[0],vertices[1]);
			edges[1] = new Edge(vertices[1],vertices[2]);
			edges[2] = new Edge(vertices[2],vertices[3]);
			edges[3] = new Edge(vertices[3],vertices[0]);
		}
	}
	private int M;	// 직사각형의 개수
	private Point start;	// 출발점
	private Point dest;		// 도착점
	private Rectangle[] rects;	// 직사각형 배열
	private Vector<Integer> interRect;	// 출발점 도착점 사이의 선분에 교점을 갖는 직사각형의 인덱스를 저장한 배열
	private Vector<Point> points;	// 출발점 도착점 사이의 선분에 교점을 갖는 직사각형 위의 모든 점들의 배열
	private void makeWeightTable()
	{
		
	}
	private void findRect()
	{	// 출발점 도착점을 잊는 선분에 교점을 갖는 직사각형을 추려냄.
		Edge line = new Edge(start,dest);
		interRect = new Vector<Integer>();
		points = new Vector<Point>();
		for(int i=0;i<M;i++)
		{
			Rectangle ri = rects[i];
			for(Edge p:ri.edges)
			{
				if(line.intersects(p))
				{
					System.out.println(i);
					for(int j=0;j<4;j++)
					{
						points.add(ri.vertices[j]);
						interRect.add(i);
						
					}
					break;	
				}
			}
		}
	}
	private void readFile(Scanner sc)
	{
		M = sc.nextInt();
		start = new Point(sc.nextInt(),sc.nextInt());
		dest = new Point(sc.nextInt(),sc.nextInt());
		rects = new Rectangle[M];
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
			theApp.findRect();
			theApp.pointsPrint();
			theApp.makeWeightTable();
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
}

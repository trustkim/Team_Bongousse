package test.trustkim;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("serial")
public class GraphDrawer extends JPanel {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("Graph Drawer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphDrawer theApp = new GraphDrawer();
		theApp.getGraph(new File(".\\src\\test\\trustkim\\graph.txt"));
		frame.add(theApp);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}

	public GraphDrawer() {
		setPreferredSize(new Dimension(800, 800));
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		for (int i=1; i<vertices.length; i++) {
			Vertex v = vertices[i];
			g.fillOval(convertToPixelX(v.x)-4, convertToPixelY(v.y)-4, 8, 8);
			g.drawString(""+v.v, convertToPixelX(v.x)-4, convertToPixelY(v.y)-4);
			Node p = v.first;
			while(p!=null) {
				Vertex w = vertices[p.vertex];
				g.drawLine(convertToPixelX(v.x), convertToPixelY(v.y), convertToPixelX(w.x), convertToPixelY(w.y));
				p = p.next;
			}
		}
		// 별도의 위치에 그림을 그리려면 여기!
		// g.setColor(Color.RED);
		// g.fillOval(convertToPixelX(60)-4, convertToPixelY(50)-4, 8, 8);
	}

	private Vertex[] vertices;
	private int minX=Integer.MAX_VALUE, minY=Integer.MAX_VALUE, maxX=-Integer.MAX_VALUE, maxY=-Integer.MAX_VALUE;
	private void getGraph(File inputFile) {
		Scanner sc = null;
		try {
			sc = new Scanner(inputFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int N = sc.nextInt();
		vertices = new Vertex[N + 1];
		for (int i = 1; i <= N; i++) {
			int v = sc.nextInt();
			int x = sc.nextInt();
			int y = sc.nextInt();

			if (x<minX) minX = x;
			if (y<minY) minY = y;
			if (x>maxX) maxX = x;
			if (y>maxY) maxY = y;

			vertices[v] = new Vertex(v, x, y);
			int nbrEdges = sc.nextInt();
			vertices[v].degree = nbrEdges;
			for (int j = 0; j < nbrEdges; j++) {
				int w = sc.nextInt();
				Node node = new Node(w);
				if (vertices[v].first == null)
					vertices[v].first = node;
				else {
					node.next = vertices[v].first;
					vertices[v].first = node;
				}
			}
		}
		sc.close();
	}


	private int convertToPixelX(int d) {
		return d*300/((maxX+minX)/2)+100;
	}

	private int convertToPixelY(int d) {
		return d*300/((maxY+minY)/2)+100;
	}

	static class Node {         // node in adjacent list
		int vertex;
	Node next;
	boolean used;
	public Node(int v) {
		vertex = v;
	}
	}

	static class Vertex {       // represents a vertex
		int v;
	int x;
	int y;
	int degree = 0;
	Node first;
	public Vertex(int v, int x, int y) {
		this.v = v;
		this.x = x;
		this.y = y;
	}
	}
}

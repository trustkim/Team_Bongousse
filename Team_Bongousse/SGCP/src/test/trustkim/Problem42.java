package test.trustkim;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class Problem42 {
	final static double pi = 3.1415926;
	
	private Node[] nodes;
	private int N;
	private int px, py;				// Ż�� ������
	private boolean[][] visited;	// face Ž�� �� �� �湮 ���� üũ
	private int[][] linesFace;		// ������ ��� ���̽��� ���ϴ��� �����ϴ� ���̺�
	// ���� ��� ���� ���� �ε����� ���� ���� ���̺� �߰� �ٶ�

	private class Node{
		int x, y, index;			// index�� NodeŸ�� �迭�� ���� �ε���
		int indegree = 0;			// ���� ����� indegree
		int outCount = 0;			//outdegree ���Ḯ��Ʈ�� ��
		Node outdegree = null;		// next. ���� ��忡 ������ ���
		Node(int index){ this.index = index; }
		Node(int index, int x, int y){ 
			this.index = index; 
			this.x = x; 
			this.y = y; 
		}
	}

	private class Edge implements Comparable<Edge>{	//� �ٿ����κ��� ���ĳ����� ����
		int tonode;									//�� ������ ���ϴ� ����� �迭�ε���
		double angle;								//�� ������ �ٿ������ ��������� �̷�� ����
		Edge(double angle, int tonode){ 
			this.angle = angle; 
			this.tonode = tonode;
		}
		public int compareTo(Edge other){			//angle�� ���� ������������ ���ĵ�
			if(angle < other.angle){ return -1; }
			else if(angle == other.angle){ return 0; }
			else { return 1; }
		}
	}
	
	private static class MyPoint{
		int x, y;
		MyPoint(int x, int y){
			this.x = x;
			this.y = y;
		}
	}
	private static class Line {	// �� ���� �մ� ����
        private MyPoint u, v;
        public Line(int x1, int y1, int x2, int y2) {
            u = new MyPoint(x1, y1);
            v = new MyPoint(x2, y2);
        }

        public Line(MyPoint p, MyPoint q) {
            u = p;
            v = q;
        }	// �� ���� �մ� ������ ����

        public boolean intersects(Line other) {
            int result1 = (other.v.x-other.u.x)*(u.y-other.u.y) - (u.x-other.u.x)*(other.v.y-other.u.y);
            int result2 = (other.v.x-other.u.x)*(v.y-other.u.y) - (v.x-other.u.x)*(other.v.y-other.u.y);
            if (result1*result2 >= 0)
                return false;

            result1 = (v.x-u.x)*(other.u.y-u.y) - (other.u.x-u.x)*(v.y-u.y);
            result2 = (v.x-u.x)*(other.v.y-u.y) - (other.v.x-u.x)*(v.y-u.y);
            if (result1*result2 >= 0)
                return false;
            return true;
        }	// �� ������ �����ϴ��� ���θ� ��ȯ
    }
	
	
	private class Face{
		int faceIndex;
	}


	private void readInput(String str){
		try{			
			Scanner input = new Scanner(new File(str));
			for(int T = input.nextInt(); T > 0; T--){
				N = input.nextInt();
				nodes = new Node[N];
				px = input.nextInt(); py = input.nextInt();
				visited = new boolean[N][N];	// ���̽� Ž�� �� �� ���Ƿ� �� �� �ʱ�ȭ ���ִ� �͵� ���� �� ����.
				linesFace = new int[N][N];		//-1:�ٱ�����, 1�̻�:face�ε���
				
				//�ϴ� input�а� ��ǥ, outdegree������ ��´�
				for(int i = 0; i < N; i++){
					nodes[i] = new Node(input.nextInt()-1, input.nextInt(), input.nextInt());
					nodes[i].outCount = input.nextInt();
					for(int k = nodes[i].outCount; k > 0; k--){
						Node newnode = new Node(input.nextInt()-1);
						newnode.outdegree = nodes[i].outdegree;
						nodes[i].outdegree = newnode;
					}
				}

				
				
				// <<<���� outlineŽ��, faceŽ��, ������ �� ã�� �� ����>>>
				//......
				//findOutline();
				//......
				
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
	}
	private double calcAngle(Node a, Node b){
		if(a.x >= b.x && a.y > b.y){ //��ǥ��� ���� ���(1��и�)
			return (Math.atan((a.x-b.x)/(a.y-b.y))/pi)*180;
		}
		else if(a.x > b.x && a.y <= b.y){ //���� �ϴ�(4��и�)
			return (Math.atan((b.y-a.y)/(a.x-b.x))/pi)*180+90;
		}
		else if(a.x <= b.x && a.y < b.y){ //���� �ϴ�(3��и�)
			return (Math.atan((b.x-a.x)/(b.y-a.y))/pi)*180+180;
		}
		else if(a.x < b.x && a.y >= b.y){ //���� ���(2��и�)
			return (Math.atan((a.y-b.y)/(b.x-a.x))/pi)*180+270;
		}
		return 0;
	}
	private void AngularSort(){
		//outdegree�� ���� indegree�� ���� �� outdegree�� �������Ͽ� ���� �Է�
		for(int j = 0; j < N; j++){
			Node tmp = nodes[j].outdegree;
			Edge[] edges = new Edge[nodes[j].outCount];
			
			int edgeindex = 0;
			while(tmp != null){
				
				//���� ������ ������ ��������� �̷�� ������ ���Ѵ�
				double angle = calcAngle(tmp,nodes[j]);						
				edges[edgeindex++] = new Edge(angle, tmp.index);	//����������� ���� ������ ��� edges �迭 ����
				
				nodes[tmp.index].indegree++;						//��������Ʈ�� ����Ű�� ������ indegree ������Ű��
				tmp = tmp.outdegree;
			}
			
			Arrays.sort(edges);		//���� ��������(�ð����, CW)���� ����
			
			nodes[j].outdegree = null;	//������ ��������Ʈ ����
			//�����ĵ� ���ο� ��������Ʈ ����
			for(int k = edges.length-1; k >= 0; k--){
				Node newnode = new Node(edges[k].tonode);
				newnode.outdegree = nodes[j].outdegree;
				nodes[j].outdegree = newnode;
			}
		}
	}
	
	private void findOutline(){		
		int small_x = nodes[0].x, small_index = 0, preindex = 0;
		for(int i = 0; i < N; i++){
			if(small_x > nodes[i].x){
				small_x = nodes[i].x;
				small_index = i;
				preindex = i;
			}
		}
		Node outstart = nodes[nodes[small_index].outdegree.index];
		visited[small_index][outstart.index] = true;
		linesFace[small_index][outstart.index] = -1;
		while(outstart.index != small_index){
			Node localdeg = outstart.outdegree;
			while(localdeg.index != preindex){
				localdeg = localdeg.outdegree;
				if(localdeg == null){
					localdeg = outstart;
				}
			}			
			localdeg = localdeg.outdegree;
			if(localdeg == null){
				localdeg = outstart.outdegree;
			}			
			
			visited[outstart.index][localdeg.index] = true;
			linesFace[outstart.index][localdeg.index] = -1;
						
			preindex = outstart.index;
			outstart = nodes[localdeg.index];			
		}
	}

	
	private void findFaces(){
		int faceCnt = 1;
		//��� ��� ������ ����
		for(int i = 0; i < N; i++){
			Node tmp = nodes[i].outdegree;
			//�� ����� ��� ���� ������ ����
			while(tmp != null){
				if(!visited[i][tmp.index]){	//�̹��������� ������ �湮���� �ʾҴٸ�
					
					
					
					Node local = nodes[tmp.index];
					int preindex = i;
					//���� for���� �ٿ����� �ǵ��� �� ������
					while(local.index != i){
						Node localocal = local.outdegree;
						//�ð�������� ���� ������ ������ ��������
						while(localocal.index != preindex){
							localocal = localocal.outdegree;
							if(localocal == null){
								localocal = local.outdegree;
							}
						}
						localocal = localocal.outdegree;
						if(localocal == null){localocal = local.outdegree;}
						
						if(visited[local.index][localocal.index]){
							System.out.println("duplicated visit..");
							break;
						}
						else{
							visited[local.index][localocal.index] = true;
							linesFace[local.index][localocal.index] = faceCnt;
						}
						preindex = local.index;
						local = nodes[localocal.index];
					}
					
					
					
					
				}				
				tmp = tmp.outdegree;
			}
		}
		
	}
	
	
//	private void execute(){
//		Edge[] edges = new Edge[5];
//		edges[0] = new Edge(5.23, 2);
//		edges[1] = new Edge(1.45, 1);
//		edges[2] = new Edge(9.47, 5);
//		edges[3] = new Edge(6.2, 4);
//		edges[4] = new Edge(3.21, 9);
//		Arrays.sort(edges);		
//		
//		for(int i = 0; i < 5; i++){
//			System.out.println(edges[i].angle);
//		}
//
//		System.out.println((Math.atan(1)/pi)*180);
//	}


	public static void main(String[] args){
		Problem42 pro = new Problem42();
		pro.readInput("input42.txt");
		pro.AngularSort();
		pro.adjPrint();
		//pro.execute();
	}
	
	private void adjPrint()	// ������� ���� ����Ʈ�� ��� �� �ش�
	{
		for(int i=0;i<N;i++)
		{
			Node p = nodes[i];
			System.out.print((p.index+1)+": ");
			if(p!=null) p=p.outdegree;
			while(p!=null)
			{
				System.out.print((p.index+1)+" ");
				p = p.outdegree;
			}
			System.out.println();
		}
	}

}

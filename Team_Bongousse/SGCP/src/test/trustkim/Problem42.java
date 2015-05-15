package test.trustkim;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;


public class Problem42 {
	final static double pi = 3.1415926;
	
	private Node[] nodes;
	private int N;
	private int px, py;				// 탈출 시작점
	private boolean[][] visited;	// face 탐지 할 때 방문 에지 체크
	private int[][] linesFace;		// 에지가 어느 페이스에 속하는지 저장하는 테이블
	// 방향 상관 없는 에지 인덱스로 만든 에지 테이블 추가 바람

	private class Node{
		int x, y, index;			// index는 Node타입 배열에 쓰일 인덱스
		int indegree = 0;			// 현재 노드의 indegree
		int outCount = 0;			//outdegree 연결리스트의 수
		Node outdegree = null;		// next. 현재 노드에 인접한 노드
		Node(int index){ this.index = index; }
		Node(int index, int x, int y){ 
			this.index = index; 
			this.x = x; 
			this.y = y; 
		}
	}

	private class Edge implements Comparable<Edge>{	//어떤 근원노드로부터 뻗쳐나가는 엣지
		int tonode;									//이 엣지가 향하는 노드의 배열인덱스
		double angle;								//이 엣지가 근원노드의 상향법선과 이루는 각도
		Edge(double angle, int tonode){ 
			this.angle = angle; 
			this.tonode = tonode;
		}
		public int compareTo(Edge other){			//angle에 따라 오름차순으로 정렬됨
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
	private static class Line {	// 두 점을 잇는 선분
        private MyPoint u, v;
        public Line(int x1, int y1, int x2, int y2) {
            u = new MyPoint(x1, y1);
            v = new MyPoint(x2, y2);
        }

        public Line(MyPoint p, MyPoint q) {
            u = p;
            v = q;
        }	// 두 점을 잇는 선분을 생성

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
        }	// 두 선분이 교차하는지 여부를 반환
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
				visited = new boolean[N][N];	// 페이스 탐지 할 때 쓰므로 그 때 초기화 해주는 것도 좋을 것 같다.
				linesFace = new int[N][N];		//-1:바깥영역, 1이상:face인덱스
				
				//일단 input읽고 좌표, outdegree정보를 담는다
				for(int i = 0; i < N; i++){
					nodes[i] = new Node(input.nextInt()-1, input.nextInt(), input.nextInt());
					nodes[i].outCount = input.nextInt();
					for(int k = nodes[i].outCount; k > 0; k--){
						Node newnode = new Node(input.nextInt()-1);
						newnode.outdegree = nodes[i].outdegree;
						nodes[i].outdegree = newnode;
					}
				}

				
				
				// <<<이하 outline탐색, face탐색, 내부의 점 찾기 등 실행>>>
				//......
				//findOutline();
				//......
				
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
	}
	private double calcAngle(Node a, Node b){
		if(a.x >= b.x && a.y > b.y){ //좌표평면 우측 상단(1사분면)
			return (Math.atan((a.x-b.x)/(a.y-b.y))/pi)*180;
		}
		else if(a.x > b.x && a.y <= b.y){ //우측 하단(4사분면)
			return (Math.atan((b.y-a.y)/(a.x-b.x))/pi)*180+90;
		}
		else if(a.x <= b.x && a.y < b.y){ //좌측 하단(3사분면)
			return (Math.atan((b.x-a.x)/(b.y-a.y))/pi)*180+180;
		}
		else if(a.x < b.x && a.y >= b.y){ //좌측 상단(2사분면)
			return (Math.atan((a.y-b.y)/(b.x-a.x))/pi)*180+270;
		}
		return 0;
	}
	private void AngularSort(){
		//outdegree에 의한 indegree값 결정 및 outdegree를 각정렬하여 새로 입력
		for(int j = 0; j < N; j++){
			Node tmp = nodes[j].outdegree;
			Edge[] edges = new Edge[nodes[j].outCount];
			
			int edgeindex = 0;
			while(tmp != null){
				
				//현재 노드와의 직선이 상향법선과 이루는 각도를 구한다
				double angle = calcAngle(tmp,nodes[j]);						
				edges[edgeindex++] = new Edge(angle, tmp.index);	//인접노드들과의 각도 정보를 담는 edges 배열 구성
				
				nodes[tmp.index].indegree++;						//인접리스트가 가리키는 노드들의 indegree 증가시키기
				tmp = tmp.outdegree;
			}
			
			Arrays.sort(edges);		//각도 오름차순(시계방향, CW)으로 정렬
			
			nodes[j].outdegree = null;	//기존의 인접리스트 제거
			//각정렬된 새로운 인접리스트 구성
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
		//모든 노드 각각에 대해
		for(int i = 0; i < N; i++){
			Node tmp = nodes[i].outdegree;
			//그 노드의 모든 엣지 각각에 대해
			while(tmp != null){
				if(!visited[i][tmp.index]){	//이방향으로의 엣지를 방문하지 않았다면
					
					
					
					Node local = nodes[tmp.index];
					int preindex = i;
					//위의 for문의 근원노드로 되돌아 올 때까지
					while(local.index != i){
						Node localocal = local.outdegree;
						//시계방향으로 가장 인접한 엣지로 가기위해
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
	
	private void adjPrint()	// 만들어진 연결 리스트를 출력 해 준다
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

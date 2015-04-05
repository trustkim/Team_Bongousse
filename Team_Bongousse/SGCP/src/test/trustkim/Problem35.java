package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Problem35 {
	public static class Cell {
		public int x,y;
		public Cell(int x,int y) { this.x=x; this.y=y; }
		public boolean valid() {
			return x>=0 && y>=0 && x<gridN && y<gridM;
		}
	}

	private static int[] offsetX = {-1, 0, 1, 0}; // N, E, S, W
	private static int[] offsetY = {0, 1, 0, -1};
	private static int gridN;
	private static int gridM;
	private static int[][] grid;
	private static int[][] turnCount;
	private static final int MAX = 999999999;
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner sc = new Scanner(new File("input35.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				int N=sc.nextInt();		// 직교다각형의 개수
				Cell[][] points = new Cell[N][];
				HashSet<Integer> x_hs = new HashSet<Integer>();
				HashSet<Integer> y_hs = new HashSet<Integer>();
				Cell init = new Cell(sc.nextInt(),sc.nextInt());x_hs.add(init.x);y_hs.add(init.y);
				Cell dest = new Cell(sc.nextInt(),sc.nextInt());x_hs.add(dest.x);y_hs.add(dest.y);
				for(int i=0;i<N;i++) {
					int K = sc.nextInt();	// 한 직교다각형의 꼭지점 개수
					points[i] = new Cell[K];
					for(int j=0;j<K;j++) {
						points[i][j] = new Cell(sc.nextInt(), sc.nextInt());
						x_hs.add(points[i][j].x);
						y_hs.add(points[i][j].y);
					}	 
				}	// file read complete
				
				gridN = x_hs.size(); gridM = y_hs.size();
				Object[] xs = x_hs.toArray(); Arrays.sort(xs);
				Object[] ys = y_hs.toArray(); Arrays.sort(ys);
//				System.out.print("x좌표들: ");
//				for(int i=0;i<gridN;i++)
//					System.out.print(xs[i]+" ");
//				System.out.println();
//				System.out.print("y좌표들: ");
//				for(int i=0;i<gridM;i++)
//					System.out.print(ys[i]+" ");
//				System.out.println();
				// change to grid
				init = changeToGrid(init,xs,ys);
				dest = changeToGrid(dest,xs,ys);
				for(int i=0;i<N;i++)
					for(int j=0;j<points[i].length;j++){
						points[i][j] = changeToGrid(points[i][j],xs,ys);
					}
				
				// make turnCount grid
				grid = new int[gridN][gridM];
				turnCount = new int[gridN][gridM];
				for(int i=0;i<gridN;i++)
					for(int j=0;j<gridM;j++) {
						grid[i][j] = 0;
						turnCount[i][j] = MAX;
					}
				for(int i=0;i<N;i++){
					Cell cur = new Cell(points[i][0].x, points[i][0].y);
					Cell next;
					for(int j=1;j<=points[i].length;j++) {
						grid[cur.x][cur.y] = 1;
						//turnCount[cur.x][cur.y] = -1;
						if(j!=points[i].length)
							next = new Cell(points[i][j].x, points[i][j].y);
						else next = new Cell(points[i][0].x, points[i][0].y);
						if(cur.x==next.x) {	// 시계방향으로 수평 이동 하였다.
							for(int k=cur.y;k!=next.y;k=k+((next.y-cur.y)/Math.abs(next.y-cur.y))) {
								grid[cur.x][k] = 1;
								//turnCount[cur.x][k] = -1;
							}
						}else if(cur.y==next.y) {	// 시계방향으로 수직 이동하였다.
							for(int k=cur.x;k!=next.x;k=k+((next.x-cur.x)/Math.abs(next.x-cur.x))) {
								grid[k][cur.y] = 1;
//								turnCount[k][cur.y] = -1;
							}
						}
						cur = next;
					}
				}
				turnCount[init.x][init.y] = 0;
				
//				for(int i=0;i<gridN;i++) {
//					for(int j=0;j<gridM;j++) {
//						System.out.print(grid[i][j]+" ");
////						System.out.print((turnCount[i][j]<0?1:turnCount[i][j]==MAX?"X":turnCount[i][j])+" ");
//					}
//					System.out.println();
//				}

				turnBFS(init, dest);
			}
			sc.close();
		} catch(FileNotFoundException e) {System.out.println("file not found...");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	
	private static Cell changeToGrid(Cell A, Object[] xs, Object[] ys) {	// 점좌푤를 그리드의 좌표로 환산하는 함수
		Cell B = new Cell(A.x, A.y);
		for(int i=0;i<xs.length;i++)
			if(B.x == (int)xs[i]) B.x = i;
		for(int i=0;i<ys.length;i++)
			if(B.y == (int)ys[i]) B.y = i;
		return B;
	}
	
	private static void turnBFS(Cell init, Cell dest) {
		Queue<Cell> queue = new LinkedList<Cell>();
		Cell cell, next, temp;
		queue.offer(init);
		while(!queue.isEmpty()) {
			cell = queue.poll();
			int t = turnCount[cell.x][cell.y];

			for(int d=0; d<4; d++) {
				next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d]);

				while(next.valid() && grid[next.x][next.y]!=1) {	// 범위와 장애물인지 검사
					if(turnCount[next.x][next.y]==MAX) {			//  첫 방문이면 인큐
						if(next.x==dest.x && next.y==dest.y) {
							System.out.println(t);
							return;
						}
						temp = new Cell(next.x,next.y);
						queue.offer(temp);
						turnCount[next.x][next.y] = t+1;
					}
					next.x+=offsetX[d]; next.y+=offsetY[d];				// 첫 방문 아니면 같은 방향으로 계속 진행
					if(next.valid()&&grid[next.x][next.y]==1) {		// 만약 범위 안의 장애물을 만나면(직교 다각형의 한 변)
						//System.out.println("! "+next.x+", "+next.y);
					}
				}
			}
		}
		System.out.println(-1);
	}
}
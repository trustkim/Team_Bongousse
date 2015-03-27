package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Problem32 {
	
	private static class Cell {
		int x, y, tilt;
		public Cell(int x, int y, int z) { this.x=x; this.y=y; this.tilt=z; }
		public boolean valid() {
			if(tilt==0)	// 세로일 때 범위 검사
				return x-1>=0 && y>=0 && x+1<N && y<N;
			else	// 가로일 때 범위 검사
				return x>=0 && y-1>=0 && x<N && y+1<N;
		}
	}
	
	private static int[] offsetX = {-1, 0, 1, 0}; // N, E, S, W
	private static int[] offsetY = {0, 1, 0, -1};
	private static int N;
	private static int[][][] grid;	// 중심점을 기준으로 시작 셀에서 각 셀까지의 거리를 저장
	private static final int MAX = 999999999;
	
	public static void main(String [] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner sc = new Scanner(new File("input32.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				N = sc.nextInt();
				Cell init = new Cell(sc.nextInt(), sc.nextInt(), sc.nextInt());
				Cell dest = new Cell(sc.nextInt(), sc.nextInt(), sc.nextInt());
				grid = new int[2][N][N];
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++) {
						grid[0][i][j] = grid[1][i][j] = sc.nextInt()==1?-1:MAX;
					}
				grid[init.tilt][init.x][init.y] = 0;
				
				BFS(init, dest);
//				for(int i=0;i<N;i++) {
//					for(int j=0;j<N;j++)
//						System.out.print(String.format("%2d", (grid[0][i][j]==-1?0:grid[0][i][j]==999999999?0:grid[0][i][j]))+" ");
//					System.out.println();
//				}
//				System.out.println();
//				for(int i=0;i<N;i++) {
//					for(int j=0;j<N;j++)
//						System.out.print(String.format("%2d",(grid[1][i][j]==-1?0:grid[1][i][j]==999999999?0:grid[1][i][j]))+" ");
//					System.out.println();
//				}
			}
			sc.close();
		}catch(FileNotFoundException e) { e.printStackTrace(); }
		System.out.println("Elapsed: "+(System.currentTimeMillis()-start)/1000.0);
	}
	
	public static void BFS(Cell init, Cell dest) {
		Queue<Cell> queue = new LinkedList<Cell>();
		queue.offer(init);
		Cell cell, next;
		int t;
		while(!queue.isEmpty()) {
			cell = queue.poll();
			t = grid[cell.tilt][cell.x][cell.y];
			for(int d=0; d<4; d++) {	// N, W, S, E로 진행
				next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d], cell.tilt);
				
				if(next.valid() && isMovable(next) && grid[next.tilt][next.x][next.y]==MAX) {
					grid[next.tilt][next.x][next.y] = t+1;
					if(next.x==dest.x && next.y==dest.y && next.tilt==dest.tilt) {
						System.out.println(t+1);
						return;
					}
					queue.offer(next);
				}
			}
			next = new Cell(cell.x,cell.y,cell.tilt==0?1:0);	// 가로 세로 전환
			if(next.valid() && isTurnable(next) && grid[next.tilt][next.x][next.y]==MAX) {
				grid[next.tilt][next.x][next.y] = t+1;
				if(next.x==dest.x && next.y==dest.y && next.tilt==dest.tilt) {
					System.out.println(t+1);
					return;
				}
				queue.offer(next);
			}
			
		}
		System.out.println(-1);
	}
	private static int[] offsetMXY = {-1,1};
	private static boolean isMovable(Cell next) {	// 세로, 가로 상태에 따라 위/아래, 좌/우에 장애물이 있으면 false, 없으면 true를 반환
		Cell temp;
		for(int d=0;d<2;d++) {
			if(next.tilt==0){	// 세로일 때
				temp = new Cell(next.x+offsetMXY[d],next.y,next.tilt);
				if(temp.valid() && grid[temp.tilt][temp.x][temp.y] < 0)	// 장애물이 아닌지!
					return false;
			}else if(next.tilt==1){	// 가로일 때
				temp = new Cell(next.x,next.y+offsetMXY[d],next.tilt);
				if(temp.valid() && grid[temp.tilt][temp.x][temp.y] < 0)	// 장애물이 아닌지!
					return false;
			}
		}
		return true;
	}
	private static boolean isTurnable(Cell next) {	// 전환 한 뒤 세로이면 위,아래 움직여지는지, 가로이면 좌,우로 움직여지는 지 검사하여 반환
		Cell temp;
		for(int d=0;d<4;d++) {
			if(next.tilt==0 && d%2==1) {
				temp = new Cell(next.x,next.y+offsetY[d],next.tilt);
				if(temp.valid()&&!isMovable(temp))
					return false;
			}else if(next.tilt==1 && d%2==0){
				temp = new Cell(next.x+offsetY[d],next.y,next.tilt);
				if(temp.valid()&&!isMovable(temp))
					return false;
			}
		}
		return true;
	}
}
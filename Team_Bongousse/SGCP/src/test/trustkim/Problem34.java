package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Problem34 {
	static class Cell {
		int x,y;
		Cell(int x, int y) {this.x=x; this.y=y; }
		boolean valid() {
			return x>=0 && y>=0 && x<M && y<N;
		}
	}
	private static int N;
	private static int M;
	private static int[][] grid;
	private static int[][] costs;
	private static Queue<Cell>[] q;
	private static int time;
	private static int max;
	private static int[] offsetX = {-1, 0, 1, 0};
	private static int[] offsetY = {0, 1, 0, -1};
	private static final int VISITED = -1;
	private static boolean timeFlag;
	
	@SuppressWarnings("unchecked")
	public static void main(String [] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner sc = new Scanner(new File("input34.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				M = sc.nextInt();	// 가로 크기
				N = sc.nextInt();	// 세로 크기
				grid = new int[M][N];
				costs = new int[M][N];
				for(int i=0;i<M;i++)
					for(int j=0;j<N;j++) {
						grid[i][j] = sc.nextInt();
						costs[i][j] = 0;
					}
				// file read complete
				
				q = new LinkedList[2];
				q[0] = new LinkedList<Cell>();
				q[1] = new LinkedList<Cell>();
				q[0].offer(new Cell(0,0));

				max=0;
				time=0;
				timeFlag = false;
				int i=0;
//				oxyBFS(i);
//				Print(grid);
//				System.out.println();
//				Print(costs);
//				System.out.println("BFS");
//				i = (i+1)%2;
//				BFS(i);
//				Print(grid);
//				System.out.println();
//				Print(costs);
				while(!q[i].isEmpty()) {
					oxyBFS(i);
					//System.out.println();
					i = (i+1)%2;
					if(timeFlag) timeFlag = false;
					//Print(grid);
				}
				
				System.out.println(max);
				
				
			}
			sc.close();
			System.out.println(((long)System.currentTimeMillis()-start)/1000.0);
		} catch (FileNotFoundException e) { System.out.println("file not found...");}
	}
	private static void Print(int [][] grid) {
		for(int i=0;i<M;i++) {
			for(int j=0;j<N;j++)
				System.out.print((grid[i][j]==VISITED?"O":grid[i][j])+" ");
			System.out.println();
		}
	}
	private static void oxyBFS(int i) {
		int j = (i+1)%2;
		while(!q[i].isEmpty()) {
			Cell cell = q[i].poll();
			for(int d=0;d<4;d++) {
				Cell next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d]);
				if(next.valid())
					if(grid[next.x][next.y]==0) {		// 산소면 체크하고 인큐
						grid[next.x][next.y] = VISITED;
						costs[next.x][next.y] = costs[cell.x][cell.y];
						q[i].offer(next);
					}else if(grid[next.x][next.y]>0){
						grid[next.x][next.y]++;			// 치즈면 치즈 셀 ++
						if(grid[next.x][next.y]>=3) {	// 치즈가 2면 이상 산소와 접했으면
							grid[next.x][next.y]=VISITED;		// 산소로 바꾸고
							costs[next.x][next.y] = costs[cell.x][cell.y]+1;	// 시간 재고
							max = Math.max(max,costs[next.x][next.y]);
							q[j].offer(next);		// 다음 BFS를 위한 큐에 인큐
							//System.out.println("next cheese: ("+next.x+", "+next.y+") / costs: "+costs[next.x][next.y]);
						}
					}
			}
		}
	}
	private static void BFS(int i) {
		int j = (i+1)%2;
		while(!q[i].isEmpty()) {
			Cell cell = q[i].poll();	// 이번에 산소로 바뀐 셀 디큐
			for(int d=0;d<4;d++) {
				Cell next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d]);
				if(next.valid())
					if(grid[next.x][next.y]==0) {		// 산소면 체크하고 인큐
						grid[next.x][next.y] = VISITED;
						costs[next.x][next.y] = costs[cell.x][cell.y];
						q[i].offer(next);
					}else if(grid[next.x][next.y]>0){
						grid[next.x][next.y]++;			// 치즈면 치즈 셀 ++
						if(grid[next.x][next.y]>=3) {	// 치즈가 2면 이상 산소와 접했으면
							grid[next.x][next.y]=VISITED;		// 산소로 바꾸고
							costs[next.x][next.y] = costs[cell.x][cell.y]+1;	// 시간 재고
							max = Math.max(max, costs[next.x][next.y]);
							q[j].offer(next);		// 다음 BFS를 위한 큐에 인큐
							//System.out.println("next cheese: ("+next.x+", "+next.y+") / costs: "+costs[next.x][next.y]);
						}
					}
			}
		}
	}
}

package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Problem31 {

	private static class Cell {
		public int x,y;
		public Cell(int x,int y) { this.x=x; this.y=y; }
		public boolean valid() {
			return x>=0 && y>=0 && x<N && y<N;
		}
	}
	
	private static int[] offsetX = {-1, 0, 1, 0}; // N, E, S, W
	private static int[] offsetY = {0, 1, 0, -1};
	private static int N;
	private static int[][] grid;
	private static int[][] turnCount;
	
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner sc = new Scanner(new File("input31.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				N=sc.nextInt();
				Cell init = new Cell(sc.nextInt(),sc.nextInt());
				Cell dest = new Cell(sc.nextInt(),sc.nextInt());
				
				grid = new int[N][N];
				turnCount = new int[N][N];
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++) {
						grid[i][j] = sc.nextInt();
						turnCount[i][j]=-1;
					}
				turnCount[init.x][init.y] = 0;
				
				turnBFS(init, dest);
				for(int i=0;i<N;i++) {
					for(int j=0;j<N;j++)
						System.out.print(turnCount[i][j]+" ");
					System.out.println();
				}
			}
			sc.close();
		} catch(FileNotFoundException e) {System.out.println("file not found...");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	public static void turnBFS(Cell init, Cell dest) {
		Queue<Cell> queue = new LinkedList<Cell>();
		queue.offer(init);
		while(!queue.isEmpty()) {
			Cell cell = queue.poll();
			int t = turnCount[cell.x][cell.y];
			
			for(int d=0; d<4; d++) {
				Cell next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d]);
				
				while(next.valid() && grid[next.x][next.y]==0 && turnCount[next.x][next.y]==-1 ) {
					
					turnCount[next.x][next.y] = t+1;
					if(next.x==dest.x && next.y==dest.y) {
						System.out.println(t);
						return;
					}
					Cell temp = new Cell(next.x,next.y);
					queue.offer(temp);
					next.x+=offsetX[d]; next.y+=offsetY[d];
				}
			}
			
		}
		System.out.println(-1);
	}
}
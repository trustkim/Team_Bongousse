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
			return x>=0 && y>=0 && x<N && y<N && tilt>=0 && tilt<=1;
		}
	}
	
	private static int[] offsetX = {-1, 0, 1, 0, 0}; // N, E, S, W, R
	private static int[] offsetY = {0, 1, 0, -1, 0};
	private static int N;
	private static int[][][] grid;
	private static final int MAX = 999999999;
	private static final int TURN_MODE = 5;
	
	public static void main(String [] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner sc = new Scanner(new File("input32.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				N = sc.nextInt();
				Cell init = new Cell(sc.nextInt(), sc.nextInt(), sc.nextInt());
				Cell dest = new Cell(sc.nextInt(), sc.nextInt(), sc.nextInt());
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++) {
						grid[0][i][j] = grid[1][i][j] = sc.nextInt()==1?-1:MAX;
					}
				grid[init.tilt][init.x][init.y] = 0;
				
				BFS(init, dest);
			}
			sc.close();
		}catch(FileNotFoundException e) { e.printStackTrace(); }
		System.out.println("Elapsed: "+(System.currentTimeMillis()-start)/1000.0);
	}
	
	public static void BFS(Cell init, Cell dest) {
		Queue<Cell> queue = new LinkedList<Cell>();
		queue.offer(init);
		while(!queue.isEmpty()) {
			Cell cell = queue.poll();
			int t = grid[cell.tilt][cell.x][cell.y];
			
			for(int d=0; d<5; d++) {
				Cell next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d], d<4?cell.tilt:cell.tilt==0?1:0);
				
				if(next.valid() && grid[next.tilt][next.x][next.y]>0 ) {
					if(isTurnable(next,d)) {
						grid[next.tilt][next.x][next.y] = Math.min(grid[next.tilt][next.x][next.y], t+1);
						if(next.x==dest.x && next.y==dest.y && next.tilt==dest.tilt) {
							System.out.println(t+1);
							return;
						}
						queue.offer(next);
					}
				}
			}
		}
		System.out.println(-1);
	}
	private static int[] offsetRX = {0, -1, 0, 1};
	private static int[] offsetRY = {-1, 0, 1, 0};
	public static boolean isTurnable(Cell cell, int mode) {
		if(mode!=TURN_MODE) return true;
		else {
			for(int d=0;d<4;d++) {
				Cell next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d], cell.tilt);
				if(!next.valid()) return false;
				else {
					if(!(new Cell(next.x+offsetRX[d], next.y+offsetRY[d], next.tilt)).valid())
							return false;
				}
			}
			return true;
		}
	}
}
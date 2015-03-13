package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.AbstractQueue;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Problem30 {
	private static int [][] grid;
	private static int N;
	private static int K;
	private static int time;
	private static final int PATHWAY = 0;
	private static final int FIRE = -1;
	private static final int BLOCKED = -2;
	private static final int PATH = 1;
	private static Queue<int[]> queue;
	
	public static void main(String [] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner input = new Scanner(new File("input30.txt"));
			for(int T=input.nextInt();T>0;T--) {

				N = input.nextInt(); K = input.nextInt();
				grid = new int[N][N];
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++){
						grid[i][j]=input.nextInt()==0?0:-1;
					}// file read complete
				
				// data structure setting
				//Print(grid);
				
				// solve
				queue = new LinkedList<int[]>();
				//System.out.println(solve(0,0,time)+" "+time);
				BFS(0,0);
			}
		}catch (FileNotFoundException e) { e.printStackTrace();}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	public static boolean solve(int x0, int y0, int t) {
		if(x0<0||y0<0||x0>N-1||y0>N-1) return false;
		else if(grid[x0][y0]!=PATHWAY) return false;
		else if(x0==N-1&&y0==N-1) {
			grid[x0][y0] = PATH; time=t;
			return true;
		}
		else {
			grid[x0][y0] = PATH;
			if(solve(x0-1,y0,t+1) || solve(x0,y0+1,t+1)
				|| solve(x0+1,y0,t+1) || solve(x0,y0-1,t+1)) return true; 
			grid[x0][y0] = BLOCKED;
			return false;
		}
	}

	public static int[][] burn(int[][] grid, int x, int y) {
		if(x-1>=0&&y>=0&&x-1<N&&y<N)
			grid[x-1][y] = FIRE;
		if(x>=0&&y+1>=0&&x<N&&y+1<N)
			grid[x][y+1] = FIRE;
		if(x+1>=0&&y>=0&&x+1<N&&y<N)
			grid[x+1][y] = FIRE;
		if(x>=0&&y-1>=0&&x<N&&y-1<N)
			grid[x][y-1] = FIRE;
		return grid;
	}
	
	public static void BFS(int x0, int y0){
		queue.offer(new int[]{x0,y0,0});
		while(!queue.isEmpty()){
			int[] vertex = queue.poll();
			int x=vertex[0];
			int y=vertex[1];
			int t=vertex[2];

			if(x==N-1&&y==N-1) {System.out.println(t); return;}
			else {
				
				grid[x][y] = PATH;
				if(x-1>=0&&y>=0&&x-1<N&&y<N&&grid[x-1][y]==0)
					queue.add(new int[]{x-1,y,t+1});
				if(x>=0&&y+1>=0&&x<N&&y+1<N&&grid[x][y+1]==0)
					queue.add(new int[]{x,y+1,t+1});
				if(x+1>=0&&y>=0&&x+1<N&&y<N&&grid[x+1][y]==0)
					queue.add(new int[]{x+1,y,t+1});
				if(x>=0&&y-1>=0&&x<N&&y-1<N&&grid[x][y-1]==0)
					queue.add(new int[]{x,y-1,t+1});
				
				if(t+1%K==0) {
					for(int i=0;i<N;i++)
						for(int j=0;j<N;j++)
							if(grid[i][j]==FIRE) burn(grid,i,j);
				}
			}
		}
		System.out.println(-1);
	}
	public static void Print(int [][] grid) {
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++)
				System.out.print((grid[i][j]<0?1:0)+" ");
			System.out.println();
		}
	}
}

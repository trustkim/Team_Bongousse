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
	private static final int MAX = 2000;
	private static final int PATHWAY = 0;
	private static final int FIRE = 1;
	private static final int PATH = 2;
	private static Queue<int[]> fireQ;
	private static Queue<int[]> queue;
	
	public static void main(String [] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner input = new Scanner(new File("input30.txt"));
			for(int T=input.nextInt();T>0;T--) {

				N = input.nextInt(); K = input.nextInt();
				grid = new int[N][N]; 
				int[][] maze = new int[N][N];
				fireQ = new LinkedList<int[]>();
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++){
						grid[i][j]=input.nextInt();
						maze[i][j]=grid[i][j]==1?0:MAX;
						if(maze[i][j]==0) fireQ.offer(new int[]{i,j,0});
					}// file read complete
				
				// data structure setting
				boolean[][] isChecked = new boolean[N][N];
				while(!fireQ.isEmpty()){
					int[] cell = fireQ.poll();
					int x=cell[0];
					int y=cell[1];
					int t=cell[2];
					
					if(isChecked[x][y]) ;
					else {
						isChecked[x][y] = true;
						maze[x][y] = Math.min(maze[x][y], t);
						if(x-1>=0&&y>=0&&x-1<N&&y<N&&maze[x-1][y]==MAX)
							fireQ.add(new int[]{x-1,y,K+t});
						if(x>=0&&y+1>=0&&x<N&&y+1<N&&maze[x][y+1]==MAX)
							fireQ.add(new int[]{x,y+1,K+t});
						if(x+1>=0&&y>=0&&x+1<N&&y<N&&maze[x+1][y]==MAX)
							fireQ.add(new int[]{x+1,y,K+t});
						if(x>=0&&y-1>=0&&x<N&&y-1<N&&maze[x][y-1]==MAX)
							fireQ.add(new int[]{x,y-1,K+t});	
					}
				}
				System.out.println("THIS IS GRID");
				Print(grid);
				System.out.println("THIS IS MAZE");
				Print(maze);
				// solve
				queue = new LinkedList<int[]>();
				BFS(0,0);
			}
		}catch (FileNotFoundException e) { e.printStackTrace();}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
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
				if(x-1>=0&&y>=0&&x-1<N&&y<N&&grid[x-1][y]==PATHWAY)
					queue.add(new int[]{x-1,y,t+1});
				if(x>=0&&y+1>=0&&x<N&&y+1<N&&grid[x][y+1]==PATHWAY)
					queue.add(new int[]{x,y+1,t+1});
				if(x+1>=0&&y>=0&&x+1<N&&y<N&&grid[x+1][y]==PATHWAY)
					queue.add(new int[]{x+1,y,t+1});
				if(x>=0&&y-1>=0&&x<N&&y-1<N&&grid[x][y-1]==PATHWAY)
					queue.add(new int[]{x,y-1,t+1});

			}
		}
		System.out.println(-1);
	}
	public static void Print(int [][] grid) {
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++)
				System.out.print(grid[i][j]+" ");
			System.out.println();
		}
	}
}

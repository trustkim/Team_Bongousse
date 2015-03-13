package test.bjay;

import java.io.File;
import java.io.FileNotFoundException;
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
	private static Queue<int[]> firequeue;
	
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
					}
				queue = new LinkedList<int[]>();
				firequeue = new LinkedList<int[]>();
				for(int i=0;i<N;i++){
					for(int j=0;j<N;j++){
						if(grid[i][j] == FIRE)
							firequeue.offer(new int[]{i,j});
					}
				}
				Print(grid);
				//System.out.println(firequeue.size());
				//burn(grid, firequeue.size());
		
				
				//System.out.println(solve(0,0,time)+" "+time);
				BFS(0,0);
			}
		}catch (FileNotFoundException e) { e.printStackTrace();}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}

	public static void burn(int[][] grid,int n) {
		for(int i=0;i<n;i++){
			int[] vertex2=firequeue.poll();
			int x2=vertex2[0];
			int y2=vertex2[1];
			
			if(x2-1>=0&&y2>=0&&x2-1<N&&y2<N){
				firequeue.add(new int[]{x2-1,y2});
				grid[x2-1][y2] = FIRE;
			}
			if(x2>=0&&y2+1>=0&&x2<N&&y2+1<N){
				firequeue.add(new int[]{x2,y2+1});
				grid[x2][y2+1] = FIRE;
			}
			if(x2+1>=0&&y2>=0&&x2+1<N&&y2<N){
				firequeue.add(new int[]{x2+1,y2});
				grid[x2+1][y2] = FIRE;
			}
			if(x2>=0&&y2-1>=0&&x2<N&&y2-1<N){
				firequeue.add(new int[]{x2,y2-1});
				grid[x2][y2-1] = FIRE;
			}	
		}
	}
	
	public static void BFS(int x0, int y0){
		queue.offer(new int[]{x0,y0,0});
		
		int tmp1=1, tmp2=1;
		while(!queue.isEmpty()){
			int[] vertex = queue.poll();
			int x=vertex[0];
			int y=vertex[1];
			int t=vertex[2];
			tmp2=t;
			if(tmp2%4==0 && tmp1%4==3)
				burn(grid,firequeue.size());

			System.out.println(t + " " + x + " " + y);

			if(x==N-1&&y==N-1) {System.out.println(t); return;}
			
			else {
				
				grid[x][y] = PATH;
				if(x-1>=0&&y>=0&&x-1<N&&y<N&&grid[x-1][y]==0)
					queue.offer(new int[]{x-1,y,t+1});
				if(x>=0&&y+1>=0&&x<N&&y+1<N&&grid[x][y+1]==0)
					queue.offer(new int[]{x,y+1,t+1});
				if(x+1>=0&&y>=0&&x+1<N&&y<N&&grid[x+1][y]==0)
					queue.offer(new int[]{x+1,y,t+1});
				if(x>=0&&y-1>=0&&x<N&&y-1<N&&grid[x][y-1]==0)
					queue.offer(new int[]{x,y-1,t+1});
			}
			tmp1=t;
			/*
			System.out.println("t : " + t + "x,y : " + x + "," +y);
			if(t!= 0 && t+1%K == 0){
				System.out.println("test");
				burn(grid, firequeue.size());
				Print(grid);
			}*/
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

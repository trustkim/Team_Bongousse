package test.bjay;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Problem31 {
	private static int [][] grid;
	private static int N;

	public static void main(String args[]){		
		long start = System.currentTimeMillis();
		try{
			Scanner input = new Scanner(new File("input31.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				N = input.nextInt();
				int srcx=input.nextInt(), srcy=input.nextInt();
				int destx=input.nextInt(), desty=input.nextInt();
				
				grid = new int[N][N];
	
				for(int i=0;i<N;i++){
					for(int j=0;j<N;j++){
						grid[i][j] = input.nextInt();
					}
				}
				
				BFS(srcx, srcy, destx, desty);
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	
	public static void BFS(int srcx, int srcy, int destx, int desty){
		Queue<int[]> queue = new LinkedList<int[]>();
		queue.offer(new int[]{srcx, srcy, -1});
		int tmpx, tmpy;
		
		while(!queue.isEmpty()){
			int[] vertex = queue.poll();
			int x= vertex[0];
			int y= vertex[1];
			int t= vertex[2];
	
			if(x==destx && y==desty) {System.out.println(t); return;}
			else {
				grid[x][y] = 1;		//중심점의 현재 위치를 1로 표시.
				tmpx = x;
				tmpy = y;
				while(x-1>=0 && grid[x-1][y]==0){
					x--;
					grid[x][y]=1;
					queue.offer(new int[]{x,y,t+1});
				}
				x=tmpx; y=tmpy;
				while(y+1<N && grid[x][y+1]==0){
					y++;
					grid[x][y]=1;
					queue.offer(new int[]{x,y,t+1});
				}
				x=tmpx; y=tmpy;
				while(x+1<N && grid[x+1][y]==0){
					x++;
					grid[x][y]=1;
					queue.offer(new int[]{x,y,t+1});
				}
				x=tmpx; y=tmpy;
				while(y-1>=0 && grid[x][y-1]==0){
					y--;
					grid[x][y]=1;
					queue.offer(new int[]{x,y,t+1});
				}
			}
		}
		System.out.println(-1);
	}
}
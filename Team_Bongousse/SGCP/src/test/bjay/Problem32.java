package test.bjay;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Problem32 {
	private static int [][][] grid;
	private static int N;

	public static void main(String args[]){		
		long start = System.currentTimeMillis();
		try{
			Scanner input = new Scanner(new File("input32.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				N = input.nextInt();
				int srcx=input.nextInt(), srcy=input.nextInt(), srcz=input.nextInt();
				int destx=input.nextInt(), desty=input.nextInt(), destz=input.nextInt();
				
				grid = new int[2][N][N];
	
				for(int i=0;i<N;i++){
					for(int j=0;j<N;j++){
						grid[1][i][j] = grid[0][i][j] = input.nextInt();
					}
				}
				
				BFS(srcx, srcy, srcz, destx, desty, destz);
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	
	public static void BFS(int srcx, int srcy, int srcz, int destx, int desty, int destz){
		Queue<int[]> queue = new LinkedList<int[]>();
		queue.offer(new int[]{srcz, srcx, srcy, 0});
		while(!queue.isEmpty()){
			int[] vertex = queue.poll();
			int z= vertex[0];
			int x= vertex[1];
			int y= vertex[2];
			int t= vertex[3];
	
			if(x==destx && y==desty && z==destz) {System.out.println(t); return;}
			else {
				grid[z][x][y] = 1;		//중심점의 현재 위치를 1로 표시.
				if(z==1 && x-1>=0 && y-1>=0 && y+1<N && grid[z][x-1][y] == 0 && grid[z][x-1][y-1] == 0 && grid[z][x-1][y+1] == 0)
					queue.offer(new int[]{1,x-1,y,t+1});	//가로일때, 위로
				if(z==0 && x-2>=0 && grid[z][x-2][y]==0 && grid[z][x-1][y]==0)
					queue.offer(new int[]{0,x-1,y,t+1});	//세로일때, 위로
				if(z==1 && y+2>=0 && y+2<N &&grid[z][x][y+1]==0 && grid[z][x][y+2]==0)
					queue.offer(new int[]{1,x,y+1,t+1});	//가로일때, 오른쪽
				if(z==0 && x-1>=0 && x+1<N && y+1<N && grid[z][x-1][y+1]==0 && grid[z][x][y+1]==0 && grid[z][x+1][y+1]==0)
					queue.offer(new int[]{0,x,y+1,t+1});	//세로일때, 오른쪽
				if(z==1 && x+1<N && y-1>=0 && y+1<N && grid[z][x+1][y-1]==0 && grid[z][x+1][y]==0 && grid[z][x+1][y+1]==0)
					queue.offer(new int[]{1,x+1,y,t+1});	//가로일때, 아래쪽
				if(z==0 && x+2<N && grid[z][x+2][y]==0 && grid[z][x+1][y]==0)
					queue.offer(new int[]{0,x+1,y,t+1});	//세로일때, 아래쪽
				if(z==1 && y-2>=0 && grid[z][x][y-2]==0 && grid[z][x][y-1]==0)
					queue.offer(new int[]{1,x,y-1,t+1});	//가로일때, 왼쪽
				if(z==0 && x-1>=0 && x+1<N && y-1>=0 && grid[z][x-1][y-1]==0 && grid[z][x][y-1]==0 && grid[z][x+1][y-1]==0)
					queue.offer(new int[]{0,x,y-1,t+1});	//세로일때, 왼쪽
				if(z==1 && x>=1 && x<=N-2 && y>=1 && y<=N-2 && grid[0][x-1][y-1]==0 && grid[0][x-1][y]==0 && grid[0][x-1][y+1]==0
						&& grid[0][x+1][y-1]==0 && grid[0][x+1][y] ==0 && grid[0][x+1][y+1]==0)
					queue.offer(new int[]{0,x,y,t+1});		//가로일때, 세로로회전
				if(z==0 && x>=1 && x<=N-2 && y>=1 && y<=N-2 && grid[1][x-1][y-1]==0 && grid[1][x][y-1]==0 && grid[1][x+1][y-1]==0
						&& grid[1][x-1][y+1]==0 && grid[1][x][y+1]==0 && grid[1][x+1][y+1]==0)
					queue.offer(new int[]{1,x,y,t+1});		//세로일때, 가로로회전
			}
		}
		System.out.println(-1);
	}
}
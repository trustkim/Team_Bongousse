import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Problem30 {
	private static int N;
	private static int K;
	private static int [][] grid;
	private static int [][] fireBurn;
	private static Queue<int[]> queue;

	private static final int MAX = 2000;
	private static final int PATHWAY = 0;
	private static final int FIRE = 1;
	private static final int PATH = 2;

	public static void main(String [] args) {
		try {
			Scanner input = new Scanner(new File("input30.txt"));
			for(int T=input.nextInt();T>0;T--) {
				N = input.nextInt(); K = input.nextInt();
				grid = new int[N][N]; 
				fireBurn = new int[N][N];
				queue = new LinkedList<int[]>();
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++){
						grid[i][j]=input.nextInt();
						fireBurn[i][j]=(grid[i][j]==FIRE?0:MAX);
						if(fireBurn[i][j]==0) queue.offer(new int[]{i,j,0});
					}// file read complete

				fireBFS();
				BFS(0,0);
				
				input.close();
			}
		}catch (FileNotFoundException e) { e.printStackTrace();}
	}
	public static void fireBFS() {
		boolean[][] isChecked = new boolean[N][N];
		while(!queue.isEmpty()){
			int[] cell = queue.poll();
			int x=cell[0];
			int y=cell[1];
			int t=cell[2];
			
			if(isChecked[x][y]) ;
			else {
				isChecked[x][y] = true;
				fireBurn[x][y] = Math.min(fireBurn[x][y], t);
				if(x-1>=0&&y>=0&&x-1<N&&y<N&&fireBurn[x-1][y]==MAX)	// 위
					queue.offer(new int[]{x-1,y,K+t});
				if(x>=0&&y+1>=0&&x<N&&y+1<N&&fireBurn[x][y+1]==MAX)	// 오른
					queue.offer(new int[]{x,y+1,K+t});
				if(x+1>=0&&y>=0&&x+1<N&&y<N&&fireBurn[x+1][y]==MAX)	// 아래
					queue.offer(new int[]{x+1,y,K+t});
				if(x>=0&&y-1>=0&&x<N&&y-1<N&&fireBurn[x][y-1]==MAX)	// 왼
					queue.offer(new int[]{x,y-1,K+t});	
			}
		}
	}
	public static void BFS(int x0, int y0){
		queue.offer(new int[]{x0,y0,0});
		while(!queue.isEmpty()){
			int[] cell = queue.poll();
			int x=cell[0];
			int y=cell[1];
			int t=cell[2];
			
			if(grid[x][y]!=PATHWAY||fireBurn[x][y]<=t) ;	// grid에서 길이 아니거나, fireBurn에서 이미 불 붙은 경우
			else if(x==N-1&&y==N-1) {System.out.println(t); return;}
			else {
				grid[x][y] = PATH;
				if(x-1>=0&&y>=0&&x-1<N&&y<N&&grid[x-1][y]==PATHWAY)	// 위
					queue.offer(new int[]{x-1,y,t+1});
				if(x>=0&&y+1>=0&&x<N&&y+1<N&&grid[x][y+1]==PATHWAY)	// 오른
					queue.offer(new int[]{x,y+1,t+1});
				if(x+1>=0&&y>=0&&x+1<N&&y<N&&grid[x+1][y]==PATHWAY)	// 아래
					queue.offer(new int[]{x+1,y,t+1});
				if(x>=0&&y-1>=0&&x<N&&y-1<N&&grid[x][y-1]==PATHWAY)	// 왼
					queue.offer(new int[]{x,y-1,t+1});
			}
		}
		System.out.println(-1);
	}
}
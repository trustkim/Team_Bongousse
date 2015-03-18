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
	private static int[][] turnCount;
	
	private static final int MAX = 999999999;
	
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("input31.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				N=sc.nextInt();
				Cell init = new Cell(sc.nextInt(),sc.nextInt());
				Cell dest = new Cell(sc.nextInt(),sc.nextInt());
				turnCount = new int[N][N];
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++) {
						turnCount[i][j] = sc.nextInt()==1?-1:MAX;
					}
				turnCount[init.x][init.y] = 0;
				
				turnBFS(init, dest);
			}
			sc.close();
		} catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
	public static void turnBFS(Cell init, Cell dest) {
		Queue<Cell> queue = new LinkedList<Cell>();
		queue.offer(init);
		while(!queue.isEmpty()) {
			Cell cell = queue.poll();
			int t = turnCount[cell.x][cell.y];
			
			for(int d=0; d<4; d++) {
				Cell next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d]);
				
				while(next.valid() && turnCount[next.x][next.y]>0) {
					turnCount[next.x][next.y] = Math.min(turnCount[next.x][next.y], t+1);
					
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
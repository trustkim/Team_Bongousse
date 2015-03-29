import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Problem33 {
	static class Cell {
		int x, y;
		Cell(int x, int y) { this.x = x; this.y = y; }
		boolean valid() {
			return x>=0 && y>= 0 && x<N && y<N;
		}
	}
	
	private static int N;
	private static String[][] grid;
	private static int[][] costs;
	private static Queue<Cell> queue;
	private static Queue<Cell> breakQueue;
	private static int[] offsetX = {0, -1, 0, 1};	// 서북동남
	private static int[] offsetY = {-1, 0, 1, 0};
	private static final int MAX = 999999999;
	
	public static void main(String [] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner sc = new Scanner(new File("input33.txt"));
			for(int T = sc.nextInt();T>0;T--) {
				N = sc.nextInt();
				Cell init = new Cell(sc.nextInt(), sc.nextInt());
				grid = new String[N][N];
				costs = new int[N][N];
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++) {
						grid[i][j] = sc.next().trim();
						costs[i][j] = MAX;
					}	// file read complete
				
				queue = new LinkedList<Cell>();
				breakQueue = new LinkedList<Cell>(); // 시작 셀을 두 큐 동시에 넣으면?
				costs[init.x][init.y] = 0;
				breakBFS(init);
				
			}
			System.out.println("Elapsed: "+((long)System.currentTimeMillis()-start)/1000.0);
			sc.close();
		} catch(FileNotFoundException e) { System.out.println("file not found..."); }
	}
	
	private static boolean isMovable(Cell cell, int d) {
		if(grid[cell.x][cell.y].charAt(d)=='0') {
			return true;
		}else return false;
	}
	
	private static void breakBFS(Cell init) {
		Cell cell,next;
		int t;
		
		queue.offer(init);
		breakQueue.offer(init);
		
		while(!breakQueue.isEmpty()) {		// 벽을 한 번씩 부수며 BFS
			while(!queue.isEmpty()) {		// 벽을 부수지 않고 BFS
				cell = queue.poll();
				t = costs[cell.x][cell.y];
				for(int d=0;d<4;d++) {
					next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d]);
					if(next.valid()&&isMovable(cell,d)&&costs[next.x][next.y]==MAX) {
							costs[next.x][next.y] = t; // 카운트함과 동시에 갔다고 체크
							queue.offer(next);
							breakQueue.offer(next);
					}
				}
			}
			
			cell = breakQueue.poll();
			t = costs[cell.x][cell.y];
			for(int d=0;d<4;d++) {
				next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d]);
				if(!next.valid()) {
					System.out.println(t+1); return;
				}
				if(!isMovable(cell,d)&&costs[next.x][next.y]==MAX) {
						costs[next.x][next.y] = t+1; // 카운트함과 동시에 갔다고 체크
						queue.offer(next);
						breakQueue.offer(next);

				}
			}
		}
	}
}
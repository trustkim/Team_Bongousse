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
			if(tilt==0)	// ������ �� ���� �˻�
				return x-1>=0 && y>=0 && x+1<N && y<N;
			else	// ������ �� ���� �˻�
				return x>=0 && y-1>=0 && x<N && y+1<N;
		}
	}
	
	private static int[] offsetX = {-1, 0, 1, 0}; // N, E, S, W
	private static int[] offsetY = {0, 1, 0, -1};
	private static int N;
	private static int[][][] grid;	// �߽����� �������� ���� ������ �� �������� �Ÿ��� ����
	private static final int MAX = 999999999;
	
	public static void main(String [] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner sc = new Scanner(new File("input32.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				N = sc.nextInt();
				Cell init = new Cell(sc.nextInt(), sc.nextInt(), sc.nextInt());
				Cell dest = new Cell(sc.nextInt(), sc.nextInt(), sc.nextInt());
				grid = new int[2][N][N];
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++) {
						grid[0][i][j] = grid[1][i][j] = sc.nextInt()==1?-1:MAX;
					}
				grid[init.tilt][init.x][init.y] = 0;
				
				BFS(init, dest);
//				for(int i=0;i<N;i++) {
//					for(int j=0;j<N;j++)
//						System.out.print(String.format("%2d", (grid[0][i][j]==-1?0:grid[0][i][j]==999999999?0:grid[0][i][j]))+" ");
//					System.out.println();
//				}
//				System.out.println();
//				for(int i=0;i<N;i++) {
//					for(int j=0;j<N;j++)
//						System.out.print(String.format("%2d",(grid[1][i][j]==-1?0:grid[1][i][j]==999999999?0:grid[1][i][j]))+" ");
//					System.out.println();
//				}
			}
			sc.close();
		}catch(FileNotFoundException e) { e.printStackTrace(); }
		System.out.println("Elapsed: "+(System.currentTimeMillis()-start)/1000.0);
	}
	
	public static void BFS(Cell init, Cell dest) {
		Queue<Cell> queue = new LinkedList<Cell>();
		queue.offer(init);
		Cell cell, next;
		int t;
		while(!queue.isEmpty()) {
			cell = queue.poll();
			t = grid[cell.tilt][cell.x][cell.y];
			for(int d=0; d<4; d++) {	// N, W, S, E�� ����
				next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d], cell.tilt);
				
				if(next.valid() && isMovable(next) && grid[next.tilt][next.x][next.y]==MAX) {
					grid[next.tilt][next.x][next.y] = t+1;
					if(next.x==dest.x && next.y==dest.y && next.tilt==dest.tilt) {
						System.out.println(t+1);
						return;
					}
					queue.offer(next);
				}
			}
			next = new Cell(cell.x,cell.y,cell.tilt==0?1:0);	// ���� ���� ��ȯ
			if(next.valid() && isTurnable(next) && grid[next.tilt][next.x][next.y]==MAX) {
				grid[next.tilt][next.x][next.y] = t+1;
				if(next.x==dest.x && next.y==dest.y && next.tilt==dest.tilt) {
					System.out.println(t+1);
					return;
				}
				queue.offer(next);
			}
			
		}
		System.out.println(-1);
	}
	private static int[] offsetMXY = {-1,1};
	private static boolean isMovable(Cell next) {	// ����, ���� ���¿� ���� ��/�Ʒ�, ��/�쿡 ��ֹ��� ������ false, ������ true�� ��ȯ
		Cell temp;
		for(int d=0;d<2;d++) {
			if(next.tilt==0){	// ������ ��
				temp = new Cell(next.x+offsetMXY[d],next.y,next.tilt);
				if(temp.valid() && grid[temp.tilt][temp.x][temp.y] < 0)	// ��ֹ��� �ƴ���!
					return false;
			}else if(next.tilt==1){	// ������ ��
				temp = new Cell(next.x,next.y+offsetMXY[d],next.tilt);
				if(temp.valid() && grid[temp.tilt][temp.x][temp.y] < 0)	// ��ֹ��� �ƴ���!
					return false;
			}
		}
		return true;
	}
	private static boolean isTurnable(Cell next) {	// ��ȯ �� �� �����̸� ��,�Ʒ� ������������, �����̸� ��,��� ���������� �� �˻��Ͽ� ��ȯ
		Cell temp;
		for(int d=0;d<4;d++) {
			if(next.tilt==0 && d%2==1) {
				temp = new Cell(next.x,next.y+offsetY[d],next.tilt);
				if(temp.valid()&&!isMovable(temp))
					return false;
			}else if(next.tilt==1 && d%2==0){
				temp = new Cell(next.x+offsetY[d],next.y,next.tilt);
				if(temp.valid()&&!isMovable(temp))
					return false;
			}
		}
		return true;
	}
}
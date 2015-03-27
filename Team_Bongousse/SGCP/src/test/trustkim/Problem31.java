package test.trustkim;

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
	
	
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner sc = new Scanner(new File("input31.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				N=sc.nextInt();
				Cell init = new Cell(sc.nextInt(),sc.nextInt());
				Cell dest = new Cell(sc.nextInt(),sc.nextInt());
				
				turnCount = new int[N][N];
				for(int i=0;i<N;i++)
					for(int j=0;j<N;j++) {
						turnCount[i][j] = sc.nextInt()==1?-1:999999999;
						//turnCount[i][j]=999999999;
					}
				turnCount[init.x][init.y] = 0;
				
				turnBFS(init, dest);
//				for(int i=0;i<N;i++) {
//					for(int j=0;j<N;j++)
//						System.out.print((turnCount[i][j]==999999999?0:turnCount[i][j]==-1?0:turnCount[i][j])+" ");
//					System.out.println();
//				}
			}
			sc.close();
		} catch(FileNotFoundException e) {System.out.println("file not found...");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	public static void turnBFS(Cell init, Cell dest) {
		Queue<Cell> queue = new LinkedList<Cell>();
		Cell cell, next, temp;
		queue.offer(init);
		while(!queue.isEmpty()) {
			cell = queue.poll();
			int t = turnCount[cell.x][cell.y];
			
			for(int d=0; d<4; d++) {
				next = new Cell(cell.x+offsetX[d], cell.y+offsetY[d]);
				
				while(next.valid() && turnCount[next.x][next.y]>0) {	// 범위와 장애물인지 검사
					if(turnCount[next.x][next.y]==999999999) {			//  첫 방문이면 인큐
						if(next.x==dest.x && next.y==dest.y) {
							System.out.println(t);
							return;
						}
						
						temp = new Cell(next.x,next.y);
						queue.offer(temp);	//System.out.print("("+temp.x+", "+temp.y+") ");
						
						turnCount[next.x][next.y] = t+1;
					}
					
					next.x+=offsetX[d]; next.y+=offsetY[d];				// 첫 방문 아니면 같은 방향으로 계속 진행
				}
			}
			//System.out.println();
		}
		System.out.println(-1);
	}
}

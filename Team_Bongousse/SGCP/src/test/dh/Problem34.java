package test.dh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

class Node34{
	int x, y;
	boolean valid(int M, int N){
		if(x >= 0 && x < M && y >= 0 && y < N){ return true; }
		else{ return false; }
	}
	Node34(int x, int y){this.x = x; this.y = y;}
}



public class Problem34 {

	final static int xflag[] = {0, -1, 0, 1};
	final static int yflag[] = {1, 0, -1, 0};
	
	public static void checkZeros(Node34 node, Queue<Node34> cheeseQueue, int M, int N, int grid[][], int countGrid[][]){
		Queue<Node34> queue = new LinkedList<Node34>();
		queue.offer(node);
		while(!queue.isEmpty()){
			Node34 current = queue.poll();
			
			for(int d = 0; d < 4; d++){
				Node34 dnode = new Node34(current.x+xflag[d], current.y+yflag[d]);
				
				if(dnode.valid(M, N) && grid[dnode.x][dnode.y] == 0){
					queue.offer(dnode);
					grid[dnode.x][dnode.y] = -1;
					
					for(int d2 = 0; d2 < 4; d2++){
						Node34 d2node = new Node34(dnode.x+xflag[d2], dnode.y+yflag[d2]);
						if(d2node.valid(M, N) && grid[d2node.x][d2node.y] == 1){
							countGrid[d2node.x][d2node.y]++;
							if(countGrid[d2node.x][d2node.y] >= 2){
								cheeseQueue.offer(d2node);
								grid[d2node.x][d2node.y] = -1;
							}
						}
					}
				}					
			}
		}	
	}

	public static int checkCheeses(Queue<Node34> Cheese, int grid[][], int countGrid[][], int M, int N, int COUNT/*=0*/){
		/*
		for(int i = 0; i < M; i++){
			for(int j = 0; j < N; j++){
				if(grid[i][j] == -1 || grid[i][j] == 0){System.out.print(" 0");}
				else{System.out.print(" 1");}
			}
			System.out.println();
		}
		System.out.println("\n");
		*/
		if(Cheese.isEmpty()){return COUNT;} //base case
		
		Queue<Node34> newCheese = new LinkedList<Node34>();
		while(!Cheese.isEmpty()){
			Node34 current = Cheese.poll();
			for(int d = 0; d < 4; d++){
				Node34 dnode = new Node34(current.x+xflag[d], current.y+yflag[d]);
				if(dnode.valid(M, N) && grid[dnode.x][dnode.y] == 1){
					//카운터 쌓고 카운터가 2이상되면 또 큐에 넣기
					countGrid[dnode.x][dnode.y]++;
					if(countGrid[dnode.x][dnode.y] >= 2){
						newCheese.offer(dnode);
						grid[dnode.x][dnode.y] = -1;
					}
				}
				else if(dnode.valid(M, N) && grid[dnode.x][dnode.y] == 0){
					//0들을 -1로 바꾸고 그 주위에 1있으면 또 거기에 카운트놓고 카운트2 이상은 큐에넣고...
					checkZeros(dnode, newCheese, M, N, grid, countGrid);							
				}
			}
		}		
		return checkCheeses(newCheese, grid, countGrid, M, N, COUNT + 1);
	}



	public static void main(String[] args) {
		long start = System.currentTimeMillis();	//프로그램 시간 재기
		try {
			Scanner input = new Scanner(new File("input34.txt"));	//파일 입력 읽어들이기

			for(int T = input.nextInt(); T > 0; T--){
				int M = input.nextInt(), N = input.nextInt();

				int grid[][] = new int[M][N];
				int countGrid[][] = new int[M][N];

				for(int m = 0; m < M; m++){
					for(int n = 0; n < N; n++){ 
						grid[m][n] = input.nextInt(); 
					}
				}

				Queue<Node34> cheeseQueue = new LinkedList<Node34>();
				Node34 startNode = new Node34(0, 0);

				checkZeros(startNode, cheeseQueue, M, N, grid, countGrid);				
				System.out.println(checkCheeses(cheeseQueue, grid, countGrid, M, N, 0));				
			}
			input.close(); //탐색 종료

		} catch (FileNotFoundException e) { System.out.println("file not found.."); }		
		long end = System.currentTimeMillis();							//시간계산 종료
		System.out.println( "run time : " + ( end - start )/1000.0 );	//시간 출력
	}
}
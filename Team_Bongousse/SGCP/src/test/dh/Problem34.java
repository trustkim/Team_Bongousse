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
					//ī���� �װ� ī���Ͱ� 2�̻�Ǹ� �� ť�� �ֱ�
					countGrid[dnode.x][dnode.y]++;
					if(countGrid[dnode.x][dnode.y] >= 2){
						newCheese.offer(dnode);
						grid[dnode.x][dnode.y] = -1;
					}
				}
				else if(dnode.valid(M, N) && grid[dnode.x][dnode.y] == 0){
					//0���� -1�� �ٲٰ� �� ������ 1������ �� �ű⿡ ī��Ʈ���� ī��Ʈ2 �̻��� ť���ְ�...
					checkZeros(dnode, newCheese, M, N, grid, countGrid);							
				}
			}
		}		
		return checkCheeses(newCheese, grid, countGrid, M, N, COUNT + 1);
	}



	public static void main(String[] args) {
		long start = System.currentTimeMillis();	//���α׷� �ð� ���
		try {
			Scanner input = new Scanner(new File("input34.txt"));	//���� �Է� �о���̱�

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
			input.close(); //Ž�� ����

		} catch (FileNotFoundException e) { System.out.println("file not found.."); }		
		long end = System.currentTimeMillis();							//�ð���� ����
		System.out.println( "run time : " + ( end - start )/1000.0 );	//�ð� ���
	}
}
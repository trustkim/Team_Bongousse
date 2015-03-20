package test.dh;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Arrays;

class canCell{
	int x, y;
	canCell(int x, int y){this.x = x; this.y = y;}
}

class Cell{
	int x, y;
	int turnCount;
	int direction;
	Cell(int x, int y, int direction, int turnCount){ this.x = x; this.y = y; this.direction = direction; this.turnCount = turnCount;}
}

public class Problem31 {

	//'동 남 서 북'순서...
	final static int flagX[] = {0, -1, 0, 1}; //행의 변화값
	final static int flagY[] = {1, 0, -1, 0}; //열의 변화값

	public static boolean CanGo(int ary[][], int N, int sx, int sy, int dx, int dy){
		Queue<canCell> queue = new LinkedList<canCell>();
		canCell src = new canCell(sx, sy);
		queue.offer(src);		
		while(!queue.isEmpty()){
			canCell current = queue.poll();
			if(current.x == dx && current.y == dy){System.out.println("pass");return true;}
			for(int d = 0; d < 4; d++){
				canCell next = new canCell(current.x + flagX[d], current.y + flagY[d]);
				if(next.x >= 0 && next.x < N && next.y >=0 && next.y < N && ary[next.x][next.y] == 0){
					ary[next.x][next.y] = -1;
					queue.offer(next);
				}
			}
		}
		System.out.println("block");
		return false;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();	//프로그램 시간 재기
		try {
			Scanner input = new Scanner(new File("input31.txt"));	//파일 입력 읽어들이기

			for(int T = input.nextInt(); T > 0; T--){
				int N = input.nextInt();
				int srcX = input.nextInt(), srcY = input.nextInt();
				int dstX = input.nextInt(), dstY = input.nextInt();
				int grid[][] = new int[N][N];

				for(int i = 0; i < N; i++){
					for(int j = 0; j < N; j++){
						grid[i][j] = input.nextInt();
					}
				}

				if(!CanGo(Arrays.copyOf(grid, N), N, srcX, srcY, dstX, dstY)){
					System.out.println(-1);
				}
				else{

					int num = 0;
					boolean going = true;
					while(going){
						Queue<Cell> queue = new LinkedList<Cell>();
						Cell Src = new Cell(srcX, srcY, 0, 0);
						queue.offer(Src);

						int copyGrid[][] = Arrays.copyOf(grid, N);
						
						while(!queue.isEmpty()){
							Cell current = queue.poll();
							if(current.x == dstX && current.y == dstY){
								System.out.println(num);
								going = false;
								break;
							}
							for(int d = 0; d < 4; d++){
								Cell next;
								int d_count;					
								int m_direct = 0;
								if(d == 0){m_direct = 0;}
								else if(d == 1){m_direct = 1;}
								else if(d == 2){m_direct = 2;}
								else if(d == 3){m_direct = 3;}

								if(current.x == srcX && current.y == srcY){
									d_count = 0;
								}
								else{
									if(m_direct != current.direction){d_count = current.turnCount+1;}
									else{d_count = current.turnCount+0;}
								}

								next = new Cell(current.x+flagX[d], current.y + flagY[d], m_direct, d_count);

								if(next.x >= 0 && next.x < N && next.y >= 0 && next.y < N 
										&& copyGrid[next.x][next.y] == 0 && next.turnCount <= num){
									copyGrid[next.x][next.y] = -1; //이 길을 지나갔음을 체크해둠
									queue.offer(next);
								}

							}
						}
						System.out.println("in going...");
						num++;
					}

				}

			}
			input.close(); //탐색 종료


		} catch (FileNotFoundException e) { System.out.println("file not found.."); }		
		long end = System.currentTimeMillis();							//시간계산 종료
		System.out.println( "run time : " + ( end - start )/1000.0 );	//시간 출력
	}
}
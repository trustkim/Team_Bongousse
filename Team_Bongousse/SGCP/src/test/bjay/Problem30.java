package test.bjay;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class Problem30 {	
	private static int N, M;
	private static int [][] maze;

	public static void main(String args[]){		
		try{
			Scanner input = new Scanner(new File("input30.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				N=input.nextInt();
				M=input.nextInt();
				maze = new int[N][M];
				for(int i=0;i<N;i++){
					for(int j=0;j<M;j++){
						maze[i][j] = input.nextInt();
					}
				}
				for(int i=0;i<N;i++){
					for(int j=0;j<M;j++){
						System.out.print(maze[i][j]);
					}
					System.out.println("");
				}
				
				//System.out.println("result : " + (solve("00") == false ? -1 : t));
				System.out.println("result : " + solve("00"));
			}			
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
	}
	
	public static int solve(String str){
		Queue<String> q = new LinkedList<String>();
		q.offer(str);
		int t = 0;
	
		while(!q.isEmpty()){
			System.out.println(q);
			String p = q.poll();
			maze[p.charAt(0)-48][p.charAt(1)-48] = 1;
			
			if(p.equals(""+(N-1)+(M-1))){
				return t;
			}
			if(p.charAt(0)-48 < N-1 && maze[p.charAt(0)-48+1][p.charAt(1)-48] == 0){		//아래로
				q.offer("" + (p.charAt(0)-48+1) + (p.charAt(1)-48));
			}
			if(p.charAt(1)-48 < M-1 && maze[p.charAt(0)-48][p.charAt(1)-48+1] == 0){		//오른쪽
				q.offer("" + (p.charAt(0)-48) + (p.charAt(1)-48+1));
			}
			if(p.charAt(0)-48 > 0 && maze[p.charAt(0)-48-1][p.charAt(1)-48] == 0){		//위로
				q.offer("" + (p.charAt(0)-48-1) + (p.charAt(1)-48));
			}
			if(p.charAt(1)-48 > 0 && maze[p.charAt(0)-48][p.charAt(1)-48-1] == 0){		//왼쪽
				q.offer("" + (p.charAt(0)-48) + (p.charAt(1)-48-1));
			}
			t++;
		}
		return -1;
		//return false;
		
	}
}
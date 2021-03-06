package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Problem27 {
	public static char[][] grid;
	public static int k;
	public static int cnt;
	
	public static void main(String args[]){		
		long start = System.currentTimeMillis();
		try{
			Scanner input = new Scanner(new File("input27.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				k = input.nextInt(); input.nextLine(); cnt=0;
				char[][] grid_A = new char[6][5];
				for(int i=0;i<6;i++) {
					String temp = input.nextLine();
					for(int j=0;j<5;j++)
						grid_A[i][j] = temp.charAt(j);
				}	// read grid_A
				char[][] grid_B = new char[6][5];
				for(int i=0;i<6;i++) {
					String temp = input.nextLine();
					for(int j=0;j<5;j++)
						grid_B[i][j] = temp.charAt(j);
				}	// read grid_B
				// file read complete
				
				// merge, transpose grids and sorting each wheels
				grid = sort(transpose(merge(grid_A, grid_B)));
				//Print_grid(grid); System.out.println();
				
				// get k passwords
				System.out.println(password("",0));
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	public static char[][] merge(char[][] A, char[][] B){
		char[][] C = new char[6][5];
		
		HashSet<Character> hs = new HashSet<Character>(6);
		for(int i=0;i<5;i++){
			int k = 0;
			
			for(int j=0;j<6;j++){
				hs.add(A[j][i]);
			}
			for(int j=0;j<6;j++){
				if(hs.contains(B[j][i])){
					C[k++][i] = B[j][i];
				}
			}
			hs.clear();
		}
		return C;
	}
	public static char[][] transpose(char[][] grid){
		char[] temp = new char[6];
		// trim & transpose
		char[][] trans_grid = new char[5][];
		
		for(int i=0;i<5;i++){
			int wheel_length = 6;
			int cnt=0;
			for(int j=0;j<6;j++){
				if(grid[j][i]==0) wheel_length--;
				else temp[cnt++] = grid[j][i];
			}
			if(wheel_length>0) trans_grid[i] = new char[wheel_length];
			for(int j=0;j<wheel_length;j++)
				trans_grid[i][j] = temp[j];
		}
		return trans_grid;
	}
	public static char[][] sort(char[][] grid){
		for(int i=0;i<grid.length;i++)
			if(grid[i] != null) Arrays.sort(grid[i]);		
		return grid;
	}
	public static String password(String pass, int level){
		if(grid[level]!=null){
			for(int i=0;i<grid[level].length;i++){					// 한 레벨에서 휠에있는 모든 문자를 확인
				if(pass.length()==level) pass += grid[level][i];	// 새로 pass에 문자를 추가할 때
				else pass=pass.substring(0, level)+grid[level][i];	// pass에서 이전 문자를 지우고 새로 넣을 때
				if(level<4){										// 5자리 pass가 완성이 안됐다면 다음 레벨로 진행
					String temp_pass = password(pass, level+1); if(cnt==k) return temp_pass;	// 중간에 정답이 나온다면 리커전 탈출
					pass = temp_pass.equals("No")?pass:temp_pass;	// 중간에 No가 나올 수 있는데 1번 휠을 검사하는 레벨이 종료 될 때만 No가 출력되게 걸러줌
				}else {
					cnt++;
					if(cnt==k) return pass;	// 조건을 만족하면 리커전 탈출 시작
				}
			}
		}
		return "No";
	}

	public static void Print_grid(char[][] grid){
		for(int j=0;j<5;j++)
			if(grid[j]!=null) {
				for(int i=0;i<grid[j].length;i++){
					System.out.print(grid[j][i]);
					if(i==grid[j].length-1) System.out.println();
				}
			}else System.out.println();
	}
}
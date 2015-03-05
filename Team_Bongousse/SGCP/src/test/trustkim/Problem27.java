package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Problem27 {
	public static char[][] grid;
	public static int k;
	
	public static void main(String args[]){		
		long start = System.currentTimeMillis();
		try{
			Scanner input = new Scanner(new File("input27.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				k = input.nextInt(); input.nextLine();
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
				
				// merge grids or sorting each grids
				grid = merge(grid_A, grid_B);
				grid = sort(grid);
				Print_grid(grid); System.out.println();
				
				// get k passwords
				//int[] wheel_index = {0,0,0,0,0};
				System.out.println(password("",0,0,0));
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
	public static char[][] sort(char[][] grid){
		for(int i=0;i<5;i++) {
			char[] temp = new char[6];
			for(int j=0;j<6;j++)
				temp[j] = grid[j][i];	// temp reads a wheel in a loop
			for(int j=0;j<6;j++) grid[j][i] = 0;
			Arrays.sort(temp);
			for(int j=0;j<temp.length;j++)
				grid[j][i] = temp[j];
		}
		return grid;
	}
	public static String password(String pass, int cnt, int level, int i){
		if(i<5) {
			char temp = grid[i][level];
			if(temp!=0){
				pass+=temp;
				if(level<4){
					pass = password(pass,cnt,level++,0);
				}
			}
			pass = password(pass,cnt,level,i++);
			
		}

		return "No";
	}
	public static void reCur(String str[], int num[], int limit[], int count, int k){
		if(count == k-1){
			StringBuilder output = new StringBuilder();
			for(int i = 0; i < 5; i++){	output.append(str[i].charAt(num[i])); }
			System.out.println(output);
		}
		else{
			count++;
			num[4]++;
			for(int j = 4; j >= 0; j--){
				if(num[j] > limit[j]-1){
					if(j == 0){System.out.println("NO"); return;}
					else{num[j] = 0; num[j-1]++;}
				}
			}		
			reCur(str, num, limit, count, k);
		}
	}
	public static void Print_grid(char[][] grid){
		for(int i=0;i<6;i++)
			for(int j=0;j<5;j++){
				System.out.print(grid[i][j]+"("+(int)grid[i][j]+")");
				if(j==4) System.out.println();
			}
	}
}
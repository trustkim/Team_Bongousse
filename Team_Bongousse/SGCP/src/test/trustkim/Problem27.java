package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Problem27 {

	public static void main(String args[]){		
		long start = System.currentTimeMillis();
		try{
			Scanner input = new Scanner(new File("input27.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				int k = input.nextInt(); input.nextLine();
				//System.out.println(k);
				char[][] grid_A = new char[6][5];
				for(int i=0;i<6;i++) {
					String temp = input.nextLine();
					for(int j=0;j<5;j++)
						grid_A[i][j] = temp.charAt(j);
				}	// read grid_A
				//Print_grid(grid_A);
				char[][] grid_B = new char[6][5];
				for(int i=0;i<6;i++) {
					String temp = input.nextLine();
					for(int j=0;j<5;j++)
						grid_B[i][j] = temp.charAt(j);
				}	// read grid_B
				//Print_grid(grid_B);
				// file read complete
				
				// merge grids or sorting each grids
				char[][] grid = merge(grid_A, grid_B);
				Print_grid(grid); System.out.println();
				
				// get k passwords
				
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	public static char[][] merge(char[][] A, char[][] B){
		char[][] C = new char[6][5];
		
		HashSet<Character> hs = new HashSet<Character>(6);	// hash set for counting arguments.
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
	
	public static void Print_grid(char[][] grid){
		for(int i=0;i<6;i++)
			for(int j=0;j<5;j++){
				System.out.print(grid[i][j]);
				if(j==4) System.out.println();
			}
	}
}
package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
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
				
				// get k passwords
				
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	
	public static void Print_grid(char[][] grid){
		for(int i=0;i<6;i++)
			for(int j=0;j<5;j++){
				System.out.print(grid[i][j]);
				if(j==4) System.out.println();
			}
	}
}
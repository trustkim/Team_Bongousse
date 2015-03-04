package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem28 {

	public static void main(String args[]){		
		long start = System.currentTimeMillis();
		try{
			Scanner input = new Scanner(new File("input28.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				int[] watch_table = new int[9];
				for(int i=0;i<9;i++) watch_table[i] = input.nextInt();
				int[][] m_btn = new int[9][];
				for(int i=0;i<9;i++){
					m_btn[i] = new int[input.nextInt()];
					for(int k=0;k<m_btn[i].length;k++)
						m_btn[i][k] = input.nextInt();
				} // file read complete
				
				
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	
	public static boolean check(int[] watch_table){
		for(int i=0;i<9;i++){
			if(watch_table[i] != 12) return false;
		}
		return true;
	} // if any watch read not 12:00, return false.
}
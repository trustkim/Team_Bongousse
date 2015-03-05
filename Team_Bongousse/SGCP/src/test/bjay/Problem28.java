package test.bjay;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem28 {
	public static int mincount;

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
				
				mincount = 50;
				solve(m_btn, new int[9], watch_table, 0);
				
				System.out.println(mincount);
				
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	
	public static void solve(int [][] m_btn, int [] set_m_btn, int [] watch_table, int level){
		for(int i=0;i<m_btn.length;i++){
			System.out.print(set_m_btn[i] + " ");
		}
		System.out.println();
		if(!promising(set_m_btn)){
			return;
		}
		else if(check(watch_table)){
			if(mincount >= level){
				mincount = level;
			}
			return;
		}
		else
			for(int i=0;i<m_btn.length;i++){
				set_m_btn[i]++;
				for(int j=0;j<m_btn[i].length;j++){
					watch_table[m_btn[i][j]-1] = watch_table[m_btn[i][j]-1] + 3;
					if(watch_table[m_btn[i][j]-1] > 12)
						watch_table[m_btn[i][j]-1] = watch_table[m_btn[i][j]-1]-12;
				}
				solve(m_btn, set_m_btn, watch_table, level++);
			}
	}
	
	public static boolean promising(int [] set_m_btn){
		for(int i=0;i<set_m_btn.length;i++){
			if(set_m_btn[i]>=4)
				return false;
		}
		return true;
	}
	
	public static boolean check(int [] watch_table){
		for(int i=0;i<watch_table.length;i++){
			if(watch_table[i] != 12)
				return false;
		}
		return true;
	}
}
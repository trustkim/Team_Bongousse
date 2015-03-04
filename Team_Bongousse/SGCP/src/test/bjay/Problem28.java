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
				
				mincount = 0;
				solve(m_btn, watch_table, 0);
				
				System.out.println(mincount);
				
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	
	public static void solve(int [][] m_btn, int [] watch_table, int count){
		if(watch_table[0]==12 && watch_table[1]==12 && watch_table[2]==12 && watch_table[3]==12 && watch_table[4]==12 &&
			watch_table[5]==12 && watch_table[6]==12 && watch_table[7]==12 && watch_table[8]==12){
			if(mincount > count){
				mincount = count;
			}
			return;
		}
		else if(count >= 27){
			return;
		}
		else
			for(int i=0;i<m_btn.length;i++){
				for(int j=0;j<m_btn[i].length;j++){
					watch_table[m_btn[i][j]-1] += 3;
				}
				solve(m_btn, watch_table, ++count);
			}
	}
}
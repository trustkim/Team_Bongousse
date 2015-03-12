package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem28 {
	public static final int MAX = 262144;
	public static int result;
	public static int[][] m_btn;
	public static void main(String args[]){		
		long start = System.currentTimeMillis();
		try{
			Scanner input = new Scanner(new File("input28.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				int[] watch_table = new int[9]; result = MAX+1;
				for(int i=0;i<9;i++) watch_table[i] = input.nextInt();
				m_btn = new int[9][];
				for(int i=0;i<9;i++){
					m_btn[i] = new int[input.nextInt()];
					for(int k=0;k<m_btn[i].length;k++)
						m_btn[i][k] = input.nextInt();
				} // file read complete
				watchs(watch_table, 0, 0);
				System.out.println(result>MAX?"No":result);
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	public static int[] push_btn(int[] watch_table, int btn_num){
		for(int i=0;i<m_btn[btn_num].length;i++){
			int temp = (watch_table[m_btn[btn_num][i]-1]+3)%12;
			watch_table[m_btn[btn_num][i]-1] = (temp==0?12:temp);
		}
		return watch_table;
	}
	public static boolean check(int [] watch_table){
		for(int i=0;i<watch_table.length;i++){
			if(watch_table[i] != 12)
				return false;
		}
		return true;
	} // if any watch read not 12:00, return false.
	
	public static void watchs(int[] watch_table, int level, int cnt){
		for(int i=0;i<4;i++){
			if(i>0) {watch_table = push_btn(watch_table, level);	cnt++;}// 현재 level에서 버튼을 누름
			if(check(watch_table))
				if(result>MAX) result = cnt;
				else result = (result>cnt?cnt:result);
			if(level<8){
				int temp_cnt = cnt; int[] temp = new int[9];
				for(int j=0;j<9;j++)
					temp[j] = watch_table[j];
				watchs(temp, level+1, temp_cnt);
			}
		}
	}
}
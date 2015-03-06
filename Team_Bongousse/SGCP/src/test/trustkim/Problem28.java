package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem28 {
	public static int cnt;
	public static int[][] m_btn;
	public static void main(String args[]){		
		long start = System.currentTimeMillis();
		try{
			Scanner input = new Scanner(new File("input28.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				int[] watch_table = new int[9]; cnt=0;
				for(int i=0;i<9;i++) watch_table[i] = input.nextInt();
				m_btn = new int[9][];
				for(int i=0;i<9;i++){
					m_btn[i] = new int[input.nextInt()];
					for(int k=0;k<m_btn[i].length;k++)
						m_btn[i][k] = input.nextInt();
				} // file read complete

				System.out.println(watchs(watch_table, 0));
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		System.out.println("Elapsed: "+(((long)System.currentTimeMillis())-start)/1000.0);
	}
	public static int[] push_btn(int[] watch_table, int btn_num){
		for(int i=0;i<m_btn[btn_num].length;i++)
			watch_table[m_btn[btn_num][i]-1]+=3;
		return watch_table;
	}
	public static boolean check(int [] watch_table){
		for(int i=0;i<watch_table.length;i++){
			if(watch_table[i] != 12)
				return false;
		}
		return true;
	} // if any watch read not 12:00, return false.
	
	public static String watchs(int[] watch_table, int level){
		for(int i=0;i<4;i++){
			if(true) return (""+cnt);
		}
		
		return "No";
	}
//	public static String password(String pass, int level){
//		if(grid[level]!=null){
//			for(int i=0;i<grid[level].length;i++){					// �� �������� �ٿ��ִ� ��� ���ڸ� Ȯ��
//				if(pass.length()==level) pass += grid[level][i];	// ���� pass�� ���ڸ� �߰��� ��
//				else pass=pass.substring(0, level)+grid[level][i];	// pass���� ���� ���ڸ� ����� ���� ���� ��
//				if(level<4){										// 5�ڸ� pass�� �ϼ��� �ȵƴٸ� ���� ������ ����
//					String temp_pass = password(pass, level+1); if(cnt==k) return temp_pass;	// �߰��� ������ ���´ٸ� ��Ŀ�� Ż��
//					pass = temp_pass.equals("No")?pass:temp_pass;	// �߰��� No�� ���� �� �ִµ� 1�� ���� �˻��ϴ� ������ ���� �� ���� No�� ��µǰ� �ɷ���
//				}else {
//					cnt++;
//					if(cnt==k) return pass;	// ������ �����ϸ� ��Ŀ�� Ż�� ����
//				}
//			}
//		}
//		return "No";
//	}
}
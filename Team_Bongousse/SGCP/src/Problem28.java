import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem28 {
	public static final int MAX = 262144;	// ���� Ʈ���� ��� �湮�ϸ� 4�� 9�¹����� ��ư�� ���� �� �ִ�
	public static int result;
	public static int[][] m_btn;
	public static void main(String args[]){
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
	}
	public static int[] push_btn(int[] watch_table, int btn_num){
		for(int i=0;i<m_btn[btn_num].length;i++){
			int temp = (watch_table[m_btn[btn_num][i]-1]+3)%12;
			watch_table[m_btn[btn_num][i]-1] = (temp==0?12:temp);
		}
		return watch_table;
	} // Ư�� ��ư�� ���� �ð���� �� �� ������
	public static boolean check(int [] watch_table){
		for(int i=0;i<watch_table.length;i++){
			if(watch_table[i] != 12)
				return false;
		}
		return true;
	} // ��� �ð谡 12:00���� �˻�
	
	public static void watchs(int[] watch_table, int level, int cnt){
		for(int i=0;i<4;i++){	// �� ��ư�� �ȴ����ų� 1~3�� �� ���� �� �ִ�
			if(i>0) {watch_table = push_btn(watch_table, level);	cnt++;}	// ���� level���� ��ư�� ����. ������ ���� ���� ���� ����
			if(check(watch_table))	// �ð���� promising�� �� �˻�
				if(result>MAX) result = cnt;	// ���ʷ� promising�� ��� 
				else result = (result>cnt?cnt:result);	// �׻� ��� ���� Ʈ���� ��ȸ �ؾ� �ּҰ��� ã�� �� �ִ�
			if(level<8){	// M1~M8������ ���� ������ ������
				int temp_cnt = cnt; int[] temp = new int[9];
				for(int j=0;j<9;j++)
					temp[j] = watch_table[j];	// ������ ������ ����. ������ �������� �ʵ��� ����
				watchs(temp, level+1, temp_cnt);
			}
		}
	}
}
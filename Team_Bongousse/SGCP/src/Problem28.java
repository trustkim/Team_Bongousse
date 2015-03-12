import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem28 {
	public static final int MAX = 262144;	// 상태 트리를 모두 방문하면 4의 9승번까지 버튼을 누를 수 있다
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
	} // 특정 버튼을 눌러 시계들을 한 번 돌린다
	public static boolean check(int [] watch_table){
		for(int i=0;i<watch_table.length;i++){
			if(watch_table[i] != 12)
				return false;
		}
		return true;
	} // 모든 시계가 12:00인지 검사
	
	public static void watchs(int[] watch_table, int level, int cnt){
		for(int i=0;i<4;i++){	// 각 버튼은 안누르거나 1~3번 만 누를 수 있다
			if(i>0) {watch_table = push_btn(watch_table, level);	cnt++;}	// 현재 level에서 버튼을 누름. 누르지 않을 때는 세지 않음
			if(check(watch_table))	// 시계들이 promising한 지 검사
				if(result>MAX) result = cnt;	// 최초로 promising한 경우 
				else result = (result>cnt?cnt:result);	// 항상 모든 상태 트리를 순회 해야 최소값을 찾을 수 있다
			if(level<8){	// M1~M8까지는 다음 레벨로 진행함
				int temp_cnt = cnt; int[] temp = new int[9];
				for(int j=0;j<9;j++)
					temp[j] = watch_table[j];	// 데이터 유지를 위해. 참조로 대입하지 않도록 주의
				watchs(temp, level+1, temp_cnt);
			}
		}
	}
}
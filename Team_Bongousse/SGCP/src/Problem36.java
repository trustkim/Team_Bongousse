import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem36 {
	private static int N;
	private static int[] indegree;				// ���ĺ� ���ں��� �Է� ������ ���� ���� �迭
	private static int[] outdegree;				// ���ĺ� ���ں� ��� ������ ���� ���� �迭
	private static int[] where_is_destination;
	// �� ���ĺ� ���ڸ� �������� �׷��� �𵨸� �� ��, �ش� ���ڿ��� ����Ͽ� ���� ������ �� �ִ� ���� ����(���� ������ ��� �ϴ��Ŀ� ���� ������)
	
	public static void main(String[] args){
		try {
			Scanner sc = new Scanner(new File("input36.txt"));
			String temp;
			for(int T = sc.nextInt();T>0;T--) {
				N = sc.nextInt();
				indegree = new int[26];				// 0���� �ʱ�ȭ
				outdegree = new int[26];			// 0���� �ʱ�ȭ
				where_is_destination = new int[26];	// ��� ���ĺ��� �������� �ڽ����� �ʱ�ȭ
				for(int i=0;i<26;i++) where_is_destination[i] = i;
				for(int i=0;i<N;i++) {
					temp = sc.next().trim();
					int start = temp.charAt(0)-'a';				// ���� �Է� �ܾ��� ù����
					int end = temp.charAt(temp.length()-1)-'a';	// ���� �Է� �ܾ��� ������
					indegree[start]++;	// start�� �����ϴ� �ܾ��̸� �ش� ���ĺ� �Է� ������ ī��Ʈ
					outdegree[end]++;	// end�� ������ �ܾ��̸� �ش� ���ĺ� ��� ������ ī��Ʈ
					int sdest = findDest(start);	// start�� �����ϴ� ���ĺ��� ������
					int edest = findDest(end);		// end�� ������ ���ĺ��� ������
					if(sdest!=edest)
						where_is_destination[sdest] = edest;
						/*	�� �ܾ�� ù���� �������� ������ �������� �ٸ���
						 *  ù���ڴ� �ݵ�� �����ڸ� ���� ù������ �������� �����ϰ� �������� ���������� ������ �� �ִ�.
						 *  ab, ax �� �ܾ ������� �Էµ� ���
						 *  a�� b�� ���� �� �� �ְ�, a�� �������� b�� ���� �ȴ�.
						 *  ���� ax�� ������ a�� �������� b, x�� �������� x�� a�� b�� ���� �� �� �ְ� x���� ���� �� �� �����Ƿ�
						 *  b�� x�� ���� �� �� �ְ� �ȴ�!
						 *  �̷��� �������� ���� �� �ִ� ������ �־��� ���ĺ� ���ڷ� ��Ե� circuit�� ���� �� �ִٰ� ����
						 */
				}// file read complete
				
				if(solve()){
					System.out.println("YES");
				}else System.out.println("NO");
			}
			
			sc.close();
		} catch(FileNotFoundException e){ System.out.println("file not found..."); }
	}
	/*
	 * ��ü�ߺμ����θ����� �װͰ� ������ findDest�� ���� ��ũ�� �����Ͽ���.
	 * ���� ��ũ: http://egloos.zum.com/mgkim/v/1007300
	 */
	private static int findDest(int v) {
		if(where_is_destination[v]==v) {
			return v;
		}else {
			where_is_destination[v] = findDest(where_is_destination[v]);
			return where_is_destination[v];
		}
	}
	private static boolean solve() {
		for(int i=0,sample_destination=0;i<26;i++) {
			if(indegree[i]>0||outdegree[i]>0) {	// ������ 0�̻��̸� �� ���̶� ������ ������
				if(sample_destination==0) sample_destination = findDest(i);
				if(indegree[i]!=outdegree[i]||findDest(i)!=sample_destination) return false;
				// �� �� �׸��� ���� ����, � ���ĺ����ε� ������ �������� �����ϴ��� �˻�
			}
		}		
		return true;
	}
}
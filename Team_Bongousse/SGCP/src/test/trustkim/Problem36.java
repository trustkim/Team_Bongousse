package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem36 {
	static class Node {
		char head, tail;
		int index;
		Node next;
		Node(char h, char t, int i) { head=h; tail=t; index=i; next=null;}
	}
	private static int N;
	private static Node[] data;
	private static final int HEAD = 0;
	private static final int TAIL = 1;
	private static boolean[] isChecked;
	public static void main(String[] args){
		try {
			Scanner sc = new Scanner(new File("input36.txt"));
			data = new Node[N];
			isChecked = new boolean[N];
			String temp;
			for(int T = sc.nextInt();T>0;T--) {
				N = sc.nextInt();
				for(int i=0;i<N;i++) {
					temp = sc.next().trim(); System.out.println(temp.charAt(temp.length()-1));
					data[i] = new Node(temp.charAt(0), temp.charAt(temp.length()-1), i);
					isChecked[i] = false;
				}// file read complete
				
				if(solve()){
					System.out.println("YES");
				}else System.out.println("NO");
			}
			
			sc.close();
		} catch(FileNotFoundException e){ System.out.println("file not found..."); }
	}
	
	private static boolean solve() {
		for(int i=0;i<N;i++)							// TAIL �迭 �ε���
			for(int j=0;j<N;j++) {						// HEAD �迭 �ε���
				if(data[j].index==data[i].index) {		// �ڱ� �ڽ��� ����ų �� ����
					break;								// �Ѿ
				}
				if(!isChecked[j]) {
					if(data[j].head == data[i].tail) {
						isChecked[j] = true;
						j=N;
					}
					if(data[j].head > data[i].tail) return false;	
				}
			}		
		return test_one_cycle();
	}
	private static boolean test_one_cycle() {
		Node Cur = data[0];
		int cnt = 0;
		while((Cur = Cur.next).index!=0) {
			cnt++;
		}
		if(cnt==N) return true;
		else return false;
	}
}

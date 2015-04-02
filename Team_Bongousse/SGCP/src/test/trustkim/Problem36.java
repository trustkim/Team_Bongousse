package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem36 {
	static class Node {
		char head, tail;
		int index;
		Node(char h, char t, int i) { head=h; tail=t; index=i; }
	}
	private static int N;
	private static Node[] data;
	private static final int HEAD = 0;
	private static final int TAIL = 1;
	public static void main(String[] args){
		try {
			Scanner sc = new Scanner(new File("input36.txt"));
			data = new Node[N];
			String temp;
			for(int T = sc.nextInt();T>0;T--) {
				N = sc.nextInt();
				for(int i=0;i<N;i++) {
					temp = sc.next();
					data[i] = new Node(temp.charAt(0), temp.charAt(temp.length()-1), i);
				}// file read complete
				
				if(solve()){
					System.out.println("YES");
				}else System.out.println("NO");
			}
			
			sc.close();
		} catch(FileNotFoundException e){ System.out.println("file not found..."); }
	}
	
	private static boolean solve() {
		for(int i=0;i<N;i++)							// TAIL 배열 인덱스
			for(int j=0;j<N;j++) {						// HEAD 배열 인덱스
				if(data[j].index==data[i].index) {		// 자기 자신은 가리킬 수 없다
					break;								// 넘어감
				}
			}
		
		return false;
	}
	private static boolean test_one_cycle() {
		return false;
	}
}

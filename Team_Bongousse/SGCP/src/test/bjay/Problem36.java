package test.bjay;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Problem36 {
	static class Node {
		char head, tail;
		int index;
		Node next;
		Node(char h, char t, int i) { head=h; tail=t; index=i; next=null;}
		Node(Node A) {head=A.head; tail=A.tail; index=A.index; next=null;}
		public static Comparator<Node> headComparator = new Comparator<Node>() {
			public int compare(Node A, Node B) {
				return (A.head - B.head);
			}
		};
		public static Comparator<Node> tailComparator = new Comparator<Node>() {
			public int compare(Node A, Node B) {
				return (A.tail - B.tail);
			}
		};
	}
	private static int N;
	private static Node[] data;
	private static Node[] heads;
	private static Node[] tails;
	private static final int HEAD = 0;
	private static final int TAIL = 1;
	private static boolean[] isChecked;
	public static void main(String[] args){
		try {
			Scanner sc = new Scanner(new File("input36.txt"));
			String temp;
			for(int T = sc.nextInt();T>0;T--) {
				N = sc.nextInt();
				data = new Node[N];
				heads = new Node[N];
				tails = new Node[N];
				isChecked = new boolean[N];
				for(int i=0;i<N;i++) {
					temp = sc.next().trim();
					data[i] = new Node(temp.charAt(0), temp.charAt(temp.length()-1), i);
					heads[i] = new Node(data[i]);
					tails[i] = new Node(data[i]);
					System.out.println(data[i].head +", "+data[i].tail);
					isChecked[i] = false;
				}// file read complete
				
				Arrays.sort(heads, 0, N, Node.headComparator);
				Arrays.sort(tails, 0, N, Node.tailComparator);
				for(int i=0;i<N;i++)
					System.out.println(heads[i].head+" <-? "+tails[i].tail);
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
				if(!isChecked[j]) {
					if(N!=1 && heads[j].index==tails[i].index) {		// 자기 자신은 가리킬 수 없다
														// 넘어감
					}else if(heads[j].head == tails[i].tail) {
						isChecked[j] = true;
						data[tails[i].index].next = data[heads[j].index];
						System.out.println(heads[j].index+" <-! "+tails[i].index);
						break;
					}
					else if(heads[j].head > tails[i].tail) return false;
				}
			}
		return test_one_cycle();
	}
	private static boolean test_one_cycle() {
		Node Cur = data[0];
		int cnt = 0;
		while(Cur.next!=null) {
			Cur = Cur.next;
			cnt++;
			if(Cur.index==0) break;
		}
		if(cnt==N) return true;
		else return false;
	}
}
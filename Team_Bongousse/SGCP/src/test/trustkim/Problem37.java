package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem37 {
	private static int N;
	private static int[] tree;
	private static int[] nodeHasChilds;
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		try {
			Scanner sc = new Scanner(new File("input37.txt"));
			boolean promissing = true;	// 파일 읽는 중(트리를 형성하는 중) 완전 이진 트리의 높이 조건을 검사하여 한 케이스를 걸러 낼 수 있다.
			for(int T=sc.nextInt();T>0;T--) {
				N=sc.nextInt();			// 검사할 트리 노드 개수
				tree = new int[N];	// 트리를 저장할 배열을 만듦 
				nodeHasChilds = new int[N];	// 각 노드가 부모로 호출 되는 개수를 세어 자식이 두 개를 초과하는 경우를 방지?
				tree[0] = 1;				// root는 항상 1로
				int p,c,f;					// p: 부모, c: p의 자식, f: 왼쪽/오른쪽 자식 플래그
				for(int i=1;i<N;i++) {		// N-1 loop
					p=sc.nextInt(); c = sc.nextInt(); f = sc.nextInt();
					int childIndex = 2*getIndex(p)+1+f;
					//System.out.println("...");
					if(childIndex<N)
						tree[childIndex] = c;
					else {
						promissing = false;	// 현재 케이스 포문 중단해버리면 파일읽던 포인터 맞춰 줘야 함...
					}
				}// file read complete
				//System.out.println("파일 읽기 끝");
				if(solve()&&promissing) System.out.println("Yes");
				else System.out.println("No");
			}
			System.out.println("Elapsed: "+((long)System.currentTimeMillis()-start)/1000.0);
			sc.close();
		}catch(FileNotFoundException e) { System.out.println("file not found...");}
	}
	private static int getIndex(int p) {	// 트리 배열에서 동일한 원소 값을 찾아 그 인덱스를 반환
		for(int i=0;i<N;i++){
			if(tree[i]==p) return i;
		}
		return -1;
	}	// O(N*N) 인가? 이렇게 하는거랑 연결리스트로 트리 만들어서 각 노드 검사하는 거랑 어느게 더 낮지?
	private static boolean solve() {	// 만들어진 트리를 검사하여 중간에 빈 곳이 있는지 검사		
		for(int i=0;i<N;i++) {
			if(tree[i]<1) return false; 
		}
		return true;
	}	// O(N)
}
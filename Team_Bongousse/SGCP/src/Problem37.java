import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem37 {
	private static int N;
	private static int[] tree;
	private static int[][] backupNodes;
	private static boolean isCBT;
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("input37.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				isCBT = true;	// 파일 읽는 중(트리를 형성하는 중) 완전 이진 트리의 조건을 검사하여 한 케이스를 걸러 낼 수 있다.
				N=sc.nextInt();				// 검사할 트리 노드 개수
				tree = new int[N];			// 트리를 저장할 배열을 만듦
				backupNodes = new int[N+1][2];	// 노드를 읽는 순서상의 문제로 미생성 노드를 부모로 하는 노드를 저장할 배열
				tree[0] = 1;				// root는 항상 1로
				int p,c,f;					// p: 부모, c: p의 자식, f: 왼쪽/오른쪽 자식 플래그
				for(int i=1;i<N;i++) {		// N-1 loop
					p=sc.nextInt(); c = sc.nextInt(); f = sc.nextInt();
					insert(p,c,f);
				}// file read complete
				if(isCBT) System.out.println("Yes");
				else System.out.println("No");
			}
			sc.close();
		}catch(FileNotFoundException e) { System.out.println("file not found...");}
	}
	private static int getIndex(int p) {	// 트리 배열에서 동일한 원소 값을 찾아 그 인덱스를 반환
		for(int i=0;i<N;i++){
			if(tree[i]==p) return i;
		}
		return -1;	// 없으면 -1
	}	// O(N)
	private static void backupProcess(int p) {	// 먼저 백업해둔 부모보다 먼저 읽은 자식 노드들을 트리에 추가.
		int left = backupNodes[p][0];
		int right = backupNodes[p][1];
		if(left!=0) {
			insert(p,left,0);
		}
		if(right!=0) {
			insert(p,right,1);
		}
		return;
	}	// backupProcess와 insert에서 리커전이 왔다갔다 한다 (@_@ )
	private static void insert(int p, int c, int f) {
		int childIndex = 2*getIndex(p)+1+f;	// 검사하는 조건은 부모와 자식 노드간 인덱스가 일정하다는 것.
		if(childIndex<=0){
			// 지금 추가할 노드의 부모를 아직 안읽은 경우 백업배열에 저장.
			backupNodes[p][f] = c;
		}else if(childIndex<N){	// insert 할 수 있음
			tree[childIndex] = c;
			backupProcess(c);	// 후처리 리커전
		}else {
			isCBT = false;	// 현재 케이스 포문 중단해버리면 파일읽던 포인터 맞춰 줘야 함...
		}
	} // O(N*N) 인가? 이렇게 하는거랑 연결리스트로 트리 만들어서 각 노드 검사하는 거랑 어느게 더 낮지?
}
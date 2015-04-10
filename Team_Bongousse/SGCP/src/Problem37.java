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
				isCBT = true;	// ���� �д� ��(Ʈ���� �����ϴ� ��) ���� ���� Ʈ���� ������ �˻��Ͽ� �� ���̽��� �ɷ� �� �� �ִ�.
				N=sc.nextInt();				// �˻��� Ʈ�� ��� ����
				tree = new int[N];			// Ʈ���� ������ �迭�� ����
				backupNodes = new int[N+1][2];	// ��带 �д� �������� ������ �̻��� ��带 �θ�� �ϴ� ��带 ������ �迭
				tree[0] = 1;				// root�� �׻� 1��
				int p,c,f;					// p: �θ�, c: p�� �ڽ�, f: ����/������ �ڽ� �÷���
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
	private static int getIndex(int p) {	// Ʈ�� �迭���� ������ ���� ���� ã�� �� �ε����� ��ȯ
		for(int i=0;i<N;i++){
			if(tree[i]==p) return i;
		}
		return -1;	// ������ -1
	}	// O(N)
	private static void backupProcess(int p) {	// ���� ����ص� �θ𺸴� ���� ���� �ڽ� ������ Ʈ���� �߰�.
		int left = backupNodes[p][0];
		int right = backupNodes[p][1];
		if(left!=0) {
			insert(p,left,0);
		}
		if(right!=0) {
			insert(p,right,1);
		}
		return;
	}	// backupProcess�� insert���� ��Ŀ���� �Դٰ��� �Ѵ� (@_@ )
	private static void insert(int p, int c, int f) {
		int childIndex = 2*getIndex(p)+1+f;	// �˻��ϴ� ������ �θ�� �ڽ� ��尣 �ε����� �����ϴٴ� ��.
		if(childIndex<=0){
			// ���� �߰��� ����� �θ� ���� ������ ��� ����迭�� ����.
			backupNodes[p][f] = c;
		}else if(childIndex<N){	// insert �� �� ����
			tree[childIndex] = c;
			backupProcess(c);	// ��ó�� ��Ŀ��
		}else {
			isCBT = false;	// ���� ���̽� ���� �ߴ��ع����� �����д� ������ ���� ��� ��...
		}
	} // O(N*N) �ΰ�? �̷��� �ϴ°Ŷ� ���Ḯ��Ʈ�� Ʈ�� ���� �� ��� �˻��ϴ� �Ŷ� ����� �� ����?
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem37 {
	private static int N;
	private static int[] tree;
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("input37.txt"));
			boolean isCBT = true;	// ���� �д� ��(Ʈ���� �����ϴ� ��) ���� ���� Ʈ���� ���� ������ �˻��Ͽ� �� ���̽��� �ɷ� �� �� �ִ�.
			for(int T=sc.nextInt();T>0;T--) {
				N=sc.nextInt();			// �˻��� Ʈ�� ��� ����
				tree = new int[N];	// Ʈ���� ������ �迭�� ����
				tree[0] = 1;				// root�� �׻� 1��
				int p,c,f;					// p: �θ�, c: p�� �ڽ�, f: ����/������ �ڽ� �÷���
				for(int i=1;i<N;i++) {		// N-1 loop
					p=sc.nextInt(); c = sc.nextInt(); f = sc.nextInt();
					int childIndex = 2*getIndex(p)+1+f;
					if(childIndex<N)
						tree[childIndex] = c;
					else {
						isCBT = false;	// ���� ���̽� ���� �ߴ��ع����� �����д� ������ ���� ��� ��...
					}
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
		return -1;
	}	// O(N*N) �ΰ�? �̷��� �ϴ°Ŷ� ���Ḯ��Ʈ�� Ʈ�� ���� �� ��� �˻��ϴ� �Ŷ� ����� �� ����?
}
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
			boolean promissing = true;	// ���� �д� ��(Ʈ���� �����ϴ� ��) ���� ���� Ʈ���� ���� ������ �˻��Ͽ� �� ���̽��� �ɷ� �� �� �ִ�.
			for(int T=sc.nextInt();T>0;T--) {
				N=sc.nextInt();			// �˻��� Ʈ�� ��� ����
				tree = new int[N];	// Ʈ���� ������ �迭�� ���� 
				nodeHasChilds = new int[N];	// �� ��尡 �θ�� ȣ�� �Ǵ� ������ ���� �ڽ��� �� ���� �ʰ��ϴ� ��츦 ����?
				tree[0] = 1;				// root�� �׻� 1��
				int p,c,f;					// p: �θ�, c: p�� �ڽ�, f: ����/������ �ڽ� �÷���
				for(int i=1;i<N;i++) {		// N-1 loop
					p=sc.nextInt(); c = sc.nextInt(); f = sc.nextInt();
					int childIndex = 2*getIndex(p)+1+f;
					//System.out.println("...");
					if(childIndex<N)
						tree[childIndex] = c;
					else {
						promissing = false;	// ���� ���̽� ���� �ߴ��ع����� �����д� ������ ���� ��� ��...
					}
				}// file read complete
				//System.out.println("���� �б� ��");
				if(solve()&&promissing) System.out.println("Yes");
				else System.out.println("No");
			}
			System.out.println("Elapsed: "+((long)System.currentTimeMillis()-start)/1000.0);
			sc.close();
		}catch(FileNotFoundException e) { System.out.println("file not found...");}
	}
	private static int getIndex(int p) {	// Ʈ�� �迭���� ������ ���� ���� ã�� �� �ε����� ��ȯ
		for(int i=0;i<N;i++){
			if(tree[i]==p) return i;
		}
		return -1;
	}	// O(N*N) �ΰ�? �̷��� �ϴ°Ŷ� ���Ḯ��Ʈ�� Ʈ�� ���� �� ��� �˻��ϴ� �Ŷ� ����� �� ����?
	private static boolean solve() {	// ������� Ʈ���� �˻��Ͽ� �߰��� �� ���� �ִ��� �˻�		
		for(int i=0;i<N;i++) {
			if(tree[i]<1) return false; 
		}
		return true;
	}	// O(N)
}
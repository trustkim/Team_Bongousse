import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem39 {
	private static int[] post;	// ����� postorder �迭
	private static int index;	// post�� �ε���
	public static void main(String [] args) {
		try {
			Scanner sc = new Scanner(new File("input39.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				int N = sc.nextInt();
				int [] preorder = new int[N];
				int [] inorder = new int[N];
				post = new int[N];
				for(int i=0;i<N;i++){
					preorder[i] = sc.nextInt();
				}
				for(int i=0;i<N;i++){
					inorder[i] = sc.nextInt();
				}
				// file read complete
				index = 0;
				try {
					solve(preorder,inorder);
					for(int i:post)
						System.out.print(i+" ");
					System.out.println();
				}catch(Exception e) {
					System.out.println("NOT EXIST");	// Ʈ���� �߻����� �ʴ� ���� �����ϱ� �����Ƽ� ����ó����...
				}
			}
			sc.close();
		}catch(FileNotFoundException e) { System.out.println("file not found...");}
	}

	private static void solve(int[] pre, int[] in) {
		// ���� pre�� �о� ��Ʈ�� ã��.
		if(pre.length==0) return ;
		int root = pre[0];								// ��Ʈ�� ã��.
		int rootIndex = getIndex(root,in);			// inorder�� ���� ���� root �ε����� ã��.
		
		// in���� ��Ʈ�� �������� ���� ����Ʈ���� ������ ����Ʈ���� ����.
		int[] inLeft = new int[rootIndex];				// ���� ����Ʈ�� �迭 ����.
		int[] inRight = new int[in.length-rootIndex-1];	// ������ ����Ʈ�� �迭 ����.
		for(int i=0;i<in.length;i++) {					// inorder�� �ѷ� ����.
			if(i<rootIndex)
				inLeft[i] = in[i];						// ���� ����Ʈ�� �迭 ����.
			if(i>rootIndex)
				inRight[i-rootIndex-1] = in[i];			// ������ ����Ʈ�� �迭 ����.
		}

		// �� ���� in�� ���� ��/������ ����Ʈ�� ũ�⸦ ������� pre�� ����.
		int[] preLeft = new int[inLeft.length];
		int[] preRight = new int[inRight.length];
		for(int i=1;i<pre.length;i++) {
			if(i<=rootIndex)
				preLeft[i-1] = pre[i];
			if(i>rootIndex)
				preRight[i-rootIndex-1] = pre[i];
		}
		
		// recursive
		solve(preLeft,inLeft);
		solve(preRight,inRight);
		post[index++] = root;

	}
	private static int getIndex(int root, int[] tree) {
		for(int i=0;i<tree.length;i++) {
			if(root==tree[i])
				return i;	// in���� ��Ʈ�� ã���� �ش� �ε����� ��ȯ.
		}
		return -1;			// ���� ��찡 �ֳ�?
	}
}
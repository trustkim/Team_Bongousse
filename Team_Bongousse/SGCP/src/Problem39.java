import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem39 {
	private static int[] post;	// 출력할 postorder 배열
	private static int index;	// post의 인덱스
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
					System.out.println("NOT EXIST");	// 트리가 발생하지 않는 경우는 생각하기 귀찮아서 예외처리함...
				}
			}
			sc.close();
		}catch(FileNotFoundException e) { System.out.println("file not found...");}
	}

	private static void solve(int[] pre, int[] in) {
		// 먼저 pre를 읽어 루트를 찾음.
		if(pre.length==0) return ;
		int root = pre[0];								// 루트를 찾음.
		int rootIndex = getIndex(root,in);			// inorder로 했을 때의 root 인덱스를 찾음.
		
		// in에서 루트를 기준으로 왼쪽 서브트리와 오른쪽 서브트리로 나눔.
		int[] inLeft = new int[rootIndex];				// 왼쪽 서브트리 배열 생성.
		int[] inRight = new int[in.length-rootIndex-1];	// 오른쪽 서브트리 배열 생성.
		for(int i=0;i<in.length;i++) {					// inorder를 둘로 나눔.
			if(i<rootIndex)
				inLeft[i] = in[i];						// 왼쪽 서브트리 배열 복사.
			if(i>rootIndex)
				inRight[i-rootIndex-1] = in[i];			// 오른쪽 서브트리 배열 복사.
		}

		// 그 다음 in을 나눈 왼/오른쪽 서브트리 크기를 기반으로 pre를 나눔.
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
				return i;	// in에서 루트를 찾으면 해당 인덱스를 반환.
		}
		return -1;			// 없는 경우가 있나?
	}
}
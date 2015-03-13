package test.dh;
//dh_ver2.0
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

class Node29{ int value, index; }

public class Problem29 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();	//���α׷� �ð� ���
		try {
			Scanner input = new Scanner(new File("input29.txt"));	//���� �Է� �о���̱�

			
			for(int T = input.nextInt(); T > 0; T --) {
				int N = input.nextInt();
				int ary[] = new int[N];		//�ܼ� �б� �迭
				int h[] = new int[N];		//������ h�迭

				Stack<Node29> stack = new Stack<Node29>();
				for(int i = 0; i < N; i++){	ary[i] = input.nextInt(); } //�⺻ �迭 ä���
				for(int i = 0; i < N; i++){
					//���ÿ� ���̰� �Ǵ� ����
					if((i == 0 && ary[i] > ary[i+1]) || (i > 0 && i < N-1 && ary[i] > ary[i+1]) || (i == N-1 && ary[i-1] <= ary[i])){
						Node29 newnode = new Node29();
						newnode.value = ary[i];
						newnode.index = i;
						stack.push(newnode);
					}
					//�ڽź��� ���� ���ӵ� ���ҵ� �� ���ϱ�(h�迭 �����)
					if(i > 0 && ary[i-1] <= ary[i]){
						Stack<Node29> substack = new Stack<Node29>();
						while(true){
							if(stack.empty()){ h[i] = i; break;}
							else if(stack.peek().value > ary[i]){ h[i] = i - stack.peek().index - 1; break;}
							substack.push(stack.pop());
						}
						while(!substack.empty()){ stack.push(substack.pop()); } //pop�� ���� �ٽ� �ǵ�����
					}
				}
				int sum = 0;
				for(int j = 0; j < N; j++){	sum += h[j]; sum %= 1000000; }
				System.out.println(sum); //���� ��� ���
			}
			input.close(); //Ž�� ����
			
			
		} catch (FileNotFoundException e) { System.out.println("file not found.."); }		
		long end = System.currentTimeMillis();							//�ð���� ����
		System.out.println( "run time : " + ( end - start )/1000.0 );	//�ð� ���
	}
}
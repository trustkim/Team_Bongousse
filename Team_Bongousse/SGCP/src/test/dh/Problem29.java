package test.dh;
//dh_ver2.0
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

class Node29{ int value, index; }

public class Problem29 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();	//프로그램 시간 재기
		try {
			Scanner input = new Scanner(new File("input29.txt"));	//파일 입력 읽어들이기

			
			for(int T = input.nextInt(); T > 0; T --) {
				int N = input.nextInt();
				int ary[] = new int[N];		//단순 읽기 배열
				int h[] = new int[N];		//문제속 h배열

				Stack<Node29> stack = new Stack<Node29>();
				for(int i = 0; i < N; i++){	ary[i] = input.nextInt(); } //기본 배열 채우기
				for(int i = 0; i < N; i++){
					//스택에 쌓이게 되는 조건
					if((i == 0 && ary[i] > ary[i+1]) || (i > 0 && i < N-1 && ary[i] > ary[i+1]) || (i == N-1 && ary[i-1] <= ary[i])){
						Node29 newnode = new Node29();
						newnode.value = ary[i];
						newnode.index = i;
						stack.push(newnode);
					}
					//자신보다 작은 연속된 원소들 수 구하기(h배열 만들기)
					if(i > 0 && ary[i-1] <= ary[i]){
						Stack<Node29> substack = new Stack<Node29>();
						while(true){
							if(stack.empty()){ h[i] = i; break;}
							else if(stack.peek().value > ary[i]){ h[i] = i - stack.peek().index - 1; break;}
							substack.push(stack.pop());
						}
						while(!substack.empty()){ stack.push(substack.pop()); } //pop한 스택 다시 되돌리기
					}
				}
				int sum = 0;
				for(int j = 0; j < N; j++){	sum += h[j]; sum %= 1000000; }
				System.out.println(sum); //최종 결과 출력
			}
			input.close(); //탐색 종료
			
			
		} catch (FileNotFoundException e) { System.out.println("file not found.."); }		
		long end = System.currentTimeMillis();							//시간계산 종료
		System.out.println( "run time : " + ( end - start )/1000.0 );	//시간 출력
	}
}
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem36 {
	private static int N;
	private static int[] indegree;				// 알파벳 문자별로 입력 차수를 세기 위한 배열
	private static int[] outdegree;				// 알파벳 문자별 출력 차수를 세기 위한 배열
	private static int[] where_is_destination;
	// 각 알파벳 문자를 정점으로 그래프 모델링 할 때, 해당 문자에서 출발하여 최종 도달할 수 있는 문자 저장(최초 세팅을 어떻게 하느냐에 따라 결정됨)
	
	public static void main(String[] args){
		try {
			Scanner sc = new Scanner(new File("input36.txt"));
			String temp;
			for(int T = sc.nextInt();T>0;T--) {
				N = sc.nextInt();
				indegree = new int[26];				// 0으로 초기화
				outdegree = new int[26];			// 0으로 초기화
				where_is_destination = new int[26];	// 모든 알파벳의 도착지를 자신으로 초기화
				for(int i=0;i<26;i++) where_is_destination[i] = i;
				for(int i=0;i<N;i++) {
					temp = sc.next().trim();
					int start = temp.charAt(0)-'a';				// 현재 입력 단어의 첫글자
					int end = temp.charAt(temp.length()-1)-'a';	// 현재 입력 단어의 끝글자
					indegree[start]++;	// start로 시작하는 단어이면 해당 알파벳 입력 차수를 카운트
					outdegree[end]++;	// end로 끝나는 단어이면 해당 알파벳 출력 차수를 카운트
					int sdest = findDest(start);	// start로 시작하는 알파벳의 목적지
					int edest = findDest(end);		// end로 끝나는 알파벳의 목적지
					if(sdest!=edest)
						where_is_destination[sdest] = edest;
						/*	한 단어에서 첫글자 목적지와 끝글자 목적지가 다르면
						 *  첫글자는 반드시 끝글자를 지나 첫글자의 목적지에 도달하고 끝글자의 목적지에도 도달할 수 있다.
						 *  ab, ax 두 단어가 순서대로 입력될 경우
						 *  a는 b에 도달 할 수 있고, a의 목적지는 b로 세팅 된다.
						 *  이후 ax를 읽으면 a의 목적지는 b, x의 목적지는 x로 a는 b에 도달 할 수 있고 x에도 도달 할 수 있으므로
						 *  b도 x에 도달 할 수 있게 된다!
						 *  이렇게 목적지를 이을 수 있는 전제는 주어진 알파벳 문자로 어떻게든 circuit을 만들 수 있다고 가정
						 */
				}// file read complete
				
				if(solve()){
					System.out.println("YES");
				}else System.out.println("NO");
			}
			
			sc.close();
		} catch(FileNotFoundException e){ System.out.println("file not found..."); }
	}
	/*
	 * 신체발부수지부모팀의 그것과 동일한 findDest는 다음 링크를 참조하였다.
	 * 참조 링크: http://egloos.zum.com/mgkim/v/1007300
	 */
	private static int findDest(int v) {
		if(where_is_destination[v]==v) {
			return v;
		}else {
			where_is_destination[v] = findDest(where_is_destination[v]);
			return where_is_destination[v];
		}
	}
	private static boolean solve() {
		for(int i=0,sample_destination=0;i<26;i++) {
			if(indegree[i]>0||outdegree[i]>0) {	// 차수가 0이상이면 한 번이라도 등장한 문자임
				if(sample_destination==0) sample_destination = findDest(i);
				if(indegree[i]!=outdegree[i]||findDest(i)!=sample_destination) return false;
				// 한 붓 그리기 가능 한지, 어떤 알파벳으로도 동일한 종착지에 도달하는지 검사
			}
		}		
		return true;
	}
}
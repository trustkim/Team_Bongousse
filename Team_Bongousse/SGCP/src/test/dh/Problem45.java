package test.dh;
//by bongousse..dh,tk,bj
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Problem45 {
	private int N, M;
	private int startNode_index, destNode_index;
	private int[] key;
	private int[] indegree;
	private Edge[] edges;
	private Edge[] inEdges;

	private class Edge{
		int node;
		int weight;
		Edge link = null;
		Edge(){}
		Edge(int node, int weight){ this.node = node; this.weight = weight; }
	}

	private void readInput(String str){
		try{
			Scanner input = new Scanner(new File(str));
			for(int T = input.nextInt(); T > 0; T--){
				N = input.nextInt(); M = input.nextInt();
				edges = new Edge[N];
				key = new int[N];
				indegree = new int[N];

				for(int j = 0; j < N; j++){key[j] = -1; edges[j] = new Edge();}
				for(int i = 0; i < M; i++){
					int from = input.nextInt();
					int to = input.nextInt();
					int weight = input.nextInt();
					
					Edge tmp = new Edge(to, weight);
					tmp.link = edges[from].link;
					edges[from].link = tmp;
					indegree[to]++;
				}				
				startNode_index = input.nextInt(); destNode_index = input.nextInt();

				//인접리스트 확인용 출력코드
				
				for(int i = 0; i < N; i++){
					Edge t = edges[i].link;
					System.out.print(i + " : ");
					while(t != null){
						System.out.print(" / " + i + " -> " + t.node);
						t = t.link;
					}
					System.out.println();
				}
				
				//읽기 완료, 실행할 함수 호출하기
				//...
				Dijkstra();
				inversedAdjlist();
				System.out.println(countMST(destNode_index));
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
	}


	private void Dijkstra(){
		Edge cur = edges[startNode_index].link;
		while(cur != null){
			key[cur.node] = cur.weight;
			indegree[cur.node]--;
			cur = cur.link;
		}

		indegree[startNode_index] = -1;
		key[startNode_index] = 0;
		while(true){
			int minIndex = -1;
			int minVal = -1;
			for(int i = 0; i < N; i++){
				if(indegree[i] == 0 && key[i] != -1 && (minVal == -1 || key[i] < minVal)){
					minIndex = i;
					minVal = key[i];
				}
			}
			if(minIndex == -1){break;}//dijkstra 종료

			Edge tmp = edges[minIndex].link;
			while(tmp != null){
				if(key[tmp.node] == -1 || (key[tmp.node] != -1 && key[tmp.node] >= key[minIndex] + tmp.weight)){
					key[tmp.node] = key[minIndex] + tmp.weight;
				}
				indegree[tmp.node]--;
				tmp = tmp.link;
			}
			//...
			indegree[minIndex] = -1;
		}
		
		
		//System.out.println(key[destNode_index]);
	}
	
	
	private void inversedAdjlist(){
		inEdges = new Edge[N];
		for(int j = 0; j < N; j++){	inEdges[j] = new Edge(); }
		for(int i = 0; i < N; i++){
			Edge tmp = edges[i].link;
			while(tmp != null){
				Edge local = new Edge(i, tmp.weight);
				local.link = inEdges[tmp.node].link;
				inEdges[tmp.node].link = local;
				
				tmp = tmp.link;
			}
		}
		for(int i = 0; i < N; i++){
			Edge t = inEdges[i].link;
			System.out.print(i + " : ");
			while(t != null){
				System.out.print(" / " + i + " -> " + t.node);
				t = t.link;
			}
			System.out.println();
		}
	}

	
	private int countMST(int index){
		if(index == startNode_index){ return 1; }

		int count = 0;
		Edge tmp = inEdges[index].link;
		while(tmp != null){
			if(key[index] - tmp.weight == key[tmp.node]){
				count += countMST(tmp.node);
			}
			tmp = tmp.link;
		}
		return count;
	}



	public static void main(String args[]){
		Problem45 pro = new Problem45();
		pro.readInput("input45.txt");
	}

}

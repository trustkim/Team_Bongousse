import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem45 {
	private class Edge{
		int u, v;
		int weight;
		Edge next;
		Edge(int u, int v, int w){
			this.u = u;
			this.v = v;
			this.weight = w;
			next = null;
		}
	}

	private int N;			// ���� ����	
	private int M;			// ���� ����
	private int[][] edges;	// �ܼ��� ���� ���̺�
	private Edge[] adjList;	// ��������Ʈ
	private int start;		// �����
	private int dest;		// ������

	/* Dijkstra�� ���� ��� */
	private int[] d;
	private int[] c;
	private boolean[] include;
	private void initDijkstra()
	{
		d = new int[N];	// ��ü �������� key���� �Ź� ���Ž��� ����. ���� N��
		c = new int[N];		// �굵 N��
		include = new boolean[N];
		for(int i=0;i<N;i++)
		{
			d[i] = -1;		// key�� ��������
		}
	}
	private int findMinKey()
	{
		// S�� ���� ���� ���� ��� �� �Ÿ��� ���� ����� ����� �ε����� ��ȯ
		double minKey=-1;		// ���� ���� ����� �������� �Ÿ��� ����
		int minIndex=-1;		// ���� ����ġ�� ���� ���� ���� �ε����� ��ȯ
		for(int i=0;i<N;i++)
		{
			if(!include[i]){
				int temp = d[i];
				if(minKey == -1 || (temp!=-1&&minKey > temp))
				{
					minKey = temp;
					minIndex=i;
				}
			}
		}
		return minIndex;
	}
	private void dijkstra()
	{	// Prim�� �˰���� ���� �����ϴ�!
		initDijkstra();
		d[start] = 0;		// ���� ������ ����.
		c[start] = 1;		// �ִ� ����� ������ �ʱ� ���� ���� ���...
		int cnt = 0;
		while(cnt<N)		// n-1�� �ݺ�. ���� dest���� �����Ǹ� ���ߵ��� ���� ����.
		{
			int u = findMinKey();			// S�� ������ �ʰ� key�� ���� ���� ������ ã��
			include[u] = true; cnt++;		// �� u�� S�� ���� ��Ŵ
			Edge p = adjList[u];
			while(p!=null)					// u�� ��� ������ ���� �� S�� ���� ���� ���� v�� key[v], pi[v]�� ����
			{
				int v = p.v;
				if(include[v])
					;// S�� ���Ե� key[v]�� �������� ����
				else
				{
					int weight = p.weight;
					if(d[v]==-1 || d[v] > d[u]+weight)
					{
						d[v] = d[u]+weight;
						c[v] = c[u];					// pi�� �ʿ� ������.
					}
					else if(d[v] == d[u]+weight)		// �ִ� ����� ������ ���� ���ؼ��� �� �κи� �߰����ָ� �ȴ�.
					{
						c[v] += c[u];
					}
				}
				p = p.next;
			}
		}
		//System.out.println("dijkstra compelete");
		System.out.println(c[dest]);
	}
	private void add(int u, int v, int weight)
	{
		if(adjList[u] == null)
			adjList[u] = new Edge(u,v,weight);
		else
		{
			Edge p = new Edge(u,v,weight);
			p.next = adjList[u];
			adjList[u] = p;
		}
	}
	private void readfile(Scanner sc){
		N = sc.nextInt();
		M = sc.nextInt();
		edges=new int[M][3];	// ��� ������ ���� ���̺�. ���߿� ��������Ʈ ������ �� ������...
		adjList = new Edge[N];
		for(int i=0;i<M;i++)
		{
			int u=sc.nextInt(),v=sc.nextInt(),w=sc.nextInt();
			add(u,v,w);
			edges[i][0] = u; edges[i][1] = v; edges[i][2] = w;
		}
		start = sc.nextInt(); dest = sc.nextInt();
	}
	public static void main(String[] args){
		Problem45 test = new Problem45();
		Scanner sc = null;
		try {
			sc = new Scanner(new File("input45.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int T=sc.nextInt();T>0;T--)
		{
			test.readfile(sc);
			test.initDijkstra();	// ���� ���Ϸ� �ٸ� �ʿ��� �ڷᱸ���� ����
			test.dijkstra();// ���ͽ�Ʈ�� ����
		}
		sc.close();
	}
}

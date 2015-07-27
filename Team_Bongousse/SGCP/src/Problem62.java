import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Problem62 {
	static class Interval implements Comparable<Interval>
	{
		int s,f;
		public Interval(int start, int finish) {s=start;f=finish;}
		boolean isOverlap(Interval other)
		{	// 겹치지 않는 경우를 걸러내는게 더 간명함!
			if(other.f < s)
				return false;
			if(f < other.s)
				return false;
			return true;
		}
		int getLength()
		{
			return f-s;
		}
		@Override
		public int compareTo(Interval other) {
			return s-other.s;
		}
	}
	
	public static ArrayList<Interval> list;
	public static Interval[] intervals;
	static int getSum()
	{
		int sum = 0;
		for(Interval p:list)
			sum += p.getLength();
		return sum;
	}
	public static void main(String[] args)
	{
		try {
			Scanner sc = new Scanner(new File("input62.txt"));
			for(int T=sc.nextInt();T>0;T--)
			{
				int N=sc.nextInt();
				intervals = new Interval[N];
				list = new ArrayList<Interval>();
				for(int i=0;i<N;i++)
				{
					intervals[i] = new Interval(sc.nextInt(),sc.nextInt());
				}	// file read complete
				Arrays.sort(intervals);	// sorting at fist
				int start = intervals[0].s, finish = intervals[0].f;
				for(int i=1;i<intervals.length;i++){
					if((new Interval(start,finish)).isOverlap(intervals[i]))
					{
						finish = Math.max(finish, intervals[i].f);	// 겹치는 경우 finish를 갱신
					}else {
						list.add(new Interval(start,finish));		// 겹치지 않으면 지금까지의 구간을 추가
						start = intervals[i].s;
						finish = intervals[i].f;
					}
				}
				list.add(new Interval(start,finish));	// 마지막에 남은 구간을 추가함
				System.out.println(getSum());	// 총 합을 계산 함
			}
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
}
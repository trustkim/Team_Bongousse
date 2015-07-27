package test.trustkim;

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
		{
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
				}
				Arrays.sort(intervals);
				int start = intervals[0].s, finish = intervals[0].f;
				for(int i=1;i<intervals.length;i++){
					if((new Interval(start,finish)).isOverlap(intervals[i]))
					{
						finish = Math.max(finish, intervals[i].f);
					}else {
						list.add(new Interval(start,finish));
						start = intervals[i].s;
						finish = intervals[i].f;
						if(i == intervals.length-1) {
							list.add(new Interval(start,finish));
							start = 0; finish = 0;
						}
					}
				}
				if(start != 0 && finish != 0)
					list.add(new Interval(start,finish));
				System.out.println(getSum());	// 총 합을 계산 함
			}
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}
	}
}
package test.bjay;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Problem25 {	


	public static void main(String args[]){		
		try{
			Scanner input = new Scanner(new File("input25.txt"));
			for(int T = input.nextInt(); T > 0; T--){
				int N=input.nextInt();
				String str = input.next();

				System.out.println("result = " + longestPalindrome(str, N));
			}			
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}	
	}

	static int longestPalindrome(String s, int size){		//가장 긴 회문을 찾는 함수
		System.out.println(size);
		if(size<=1)
			return 1;
		for(int i=0;i<s.length()-size+1;i++){				//i번째부터 size만큼 떼어내서,
			char [] ary = s.substring(i,i+size).toCharArray();		//charArray로 바꾼다음
			Arrays.sort(ary);								//먼저 이진정렬을 해주고,
			System.out.println(ary);
			while(nextPermutation(ary)){					//순열을 돌려가면서
				System.out.println(ary);
				if(isPalindrome(ary))						//회문이면
					//System.out.println("size = " + size);
					return size;							//현재의 size를 리턴한다.
			}
		}

		longestPalindrome(s, size-1);						//없다면 size를 1줄여서 호출한다.
		return -1;
	}

	static boolean isPalindrome(char [] s){		//회문인지 판별하는 함수
		if(s.length%2 == 0){					//s[i]와 s[s.length-1-i]를 비교해서 다른경우가 있다면 false를 리턴, 모두 통과하면 true를 리턴
			for(int i=0;i<s.length/2;i++){
				if(s[i] != s[s.length-1-i])
					return false;
			}
		}
		else if(s.length%2 == 1){
			for(int i=0;i<s.length/2;i++){
				if(s[i] != s[s.length-1-i])
					return false;
			}
		}
		return true;

	}
	static boolean nextPermutation(char[] array){	//순열. workshop4주차의 pdf파일에 있는 nextPermutation을 int에서 char로 수정.
		int i=array.length -1;
		while(i>0 && array[i-1] >= array[i])
			i--;
		if(i<=0)
			return false;

		int j=array.length-1;
		while(array[j]<=array[i-1])
			j--;

		char temp = array[i-1];
		array[i-1] = array[j];
		array[j] = temp;

		j = array.length -1;
		while(i<j){
			temp = array[i];
			array[i] = array[j];
			array[j] = temp;
			i++;
			j--;
		}
		return true;
	}

}

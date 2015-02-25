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

	static int longestPalindrome(String s, int size){		//���� �� ȸ���� ã�� �Լ�
		System.out.println(size);
		if(size<=1)
			return 1;
		for(int i=0;i<s.length()-size+1;i++){				//i��°���� size��ŭ �����,
			char [] ary = s.substring(i,i+size).toCharArray();		//charArray�� �ٲ۴���
			Arrays.sort(ary);								//���� ���������� ���ְ�,
			System.out.println(ary);
			while(nextPermutation(ary)){					//������ �������鼭
				System.out.println(ary);
				if(isPalindrome(ary))						//ȸ���̸�
					//System.out.println("size = " + size);
					return size;							//������ size�� �����Ѵ�.
			}
		}

		longestPalindrome(s, size-1);						//���ٸ� size�� 1�ٿ��� ȣ���Ѵ�.
		return -1;
	}

	static boolean isPalindrome(char [] s){		//ȸ������ �Ǻ��ϴ� �Լ�
		if(s.length%2 == 0){					//s[i]�� s[s.length-1-i]�� ���ؼ� �ٸ���찡 �ִٸ� false�� ����, ��� ����ϸ� true�� ����
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
	static boolean nextPermutation(char[] array){	//����. workshop4������ pdf���Ͽ� �ִ� nextPermutation�� int���� char�� ����.
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

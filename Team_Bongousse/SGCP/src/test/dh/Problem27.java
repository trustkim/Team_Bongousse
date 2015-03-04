package test.dh;

//by dh_ver1.0
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class Problem27 {
	
	
	public StringBuilder reCur(String str[], int num[], int count, int k){
		StringBuilder aaa = new StringBuilder();
		
		count++;
		
		
		
		
		
		return aaa;
	}	
	
	public static void main(String args[]){
		
		try{
			Scanner input = new Scanner(new File("input27.txt"));
			for(int T = input.nextInt(); T > 0; T--){
				int K = input.nextInt();
				
				String str[] = new String[12];
				for(int L = 0; L < 12; L++){ str[L] = input.next(); }				
				
				
				
				String aryStr[] = new String[5];
				int aryNum[] = new int[5];			
				
				for(int n = 0; n < 5; n++){
					StringBuilder charAry = new StringBuilder();				
					HashSet<Character> set = new HashSet<Character>();
					
					for(int l = 0; l < 12; l++){
						if(!set.add(str[l].charAt(n))){
							charAry.append(str[l].charAt(n));
						}
					}
					
					aryNum[n] = charAry.length();
					char col[] = new char[aryNum[n]];
					
					for(int m = 0; m < aryNum[n]; m++){ col[m] = charAry.charAt(m); }					
					Arrays.sort(col);
					StringBuilder destSt = new StringBuilder();
					for(int m = 0; m < aryNum[n]; m++){ destSt.append(col[m]); }
					
					aryStr[n] = destSt.toString();
				}
				
				int limit = 1;
				for(int i = 0; i < 5; i++){	limit *= aryNum[i]; }
				
				if(K > limit){System.out.println("NO");}
				else{
					
				}
				
			}			
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
		
		
		/*
		char ary[] = {'a', 'b', 'b', 'c'};
		HashSet<Character> set = new HashSet<Character>();
		for(int n = 0; n < 4; n++){
			if(!set.add(ary[n])){
				System.out.println(ary[n]);
			}
		}
		*/
		
	}
}

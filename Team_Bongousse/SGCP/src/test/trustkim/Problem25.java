package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem25 {
	public static String  str;
	public static int[] appeared = new int [26];
	
	public static void main(String args[]){		
		try{
			Scanner input = new Scanner(new File("input25.txt"));
			for(int T = input.nextInt(); T > 0; T--){
				for(int N = input.nextInt(); N > 0; N--){
					
				}
				System.out.println(input.nextLine());
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
	}
}
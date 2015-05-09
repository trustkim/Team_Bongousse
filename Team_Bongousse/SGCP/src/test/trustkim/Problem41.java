package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem41 {
	public static void main(String [] args) {
		try {
			Scanner sc = new Scanner(new File("input41.txt"));
			for(int T=sc.nextInt();T>0;T--) {
				
			}
			sc.close();
		}catch(FileNotFoundException e) {System.out.println("file not found...");}		
	}
}

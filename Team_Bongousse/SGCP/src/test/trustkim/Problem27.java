package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem27 {

	public static void main(String args[]){		
		try{
			Scanner input = new Scanner(new File("input27.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
	}
}
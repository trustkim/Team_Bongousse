package test.trustkim;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem26 {
	public static final int MAX = 1024;
	public static int[][] matH = new int [MAX][MAX];
	public static int N;
	
	public static void main(String args[]){		
		try{
			Scanner input = new Scanner(new File("input26.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				for(int i=0;i<MAX;i++){
					for(int j=0;j<MAX;j++) matH[i][j] = -1;
				}
				N=input.nextInt();
				build(N);
				Print(N);
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
	}
	
	public static void build(int N){
		if(N==1){ matH[0][0] = 1;
		} else{
			build(N/2);
			
		}
	}
	
	public static void Print(int N){
		for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){ System.out.print(matH[j][i]+" ");}
			System.out.print("\n");
		}
	}
}
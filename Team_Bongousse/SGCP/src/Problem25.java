import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Problem25 {
	public static String  str;
	public static int[] appeared = new int [26];
	public static int N;
	
	public static void main(String args[]){		
		try{
			Scanner input = new Scanner(new File("input25.txt"));
			for(int T = input.nextInt(); T > 0; T--){			
				N=input.nextInt(); input.nextLine();
				str = input.nextLine();			
				System.out.println(palindrome(N));
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
	}
	public static void appear(int start, int level){
		for(int i=0;i<26;i++){appeared[i]=0;}	// init appeared
		for(int i=0;i<level;i++){
			appeared[(int)str.charAt(start++)-'a']++;	// appeared[(int)temp-97];
		}
	}
	public static int palindrome(int level){
		for(int i=0;i<N;i++){
			if(promising(i, level))
				return level;
			if(i+level==N) break;
		}
		if(level>1) return palindrome(level-1);
		else return level;
	}
	public static boolean promising(int start, int level){
		appear(start, level); //Print(start, level); Print_App();
		if(level%2==0){
			for(int i=0;i<26;i++) if(appeared[i]%2!=0) return false;
		}else {
			int the_one_counter = 0;
			for(int i=0;i<26;i++) {
				if(appeared[i]%2!=0) the_one_counter++;
				if(the_one_counter>1) return false;
			}
		}
		return true;
	}
}
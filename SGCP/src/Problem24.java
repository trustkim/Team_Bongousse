import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashSet;
 
public class Problem24 {	
	public static final int MAX_KEY_SIZE = 10;
	public static void main(String args[]){		
		try{
			Scanner input = new Scanner(new File("input24.txt"));
			for(int T = input.nextInt(); T > 0; T--){
				int[] letters_in_a_line = new int[3];
				String[] lines = new String[3];						// array for str1, str2, str3.
				HashSet<Character> hs = new HashSet<Character>(10);	// hash set for counting arguments.
				boolean result = true;
				for(int i=0;i<3;i++){
					letters_in_a_line[i] = input.nextInt();		// read number of letters in each lines.
				}	// read letters at a line
				input.nextLine();
				for(int i=0;i<3;i++){
					lines[i] = input.nextLine().trim(); // read a line(str#).
					for(int j=0;j<letters_in_a_line[i];j++){
						char temp = lines[i].charAt(j);	// read a letter in a line
						if(!hs.contains(temp)){
							hs.add(temp);
							if(hs.size()>MAX_KEY_SIZE) {result = false; break;}	// ex. ABCDEFGHIJK
						}
					}
				}
				int key_size = hs.size(); char[] key_set = new char[MAX_KEY_SIZE];
				if(key_size<=MAX_KEY_SIZE){
					for(int i=0;i<MAX_KEY_SIZE;i++){
						key_set[i] = hs.iterator().next();
					}	// init key_set
				}
				char[] test_set = {'A'};
				System.out.println(trans_str_to_int("AAA",test_set));
				if(result) System.out.println("YES"); // print result
				else System.out.println("NO");
			}			
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}	
	}
	public static char toChar(int index){
		switch(index){
		case 0: return '0';
		case 1: return '1';
		case 2: return '2';
		case 3: return '3';
		case 4: return '4';
		case 5: return '5';
		case 6: return '6';
		case 7: return '7';
		case 8: return '8';
		case 9: return '9';
		default: System.exit(1); return '!';
		}
	}
	public static int trans_str_to_int(String line, char[] key_set){
		for(int i=0;i<key_set.length;i++){
			if(key_set[i]!='0')
				line.replace(key_set[i], toChar(i));
		}
		return Integer.parseInt(line);
	}
	public static void try_puzzle_rules(String[] lines, char[] key_set){
		// recursion
		// resemble key_set...
		// if(trans_str_to_int(str1, key_set)+trans_str_to_int(str2, key_set)==trans_str_to_int(str3, key_set)
	}
}
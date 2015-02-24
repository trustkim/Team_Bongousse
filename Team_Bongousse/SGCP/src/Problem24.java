import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
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
							if(hs.size()>MAX_KEY_SIZE) break;	// ex. ABCDEFGHIJK
						}
					}
				}

				int key_size = hs.size(); char[] key_set = new char[MAX_KEY_SIZE];
				if(key_size<=MAX_KEY_SIZE){
					Iterator<Character> iter=hs.iterator(); int index =0;
					while(iter.hasNext()){
						key_set[index++] = iter.next();
					}
					for(int i=key_size;i<MAX_KEY_SIZE;i++){
						key_set[i] = '0';
					}	// init key_set
					
					if(try_puzzle_rules(lines, key_set, key_size, 0)){
						System.out.println("YES"); // print result
					}else System.out.println("NO");
				}else System.out.println("NO");
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
	public static int trans_str_to_int(String line, char[] key_set, int key_size){
		for(int i=0;i<key_set.length;i++){
			if(i==key_size) break;
			if(key_set[i]!='0')
				line = line.replace(key_set[i], toChar(i));
		}
		return Integer.parseInt(line);
	}
	public static boolean try_puzzle_rules(String[] lines, char[] key_set, int key_size, int index){
//		// recursion
//		if(trans_str_to_int(lines[0], key_set, key_size)+trans_str_to_int(lines[1], key_set, key_size)==trans_str_to_int(lines[2], key_set, key_size)){
//			return true;
//		}else {
//			for(int i=index;i<MAX_KEY_SIZE;i++){
//				// resemble key_set...
//				return try_puzzle_rules(lines, key_set, key_size, i);
//			}
//		}
		return false;
	}
}
package test.trustkim;

import java.io.File;
import java.io.FileWriter;

public class GeneratorInput {
	public static void main(String [] args) {
		try{
			FileWriter fw = new FileWriter(new File("input32.txt"));
			fw.append("1000 0 1 1 998 999 0\n");
			for(int i=0;i<1000;i++) {
				for(int j=0;j<1000;j++)
					fw.append("0 ");
				fw.append("\n");
			}
			fw.append("1000 0 1 1 998 999 0\n");
			for(int i=0;i<1000;i++) {
				for(int j=0;j<1000;j++)
					fw.append("1 ");
				fw.append("\n");
			}
			fw.close();
			System.out.println("file write complete");
		} catch(Exception e) { e.printStackTrace();}
		
	}
}
package test.trustkim;

import java.io.File;
import java.io.FileWriter;

public class InputGenerator {
	public static void main(String [] args) {
		try{
			FileWriter fw = new FileWriter(new File("input34.txt"));
			fw.append("1000 1000\n");
			for(int i=0;i<1000;i++) {
				for(int j=0;j<1000;j++){
					if(i>0&&i<1000-1&&j>0&&j<1000-1) {
						fw.append("1 ");
					}else fw.append("0 ");
				}
				fw.append("\n");
			}
			fw.close();
			System.out.println("file write complete");
		} catch(Exception e) { e.printStackTrace();}
		
	}
}
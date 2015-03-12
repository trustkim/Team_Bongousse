package test.dh;
//by dh_ver1.0
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Problem28 {

	public static void reCur(int init[], int M[][], int moveNum){
		if(moveNum > 27){					//Ż������
			System.out.println("NO"); 
			return;	
		}

		int index[] = new int[moveNum];

		while(moveNum > 0){
			boolean goOut = false;
			boolean pass = false;
			int duplicate[] = new int[9];
			/*
			for(int i = 0; i < moveNum; i++){
				System.out.print(" ");
				System.out.print(index[i]);
			}System.out.println();
			*/
			for(int i = moveNum-1; i >= 0; i--){
				if(index[i] > 8){
					
					if(i == 0){ 			//moveNum�� ���� ����� ���� �� ������
						goOut = true; 
						break;
					}
					index[i] = 0;
					index[i-1] += 1;
				}
				duplicate[index[i]]++;
				if(duplicate[index[i]] == 4){
					pass = true;
					break;
				}
			}			
			if(goOut == true){ break; } 	//���̻��� ����� �� �����Ƿ� Ż��
			if(pass == false){
				int semi[] = new int[9];
				semi = init;

				for(int n = 0; n < moveNum; n++){		//��ư�� ����Ű�� �ð�� 90�� ȸ��
					for(int m = 0; m < M[index[n]].length; m++){
						semi[M[index[n]][m]] += 3;
						if(semi[M[index[n]][m]] > 12){semi[M[index[n]][m]] %= 12;}
					}
				}
				boolean alltwel = true;
				for(int l = 0; l < 9; l++){				//��� �ð谡 12�ð� �Ǿ��°�?
					if(semi[l] != 12){alltwel = false; break;}
					
				}
				if(alltwel == true){					//yes��� �� ��� �� ��ȯ
					System.out.println(moveNum);
					return;					
				}
			}
			index[moveNum-1]++;
		}
		
		reCur(init, M, moveNum+1);
		return;
	}


	public static void main(String args[]){
		try{
			Scanner input = new Scanner(new File("input28.txt"));
			for(int T = input.nextInt(); T > 0; T--){
				System.out.println("T == " + T);

				//���� 9�� �ð���� �ð� �б�
				int initTime[] = new int[9];
				for(int s = 0; s < 9; s++){ initTime[s] = input.nextInt(); }

				//��ưM �����ϱ�
				int M[][] = new int[9][];
				for(int l = 0; l < 9; l++){
					int head = input.nextInt();
					M[l] = new int[head];
					for(int c = 0; c < head; c++){ M[l][c] = input.nextInt()-1; }
				}
				
				System.out.println("initTime[] >>");
				for(int i = 0; i < 9; i++){System.out.print(" "+initTime[i]);}
				System.out.println();
				
				System.out.println("M[][] >>");
				for(int i = 0; i < 9; i++){
					for(int j = 0; j < M[i].length; j++){
						System.out.print(" " + M[i][j]);
					}
					System.out.println();
				}
				
				
				//reCur(initTime, M, 1);
			}
			input.close();
		}catch(FileNotFoundException e){System.out.println("file not found..");}
	}

}

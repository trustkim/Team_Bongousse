package test.bjay;

class Problem46{
	public static int n;
	private int [][] m;
	
	public static void main(String[] arg){
		Problem46 test = new Problem46();
		n = 21;
		test.mat();
		for(int i=1;i<=20;i++)
			System.out.println("n=" + i + " : " + test.m[i][i]);	
	}
	public void mat(){
		m = new int[n][n];
		for(int i=0;i<n;i++){
			for(int j=i;j<n;j++){
				if(i==0)
					m[i][j] = 1;
				else if(i==j)
					m[i][j] = m[i-1][j];
				else
					m[i][j] = m[i][j-1] + m[i-1][j];
			}
		}
	}
}
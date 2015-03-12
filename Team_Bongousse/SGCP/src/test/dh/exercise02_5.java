package test.dh;


public class exercise02_5 {

	public static boolean HorseCanGo(int [][] board, int N, int x, int y, int dx, int dy){
		
		if(x == dx && y == dy){ board[x][y] = -1; return true; }
		else if(x < 0 || x >= N || y < 0 || y >= N){ return false; }
		else if(board[x][y] != 0){ return false; }
		else{
			board[x][y] = -1; //지나온 길
			for(int p = -1; p <= 1; p += 2){
				if(x+p >0 && x+p < N-1 && board[x+p][y] <= 0){
					if(HorseCanGo(board, N, x+2*p, y+p, dx, dy)	|| HorseCanGo(board, N, x+2*p, y-p, dx, dy))
					{ return true; }
				}
				if(y + p > 0 && y + p < N-1 && board[x][y+p] <= 0){
					if(HorseCanGo(board, N, x+p, y+2*p, dx, dy)	|| HorseCanGo(board, N, x-p, y+2*p, dx, dy))
					{ return true; }
				}				
			}
			board[x][y] = -2; //지나와본 길 중에서 유망하지 않은 길
			return false;
		}
	}

	public static void main(String args[]){
		int ChessBoard[][] =
			{	{0, 0, 0, 1, 0, 0, 0, 1},
				{1, 0, 0, 0, 0, 0, 0, 0},
				{0, 0, 1, 1, 0, 1, 0, 0},
				{0, 0, 0, 0, 0, 0, 0, 0}, 
				{1, 0, 0, 0, 0, 0, 1, 0}, 
				{0, 1, 0, 0, 0, 0, 1, 0}, 
				{1, 0, 0, 0, 0, 0, 1, 0}, 
				{0, 1, 0, 0, 0, 0, 1, 0}	};

		System.out.println(HorseCanGo(ChessBoard, 8, 0,0,5,5));
	}
}

package ttt;

import java.util.Random;
class TicTacToe
{
	protected  static final int HUMAN        = 2;
	protected  static final int COMPUTER     = 1;
	public  static final int EMPTY        = 0;

	public  static final int HUMAN_WIN    = 0;
	public  static final int DRAW         = 1;
	public  static final int UNCLEAR      = 2;
	public  static final int COMPUTER_WIN = 3;

	protected  int [ ] [ ] board = new int[ 3 ][ 3 ];
    private Random random=new Random();  
	private int side= 1 + random.nextInt(2);
	private int position=UNCLEAR;
    private int value = 0;
    private int bestRow = 0;
    private int bestColumn = 0;
    private int depth = 0;
	private int bestdepth = 20;
	private char computerChar,humanChar;

	// Constructor
	public TicTacToe( )
	{
		clearBoard( );
		initSide();
	}
	
	private void initSide()
	{
	    if (this.side==COMPUTER)
		{
			computerChar='X'; humanChar='O';
		}
		else
		{
			computerChar='O'; humanChar='X';
		}
    }
    
    public void setComputerPlays()
    {
        this.side=COMPUTER;
        initSide();
    }
    
    public void setHumanPlays()
    {
        this.side=HUMAN;
        initSide();
    }

	public boolean computerPlays()
	{
	    return side==COMPUTER;
	}

	public int chooseMove()
	{
	    Best best=chooseMove(COMPUTER);
	    return best.row*3+best.column;
	    //return 0;
    }
    
    // Find optimal move || minimax
	private Best chooseMove( int side ) {
		depth++;
		int opp;                // The other side
		Best reply  ;  			// Opponent's best reply
		int simpleEval;         // Result of an immediate evaluation

		if(side == COMPUTER){
			opp = HUMAN;
		}else{
			opp = COMPUTER;
		}

        // move through every square
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board[0].length; y++){
				if(squareIsEmpty(x,y)){
					place(x, y, side);
					reply = chooseMove(opp);
					if((simpleEval = positionValue()) != UNCLEAR){

                        // checks if the next move is a the best move for the computer
						if(side == COMPUTER && reply.val >= simpleEval && reply.depth >= depth ){
							value = simpleEval;
							bestRow = x;
							bestColumn = y;
							bestdepth = depth;
						}

                        // checks if the next move is the best move for the human
						if(side == HUMAN && reply.val <= simpleEval && reply.depth >= depth ){
							value = simpleEval;
							bestRow = x;
							bestColumn = y;
							bestdepth = depth;
						}

                        // checks if the current situation will end in a draw if the game is played perfectly
						if( simpleEval == DRAW  && reply.depth >= depth ){
							value = simpleEval;
							bestRow = x;
							bestColumn = y;
							bestdepth = depth;
						}
					}
					place(x, y, EMPTY);
				}
			}
		}
		depth--;
		return new Best(value, bestRow, bestColumn , bestdepth);
	}

   
    //check if move ok
    public boolean moveOk(int move)
    {
 	    return ( move>=0 && move <=8 && board[move/3 ][ move%3 ] == EMPTY );
        //return true;
    }
    
    // play move
    public void playMove(int move)
    {
		board[move/3][ move%3] = this.side;
		if (side==COMPUTER)
			this.side=HUMAN;
		else
			this.side=COMPUTER;
	}


	// Simple supporting routines
	protected void clearBoard( )
	{
		for(int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[0].length; y++) {
				board[x][y] = EMPTY;
			}
		}
	}


	private boolean boardIsFull( )
	{
        Boolean boardfull = true;
        for(int x = 0; x < board.length * board[0].length; x++){
            if(board[x/board.length][x%board[0].length] ==  EMPTY){
                boardfull = false;
            }
        }
        return boardfull;
	}

	// Returns whether 'side' has won in this position
    protected boolean isAWin( int side ){
        int [][] win = {
                { 0, 1, 2 },
                { 3, 4, 5 },
                { 6, 7, 8 },
                { 0, 3, 6 },
                { 1, 4, 7 },
                { 2, 5, 8 },
                { 0, 4, 8 },
                { 2, 4, 6 },
        };

        for(int i = 0; i < win.length; i++) {
            if(coordinates(win[i][0]) == side && coordinates(win[i][1]) == side && coordinates(win[i][2]) == side) {
                return true;
            }
        }
        return false;
    }

    /*
     * Converts a integer to a [][] 2d array value
     * return the board[][]
     */
    public int coordinates(int veld){
        return board[veld/board.length][veld%board[0].length];
    }

	// Play a move, possibly clearing a square
	private void place( int row, int column, int piece )
	{
		board[ row ][ column ] = piece;
	}

	private boolean squareIsEmpty( int row, int column )
	{
        if(board[ row ][ column ] == EMPTY){
            return  true;
        }else{
            return false;
        }
	}

	// Compute static value of current position (win, draw, etc.)
	protected int positionValue( )
	{
        if(isAWin(HUMAN)) {
            return HUMAN_WIN;
        }else if(isAWin(COMPUTER)){
            return COMPUTER_WIN;
        }else if(boardIsFull()){
            return DRAW;
        }
        return UNCLEAR;
	}
	
	
	public String toString()
	{
		String output = "";
		for(int x = 0; x < board.length; x++){
			for(int y = 0; y < board[0].length; y++){
				if(board[x][y] == EMPTY){
					output += " |" ;
				}else if(board[x][y] == HUMAN){
					output += humanChar + "|" ;

				}else if(board[x][y] == COMPUTER){
					output += computerChar + "|" ;
				}
			}
			output += "\n------\n" ;
		}
		return output;
	}  
	
	public boolean gameOver()
	{
	    this.position=positionValue();
	    return this.position!=UNCLEAR;
    }
    
    public String winner()
    {
        if      (this.position==COMPUTER_WIN) return "computer";
        else if (this.position==HUMAN_WIN   ) return "human";
        else                                  return "nobody";
    }
    
	
	private class Best {
        int row;
        int column;
		int depth;
        int val;

        public Best(int v) {
            this(v, 0, 0);
        }

        public Best(int v, int r, int c) {
			val = v;
			row = r;
			column = c;
		}

		public Best(int v, int r, int c, int d) {
			val = v;
			row = r;
			column = c;
			depth = d;
		}
    }
}


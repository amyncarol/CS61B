/* implementation of board class */

public class Board{

	private boolean shouldBeEmpty;
	public Board(boolean shouldBeEmpty){
		this.shouldBeEmpty=shouldBeEmpty;
	}

	private static int N=8;
    private static Piece[][] pieces = new Piece[N][N];
    private static Board b = new Board(false);
    private static boolean[][] pieceSelect = new boolean[N][N];
    private static boolean isFiresTurn = true;
    private static boolean hasSelectedPiece = false;
    private static int[] selectedPieceCoodinates = new int[2];
    private static boolean hasSelectedSquare = false;
    private static boolean hasMoved = false; //cannot modify yet

    /** Draws an N x N board. Adapted from:
        http://introcs.cs.princeton.edu/java/15inout/CheckerBoard.java.html
     */

    private static void drawBoard(int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i + j) % 2 == 0) StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                else                  StdDrawPlus.setPenColor(StdDrawPlus.RED);
                StdDrawPlus.filledSquare(i + .5, j + .5, .5);
            }
        }
        if (b.shouldBeEmpty==false){
       		initializePieces(N);
        	drawPieces(N);
        }
    }

    private static void initializePieces(int N){
    	for (int i = 0; i < N; i++) {
    		for (int j=0; j < N; j++){
    			if ((i + j) % 2 == 0){
	    			switch (j){
	    				case 0: pieces[i][j]=new Piece(true, b, i, j, "pawn");
	    						break;
	    				case 1: pieces[i][j]=new Piece(true, b, i, j, "shield");
	    						break;
	    				case 2: pieces[i][j]=new Piece(true, b, i, j, "bomb");
	    						break;
	    				case 5: pieces[i][j]=new Piece(false, b, i, j, "bomb");
	    						break;
	    				case 6: pieces[i][j]=new Piece(false, b, i, j, "shield");
	    						break;
	    				case 7: pieces[i][j]=new Piece(false, b, i, j, "pawn");
	    						break;	
	    				default: pieces[i][j]=null;
	    						 break;
	    			}
	    		}
    		}
    	}
    }
    private static void drawPieces(int N){
		for (int i = 0; i < N; i++){
    		for (int j=0; j < N; j++){
    			Piece p = pieces[i][j];
				if (p!=null){
					if (p.isFire()){
						if (p.isBomb()){
							if (p.isKing()){
							StdDrawPlus.picture(p.x + .5, p.y + .5, "img/bomb-fire-crowned.png", 1, 1);
					    	} else {
					    	StdDrawPlus.picture(p.x + .5, p.y + .5, "img/bomb-fire.png", 1, 1);
					   		} 
						} else if (p.isShield()){
							if (p.isKing()){
							StdDrawPlus.picture(p.x + .5, p.y + .5, "img/shield-fire-crowned.png", 1, 1);
					    	} else {
					    	StdDrawPlus.picture(p.x + .5, p.y + .5, "img/shield-fire.png", 1, 1);
					   		} 
						} else {
							if (p.isKing()){
							StdDrawPlus.picture(p.x + .5, p.y + .5, "img/pawn-fire-crowned.png", 1, 1);
					    	} else {
					    	StdDrawPlus.picture(p.x + .5, p.y + .5, "img/pawn-fire.png", 1, 1);
					   		}
						}
					} else {
						if (p.isBomb()){
							if (p.isKing()){
							StdDrawPlus.picture(p.x + .5, p.y + .5, "img/bomb-water-crowned.png", 1, 1);
					    	} else {
					    	StdDrawPlus.picture(p.x + .5, p.y + .5, "img/bomb-water.png", 1, 1);
					   		} 
						} else if (p.isShield()){
							if (p.isKing()){
							StdDrawPlus.picture(p.x + .5, p.y + .5, "img/shield-water-crowned.png", 1, 1);
					    	} else {
					    	StdDrawPlus.picture(p.x + .5, p.y + .5, "img/shield-water.png", 1, 1);
					   		} 
						} else {
							if (p.isKing()){
							StdDrawPlus.picture(p.x + .5, p.y + .5, "img/pawn-water-crowned.png", 1, 1);
					    	} else {
					    	StdDrawPlus.picture(p.x + .5, p.y + .5, "img/pawn-water.png", 1, 1);
					   		}
						}
					}
				}
            }	
    	}
	}    
    

	public Piece pieceAt(int x, int y){
		if (x<0 || x>=N || y<0 || y>=N){
			return null;
		} else{
			return pieces[x][y];
		}
	}

/** place a piece at x,y, if reaches the other end, crown it
 * also can place null at x,y 
*/
	public void place(Piece p, int x, int y){
		if (x<0 || x>=N || y<0 || y>=N){
		} else {
			if (p!=null) {
				p.x=x;
				p.y=y;
				if (canCrown(p)) {
					crown(p);
				}
			}
			pieces[x][y]=p;
		}
	}
/** whether can crown
*/
	private boolean canCrown(Piece p){
		boolean shouldReachEnd = ((p.y==(N-1) && p.isFire()) || (p.y==0) && !p.isFire());
		return ((!p.isKing() && shouldReachEnd));
	}
/** crown a piece
*/
	private void crown(Piece p){
		if (p.type.equals("pawn")){
			p.type = "pawn-king";
		}
		if (p.type.equals("shield")){
			p.type = "shield-king";
		}
		if (p.type.equals("bomb")){
			p.type = "bomb-king";
		}
	}



	public boolean canSelect(int x, int y){
		if (pieces[x][y]!=null){
			return canSelectPiece(x,y);
		} else {
			return canSelectSquare(x,y);
		}
	}
	/** return true if can select piece
	*/
	private boolean canSelectPiece(int x, int y){
		return ( isMyTurn(x,y) && ((!hasSelectedPiece) || hasSelectedPiece && (!hasMoved)));
	}

	/** return true if can select empty square
	*/
	private boolean canSelectSquare(int xf, int yf){
		if (!hasSelectedPiece){
			return false;
		} else {
			int xi=selectedPieceCoodinates[0];
			int yi=selectedPieceCoodinates[1];
			if ((!hasMoved) && validMove(xi,yi,xf,yf)){
				return true;
			} else {
				return false;  //hasn't consider multiple capture yet!!!
			}
		}
	}

	/** test if it is a valid move
	 * 1\ pieces[xf][yf] should be null;
	 * 2\ get value for dx=xf-xi and dy=yf-yi;
	 * 3\ if isFire, dy<0 && not king--->false;
	 * 4\ if abs(dy==1) && abs(dx==1)--->true;
	 * 5\ if abs(dx,dy==2), xi+dx,yi+dy, not fire --->true; 
	*/ 
	private boolean validMove(int xi, int yi, int xf, int yf){
		int dx=xf-xi;
		int dy=yf-yi;
		Piece p = pieces[xi][yi];
		if (pieces[xf][yf]!=null){
			return false;
		} else if ( ((p.isFire() && dy<=0) || (!p.isFire() && dy>=0 )) && (!p.isKing()) ){
			return false;
		} else if ((Math.abs(dx)==1)&&(Math.abs(dy)==1)){
			return true;
		} else if ((Math.abs(dx)==2)&&(Math.abs(dy)==2)){
			Piece pMiddle = pieces[xi+dx/2][yi+dy/2];
			if (pMiddle.isFire()^p.isFire()){
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/* return true if it is the corresponding player’s turn 
	*/
	private boolean isMyTurn(int x, int y){
		return (!(pieces[x][y].isFire()^isFiresTurn));
	}

	public void select(int x, int y){
		if (canSelect(x,y)){
			pieceSelect[x][y]=true;
		}
		if (pieces[x][y]!=null){
			hasSelectedPiece = true;
			hasSelectedSquare = false;
			selectedPieceCoodinates[0]=x;
			selectedPieceCoodinates[1]=y;
		} else {
			hasSelectedSquare = true;
			hasSelectedPiece = false;
		}
	}

	/** return true if can End my turn 
	*/

	public boolean canEndTurn(){
		if (hasMoved){
			return true;
		} else {
			return false;
		}
	}

	/** to end my turn 
	 * 1\ exchange turn 
	 * 2\ reset hasMoved
	 * 3\ reset hasSelectedPiece and hasSelectedSquare
	 * 4\ reset selectedPieceCoodinates
	 * 5\ reset pieceSelect
	*/ 
	public void endTurn(){
		isFiresTurn = (!isFiresTurn);
		hasMoved = false;
		hasSelectedPiece = false;
		hasSelectedSquare = false;
		selectedPieceCoodinates = new int[2];
		pieceSelect = new boolean[N][N];
	}

	public static void main(String[] args) {
        StdDrawPlus.setXscale(0, N);
        StdDrawPlus.setYscale(0, N);
        drawBoard(N);
        
    }

}
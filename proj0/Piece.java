/* implementation of piece class */

public class Piece{

	public boolean isFire;
	public Board b;
	public int x,y;
	public String type;
	private int xOld;
	private int yOld;

/** Piece constructor */
	public Piece(boolean isFire, Board b, int x, int y, String type){
		this.isFire=isFire;
		this.b=b;
		this.x=x;
		this.y=y;
		this.type=type;
	}
/* if a piece is fire, return true */
	public boolean isFire(){
		return isFire;
	}

/* if a piece is fire, return 0 */
	public int side(){
		int i;
		if (this.isFire){
			i=0;
		} else{
			i=1;
		}
		return i;
	}
	
/* if a piece is king, return true */
	public boolean isKing(){
		return (type.equals("pawn-king")||type.equals("bomb-king")||type.equals("shield-king"));
	}

/* if a piece is shield, return true */
	public boolean isShield(){
		return (type.equals("shield")||type.equals("shield-king"));
	}

/* if a piece is bomb, return true */
	public boolean isBomb(){
		return (type.equals("bomb")||type.equals("bomb-king"));
	}


/** move the piece to x,y and capture any piece if applicable
* 1\ place piece at x,y ; remove the piece at original position on board
* 3\ change the captured piece to null, regular capture
* 4\ bomb capture
*/
	public void move(int x, int y){
		xOld = this.x;
		yOld = this.y;
		b.place(this, x, y);
		b.place(null, xOld, yOld); //should implement remove in move???

		if (Math.abs(x-xOld)==2){
			int xMid = (x+xOld)/2;
			int yMid = (y+yOld)/2;
			this.b.place(null, xMid, yMid);
			if (this.isBomb()){
				bombCapture(x, y);
			}
		}
	}

/** bomb capture
*/

	private void bombCapture(int x, int y){
		for (int i=-1; i<2; i++){
			for (int j=-1; j<2; j++){
				Piece p = this.b.pieceAt(x+i, y+j);
				if (p==null){}
				else if (!p.isShield()){
					this.b.place(null, x+i, y+j);
				}
			}
		}
	}

/** Returns whether or not this Piece has captured another piece this turn.
*/
	public boolean hasCaptured(){
		if(Math.abs(x-xOld)==2){
			return true;
		} else {
			return false;
		}
	}
 
/** Called at the end of each turn on the Piece that moved. 
 * Makes sure the piece's hasCaptured() value returns to false.
 */
public void doneCapturing(){
	xOld = x;
	yOld = y;
}








}


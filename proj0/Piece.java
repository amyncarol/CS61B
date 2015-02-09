/* implementation of piece class */

public class Piece{

	private boolean isFire;
	public Board b;
	public int x,y;
	private String type;

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
		return type.equals("king");
	}

/* if a piece is shield, return true */
	public boolean isShield(){
		return type.equals("shield");
	}

/* if a piece is bomb, return true */
	public boolean isBomb(){
		return type.equals("bomb");
	}
}
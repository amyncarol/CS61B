import static org.junit.Assert.*;
import org.junit.Test;

/** test the Board class*/

public class TestBoard{
	
/** test the constructer of board class*/

/** test pieceAt method */
@Test
public void TestPieceAt(){
	Board b = new Board(false); 
	Piece newPiece = new Piece(true, b, 1, 3, "pawn");
	b.place(newPiece, 5, 3);
	Piece p = b.pieceAt(5, 3);
	assertEquals(true,p.isFire());
	assertEquals(5,p.x);
	assertEquals(3,p.y);
	assertEquals(true, ( !p.isBomb() &&  !p.isShield() ) );

	p = b.pieceAt(0,8);
	assertEquals(null,p);
	p = b.pieceAt(0,1);
	assertEquals(null,p);
} 

/** test if canSelect() and select() can work
*/

/** fire pawn move forward 
*/
@Test
public void TestCanSelect(){
	Board b = new Board(false); 
	Piece newPiece = new Piece(true, b, 1, 3, "pawn");
	b.place(newPiece, 5, 3);
	assertEquals(true, b.canSelect(5, 3));
	b.select(5, 3);
	assertEquals(true, b.canSelect(4, 4));
	b.select(4, 4);	
}

/** select an empty square without first select a piece
*/
@Test
public void TestCanSelect2(){
	Board b = new Board(false); 
	Piece newPiece = new Piece(true, b, 1, 3, "pawn");
	b.place(newPiece, 5, 3);
	assertEquals(false, b.canSelect(4, 4));
	assertEquals(false, b.canSelect(3, 4));
}

/** fire king capture backwards
*/
@Test
public void TestCanSelect3(){
	Board b = new Board(false); 
	Piece newPiece = new Piece(false, b, 0, 0, "pawn");
	Piece newPiece2 = new Piece(true, b, 0, 0, "king");
	b.place(newPiece, 6, 2);
	b.place(newPiece2, 5, 3);
	assertEquals(true, b.canSelect(5, 3));
	b.select(5, 3);
	assertEquals(false, b.canSelect(6, 2));
	assertEquals(true, b.canSelect(7, 1));
}






 public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBoard.class);
    }
}
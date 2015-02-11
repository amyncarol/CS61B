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

	b.place(newPiece, 5, 7);
	p = b.pieceAt(5, 7);
	assertEquals("pawn-king", p.type);
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
	Piece newPiece2 = new Piece(true, b, 0, 0, "pawn-king");
	b.place(newPiece, 6, 2);
	b.place(newPiece2, 5, 3);
	assertEquals(true, b.canSelect(5, 3));
	b.select(5, 3);
	assertEquals(false, b.canSelect(6, 2));
	assertEquals(true, b.canSelect(7, 1));
}

/** test multiple capture
*/
@Test
public void TestCanSelect4(){
	Board b = new Board(false); 
	Piece newPiece = new Piece(false, b, 0, 0, "pawn");
	Piece newPiece2 = new Piece(true, b, 0, 0, "pawn-king");
	Piece newPiece3 = new Piece(true, b, 0, 0, "pawn-king");
	b.place(newPiece, 2, 4);
	b.place(newPiece2, 3, 3);
	b.place(newPiece3, 5, 1);
	assertEquals(true, b.canSelect(2, 4));
	b.select(2, 4);
	assertEquals(true, b.canSelect(4, 2));
	b.select(4, 2);
	Piece p = b.pieceAt(2, 4);
	p.move(4, 2);
	assertEquals(true, b.canSelect(6, 0));
	b.select(6, 0);
	p.move(6, 0);
	assertEquals("pawn-king", p.type);
	assertEquals(false, b.canSelect(7, 1));
}

/** test canEndTurn 
*/
@Test
public void TestCanEndTurn(){
	Board b = new Board(false); 
	assertEquals(false, b.canEndTurn());
}

/** test remove
*/
@Test
public void TestRemove(){
	Board b = new Board(false); 
	Piece newPiece = new Piece(false, b, 0, 0, "pawn");
	Piece newPiece2 = new Piece(true, b, 0, 0, "pawn-king");
	b.place(newPiece, 6, 2);
	b.place(newPiece2, 5, 3);
	Piece p = b.remove(6, 2);
	assertEquals("pawn", p.type);
	p = b.remove(6, 2);
	assertEquals(null, p);
	p = b.remove(1, 8);
	assertEquals(null, p);
}

/** test winner
 */
@Test
public void TestWinner(){
	Board b = new Board(false); 
	Piece newPiece = new Piece(false, b, 0, 0, "pawn");
	Piece newPiece2 = new Piece(true, b, 0, 0, "pawn-king");
	b.place(newPiece, 6, 2);
	b.place(newPiece2, 5, 3);
	assertEquals(null, b.winner());
	b.remove(6, 2);
	assertEquals("Fire", b.winner());
	b.remove(5, 3);
	assertEquals("No one", b.winner());
}



 public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestBoard.class);
    }
}
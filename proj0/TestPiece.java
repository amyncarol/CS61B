import static org.junit.Assert.*;
import org.junit.Test;

/** test the piece class*/

public class TestPiece{

/** test the constructer of piece class*/
@Test 
public void TestConstructor(){
	Board someboard = new Board(true); //empty board;
	Piece p = new Piece(true, someboard, 1, 3, "pawn");
	assertEquals(true, p.isFire());
	assertEquals(someboard, p.b);
	assertEquals(1, p.x);
	assertEquals(3, p.y);
	assertEquals(true, ( !p.isBomb() &&  !p.isShield() ) );
}

/* test isFire() method and side() method */
@Test
public void TestIsFire(){
	Board someboard = new Board(true); //empty board;
	Piece p = new Piece(true, someboard, 1, 3, "pawn");
	assertEquals(true, p.isFire());
	assertEquals(0, p.side());
	p = new Piece(false, someboard, 1, 3, "pawn");
	assertEquals(false, p.isFire());
	assertEquals(1, p.side());
}

/* test isKing isBomb isShield */
@Test
public void TestIsKingBombShield(){
	Board someboard = new Board(true); //empty board;
	Piece p = new Piece(true, someboard, 1, 3, "pawn");
	assertEquals(false, p.isKing());
	assertEquals(false, p.isBomb());
	assertEquals(false, p.isShield());
	p = new Piece(true, someboard, 1, 3, "shield");
	assertEquals(false, p.isKing());
	assertEquals(false, p.isBomb());
	assertEquals(true, p.isShield());
	p = new Piece(true, someboard, 1, 3, "bomb");
	assertEquals(false, p.isKing());
	assertEquals(true, p.isBomb());
	assertEquals(false, p.isShield());
}

/** test move method
*/
@Test
public void TestMove(){
	Board b = new Board(true);
	Piece p = new Piece(true, b, 0, 0, "pawn");
	b.place(p, 1, 3);
	p = new Piece(false, b, 0, 0, "king");
	b.place(p, 2, 4);

	Piece p1 = b.pieceAt(1, 3);
	p1.move(3, 5);
	assertEquals(null, b.pieceAt(2, 4));
	assertEquals(null, b.pieceAt(1, 3));
}

/** test bomb capture
*/
@Test
public void TestMove2(){
	Board b = new Board(true);
	Piece p = new Piece(false, b, 0, 0, "bomb");
	b.place(p, 3, 5);
	p = new Piece(true, b, 0, 0, "bomb");
	b.place(p, 4, 4);
	p = new Piece(true, b, 0, 0, "bomb");
	b.place(p, 6, 4);
	p = new Piece(true, b, 0, 0, "bomb");
	b.place(p, 6, 2);
	p = new Piece(true, b, 0, 0, "shield");
	b.place(p, 4, 2);

	Piece p1 = b.pieceAt(3, 5);
	p1.move(5, 3);
	assertEquals(null, b.pieceAt(5, 3));
	assertEquals(null, b.pieceAt(3, 5));
	assertEquals(null, b.pieceAt(4, 4));
	assertEquals(null, b.pieceAt(6, 4));
	assertEquals(null, b.pieceAt(6, 2));
	assertEquals("shield", b.pieceAt(4, 2).type);
}












 public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestPiece.class);
    }
}

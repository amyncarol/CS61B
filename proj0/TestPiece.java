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















 public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestPiece.class);
    }
}

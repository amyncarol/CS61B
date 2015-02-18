import static org.junit.Assert.*;
import org.junit.Test;

public class TestMaxPlanet{


	@Test
	public void TestmaxPlanet(){
		Planet[] planets = new Planet[4];
		planets[0] = new Planet(0.0, 0.0, 0.0, 0.0, 1.1, null, 3.2);
		planets[1] = new Planet(0.0, 0.0, 0.0, 0.0, 1.3, null, 1.0);
		planets[2] = new Planet(0.0, 0.0, 0.0, 0.0, 1.4, null, 1.3);
		planets[3] = new Planet(0.0, 0.0, 0.0, 0.0, 100.1, null, 0.9);	
		Planet p = MaxPlanet.maxPlanet(planets, new MassComparator());	
		assertEquals(100.1, p.getMass(), 10E-10);
		p = MaxPlanet.maxPlanet(planets, new RadiusComparator());
		assertEquals(3.2, p.getRadius(), 10E-10);
						
	}






public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestMaxPlanet.class);
    }
}
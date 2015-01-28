public class NBody{
	public static void main(String[] args){
	/*read in T and dt and the planet file
	*/
		double T=Double.parseDouble(args[0]);
		double dt=Double.parseDouble(args[1]);
		String filename=args[2];
		In in=new In(filename);
		int nPlanet=in.readInt();
		double radUniverse=in.readDouble();
		Planet[] planets = new Planet[nPlanet];
        for (int i=0 ; i<nPlanet ; i=i+1){
        	planets[i]=getPlanet(in);
        }
    /*draw the initial universe
    */   
        //StdAudio.play("audio/2001.mid");
     	StdDraw.setScale(0.0-radUniverse,radUniverse);
     	StdDraw.picture(0,0,"images/starfield.jpg");
     	for (int i=0 ; i<nPlanet ; i=i+1){
        	planets[i].draw();
        }

    /*update the universe
    */
    	for (double t=0; t<=T ;t=t+dt){
			StdDraw.picture(0,0,"images/starfield.jpg");
    		for (int i=0 ; i<nPlanet ; i=i+1){
        		planets[i].setNetForce(planets);
        	}
        	for (int i=0 ; i<nPlanet ; i=i+1){
        		planets[i].update(dt);
        		planets[i].draw();
        	}
        StdDraw.show(10);
    	}   

    	

    	StdOut.printf("%d\n", nPlanet);
		StdOut.printf("%.2e\n", radUniverse);
			for (int i = 0; i < nPlanet; i++) {
    			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                   planets[i].x,planets[i].y,planets[i].xVelocity,
                   planets[i].yVelocity,planets[i].mass,planets[i].img);
			}

	}

/* Note that getPlanet will NOT work on an In unless
 * the In has already had its number of planets and 
 * universe radius read.
 */
 	public static Planet getPlanet(In in){
		Planet planet=new Planet(0.0,0.0,0.0,0.0,0.0,"img");
		planet.x=in.readDouble();
		planet.y=in.readDouble();
		planet.xVelocity=in.readDouble();
		planet.yVelocity=in.readDouble();
		planet.mass=in.readDouble();
		planet.img=in.readString();
		return planet;
	}


}
public class Planet{
	private double radius;
	public double x,y;
	public double xVelocity, yVelocity;
	public double mass;
	public String img;
	public Planet(double startX, double startY, 
					double startXVelocity, double startYVelocity,
					double startMass, String startImg, double radius){
		x=startX;
		y=startY;
		xVelocity=startXVelocity;
		yVelocity=startYVelocity;
		mass=startMass;
		img=startImg;
		this.radius = radius;

	}

	public double getRadius(){
		return radius;
	}

	public double getMass() {
		return mass;
	}

    /*calculate the distance between this planet and another planetB.*/
	public double calcDistance(Planet planetB){
			double distance = (planetB.x-x)*(planetB.x-x)
								+(planetB.y-y)*(planetB.y-y);
			distance = Math.sqrt(distance);
			return distance;
	}
	/*calculate the gravitational force between this planet and another
	 * planetB. */
	public double calcPairwiseForce(Planet planetB){
		double g = 6.67e-11;
		double distance = calcDistance(planetB);
		double force = g*mass*planetB.mass/(distance*distance);
		return force;
	}
	/*calculate the pairwise force in x direction */
	public double calcPairwiseForceX(Planet planetB){
		double distance = calcDistance(planetB);
		double force = calcPairwiseForce(planetB);
		double forceX = force*(planetB.x-x)/distance;
		return forceX;
	}

	/* calculate the pairwise force in y direction */
	public double calcPairwiseForceY(Planet planetB){
		double distance = calcDistance(planetB);
		double force = calcPairwiseForce(planetB);
		double forceY = force*(planetB.y-y)/distance;
		return forceY;
	}

	/* void method: set the value for the total netforce of this planet */
	public double xNetForce;
	public double yNetForce;

	public void setNetForce(Planet[] planetList){
			xNetForce=0;
			yNetForce=0;
		for (int i=0; i<planetList.length; i=i+1) {
			if (planetList[i]!= this){
				this.xNetForce=this.xNetForce+calcPairwiseForceX(planetList[i]);
				this.yNetForce=this.yNetForce+calcPairwiseForceY(planetList[i]);			
			}
		}
	}
	/* draw the planet at its (x,y) position using its img */
	public void draw(){
		StdDraw.picture(this.x,this.y,"images/"+this.img);
	}

	/*update the position and velocity of the planet after time interval dt, 
	 *assumming the netForce has already been set*/
	public double xAccel;
	public double yAccel;

	public void update(double dt){
		xAccel = xNetForce/mass;
		yAccel = yNetForce/mass;
		xVelocity=xVelocity+dt*xAccel;
		yVelocity=yVelocity+dt*yAccel;
		x=x+dt*xVelocity;
		y=y+dt*yVelocity;
	}


}
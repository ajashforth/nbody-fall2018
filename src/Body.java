public class Body {
	private double myXPos;
	private double myYPos;
	private double myXVel;
	private double myYVel;
	private double myMass;
	private String myFileName;
	
	/**
	* Create a body from parameters
	* @param xp initial x position
	* @param yp initial y position
	* @param xv initial x velocity
	* @param yv initial y velocity
	* @param mass of object
	* @param filename of image for object animation
	*/
	public Body(double xp, double yp, double xv, double yv, double mass, String filename) {
		myXPos = xp;
		myYPos = yp;
		myXVel = xv;
		myYVel = yv;
		myMass = mass;
		myFileName = filename;
	}
	
	/**
	* Copy constructor: copy instance variables from one body to this body
	* @param b is the body used to initialize this body
	*/
	public Body(Body b) {
		myXPos = b.getX();
		myYPos = b.getY();
		myXVel = b.getXVel();
		myYVel = b.getYVel();
		myMass = b.getMass();
		myFileName = b.getName();
	}
	
	/**
	* Gets the X position of the designated body
	* @return the x position of the body
	*/
	public double getX() {
		return this.myXPos;
	}
	
	/**
	* Gets the Y position of the designated body
	* @return the y position of the body
	*/
	public double getY() {
		return this.myYPos;
	}
	
	/**
	* Gets the X velocity of the designated body
	* @return the x velocity of the body
	*/
	public double getXVel() {
		return this.myXVel;
	}
	
	/**
	* Gets the Y velocity of the designated body
	* @return the y velocity of the body
	*/
	public double getYVel() {
		return this.myYVel;
	}
	
	/**
	* Gets the mass of the designated body
	* @return the mass of the body
	*/
	public double getMass() {
		return this.myMass;
	}
	
	/**
	* Gets the filename for the image of the designated body
	* @return the string of the filename
	*/
	public String getName() {
		return this.myFileName;
	}
	
	/**
	*  Calculates the distance between 2 bodies
	* @param b the other body you want to find the distance from the first body
	* @return the distance between the 2 bodies
	*/
	public double calcDistance(Body b) {
		return Math.sqrt(Math.pow(this.getX() - b.getX(),2) + Math.pow(this.getY() - b.getY(),2));
	}
	
	/**
	* Calculates the force exerted from body p to another body this
	* @param p the body that force is being exerted on
	* @return the force in Nm^2/kg^2 exerted by body p onto another body this
	*/
	public double calcForceExertedBy(Body p) {
		double G = 6.67 * Math.pow(10, -11);
		double mass1 = this.getMass();
		double mass2 = p.getMass();
		double r = this.calcDistance(p);
		return (G * mass1 * mass2) / (Math.pow(r, 2));
	}
	
	/**
	* Calculates the force exerted in the x direction by body p on another body this
	* @param p the other body
	* @return the force exerted in the x direction
	*/
	public double calcForceExertedByX(Body p) {
		double F = this.calcForceExertedBy(p);
		double r = this.calcDistance(p);
		double dx = -(this.getX() - p.getX());
		return (F * dx) / r;
	}
	
	/**
	* Calculates the force exerted in the y direction by body p on another body this
	* @param p the other body
	* @return the force exerted in the y direction
	*/
	public double calcForceExertedByY(Body p) {
		double F = this.calcForceExertedBy(p);
		double r = this.calcDistance(p);
		double dy = -(this.getY() - p.getY());
		return (F * dy) / r;
	}
	
	/**
	* Calculates the net force exerted on this body by all other bodies onto this body in the x direction
	* @param all of the bodies
	* @return the total force exerted in the x direction on this body
	*/
	public double calcNetForceExertedByX(Body[] bodies) {
		double totalForce = 0;
		for(Body b : bodies) {
			if (! b.equals(this)) {
				totalForce = totalForce + this.calcForceExertedByX(b);
			}
		}
		return totalForce;
	}
	
	/**
	* Calculates the net force exerted on this body by all other bodies onto this body in the y direction
	* @param all of the bodies
	* @return the total force exerted in the y direction on this body
	*/
	public double calcNetForceExertedByY(Body[] bodies) {
		double totalForce = 0;
		for(Body b : bodies) {
			if (! b.equals(this)) {
				totalForce = totalForce + this.calcForceExertedByY(b);
			}
		}
		return totalForce;
	}
	
	/**
	* 
	* @param deltaT small time steps
	* @param xforce Net force exerted on this in the x direction
	* @param yforce net force exerted on this in the y direction
	*/
	public void update(double deltaT, double xforce, double yforce) {
		double ax = xforce / this.getMass();
		double ay = yforce / this.getMass();
		double nvx = this.getXVel() + (deltaT * ax);
		double nvy = this.getYVel() + (deltaT * ay);
		double nx = this.getX() + (deltaT * nvx);
		double ny = this.getY() + (deltaT * nvy);
		this.myXPos = nx;
		this.myYPos = ny;
		this.myXVel = nvx;
		this.myYVel = nvy;
	}
	
	/**
	* Draws where all the planets should be now
	*/
	public void draw() {
		StdDraw.picture(myXPos, myYPos, "images/"+myFileName);
	}
}

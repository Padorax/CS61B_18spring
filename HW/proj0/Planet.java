import edu.princeton.cs.introcs.StdDraw;

public class Planet {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	static final double G = 6.67e-11;
	public Planet(double xP, double yP, double xV, double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}
	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}
	public double calcDistance(Planet p0) {
		double dx = this.xxPos - p0.xxPos;//can remove 'this'
		double dy = yyPos - p0.yyPos;
		double dis = dx*dx + dy*dy;
		return  java.lang.Math.sqrt(dis);
	}
	public double calcForceExertedBy(Planet p0) {
		double rr = this.calcDistance(p0)*calcDistance(p0);//can remove 'this'
		double force = G*this.mass*p0.mass/rr;
		return force;
	}
	public double calcForceExertedByX(Planet p0) {
		double f = calcForceExertedBy(p0);
		double dx = p0.xxPos - xxPos;
		double fx = f*dx/calcDistance(p0);
		return fx; 
	}
	public double calcForceExertedByY(Planet p0) {
		double f = calcForceExertedBy(p0);
		double dy = p0.yyPos - yyPos;
		double fy = f*dy/calcDistance(p0);
		return fy;
	}
	public double calcNetForceExertedByX(Planet[] allpt) {
		int N = allpt.length;
		double f = 0;
		for (int i = 0; i < N; i += 1) {
			if (this.equals(allpt[i])) {continue;} 
			f += calcForceExertedByX(allpt[i]);
		}
		return f;
	}
	public double calcNetForceExertedByY(Planet[] allpt) {
	/**getting rid of verbosed for loop*/
		double f = 0;
		for (Planet p : allpt) {
			if (this.equals(p)) {continue;}
			f += calcForceExertedByY(p);
		}
		return f;
	}
	public void update(double dt, double fX, double fY) {
		double aX = fX/mass;
		double aY = fY/mass;
		xxVel += aX*dt;
		yyVel += aY*dt;
		xxPos += dt*xxVel;
		yyPos += dt*yyVel;
	}

	public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/"+imgFileName,2e10,2e10);
        StdDraw.show();
    }
}
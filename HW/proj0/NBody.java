import edu.princeton.cs.introcs.StdDraw;

public class NBody {
	public static double readRadius(String file) {
		In in = new In(file);
		int num = in.readInt();
		double radius = in.readDouble();
		return radius;
	}
	public static Planet[] readPlanets(String file) {
		In in = new In(file);
		int num = in.readInt();
		double radius = in.readDouble();
		Planet[] allplanet = new Planet[5];
		for (int i = 0; i < 5; i += 1){
			Planet p = new Planet(in.readDouble(),in.readDouble(),in.readDouble(),
				in.readDouble(),in.readDouble(),in.readString());
			allplanet[i] = p;
		}
		return allplanet;
	}

	public static void main(String[] args) {
	    /**
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double r = readRadius(filename);
		Planet[] planets = readPlanets(filename);
         */
	    double T = 157788000.0;
	    double dt = 25000.0;
	    String filename = "data/planets.txt";
        String background = "images/starfield.jpg";
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);
        /** dummy static universe
        StdDraw.setScale(-r, r);
        StdDraw.clear();
        StdDraw.picture(0, 0, background);
        StdDraw.show();
        for (Planet p : planets) {
            p.draw();
        }
         */
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        for (double t = 0; t <= T; t += dt) {
            double[] xForces = new double[5];
            double[] yForces = new double[5];
            for (int i = 0; i < 5; i += 1) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for (int i = 0; i < 5; i += 1) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            StdDraw.clear();
            StdDraw.picture(0, 0, background);
            for (Planet p : planets) {
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
        }
        //printing out the universe
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }

    }

}
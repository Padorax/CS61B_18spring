import java.util.List;
import java.util.ArrayList;

/**
 * This class provides a main method for experimenting with GraphDB construction.
 * You could also use MapServer, but this class lets you play around with
 * GraphDB in isolation from all the rest of the parts of this assignment.
 */
public class GraphDBLauncher {
//    private static final String OSM_DB_PATH = "../library-sp18/data/berkeley-2018.osm.xml";
    private static final String OSM_DB_PATH = "../library-sp18/data/berkeley-2018-small.osm.xml";

    public static void main(String[] args) {
        GraphDB g = new GraphDB(OSM_DB_PATH);

        Iterable<Long> verticesIterable = g.vertices();
//        System.out.println(g.vertices.size());
//        System.out.println(g.edges.size());

        /* Convert iterable to a list. */
        List<Long> vertices = new ArrayList<Long>();
        for (long v : verticesIterable) {
            vertices.add(v);
        }

        System.out.println("There are " + vertices.size() + " vertices in the graph.");

        System.out.println("The first 10 vertices are:");
        for (int i = 0; i < 10; i += 1) {
            if (i < vertices.size()) {
                System.out.println(vertices.get(i));
            }
        }

        long v = g.closest(-122.258207, 37.875352);
        System.out.print("The vertex number closest to -122.258207, 37.875352 is " + v + ", which");
        System.out.println(" has longitude, latitude of: " + g.lon(v) + ", " + g.lat(v));
//      test bearing
        System.out.print(g.bearing(Long.valueOf("4333613086"), Long.valueOf("4333613087")));
//        System.out.println("To get started, uncomment print statements in GraphBuildingHandler.");
    }
}
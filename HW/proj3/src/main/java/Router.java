import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {
        HashMap<Long, Double> priorities = new HashMap<>();
        HashMap<Long, Double> distance = new HashMap<>();
        HashMap<Long, Long> nodeParent = new HashMap<>();
        PriorityQueue<Long> fringe = new PriorityQueue<>((a, b) -> Double.compare(priorities.get(a),
                priorities.get(b)));
        HashSet<Long> visited = new HashSet<>();

        long s = g.closest(stlon, stlat);
        long t = g.closest(destlon, destlat);
//**************************************************
//        System.out.println("in Router s and t are" + s + ":" + t);
//**************************************************

        distance.put(s, 0.0);
        nodeParent.put(s, s);
        double priority = g.distance(s, t);
        priorities.put(s, priority);

        fringe.add(s);
        shortPathHelp(priorities, distance, nodeParent, visited, fringe, g, t);

        List<Long> shortPath = new ArrayList<>();
        long temp = t;
        while (temp != s) {
            if (nodeParent.get(temp) == null) {
                break;
            }
            shortPath.add(0, temp);
            temp = nodeParent.get(temp);
        }
        shortPath.add(0, temp);
//**************************************************
//        System.out.println("in Router shortPath is" + shortPath);
//**************************************************
        return shortPath;
    }

    private static void shortPathHelp(HashMap<Long, Double> priorities, HashMap<Long, Double>
            distance, HashMap<Long, Long> nodeParent, HashSet<Long> visited,
                                      PriorityQueue<Long> fringe, GraphDB g, Long t) {

        while (!fringe.isEmpty()) {
            long vId = fringe.poll();
            if (vId == t) {
                return;
            }
            if (!visited.contains(vId)) {
                visited.add(vId);
                for (long w : g.adjacent(vId)) {
                    if (w != nodeParent.get(vId)) {
                        double vToW = g.distance(vId, w); //ed(v, w)
                        double sToW = distance.get(vId) + vToW; //d(s, v) + ed(v, w)
                        if (!distance.containsKey(w) || distance.get(w) > sToW) {
                            distance.put(w, sToW);
                            nodeParent.put(w, vId);

                            double priority = (sToW + g.distance(w, t));

                            priorities.put(w, priority);
                            fringe.add(w);
                        }

                    }
                }
            }
//            d(s, v): best known distance from s to v
//            ed(v, w): euclidean distance from v to w
//            h(w): euclidean distance from w to goal

//            If d(s, v) + ed(v, w) is less than d(s, w) in best:
//            update best so that d(s, w) = d(s, v) + ed(v, w)
//            add w to the fringe with a priority equal to d(s, v) + ed(v, w) + h(w).
        }
    }


    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */

    public static List<NavigationDirection> routeDirections2(GraphDB g, List<Long> route) {
//        return Collections.emptyList();
        List<NavigationDirection> res = new LinkedList<>();
        long n1 = route.get(0), n2 = route.get(1);
        double bearing = g.bearing(n1, n2);
        String name = g.getEdge(n1, n2).getEdgeName();
        NavigationDirection direc = new NavigationDirection();
//        start on
        direc.direction = 0;
        direc.way = name;

        for (int i = 0; i < route.size() - 1; i++) {
            long v1 = route.get(i), v2 = route.get(i+1);
            double newbearing = g.bearing(v1, v2);
            double bearingdiff = newbearing - bearing;
            String newname = g.getEdge(v1, v2).getEdgeName();

            if (bearingdiff >= -15 && bearingdiff <= 15) {
//                straight
                if (newname == name) {
                    direc.distance += g.distance(v1, v2);
                } else {
//                straight enter new road
                    res.add(direc);
                    direc = new NavigationDirection();
                    direc.distance = g.distance(v1, v2);
                    if(newname != "") direc.way = newname;
                    direc.direction = 1;
                }

            } else {
//                finish current direction or road
                res.add(direc);
//                init new direction or road
                direc = new NavigationDirection();
                direc.distance += g.distance(v1, v2);
                if(newname != "") direc.way = newname;

                if (bearingdiff > 15 && bearingdiff <=30) {
//                slight left
                    direc.direction = 2;
                } else if (bearing >= -30 && bearingdiff <-15) {
                    direc.direction = 3;
                } else if (bearingdiff > 15 && bearingdiff <= 100) {
                    direc.direction = 5;
                } else if (bearingdiff >=-15 && bearingdiff < -100) {
                    direc.direction = 4;
                } else if (bearingdiff >100) {
                    direc.direction = 6;
                } else {
                    direc.direction = 7;
                }
            }
        }
//        the last part
        res.add(direc);

        return res;
    }

    //   should not change direction if on same road even if bearing changes
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
//        return Collections.emptyList();
        List<NavigationDirection> res = new LinkedList<>();
        if (route.size() <= 1) {
            NavigationDirection direc = new NavigationDirection();
            res.add(direc);
            return res;
        }

        long n1 = route.get(0), n2 = route.get(1);
        double bearing = g.bearing(n1, n2);
        String name = g.getEdge(n1, n2).getEdgeName();
        name = (name == null)? "unknown road": name;
        NavigationDirection direc = new NavigationDirection();
//        start
        direc.direction = 0;
        direc.way = name;

        for (int i = 0; i < route.size() - 1; i++) {
            long v1 = route.get(i), v2 = route.get(i+1);

            String newname = g.getEdge(v1, v2).getEdgeName();
            newname = (newname == null)? "unknown road": newname;
//            continue on current road
            if (newname.equals(direc.way)) {
                direc.distance += g.distance(v1, v2);
                bearing = g.bearing(v1, v2);
            } else {
                //                finish current direction or road
                res.add(direc);
                //                init new road
                direc = new NavigationDirection();
                direc.distance += g.distance(v1, v2);
                direc.way = newname;

                double newbearing = g.bearing(v1, v2);
                double bearingdiff = newbearing - bearing;
                if (bearingdiff < -180) bearingdiff += 360;
                if (bearingdiff > 180) bearingdiff -= 360;
                bearing = newbearing;

                if (bearingdiff >= -15 && bearingdiff <= 15) {
//                straight enter new road
                    direc.direction = 1;
                } else if (bearingdiff > 15 && bearingdiff <=30) {
//                slight left
                        direc.direction = 3;
                    } else if (bearingdiff >= -30 && bearingdiff <-15) {
                        direc.direction = 2;
                    } else if (bearingdiff > 15 && bearingdiff <= 100) {
                        direc.direction = 4;
                    } else if (bearingdiff >=-100 && bearingdiff < -15) {
                        direc.direction = 5;
                    } else if (bearingdiff >100) {
                        direc.direction = 7;
                    } else {
                        direc.direction = 6;
                    }
                }
            }
            //        the last part
            res.add(direc);
            return res;
        }






    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}

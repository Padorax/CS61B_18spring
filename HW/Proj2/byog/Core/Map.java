package byog.Core;
import byog.TileEngine.*;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

public class Map implements java.io.Serializable {
    private static final long serialVersionUID = 154934524234354L;
    static long seed;
    int width;
    int height;
    int nRoom;
    double mu;
    double sigma;
    Random randomGen;

    TETile[][] canvas;
    Position door;
    Player player;


    Map(int s, int width, int height) {
        seed = s;
        randomGen = new Random(seed);
        this.width = width;
        this.height = height;
        nRoom = (int) RandomUtils.gaussian(randomGen, 25, 5);
        mu = 5;
        sigma = 4;
    }

    void initCanvas(TERenderer ter, int offHead) {
        ter.initialize(width, height + offHead, 0, 2);
    }

    TETile[][] buildMap(int offHead) {
        TERenderer ter = new TERenderer();
        initCanvas(ter, offHead);

        //initialize
        canvas = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                canvas[x][y] = Tileset.NOTHING;
            }
        }

        // make rooms
        ArrayList<Room> roomsList = makeRooms(canvas, nRoom);

        // connect rooms
        connectRooms(canvas, roomsList);

        // build walls
        buildWall(canvas);

        //add door
        door = addDoor(canvas);

        player = addPlayer(canvas, 1);
        //ter.renderFrame(canvas);
        return canvas;

    }

    /** fill the rectangular space with specific TETile
     * p specify the lower left corner */
    void makeSpace(TETile[][] world, Position p, int w, int h, TETile t) {
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (world[p.x + i][p.y + j] == Tileset.NOTHING) {//why this check?
                    world[p.x + i][p.y + j] = t;
                }
            }
        }
    }

    /** connect every neighbor pair of rooms*/
    void connectRooms(TETile[][] world, ArrayList<Room> roomsList) {
        for (int i = 0; i < roomsList.size() - 1; i++) {
            Room ra = roomsList.get(i);
            Room rb = roomsList.get(i + 1);
            //a random position pa inside room ra
            Position pa = new Position(ra.p.x + randomGen.nextInt(ra.width),
                    ra.p.y + randomGen.nextInt(ra.height));
            Position pb = new Position(rb.p.x + randomGen.nextInt(rb.width),
                    rb.p.y + randomGen.nextInt(rb.height));
            connectPositions(world, pa, pb);
        }
    }

    /** connect two positions */
    void connectPositions(TETile[][] world, Position a, Position b) {
        if (a.x == b.x) {//vertical hallway
            makeSpace(world, new Position(a.x, Math.min(a.y, b.y)),
                    1, Math.abs(a.y - b.y) + 1, Tileset.HALLWAY);//same character with room
        } else if (a.y == b.y) {//horizontal hallway
            makeSpace(world, new Position(Math.min(a.x, b.x), a.y),
                    Math.abs(a.x - b.x) + 1, 1, Tileset.HALLWAY);
        } else {//L hallway
            Position dummy = new Position(a.x, b.y);
            connectPositions(world, a, dummy);
            connectPositions(world, b, dummy);
        }
    }

    /** build walls*/
    void buildWall(TETile[][] world) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (world[i][j] == Tileset.NOTHING && checkNeighbours(world, i, j, 1)) {
                    world[i][j] =  Tileset.WALL;
                }
            }
        }
    }

    /** check a given position is a valid position for walls or closed door
     * determined by the number of Tileset.FLOOR in all eight neighbors*/
    boolean checkNeighbours(TETile[][] world, int x, int y, int numFloors) {
        int checked = 0;
        int xLeft = Math.max(0, x - 1);
        int xRight = Math.min(x + 1, width - 1);
        int yUp = Math.min(y + 1, height - 1);
        int yLow = Math.max(0, y - 1);
        for (int i = xLeft; i <= xRight; i++) {
            for (int j = yLow; j <= yUp; j++) {
                if (world[i][j] == Tileset.FLOOR || world[i][j] == Tileset.HALLWAY) {
                    checked += 1;
                    if (checked == numFloors) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** check if new room overlaps with all current rooms
     * return true if overlap with any of current rooms
     * https://stackoverflow.com/questions/306316/determine-if-two-rectangles-overlap-each-other
     */
    boolean overlap(ArrayList<Room> rooms, Room ra) {
        for (Room rb : rooms) {
            if (ra.x1 < rb.x2 && ra.x2 > rb.x1 && ra.y1 > rb.y2 && ra.y2 + 1 < rb.y1) {
                return true;
            }
        }
        return false;
    }

    /** make rooms
     * fixed number, all non-ovelapped, rank from the left to right*/
    ArrayList<Room> makeRooms(TETile[][] world, int num) {
        int curNumRooms = 0;
        ArrayList<Room> roomsList = new ArrayList<>();
        while (curNumRooms < num) {
            int px = RandomUtils.uniform(randomGen, 2, width - 2);
            int py = RandomUtils.uniform(randomGen, 2, height - 2);
            int w = (int) Math.max(Math.min(RandomUtils.gaussian(randomGen, mu, sigma),
                    width - px -1), 2);
            int h = (int) Math.max(Math.min(RandomUtils.gaussian(randomGen, mu, sigma),
                    height - py -1), 2);
            Room r = new Room(curNumRooms, new Position(px, py), w, h);
            if(!overlap(roomsList, r)) {
                roomsList.add(r);
                makeSpace(world, new Position(px, py), w, h, Tileset.FLOOR);
                curNumRooms += 1;
            }
        }
        Collections.sort(roomsList);
        return roomsList;
    }

    /** Add a locked door*/
    Position addDoor(TETile[][] world) {
        boolean added = false;
        int startx = 0;
        int starty = 0;
        while (!added) {
            startx = (int) RandomUtils.gaussian(randomGen, width / 2, width / 5);
            starty = 1;
            while (world[startx][starty] != Tileset.WALL) {
                //note here why can we use ==?
                starty += 1;
            }
            if (checkNeighbours(world, startx, starty, 2)) {
                world[startx][starty] = Tileset.LOCKED_DOOR;
                added = true;
            }
        }
        return new Position(startx, starty);
    }

    Player addPlayer(TETile[][] world, int numPlayers) {
        int added = 0;
        int px = 0;
        int py = 0;
        while (added < numPlayers) {
            px = RandomUtils.uniform(randomGen, 2, width - 2);
            py = RandomUtils.uniform(randomGen, 2, height -2);
            if (world[px][py] == Tileset.FLOOR) {
                world[px][py] = Tileset.PLAYER;
                added += 1;
            }
        }
        return new Player(new Position(px, py));
    }

    public static void main(String[] args) {
        int s = 20180905;
        int width = 80;
        int height = 40;
        Random randomGen = new Random();
        int nRoom = RandomUtils.poisson(randomGen, 25);
        Map map = new Map(s, width, height);

        TERenderer ter = new TERenderer();
        ter.initialize(width, height);

        //initialize
        TETile[][] world = new TETile[width][height];
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        // make rooms
        ArrayList<Room> roomsList = map.makeRooms(world, nRoom);

        // connect rooms
        map.connectRooms(world, roomsList);

        // build walls
        map.buildWall(world);

        ter.renderFrame(world);
    }


}

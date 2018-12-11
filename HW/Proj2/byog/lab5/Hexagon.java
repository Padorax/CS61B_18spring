package byog.lab5;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import java.util.Random;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class Hexagon {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    private static final long SEED = 2873120;
    private static final Random RANDOM = new Random(SEED);

    //* Position class represents tile position
    public static class Position {
        int x;
        int y;

        public Position(int xpos, int ypos) {
            x = xpos;
            y = ypos;
        }
    }

    /**
     * draw a single hexagon
     */
    //helper method for addHexagon
    //return the lower and upper Y-bound at given x for the right triangular
    private static int[] rightHeight(Position p, int s, int x){
        int[] yrange = {p.y+x-p.x-s+1, 3*s+p.x+p.y-x-2};
        return yrange;
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        //Position[] allhexPos = new Position[4*s*s - 2*s];
        /** tricky to add to array by index: allhexPos
         for (Position hexpos : allhexPos) {
         world[hexpos.x][hexpos.y] = t;
         }
         */
        //middle rectangular area
        for (int x = p.x; x < p.x + s; x += 1) {
            for (int y = p.y; y < p.y + 2*s; y += 1) {
                world[x][y] = t;
            }
        }
        //right & right rectangular
        for (int x = p.x + s; x <= p.x + 2*s - 2; x += 1) {
            for (int y = rightHeight(p, s, x)[0]; y <= rightHeight(p, s, x)[1]; y += 1) {
                //right
                world[x][y] = t;
                //left
                world[2*p.x+s-1-x][y] = t;
            }
        }
    }

    private static Position topRightNeighbor (Position curPos, int s) {
        Position topRightPos = new Position(curPos.x+2*s-1, curPos.y+s);
        return topRightPos;
    }

    private static Position bottomRightNeighbor (Position curPos, int s) {
        Position topRightPos = new Position(curPos.x+2*s-1, curPos.y-s);
        return topRightPos;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.MOUNTAIN;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.TREE;
            case 3: return Tileset.SAND;
            case 4: return Tileset.GRASS;
            case 5: return Tileset.WATER;
            default: return Tileset.NOTHING;
        }
    }

    //draw a column of N hexes(each size s), each one with a random biome, bottom hex position is bp
    private static void drawRandomVerticalHexes (TETile[][] world, int N, int s, Position bp) {
        for (int i = 0; i < N; i += 1) {
            Position curPos = new Position(bp.x, bp.y + 2*i*s );
            addHexagon(world, curPos, s, randomTile());
        }
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);
        int hexSize = 3;

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        //add single Hexagon
        Position p =  new Position(8, 10);
        //addHexagon(world, p,3, Tileset.FLOWER);

        //check topRightNeighbor
        /**
        Position tr = bottomRightNeighbor(p, 3);
        world[tr.x][tr.y] = Tileset.TREE;
        */

        //check drawRandomVerticalHexes
        /**
        drawRandomVerticalHexes(world, 4, 3, p);
        */

        //A ugly way to draw our hex world
        drawRandomVerticalHexes(world, 3, hexSize, p);
        Position p2 = bottomRightNeighbor(p, hexSize);
        drawRandomVerticalHexes(world, 4, hexSize, p2);
        Position p3 = bottomRightNeighbor(p2, hexSize);
        drawRandomVerticalHexes(world, 5, hexSize, p3);
        Position p4 = topRightNeighbor(p3, hexSize);
        drawRandomVerticalHexes(world, 4, hexSize, p4);
        Position p5 = topRightNeighbor(p4, hexSize);
        drawRandomVerticalHexes(world, 3, hexSize, p5);

        // draws the world to the screen
        ter.renderFrame(world);
    }


}
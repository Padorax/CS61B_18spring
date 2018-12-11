package byog.Core;

import byog.TileEngine.*;
import org.junit.Test;
import java.util.Random;

public class Room implements Comparable<Room>{
    //package private
    int no;
    Position p;
    int x1;
    int x2;
    int y1;
    int y2;
    int width;
    int height;
    int dist;

    Room(int no, Position p, int width, int height) {
        this.no = no;
        this.p = p;
        this.width = width;
        this.height = height;
        x1 = p.x;
        x2 = p.x + width - 1;
        y1 = p.y;
        y2 = p.y + height -1;
    }

    @Override
    public int compareTo(Room uddaRoom) {
        return this.x2 - uddaRoom.x1;
    }


    /**
    private final static int roomSize = 10;//biggest room size
    private static final long SEED = 2873029;
    private static final Random RANDOM = new Random(SEED);
    //private static int[][] usedSpace = new int[80][30];//0:unused; 1:

    private int roomWidth;
    private int roomHeight;
    private Position roomPos;//bottomleft corner point of room floor
    private TETile[][] roomWorld;//world our room is in



    public Room(int width, int height, Position p, TETile[][] world) {
        roomWidth = width;
        roomHeight = height;
        roomPos = p;
        roomWorld = world;
    }

    //instantiate a valid random room
    public Room(TETile[][] world) {
        int randomWidth = RANDOM.nextInt(roomSize) + 1;//room area cannot be zero
        int randomHeight = RANDOM.nextInt(roomSize) + 1;
        // RANDOM.nextInt(max-min+1)+min
        // room cannot be out of world. xPos(1, w-2-rw), yPos(1, h-2-rh)
        int randomXPos = RANDOM.nextInt(world.length-randomWidth-2) + 1;
        int randomYPos = RANDOM.nextInt(world[0].length-randomHeight-2) + 1;
        roomWidth = randomWidth;
        roomHeight = randomHeight;
        roomPos = new Position(randomXPos, randomYPos);
        roomWorld = world;
    }

    public void addRoom() {
        //add Floor
        for (int i = roomPos.x; i < roomPos.x + roomWidth; i++) {
            for (int j = roomPos.y; j < roomPos.y + roomHeight; j++) {
                roomWorld[i][j] = Tileset.FLOOR;
            }
        }
        //add 4 Walls
        for (int i = roomPos.x - 1; i < roomPos.x + roomWidth + 1; i ++) {
            //horizontal
            roomWorld[i][roomPos.y - 1] = Tileset.WALL;
            roomWorld[i][roomPos.y + roomHeight] = Tileset.WALL;
        }
        for (int j = roomPos.y - 1; j < roomPos.y + roomHeight + 1; j ++) {
            //vertical
            roomWorld[roomPos.x + roomWidth][j] = Tileset.WALL;
            roomWorld[roomPos.x-1][j] = Tileset.WALL;
        }
    }

    public void addRoom2() {
        //add Floor
        for (int i = roomPos.x; i < roomPos.x + roomWidth; i++) {
            for (int j = roomPos.y; j < roomPos.y + roomHeight; j++) {
                roomWorld[i][j] = Tileset.FLOOR;
            }
        }
        //add 4 Walls
        for (int i = roomPos.x - 1; i < roomPos.x + roomWidth + 1; i ++) {
            //horizontal
            int hl = roomPos.y - 1;
            int hu = roomPos.y + roomHeight;
            //deal with overlapping
            if (roomWorld[i][hl].character() == '·') {
                roomWorld[i][hl] = Tileset.FLOOR;
            }
            else {
                roomWorld[i][hl] = Tileset.WALL;
            }
            if (roomWorld[i][hu].character() == '·') {
                roomWorld[i][hu] = Tileset.FLOOR;
            }
            else {
                roomWorld[i][hu] = Tileset.WALL;
            }
        }
        for (int j = roomPos.y - 1; j < roomPos.y + roomHeight + 1; j ++) {
            //vertical
            int vl = roomPos.x-1;
            int vu = roomPos.x + roomWidth;
            if (roomWorld[vl][j].character() == '·') {
                roomWorld[vl][j] = Tileset.FLOOR;
            }
            else {
                roomWorld[vl][j] = Tileset.WALL;
            }
            if (roomWorld[vu][j].character() == '·') {
                roomWorld[vu][j] = Tileset.FLOOR;
            }
            else {
                roomWorld[vu][j] = Tileset.WALL;
            }
        }
    }

    /**
    public static void overlap() {
        for (int i = 0; i < usedSpace.length; i++) {
            for (int j = 0; j < usedSpace[0].length; j++){
                usedSpace[i][j] = 0;
            }
        }
    }
     */

}

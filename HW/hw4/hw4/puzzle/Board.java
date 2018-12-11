package hw4.puzzle;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class Board implements  WorldState{
    private int[][] board;
    private int[][] goal;//default null as size is not given
    private int N;


    private int[][] getGoal() {
        //must instantiating first. default class variabel for goal is null;
        //or setting size at top
        goal = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                goal[i][j] = i*N + j + 1;
            }
        }
        goal[N-1][N-1] = 0;
        return goal;
    }
    private int[][] copyTiles(int[][] tiles) {
        int[][] copytiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                copytiles[i][j] = tiles[i][j];
            }
        }
        return copytiles;
    }
    /** Constructs a board from an N-by-N array of tiles where
     tiles[i][j] = tile at row i, column j*/
    public Board(int[][] tiles) {
        //Mutable board:if we modify tiles later outside, Board.board will not change??? --depending on how you change tiles!!
       // board = tiles;

        N = tiles.length;
        //immutable board
        //no need to initialize
        //board = new int[N][N];
        board = copyTiles(tiles);
        goal = getGoal();
    }

    public Board(int[][] tiles, int[][] goals) {
        N = tiles.length;
        //board = new int[N][N];
        board = copyTiles(tiles);
        goal = goals;
    }

    /** Returns value of tile at row i, column j (or 0 if blank)*/
    //getter method, cannot change board
    public int tileAt(int i, int j) {
        if (i < 0 || i > N-1 || j < 0 || j > N-1) {
            throw new java.lang.IndexOutOfBoundsException("Invalid element position!");
        }
        //return goal[i][j];//test getGoal
        return board[i][j];
    }

    /** Returns the board size N*/
    public int size() {
        return N;
    }

    private int[] nullPos() {
        //iterating to get space/0 position
        int[] p = new int[2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i,j) == 0) {
                    p[0] = i;
                    p[1] = j;
                    break;
                }
            }
        }
        return p;
    }
    private int[][] copy() {
        //cannot let nbrTile = board and modify nbr, have to copy by loop
        int[][] nbrTile = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                nbrTile[i][j] = tileAt(i, j);
            }
        }
        return nbrTile;
    }

    //(ox, oy) is the index of null in tile, change tile to its nbr according to dir
    //need to copy tile everytime before move
    private int[][] move(char dir, int ox, int oy) {
        int[][] tile = copy();
        switch (dir) {
            //swap null/0 with the element above it
            case 'w': {
                tile[ox][oy] = tile[ox - 1][oy];
                tile[ox - 1][oy] = 0;
                break;
            }//swap with below
            case 's': {
                tile[ox][oy] = tile[ox + 1][oy];
                tile[ox + 1][oy] = 0;
                break;
            }//with left
            case 'a': {
                tile[ox][oy] = tile[ox][oy - 1];
                tile[ox][oy - 1] = 0;
                break;
            }//with right
            case 'd': {
                tile[ox][oy] = tile[ox][oy + 1];
                tile[ox][oy + 1] = 0;
            }
        }
        return tile;
    }
    @Override
    /** Returns the neighbors of the current board*/
    public Iterable<WorldState> neighbors() {
        //brute force will be O(N^2!), so tedious
        Set<WorldState> neighbs = new HashSet<>();
        int[] oPos = nullPos();
        int ox = oPos[0];
        int oy = oPos[1];
        //int[][] nbrTile = new int[N - 1][N - 1];
        int[][] nbrTile;
        //0 in first row
        if (ox == 0) {
           nbrTile = move('s', ox, oy);
           neighbs.add(new Board(nbrTile, goal));//same goal for all nbr, no need to recalculate
            //left top
            if (oy == 0) {
                nbrTile = move('d', ox, oy);
                neighbs.add(new Board(nbrTile, goal));
            } else if (oy == N - 1) {
                //right top
                nbrTile = move('a', ox, oy);
                neighbs.add(new Board(nbrTile, goal));
            } else {
                nbrTile = move('d', ox, oy);
                neighbs.add(new Board(nbrTile, goal));
                nbrTile = move('a', ox, oy);
                neighbs.add(new Board(nbrTile, goal));
            }
        } else if (ox == N - 1) {//last row
            nbrTile = move('w', ox, oy);
            neighbs.add(new Board(nbrTile));
            //bottom left
            if (oy == 0) {
                nbrTile = move('d', ox, oy);
                neighbs.add(new Board(nbrTile));
            } else if (oy == N - 1) {
                //bottom right
                nbrTile = move('a', ox, oy);
                neighbs.add(new Board(nbrTile));
            } else {
                nbrTile = move('d', ox, oy);
                neighbs.add(new Board(nbrTile));
                nbrTile = move('a', ox, oy);
                neighbs.add(new Board(nbrTile));
            }
        } else if (oy == 0) {
            //left col except for left top and bottom top
            nbrTile = move('d', ox, oy);
            neighbs.add(new Board(nbrTile));
        } else if (oy == N - 1) {
            nbrTile = move('a', ox, oy);
            neighbs.add(new Board(nbrTile));
        } else {
            //inner
            nbrTile = move('w', ox, oy);
            neighbs.add(new Board(nbrTile));
            nbrTile = move('s', ox, oy);
            neighbs.add(new Board(nbrTile));
            nbrTile = move('a', ox, oy);
            neighbs.add(new Board(nbrTile));
            nbrTile = move('d', ox, oy);
            neighbs.add(new Board(nbrTile));
        }
        return neighbs;
    }

    /** Hamming estimates: number of tiles in the wrong position*/
    public int hamming() {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                //or use tileAt(i, j) to avoid change to board
                if (board[i][j] != 0 && board[i][j] != goal[i][j]) {
                    count += 1;
                }

            }
        }
        return count;
    }

    //helper function of manhattan: return the correct (i,j) for num(i.e, pos of num in goal)
    private int[] correctPos(int num) {
        if (num == 0) {
            throw new IllegalArgumentException();
            //pos[0] = N - 1;
            //pos[1] = N - 1;
        }
        int[] pos = new int[2];
        //include in one if
        pos[0] = (num % N == 0) ? num / N - 1 : num / N;
        pos[1] = (num % N == 0) ? N - 1 : num % N -1;
        return pos;
    }

    /** Manhattan estimate: sum of the vertical and horizontal distance from the tiles
     * to their goal positions*/
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int num = tileAt(i, j);
                if (num == 0 || num == goal[i][j]) {
                    continue;
                }
                int x = correctPos(num)[0];
                int y = correctPos(num)[1];
                dist += (Math.abs(x-i) + Math.abs(y-j));
            }
        }
        return dist;
    }

    @Override
    /** Estimated distance to goal. This method should simply return the results of
     * manhattan() when submitted to Gradescope.*/
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    /** Returns true if this board's tile values are the same
     position as y's*/
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || y.getClass()!= getClass()) {//getClass gets the dynamic/runtime class
            return false;
        }
        Board newboard = (Board) y;
        if (newboard.size() != N) return false;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i,j) != newboard.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    /** Returns the string representation of the board. Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    public static void main(String[] args) {
        //test mutable board
        int[][] tile = {{8,0,1}, {4, 3, 2}, {7, 6, 5}};
        Board b = new Board(tile);
        //System.out.println(b.toString());

        //change tile will affect board
        tile[0][0] = 2;
        //System.out.println(b.toString());

        //BE CAREFUL does not affect board, as we are actually change the reference of tile
        tile = new int[][]{{1,2,3},{4,7,0},{8,6,5}};
        //System.out.println(b.toString());

        /*
        System.out.println(b.correctPos(3)[0]);
        System.out.println(b.correctPos(3)[1]);
        */
        //System.out.print(b.toString());
        //System.out.println(b.hamming());
        //System.out.println(b.manhattan());
        //System.out.println(b.nullPos()[0]);
        /**
        for (WorldState nb : b.neighbors()) {
            //don't need to cast as toString override object method and thus will be replaced by
            //Board.toString()
            System.out.println(nb.toString());
        }*/

        //Board b2 = new Board(tile);
        //System.out.print(b2.equals(b));

    }
}

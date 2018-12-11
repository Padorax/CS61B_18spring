package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.junit.*;

import java.util.Random;

//solve backwash

public class Percolation {
    private boolean[][] grid;
    private int size;
    private int numOfOpen;
    public WeightedQuickUnionUF Union;

    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        grid = new boolean[N][N];
        size = N;
        numOfOpen = 0;
        /*
        Union = new WeightedQuickUnionUF(N*N);
        */
        //default boolean for instance variable is false
        /**
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }*/

        // add virtual union top and union bottom, N^2 is top, N^2+1 is bottom
        Union = new WeightedQuickUnionUF(N*N + 2);
        //connect top and bottom
        for (int i = 0; i < N; i++) {
            Union.union(i, N*N);
        }
        for (int j = 0; j < N; j++) {
            Union.union(xyTo1D(N - 1, j), N*N + 1);
        }
    }

    /** assign each square a number*/
    private int xyTo1D(int r, int c) {
        return size*r + c;
    }

    /** open the site(row, col) if it is not open already */
    public void open(int row, int col){
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            numOfOpen += 1;
        }
        int pos = xyTo1D(row, col);
        //search 4 directions for open squares to union
        int left = xyTo1D(row, col - 1);
        int right = xyTo1D(row, col + 1);
        int up = xyTo1D(row - 1, col);
        int down = xyTo1D(row + 1, col);
        //as this is quick union, we will not check if the surrounding neighbor is connected to pos
        if (col > 0) {
            if (isOpen(row, col - 1)) {
                Union.union(pos, left);
            }
        }
        if (col < size - 1) {
            if (isOpen(row, col + 1)) {
                Union.union(pos, right);
            }
        }
        if (row > 0) {
            if (isOpen(row - 1, col)) {
                Union.union(pos, up);
            }
        }
        if (row < size - 1) {
            if (isOpen(row + 1, col)) {
                Union.union(pos, down);
            }
        }
    }

    /** is the site(row, col) open? */
    public boolean isOpen(int row, int col) {
        return grid[row][col] == true;
    }

    /** is the site(row, col) full? */
    public boolean isFull(int row, int col) {
        //with virtual top layer, the surface should not be full unless open
        if (isOpen(row, col)) {
            return Union.connected(xyTo1D(row, col), size * size);
        }
        return false;
    }
    /**
    public boolean isFull(int row, int col) {
        int index = xyTo1D(row, col);
        //if connected to any grid in top layer
        for (int i = 0; i < size; i++) {
            if (Union.connected(i, index)) {
                return true;
            }
        }
        return false;
    }
     */

    /** number of open sites */
    public int numberOfOpenSites(){
        return numOfOpen;
    }


    /** does the system percolate? */
    //check if virtual top connects to virtual bottom
    public boolean percolates() {
        return Union.connected(size*size, size*size + 1);
    }
    /**
    //check every open top grid to see if it's connected to any open bottom grid
    public boolean percolates() {
        for (int i = 0; i < size; i++) {
            if (isOpen(0, i)) {
                for (int j = 0; j < size; j++) {
                    if (isOpen(size - 1, j)) {
                        int topIndex = xyTo1D(0, i);
                        int bottomIndex = xyTo1D(size - 1, j);
                        if (Union.connected(topIndex, bottomIndex)) {
                            return true;
                        };
                    }
                }
            }
        }
        return false;
    }
    */

    /** unit testing */
    public static void main(String[] args) {

        Percolation P = new Percolation(5);
        System.out.println(P.isFull(0,2));
        P.open(0, 2);
        System.out.println(P.isFull(0,2));
        P.open(1,0);
        System.out.print(P.isFull(1, 0));
        //System.out.println(P.percolates());
        /*
        P.open(3, 4);
        P.open(2,4);
        assert(P.Union.connected(14, 19));
        P.open(2,2);
        P.open(2, 3);
        P.open(0,2);
        P.open(1,2);
        assert(P.isFull(2,2));
        System.out.println(P.percolates());
        P.open(4,4);
        System.out.println(P.percolates());
        */

        /*
        Random rand = new Random();
        for (int i = 0; i < 1; i++) {

            Percolation P = new Percolation(5);
            int openNum = 0;
            while (!P.percolates()) {
                openNum += 1;
                int row = rand.nextInt(5);
                int col = rand.nextInt(5);
                P.open(row, col);
            }
            System.out.println(openNum);
        }
        */

    }



}

package hw2;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import java.util.Random;

public class PercolationStats {
    //private Percolation P;
    private int[] thresholdList;
    Random rand = new Random();
    //private varible, public getter/setter
    //If this variable is used frequently, make it a instance variable rather than
    //passing it around tediously among different methods
    private int T;
    private double mean;
    private double std;

    /** perform T independent experiments on an N-by-N grid */
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("invalid simulation");
        }
        this.T = T;
        thresholdList = new int[T];
        //start simulation
        for (int i = 0; i < T; i++) {

            Percolation P = pf.make(N);
            int openNum = 0;
            while (!P.percolates()) {
                //openNum += 1;
                int row = rand.nextInt(N);
                int col = rand.nextInt(N);
                if (!P.isOpen(row, col)) {
                    openNum += 1;
                    P.open(row, col);
                }
            }
            thresholdList[i] = openNum;
        }

        this.mean = mean();
        this.std = stddev();
    }

    /** sample mean of percolation threshold */
    public double mean() {
        int sum = 0;
        for (int i = 0; i < thresholdList.length; i++) {
            sum += thresholdList[i];
        }
        return sum / T;
    }

    /** sample standard deviation of percolation threshold */
    public double stddev() {
        int sum = 0;
        double avg = mean();
        for (int i = 0; i < T; i++) {
            sum += Math.pow(thresholdList[i]-avg, 2);
        }
        return sum / T - 1;
    }

    /** low endpoint of 95% confidence interval */
    public double confidenceLow() {
        return mean -  1.96*std/(Math.sqrt(T));
    }

    /** high endpoint of 95% confidence interval*/
    public double confidenceHigh() {
        return mean +  1.96*std/(Math.sqrt(T));
    }

    public static void main(String[] args) {
        Stopwatch timer = new Stopwatch();
        double sum = 0.0;

        PercolationStats ps = new PercolationStats(400, 100, new PercolationFactory());

        double time = timer.elapsedTime();
        StdOut.printf("%e (%.2f seconds)\n", sum, time);

        System.out.println(ps.mean);
        System.out.println(ps.std);


    }
}

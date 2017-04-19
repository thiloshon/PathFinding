import com.sun.org.apache.bcel.internal.generic.NEW;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

/*************************************************************************
 *  Author: Dr E Kapetanios
 *  Last update: 22-02-2017
 *
 *  Name: Thiloshon Nagarajah
 *  IIT ID: 2015298
 *  UoW ID: w1608489
 *************************************************************************/

public class PathFindingOnSquaredGrid {

    // test client
    public static void main(String[] args) {
        // boolean[][] open = StdArrayIO.readBoolean2D();
        int size = 10;

        // The following will generate a 10x10 squared grid with relatively few obstacles in it
        // The lower the second parameter, the more obstacles (black cells) are generated
        boolean[][] randomlyGenMatrix = random(size, 0.7);

        StdArrayIO.print(randomlyGenMatrix); // prints to the console
        show(randomlyGenMatrix, true); // prints as JFrame or whatever

        // TODO: Uncomment this
        //System.out.println("The system percolates: " + percolates(randomlyGenMatrix));

        // TODO: Uncomment this
        //System.out.println("The system percolates directly: " + percolatesDirect(randomlyGenMatrix));

        // Reading the coordinates for points A and B on the input squared grid.

        Scanner in = new Scanner(System.in);
        System.out.println("Enter i for A > ");
        int Ai = in.nextInt();

        System.out.println("Enter j for A > ");
        int Aj = in.nextInt();

        System.out.println("Enter i for B > ");
        int Bi = in.nextInt();

        System.out.println("Enter j for B > ");
        int Bj = in.nextInt();

        // System.out.println("Coordinates for A: [" + Ai + "," + Aj + "]");
        // System.out.println("Coordinates for B: [" + Bi + "," + Bj + "]");

        //System.out.println("Drawing Start and End Points....");

        //show(randomlyGenMatrix, true, Ai, Aj, Bi, Bj);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.circle(Aj, size - Ai - 1, .5);
        StdDraw.circle(Bj, size - Bi - 1, .5);

        //System.out.println("Points Drawn. Finding Path...");

        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Start the clock ticking in order to capture the time being spent on inputting the coordinates
        // You should position this command accordingly in order to perform the algorithmic analysis
        Stopwatch timerFlow = new Stopwatch();


        PathFind pathFindEuclidean = new PathFind();
        pathFindEuclidean.setEuclideanDistance();
        System.out.print("Euclidean Distance");
        ArrayList<Cell> pathEuclidean = pathFindEuclidean.find(randomlyGenMatrix, new Cell(Ai, Aj), new Cell(Bi, Bj));


        // THIS IS AN EXAMPLE ONLY ON HOW TO USE THE JAVA INTERNAL WATCH
        // Stop the clock ticking in order to capture the time being spent on inputting the coordinates
        // You should position this command accordingly in order to perform the algorithmic analysis
        StdOut.println("\nElapsed time = " + timerFlow.elapsedTime());
        StdOut.println("");
        PathFind pathFind2 = new PathFind();
        pathFind2.setManhattanDistance();
        System.out.println("Manhattan Distance");
        ArrayList<Cell> pathMH = pathFind2.find(randomlyGenMatrix, new Cell(Ai, Aj), new Cell(Bi, Bj));

        PathFind pathFind3 = new PathFind();
        pathFind3.setChebyshevDistance();
        System.out.print("\nChebyshev Distance");
        ArrayList<Cell> pathC = pathFind3.find(randomlyGenMatrix, new Cell(Ai, Aj), new Cell(Bi, Bj));

        StdDraw.setPenColor(Color.RED);
        StdDraw.setPenRadius(0.008);

        for (int x = 1; x < pathEuclidean.size(); x++) {
            StdDraw.line(pathEuclidean.get(x - 1).getY(), size - 1 - pathEuclidean.get(x - 1).getX(), pathEuclidean.get(x).getY(), size - 1 - pathEuclidean.get(x).getX());
        }

        StdDraw.setPenColor(Color.green);
        StdDraw.setPenRadius(0.002);

        for (Cell cell : pathC) {
            StdDraw.circle(cell.getY(), size - cell.getX() - 1, .4);
        }

        StdDraw.setPenColor(Color.MAGENTA);
        for (Cell cell : pathMH) {
            StdDraw.circle(cell.getY(), size - cell.getX() - 1, .5);
        }

    }

    // given an N-by-N matrix of open cells, return an N-by-N matrix
    // of cells reachable from the top
    public static boolean[][] flow(boolean[][] open) {
        int N = open.length;

        boolean[][] full = new boolean[N][N];
        for (int j = 0; j < N; j++) {
            flow(open, full, 0, j);
        }
        return full;
    }

    // determine set of open/blocked cells using depth first search
    public static void flow(boolean[][] open, boolean[][] full, int i, int j) {
        int N = open.length;

        // base cases
        if (i < 0 || i >= N) return;    // invalid row
        if (j < 0 || j >= N) return;    // invalid column
        if (!open[i][j]) return;        // not an open cell
        if (full[i][j]) return;         // already marked as open

        full[i][j] = true;

        flow(open, full, i + 1, j);   // down
        flow(open, full, i, j + 1);   // right
        flow(open, full, i, j - 1);   // left
        flow(open, full, i - 1, j);   // up
    }

    // does the system percolate?
    public static boolean percolates(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) return true;
        }

        return false;
    }

    // does the system percolate vertically in a direct way?
    public static boolean percolatesDirect(boolean[][] open) {
        int N = open.length;

        boolean[][] full = flow(open);
        int directPerc = 0;
        for (int j = 0; j < N; j++) {
            if (full[N - 1][j]) {
                // StdOut.println("Hello");
                directPerc = 1;
                int rowabove = N - 2;
                for (int i = rowabove; i >= 0; i--) {
                    if (full[i][j]) {
                        // StdOut.println("i: " + i + " j: " + j + " " + full[i][j]);
                        directPerc++;
                    } else break;
                }
            }
        }

        // StdOut.println("Direct Percolation is: " + directPerc);
        if (directPerc == N) return true;
        else return false;
    }

    // draw the N-by-N boolean matrix to standard draw
    public static void show(boolean[][] a, boolean which) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        ;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // draw the N-by-N boolean matrix to standard draw, including the points A (x1, y1) and B (x2,y2) to be marked by a circle
    public static void show(boolean[][] a, boolean which, int x1, int y1, int x2, int y2) {
        int N = a.length;
        StdDraw.setXscale(-1, N);
        ;
        StdDraw.setYscale(-1, N);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (a[i][j] == which)
                    if ((i == x1 && j == y1) || (i == x2 && j == y2)) {
                        StdDraw.circle(j, N - i - 1, .5);
                    } else StdDraw.square(j, N - i - 1, .5);
                else StdDraw.filledSquare(j, N - i - 1, .5);
    }

    // return a random N-by-N boolean matrix, where each entry is
    // true with probability p
    public static boolean[][] random(int N, double p) {
        boolean[][] a = new boolean[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = StdRandom.bernoulli(p);
        return a;
    }

}




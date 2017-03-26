import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by Thiloshon on 26-Mar-17.
 */
public class PathFind {

    private static PriorityQueue<Cell> open;
    private static boolean closed[][];
    private Cell[][] grid;
    private Cell start, end;

    private ArrayList<Cell> path = new ArrayList<>();

    private int horizontalVerticalCost;
    private int diagonalCost;

    ArrayList<Cell> find(boolean[][] matrix, Cell start, Cell end) {
        //System.out.println("\n\nTest Case #" + tCase);
        //Reset
        this.start = start;
        this.end = end;
        int size = matrix.length;

        setEuclideanDistance();
        //setChebyshevDistance();
        //setManhattanDistance();

        grid = new Cell[size][size];
        //System.out.println(grid.length);
        closed = new boolean[size][size];
        open = new PriorityQueue<>((Object o1, Object o2) -> {
            Cell c1 = (Cell) o1;
            Cell c2 = (Cell) o2;

            return c1.getFinalCost() < c2.getFinalCost() ? -1 :
                    c1.getFinalCost() > c2.getFinalCost() ? 1 : 0;
        });
        //Set start position
        //setStartCell(si, sj);  //Setting to 0,0 by default. Will be useful for the UI part

        //Set End Location
        //setEndCell(ei, ej);

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                grid[i][j] = new Cell(i, j);
                grid[i][j].setHeuristicCost(Math.abs(i - end.getX()) + Math.abs(j - end.getY()));
//                  System.out.print(grid[i][j].heuristicCost+" ");
            }
//              System.out.println();
        }
        grid[start.getX()][start.getY()].setFinalCost(0);

        /*for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                System.out.print(grid[i][j].getHeuristicCost()+" ");
            }
            System.out.println("");
        }*/

        setBlocks(matrix);

           /*
             Set blocked cells. Simply set the cell values to null
             for blocked cells.
           */
        /*for (int i = 0; i < blocked.length; ++i) {
            setBlocked(blocked[i][0], blocked[i][1]);
        }*/

        //Display initial map
        /*System.out.println("Grid: ");
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (i == start.getX() && j == start.getY()) System.out.print("SO  "); //Source
                else if (i == end.getX() && j == end.getY()) System.out.print("DE  ");  //Destination
                else if (grid[i][j] != null) System.out.printf("%-3d ", 0);
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();*/

        AStar();
        /*System.out.println("\nScores for cells: ");
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (grid[i][j] != null) System.out.printf("%-3d ", grid[i][j].getFinalCost());
                else System.out.print("BL  ");
            }
            System.out.println();
        }
        System.out.println();*/

        if (closed[end.getX()][end.getY()]) {
            //Trace back the path
            //System.out.println("Path: ");
            Cell current = grid[end.getX()][end.getY()];
            System.out.print(current);
            while (current.parent != null) {
                path.add(current.parent);
                //System.out.print(" -> " + current.parent);
                current = current.parent;
            }
            System.out.println();
        } else System.out.println("No possible path");

        return path;
    }

    private void AStar() {

        //add the start location to open list.
        try {
            open.add(grid[start.getX()][start.getY()]);
        } catch (NullPointerException e) {
            System.out.println("Start Point is Blocked");
        }

        Cell current;

        while (true) {
            current = open.poll();
            if (current == null) break;
            closed[current.getX()][current.getY()] = true;

            if (current.equals(grid[end.getX()][end.getY()])) {
                return;
            }

            Cell t;
            if (current.getX() - 1 >= 0) {
                t = grid[current.getX() - 1][current.getY()];
                updateCost(current, t, current.getFinalCost() + horizontalVerticalCost);

                if (current.getY() - 1 >= 0) {
                    t = grid[current.getX() - 1][current.getY() - 1];
                    updateCost(current, t, current.getFinalCost() + diagonalCost);
                }

                if (current.getY() + 1 < grid[0].length) {
                    t = grid[current.getX() - 1][current.getY() + 1];
                    updateCost(current, t, current.getFinalCost() + diagonalCost);
                }
            }

            if (current.getY() - 1 >= 0) {
                t = grid[current.getX()][current.getY() - 1];
                updateCost(current, t, current.getFinalCost() + horizontalVerticalCost);
            }

            if (current.getY() + 1 < grid[0].length) {
                t = grid[current.getX()][current.getY() + 1];
                updateCost(current, t, current.getFinalCost() + horizontalVerticalCost);
            }

            if (current.getX() + 1 < grid.length) {
                t = grid[current.getX() + 1][current.getY()];
                updateCost(current, t, current.getFinalCost() + horizontalVerticalCost);

                if (current.getY() - 1 >= 0) {
                    t = grid[current.getX() + 1][current.getY() - 1];
                    updateCost(current, t, current.getFinalCost() + diagonalCost);
                }

                if (current.getY() + 1 < grid[0].length) {
                    t = grid[current.getX() + 1][current.getY() + 1];
                    updateCost(current, t, current.getFinalCost() + diagonalCost);
                }
            }
        }
    }

    private void updateCost(Cell current, Cell t, int cost) {
        if (t == null || closed[t.getX()][t.getY()]) return;
        int t_final_cost = t.getHeuristicCost() + cost;

        boolean inOpen = open.contains(t);
        if (!inOpen || t_final_cost < t.getFinalCost()) {
            t.setFinalCost(t_final_cost);
            t.parent = current;
            if (!inOpen) open.add(t);
        }
    }

    private void setBlocks(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (!matrix[i][j]) {
                    grid[i][j] = null;
                }
            }
        }
    }

    private void setManhattanDistance() {
        horizontalVerticalCost = 10;
        diagonalCost = 20;
    }

    private void setEuclideanDistance() {
        horizontalVerticalCost = 10;
        diagonalCost = 14;
    }

    private void setChebyshevDistance() {
        horizontalVerticalCost = 10;
        diagonalCost = 10;
    }
}

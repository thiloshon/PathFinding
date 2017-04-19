/*************************************************************************
 *
 *  Last update: 26-Mar-17
 *
 *  Name: Thiloshon Nagarajah
 *  IIT ID: 2015298
 *  UoW ID: w1608489
 *************************************************************************/
public class Cell {
    private double heuristicCost = 0; //Heuristic cost
    private double finalCost = 0; //G+H
    private int x, y;
    Cell parent;
    double parentCost = 0;

    Cell(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public void setHeuristicCost(double heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public void setFinalCost(double finalCost) {
        this.finalCost = finalCost;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public double getHeuristicCost() {

        return heuristicCost;
    }

    public double getFinalCost() {
        return finalCost;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}

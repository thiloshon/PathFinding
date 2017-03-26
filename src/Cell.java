/**
 * Created by Thiloshon on 26-Mar-17.
 */
public class Cell {
    private int heuristicCost = 0; //Heuristic cost
    private int finalCost = 0; //G+H
    private int x, y;
    Cell parent;

    Cell(int x, int y) {

        this.x = x;
        this.y = y;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public void setFinalCost(int finalCost) {
        this.finalCost = finalCost;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public int getHeuristicCost() {

        return heuristicCost;
    }

    public int getFinalCost() {
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

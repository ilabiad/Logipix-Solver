import java.util.LinkedList;

public class Cell {
	
	int value = 0;
	int x,y;
	LinkedList<BrokenLine> listBrokenLines;
	boolean occupied = false;
	Cell connectedCell;
	
	public Cell() {
		
	}
	
	public Cell(int v, int x, int y) {
		this.x = x;
		this.y = y;
		value = v;
		if (value > 0) {
			listBrokenLines = new LinkedList<BrokenLine>();
			occupied = true;
		}
	}
	
	public void connectToCell(Cell c) {
		this.connectedCell = c;
		c.connectedCell = this;
	}
	
	public void disconnectFromCell(Cell c) {
		this.connectedCell = null;
		c.connectedCell = null;
	}
	
	public int distance(Cell c) {
		return Math.abs(x - c.x) + Math.abs(y - c.y);
	}
	
	public int distance(int x0, int y0) {
		return Math.abs(x - x0) + Math.abs(y - y0);
	}
}

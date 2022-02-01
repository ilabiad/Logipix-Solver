import java.util.ArrayList;

public class BrokenLine {
	
	Cell first, last;
	Cell[] line;
	
	
	public BrokenLine(Cell f, int length) {
		line = new Cell[length];
		first = f;
		line[0] = f;
	}
	
	public BrokenLine(Cell f, int length, Cell l) {
		line = new Cell[length];
		first = f;
		last = l;
		line[0] = f;
		line[line.length-1] = l;
	}
	
	public BrokenLine copyAndAddCell(Cell toAdd, int addIndex) {
		BrokenLine bl = new BrokenLine(first, line.length, last);
		for (int i=0; i < line.length; i++){
			bl.line[i] = line[i];
		}
		bl.line[addIndex] = toAdd;
		return bl;
	}
	
}

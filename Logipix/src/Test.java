
public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Logipix logipix = new Logipix();
		logipix.loadFromTxt("logipix1.txt");
		DisplayLogipix dl = new DisplayLogipix();
		dl.display(logipix.puzzle, logipix.puzzle[0].length * 30, logipix.puzzle.length * 30);
		
		logipix.solveBacktracking();

		//Cell cell = logipix.mapCellValues.get(logipix.intToConsider.get(3)).get(1);
		/*
		Cell cell = logipix.cellPuzzle[4][5];
		System.out.println("line= " + String.valueOf(cell.y) +", col= " + String.valueOf(cell.x));
		System.out.println(cell.value);
		logipix.addBrokenLines(cell);
		System.out.print("Size = ");
		System.out.println(cell.listBrokenLines.size());
		logipix.addBrokenLines(cell);
		System.out.print("Size = ");
		System.out.println(cell.listBrokenLines.size());
		
		
		for (BrokenLine bl : cell.listBrokenLines) {
			System.out.println("printing a broken line");
			for (Cell c : bl.line) {
				System.out.println(String.valueOf(c.y) +", " + String.valueOf(c.x));
			}
		}
		
		if (cell.listBrokenLines.size() > 0)
			dl.drawBrokenLine(cell.listBrokenLines.get(0));
		*/

		drawAllLines(logipix, dl);
		
		//cell = logipix.cellPuzzle[5][5];
		//logipix.addBrokenLines(cell);
		//System.out.println("line= " + String.valueOf(cell.y) +", col= " + String.valueOf(cell.x));
		//System.out.println("size= " + String.valueOf(cell.listBrokenLines.size()));
		//System.out.println(cell.occupied);
		//System.out.println(cell.value);
		
		//System.out.println(logipix.cellPuzzle[5][4].connectedCell != null);
		//System.out.println(logipix.mapCellValues.get(6).size());
		//testRecu(0, new boolean[] {false});
	}
	
	static void drawAllLines(Logipix l, DisplayLogipix dl) {
		for (int intToConsiderIndex : l.intToConsider) {
			for (Cell cell : l.mapCellValues.get(intToConsiderIndex)) {
				if (cell.listBrokenLines.size() > 0)
					dl.drawBrokenLine(cell.listBrokenLines.get(0));
			}
		}
	}
	
	static void testRecu(int index, boolean[] done) {
		if (done[0])
			return;
		
		if (index >= 5) {
			done[0] = true;
			return;
		}
		
		System.out.println(index);
		testRecu(index + 1, done);
		if (!done[0])
			System.out.println(index);
	}
	
}

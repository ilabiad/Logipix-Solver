import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;


public class Logipix {
	int[][] puzzle;
	Cell[][] cellPuzzle;
	int n_lines;
	int n_col;
	HashMap<Integer, ArrayList<Cell>> mapCellValues = new HashMap<Integer, ArrayList<Cell>>();
	ArrayList<Integer> intToConsider = new ArrayList<Integer>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void loadFromTxt(String path) {
		File file = new File(path);
		Scanner sc;
		try {
			sc = new Scanner(file);
			n_col = Integer.parseInt(sc.nextLine());
			n_lines = Integer.parseInt(sc.nextLine());
			puzzle = new int[n_lines][n_col];
			cellPuzzle = new Cell[n_lines][n_col];
			String[] line;
			int i = 0;
			while(i < n_lines) {
				line = sc.nextLine().split(" ");
				for(int j=0; j<line.length; j++) {
					puzzle[i][j] = Integer.parseInt(line[j]);
					cellPuzzle[i][j] = new Cell(puzzle[i][j], j, i);
					if( puzzle[i][j] > 0 ){
						if (mapCellValues.containsKey(puzzle[i][j])) {
							mapCellValues.get(puzzle[i][j]).add(cellPuzzle[i][j]);
						}
						else {
							mapCellValues.put(puzzle[i][j], new ArrayList<Cell>(List.of(cellPuzzle[i][j])));
							intToConsider.add(puzzle[i][j]);
						}
					}
						
				}
				i += 1;
			}
			intToConsider.sort(null);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void addBrokenLines(Cell c) {
		if (c.value == 1) {
			c.listBrokenLines.add(new BrokenLine(c, c.value, c));
			return;
		}
		//we look for possible cells to connect with
		ArrayList<Cell> possibleCandidates = new ArrayList<Cell>(); //can be calculated when loading the puzzle and then cached instead
		                                                            // of recalculating it (maybe)
		for(Cell cell : mapCellValues.get(c.value)) {
			if (c.distance(cell) <= c.value - 1 && cell != c) {
				possibleCandidates.add(cell);
			}
		}
		/*
		for(Cell cell : possibleCandidates) {
			System.out.println(String.valueOf(cell.x) +", " + String.valueOf(cell.y));
		}
		*/
		int startingIndex;
		for(Cell cell : possibleCandidates) {
			startingIndex = c.listBrokenLines.size();
			int stepsLeft = c.value -1;
			c.listBrokenLines.add(new BrokenLine(c, c.value, cell));
			Cell currentCell;
			int counter;
			BrokenLine currentBL;
			int size;
			while (stepsLeft > 1) {
				size = c.listBrokenLines.size();
				for (int i=startingIndex; i < size; i++) {
					currentBL = c.listBrokenLines.get(i);
					currentCell = currentBL.line[c.value -1-stepsLeft];
					counter = 0;
					if (currentCell.x + 1 < n_col && cell.distance(currentCell.x+1, currentCell.y) <= stepsLeft-1 
							&&  (!cellPuzzle[currentCell.y][currentCell.x+1].occupied || cellPuzzle[currentCell.y][currentCell.x+1] == cell)
							&& !Arrays.asList(currentBL.line).contains(cellPuzzle[currentCell.y][currentCell.x+1])) {
						currentBL.line[c.value -1-stepsLeft+1] = cellPuzzle[currentCell.y][currentCell.x+1];
						counter += 1;
					}
					if (currentCell.x - 1 >= 0 && cell.distance(currentCell.x-1, currentCell.y) <= stepsLeft-1
							&&  (!cellPuzzle[currentCell.y][currentCell.x-1].occupied || cellPuzzle[currentCell.y][currentCell.x-1] == cell)
							&& !Arrays.asList(currentBL.line).contains(cellPuzzle[currentCell.y][currentCell.x-1])) {
						if (counter > 0) {
							c.listBrokenLines.add(currentBL.copyAndAddCell(cellPuzzle[currentCell.y][currentCell.x-1], c.value -1-stepsLeft+1));
						}
						else {
							currentBL.line[c.value -1-stepsLeft+1] = cellPuzzle[currentCell.y][currentCell.x-1];
						}
						counter += 1;
					}
					if (currentCell.y + 1 < n_lines && cell.distance(currentCell.x, currentCell.y+1) <= stepsLeft-1
							&&  (!cellPuzzle[currentCell.y+1][currentCell.x].occupied || cellPuzzle[currentCell.y+1][currentCell.x] == cell)
							&& !Arrays.asList(currentBL.line).contains(cellPuzzle[currentCell.y+1][currentCell.x])) {
						if (counter > 0) {
							c.listBrokenLines.add(currentBL.copyAndAddCell(cellPuzzle[currentCell.y+1][currentCell.x], c.value -1-stepsLeft+1));
						}
						else {
							currentBL.line[c.value -1-stepsLeft+1] = cellPuzzle[currentCell.y+1][currentCell.x];
						}
						counter += 1;
					}
					if (currentCell.y - 1 >= 0 && cell.distance(currentCell.x, currentCell.y-1) <= stepsLeft-1
							&&  (!cellPuzzle[currentCell.y-1][currentCell.x].occupied || cellPuzzle[currentCell.y-1][currentCell.x] == cell)
							&& !Arrays.asList(currentBL.line).contains(cellPuzzle[currentCell.y-1][currentCell.x])) {
						if (counter > 0) {
							c.listBrokenLines.add(currentBL.copyAndAddCell(cellPuzzle[currentCell.y-1][currentCell.x], c.value -1-stepsLeft+1));
						}
						else {
							currentBL.line[c.value -1-stepsLeft+1] = cellPuzzle[currentCell.y-1][currentCell.x];
						}
						counter += 1;
					}
					if (counter == 0) {
						c.listBrokenLines.remove(i);
						i--;
						size--;
					}
				}
				stepsLeft += -1;
				/*
				for (BrokenLine bl : c.listBrokenLines) {
					System.out.println("printing a broken line with stepsLeft = " + String.valueOf(stepsLeft));
					for (Cell cellt : bl.line) {
						if (cellt != null)
							System.out.println(String.valueOf(cellt.x) +", " + String.valueOf(cellt.y));
					}
				}
				*/
			}
			
		}
	}
	
	public void solveBacktracking() {
		solveBackTrackingRecu(0, 0, new boolean[]{false});
	}
	
	private void solveBackTrackingRecu(int intToConsiderIndex, int clueIndex, boolean[] done) {
		if (done[0])
			return;
		
		if (intToConsiderIndex == intToConsider.size()) {
			done[0] = true;
			return;
		}
		
		Cell c = mapCellValues.get(intToConsider.get(intToConsiderIndex)).get(clueIndex);
		
		if (c.connectedCell != null) {
			if (clueIndex == mapCellValues.get(intToConsider.get(intToConsiderIndex)).size()-1) {
				System.out.println("Finished from " + String.valueOf(intToConsider.get(intToConsiderIndex)));
				solveBackTrackingRecu(intToConsiderIndex + 1, 0, done);
			}
			else
				solveBackTrackingRecu(intToConsiderIndex, clueIndex + 1, done);
		}
		else {
			addBrokenLines(c);
			for(int i=0; i < c.listBrokenLines.size(); i++) {
				for (Cell cell : c.listBrokenLines.get(i).line) {
					cell.occupied = true;
				}
				c.connectToCell(c.listBrokenLines.get(i).last);
				if (clueIndex == mapCellValues.get(intToConsider.get(intToConsiderIndex)).size()-1) {
					System.out.println("Finished from " + String.valueOf(intToConsider.get(intToConsiderIndex)));
					solveBackTrackingRecu(intToConsiderIndex + 1, 0, done);
				}
				else
					solveBackTrackingRecu(intToConsiderIndex, clueIndex + 1, done);
				
				if (done[0])
					return;
				
				for (Cell cell : c.listBrokenLines.get(i).line) {
					if (cell.value == 0)
						cell.occupied = false;
				}
				c.disconnectFromCell(c.listBrokenLines.get(i).last);
			}
			c.listBrokenLines.clear();
		}
	}
	
	
	public void solveBacktrackingNew() {
		
	}
	
	private void solveBacktrackingNewRec(Cell c) {
		/* this function runs backtracking by choosing the cell where to run it
		 * when arriving at a dead end it looks for the possibles cells causing the problem near it
		 * and "marks" them probably by adding a field in the Cell class 
		 * then it returns (the type of return is not final) and when arriving at an unmarked Cell simply return as well
		 * when arriving at a marked Cell don't return but rather take the next borkenLine and descend in the backtracking again
		 * obviously the unmarked targets will not recalculate brokenLines since they will be probably "away" this still need to be implemented
		 * upon arriving at the Cell that started this process simply call addBrokenLine to check if a possibility has emerged 
		 * if not the process repeats 
		 */
	}
}	
















